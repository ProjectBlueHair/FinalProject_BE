package com.bluehair.hanghaefinalproject.music.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.exception.NotFoundException;
import com.bluehair.hanghaefinalproject.common.service.MultipartFileConverter;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.music.repository.MusicRepository;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.bluehair.hanghaefinalproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.MUSIC;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.COLLABO_NOT_FOUND;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application-s3.properties")
public class MusicService {
    private final PostRepository postRepository;
    private final MusicRepository musicRepository;
    private final CollaboRequestRepository collaboRequestRepository;

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Transactional
    public void saveMusic(List<MultipartFile> multipartFileList, Long postId,
                          List<String> musicPartList,
                          CollaboRequest collaboRequest) throws IOException, UnsupportedAudioFileException {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException(MUSIC, SERVICE, POST_NOT_FOUND));

        saveMusicListAtS3(multipartFileList, musicPartList, collaboRequest, post);
    }

    private String uploadMusic(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    private void cutAudioSamples(List<AudioSample> audioSampleList, Post post) throws IOException {
        double playingTime = 0;
        File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
        try {
            // Post의 MusicFile 저장 및 PlaytingTime 가져오기
            FileUtils.copyURLToFile(new URL(post.getMusicFile()), file);
            AudioSample postAudioSample = new AudioSample(file);
            playingTime = (double) postAudioSample.getFrameLength() / postAudioSample.getFormat().getFrameRate();
        } catch (Exception e) {
            log.error("PostMusicFile 확인 불가능");
            // NullPointerException 발생한 경우, 주어진 MusicFile 중 가장 긴 녀석을 Playing Time으로 설정
            for (AudioSample audioSample : audioSampleList) {
                if ((double) audioSample.getFrameLength() / audioSample.getFormat().getFrameRate() > playingTime){
                    playingTime = (double) audioSample.getFrameLength() / audioSample.getFormat().getFrameRate();
                }
            }
        }
        file.delete();
        for (AudioSample audioSample : audioSampleList) {
            AudioEditor.audioCutter(audioSample, playingTime);
        }
    }

    private List<AudioSample> convertMFileToAudioSample(List<MultipartFile> multipartFileList) throws IOException, UnsupportedAudioFileException {
        List<AudioSample> audioSampleList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            File file = MultipartFileConverter.convertMFileToFile(multipartFile);
            audioSampleList.add(new AudioSample(file));
            file.delete();
        }
        return audioSampleList;
    }

    @Transactional
    public void mixMusic(Long collaboRequestId) throws IOException, UnsupportedAudioFileException {
        // 0-1. CollaboRequest 불러오기
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(()->new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND));
        // 0-2. Post 불러오기
        Post post = collaboRequest.getPost();

        // 1. MusicFile을 File로 변경
        List<File> fileList = new ArrayList<>();
        // 1-1. CollaboRequest의 Music List의 MusicFile
        for (Music music : collaboRequest.getMusicList()) {
            File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
            FileUtils.copyURLToFile(new URL(music.getMusicFile()), file);
            fileList.add(file);
        }
        // 1-2. Post의 MusicFile
        if(post.getMusicFile() != null) {
            File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
            FileUtils.copyURLToFile(new URL(post.getMusicFile()), file);
            fileList.add(file);
        }

        // 2. 파일을 AudioSample로 변경하고, File을 삭제
        List<AudioSample> audioSampleList = new ArrayList<>();
        for (File file : fileList) {
            audioSampleList.add(new AudioSample(file));
            file.delete();
        }

        // 3. 주어진 AudioSample들을 합성
        AudioSample mixedSample = AudioEditor.audioMixer(audioSampleList);
        // 4. 합성된 AudioSample을 파일로 변환
        File mixedSampleInFile = mixedSample.audioSampleToFile();
        // 5. 파일을 Multipart 파일로 변환
        MultipartFile mixedSampleInMFile = MultipartFileConverter.convertFiletoMFile(mixedSampleInFile);
        // 6. S3에 업로드
        String musicFile = uploadMusic(mixedSampleInMFile);
        // 7. Post의 MusicFile을 변경
        post.updateMusicFile(musicFile);
        // 8. 임시파일 삭제
        mixedSampleInFile.delete();
    }

    @Transactional
    public void deleteMusicList(Long collaboRequestId) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(() -> new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND));

        for (Music music : collaboRequest.getMusicList()) {
            deleteS3(music.getMusicFile());
        }

        musicRepository.deleteAllByCollaboRequest(collaboRequest);
    }

    private void deleteS3(String musicFile) {
        String source = URLDecoder.decode(musicFile.replace("https://jeeklee-soundsample-bucket.s3.ap-northeast-2.amazonaws.com/", ""), StandardCharsets.UTF_8);
        amazonS3.deleteObject(bucket, source);
    }

    @Transactional
    public void updateMusic(Long collaboRequestId, List<MultipartFile> multipartFileList, List<String> musicPartList) throws IOException, UnsupportedAudioFileException {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(()-> new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND));

        Post post = collaboRequest.getPost();

        saveMusicListAtS3(multipartFileList, musicPartList, collaboRequest, post);
    }

    private void saveMusicListAtS3(List<MultipartFile> multipartFileList, List<String> musicPartList, CollaboRequest collaboRequest, Post post) throws IOException, UnsupportedAudioFileException {
        // 1. 디렉토리 생성
        MultipartFileConverter.generateTempPath();

        // 2. MultipartFile을 AudioSample로 변환
        List<AudioSample> audioSampleList = convertMFileToAudioSample(multipartFileList);

        // 3. AudioSample들을 길이에 맞춰 자르기
        cutAudioSamples(audioSampleList, post);

        // 4. S3 업로드 및 Music Repository에 저장
        for (int i = 0; i < audioSampleList.size(); i++) {
            File tmpFile = audioSampleList.get(i).audioSampleToFile();
            String musicFile = uploadMusic(MultipartFileConverter.convertFiletoMFile(tmpFile));
            Music music = new Music(musicFile, musicPartList.get(i), collaboRequest);
            musicRepository.save(music);
            tmpFile.delete();
        }
    }
}
