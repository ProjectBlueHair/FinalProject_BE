package com.bluehair.hanghaefinalproject.music.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.collaboRequest.repository.CollaboRequestRepository;
import com.bluehair.hanghaefinalproject.common.exception.InvalidAudioFileException;
import com.bluehair.hanghaefinalproject.common.exception.InvalidRequestException;
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
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;

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
                          Long collaboRequestId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException(MUSIC, SERVICE, POST_NOT_FOUND, "Post ID : " + postId));
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(()-> new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND, "CollaboReqeust Id : " + collaboRequestId));

        try {
            saveMusicListAtS3(multipartFileList, musicPartList, collaboRequest, post);
        }
        catch(UnsupportedAudioFileException | IOException | InvalidRequestException e) {
            throw new InvalidAudioFileException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE, collaboRequest, null, musicPartList.toString());
        }
    }

    private String uploadMusic(MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, s3FileName).toString()
                .replace("https://jeeklee-soundsample-bucket.s3.ap-northeast-2.amazonaws.com/", "https://d2bux1qnayee1u.cloudfront.net/");
    }

    private void cutAudioSamples(List<AudioSample> audioSampleList, Post post) throws IOException {
        double playingTime = 0;
        File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
        try {
            FileUtils.copyURLToFile(new URL(post.getMusicFile()), file);
            AudioSample postAudioSample = new AudioSample(file);
            playingTime = (double) postAudioSample.getFrameLength() / postAudioSample.getFormat().getFrameRate();
        } catch (Exception e) {
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
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(()->new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaboRequestId));
        Post post = collaboRequest.getPost();

        List<File> fileList = new ArrayList<>();
        for (Music music : collaboRequest.getMusicList()) {
            File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
            FileUtils.copyURLToFile(new URL(music.getMusicFile()), file);
            fileList.add(file);
        }

        if(post.getMusicFile() != null) {
            File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
            FileUtils.copyURLToFile(new URL(post.getMusicFile()), file);
            fileList.add(file);
        }

        List<AudioSample> audioSampleList = new ArrayList<>();
        for (File file : fileList) {
            audioSampleList.add(new AudioSample(file));
            file.delete();
        }

        AudioSample mixedSample = AudioEditor.audioMixer(audioSampleList);

        File mixedSampleInFile = mixedSample.audioSampleToFile();

        MultipartFile mixedSampleInMFile = MultipartFileConverter.convertFiletoMFile(mixedSampleInFile);

        String musicFile = uploadMusic(mixedSampleInMFile);

        if (post.getMusicFile()!=null){
            deleteS3(post.getMusicFile());
        }

        post.updateMusicFile(musicFile);

        mixedSampleInFile.delete();
    }

    @Transactional
    public void deleteMusicList(Long collaboRequestId) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(() -> new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaboRequestId));

        for (Music music : collaboRequest.getMusicList()) {
            deleteS3(music.getMusicFile());
        }

        musicRepository.deleteAllByCollaboRequest(collaboRequest);
    }

    private void deleteS3(String musicFile) {
        String source = URLDecoder.decode(musicFile.replace("https://d2bux1qnayee1u.cloudfront.net/", ""), StandardCharsets.UTF_8);
        amazonS3.deleteObject(bucket, source);
    }

    @Transactional
    public void updateMusic(Long collaboRequestId, List<MultipartFile> multipartFileList, List<String> musicPartList) {
        CollaboRequest collaboRequest = collaboRequestRepository.findById(collaboRequestId)
                .orElseThrow(()-> new NotFoundException(MUSIC, SERVICE, COLLABO_NOT_FOUND, "CollaboRequest ID : " + collaboRequestId));

        Post post = collaboRequest.getPost();

        try {
            saveMusicListAtS3(multipartFileList, musicPartList, collaboRequest, post);
        }
        catch(UnsupportedAudioFileException | IOException | InvalidRequestException e) {
            throw new InvalidAudioFileException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE, null, null, musicPartList.toString());
        }
    }

    private void saveMusicListAtS3(List<MultipartFile> multipartFileList, List<String> musicPartList, CollaboRequest collaboRequest, Post post) throws IOException, UnsupportedAudioFileException {
        MultipartFileConverter.generateTempPath();

        List<AudioSample> audioSampleList = convertMFileToAudioSample(multipartFileList);

        cutAudioSamples(audioSampleList, post);

        for (int i = 0; i < audioSampleList.size(); i++) {
            File tmpFile = audioSampleList.get(i).audioSampleToFile();
            String musicFile = uploadMusic(MultipartFileConverter.convertFiletoMFile(tmpFile));
            Music music = new Music(musicFile, musicPartList.get(i), collaboRequest);
            musicRepository.save(music);
            tmpFile.delete();
        }
    }
}
