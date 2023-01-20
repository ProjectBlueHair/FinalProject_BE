package com.bluehair.hanghaefinalproject.music.service;

import com.bluehair.hanghaefinalproject.common.exception.InvalidRequestException;

import com.bluehair.hanghaefinalproject.common.service.MultipartFileConverter;
import com.bluehair.hanghaefinalproject.common.service.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.*;
import java.io.*;

import static com.bluehair.hanghaefinalproject.common.exception.Domain.MUSIC;
import static com.bluehair.hanghaefinalproject.common.exception.Layer.SERVICE;
import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.INVALID_SOUNDSAMPLE;

@Getter
@Slf4j
@AllArgsConstructor
public class AudioSample {
    private float sampleRate = AudioSystem.NOT_SPECIFIED;
    private AudioFormat format = null;
    private long frameLength;
    @Setter
    private float[] right;
    @Setter
    private float[] left;

    public AudioSample(File file) throws UnsupportedAudioFileException, IOException, InvalidRequestException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        format = audioInputStream.getFormat();

        if(!file.getName().substring(file.getName().lastIndexOf(".")+1).equals("wav") ||
                !Validator.isValidAudioFormat(format)) {
            log.warn("filename: " + file.getName());
            log.warn("Channels: " + format.getChannels());
            log.warn("bitDepth: " + format.getSampleSizeInBits());
            throw new InvalidRequestException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE);
        }

        // Frame의 크기(Frame을 표현할 수 있는 범위) - byte
        int bytePerFrame = format.getFrameSize();
        // 초당 Frame의 갯수
        sampleRate = format.getSampleRate();
        // 전체 프레임의 숫자
        frameLength = audioInputStream.getFrameLength();
        // numBytes = 전체 프레임 수 * 프레임 당 byte
        long numBytes = frameLength * bytePerFrame;

        byte[] audioBytes = new byte[(int) numBytes];

        // AudioBytes에 오디오 인풋 저장. numbytes와 다를 경우 파일이 커서 못읽는 경우, 잘라내야함
        if(numBytes != audioInputStream.read(audioBytes)) {
            throw new InvalidRequestException(MUSIC, SERVICE, INVALID_SOUNDSAMPLE);
        }

        convertToFloat(audioBytes, format);

        audioInputStream.close();
        file.delete();
    }

    private void convertToFloat(byte[] raw, AudioFormat format) {
        int bytesPerSample = format.getSampleSizeInBits() / 8;

        left = new float[raw.length / 2 / bytesPerSample]; // /2 is for 2channels
        right = new float[raw.length / 2 / bytesPerSample];

        for (int i = 0, next = 0; i < raw.length; ++next) {
            byte left1, right1, left2 = 0, right2 = 0;
            left1 = raw[i++];
            if (bytesPerSample == 2)
                left2 = raw[i++];
            right1 = raw[i++];
            if (bytesPerSample == 2)
                right2 = raw[i++];
            if (bytesPerSample == 2 && !format.isBigEndian()) {
                byte temp = left1; // want left1 most significant, left2 least
                // significant
                left1 = left2;
                left2 = temp;
                temp = right1;
                right1 = right2;
                right2 = temp;
            }
            if (format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
                left[next] = signedByteToInt(left1, left2);
                right[next] = signedByteToInt(right1, right2);
            } else { // PCM_UNSIGNED
                left[next] = unsignedByteToInt(left1, left2);
                right[next] = unsignedByteToInt(right1, right2);
            }
        }
    }
    private float signedByteToInt(byte b1, byte b2) {
        int sample = (b1 << 8) | (b2 & 0xFF);
        return sample / 32768.f;
    }
    private float unsignedByteToInt(byte b1, byte b2) {
        int sample = (b1 & 0xff) << 8 | (b2 & 0xff);
        return sample / 65535.f * 2 - 1;
    }

    public File audioSampleToFile() throws IOException {
        File file = File.createTempFile("temp_", ".wav", new File(MultipartFileConverter.tmpPath));
        byte[] bytes = convertToBytes();
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        AudioInputStream stream = new AudioInputStream(byteStream, format, bytes.length / 2 / 2);

        try {
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            System.out.println("Unable to write to output file ");
        }
        stream.close();
        return file;
    }

    private byte[] convertToBytes() {
        byte[] audioBytes = new byte[left.length * 2 * 2];
        int bytePos = 0;
        int size = Math.min(left.length, right.length);
        for (int i = 0; i < size; ++i) {
            short leftVal = (short) (left[i] * 32767);
            short rightVal = (short) (right[i] * 32767);
            if (format.isBigEndian()) {
                audioBytes[bytePos++] = (byte) (leftVal >> 8); // msb
                audioBytes[bytePos++] = (byte) (leftVal & 0xFF); // lsb
                audioBytes[bytePos++] = (byte) (rightVal >> 8); // msb
                audioBytes[bytePos++] = (byte) (rightVal & 0xFF); // lsb
            } else { // littleEndian
                audioBytes[bytePos++] = (byte) (leftVal & 0xFF); // lsb
                audioBytes[bytePos++] = (byte) (leftVal >> 8); // msb
                audioBytes[bytePos++] = (byte) (rightVal & 0xFF); // lsb
                audioBytes[bytePos++] = (byte) (rightVal >> 8); // msb
            }
        }
        return audioBytes;
    }
}
