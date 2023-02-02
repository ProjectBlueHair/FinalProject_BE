package com.bluehair.hanghaefinalproject.music.service;

import java.util.Arrays;
import java.util.List;

public class AudioEditor {
    public static void audioCutter(AudioSample audioSample, double playingTime){
        int arraySize =
                (int) (audioSample.getFormat().getFrameSize()
                                * playingTime
                                * audioSample.getFormat().getFrameRate()
                                * 4
                                / audioSample.getFormat().getSampleSizeInBits());

        float[] tmpLeft = Arrays.copyOf(audioSample.getLeft(), arraySize);
        float[] tmpRight = Arrays.copyOf(audioSample.getRight(), arraySize);

        audioSample.setLeft(tmpLeft);
        audioSample.setRight(tmpRight);
    }

    public static AudioSample audioMixer(List<AudioSample> audioSampleList) {
        int maxArraySize = 0;
        int index = 0;
        for (int i = 0; i < audioSampleList.size(); i++) {
            if(audioSampleList.get(i).getLeft().length > maxArraySize) {
                maxArraySize = audioSampleList.get(i).getLeft().length;
                index = i;
            }
        }

        AudioSample mixedAudioSample = new AudioSample(
                audioSampleList.get(index).getSampleRate(),
                audioSampleList.get(index).getFormat(),
                audioSampleList.get(index).getFrameLength(),
                new float[maxArraySize],
                new float[maxArraySize]);

        for (AudioSample audioSample : audioSampleList) {
            addFloatArray(mixedAudioSample, audioSample);
        }

        limiter(mixedAudioSample);

        return mixedAudioSample;
    }

    private static void addFloatArray(AudioSample sampleToBeAdded, AudioSample sampleToAdd){
        float[] tmpLeft = new float[sampleToBeAdded.getLeft().length];
        float[] tmpRight = new float[sampleToBeAdded.getRight().length];

        int diffrence = sampleToBeAdded.getLeft().length / sampleToAdd.getLeft().length;
        for (int i = 0; i < sampleToAdd.getLeft().length; i++) {
            for (int j = 0; j < diffrence; j++) {
                tmpLeft[diffrence*i + j] = sampleToAdd.getLeft()[i] + sampleToBeAdded.getLeft()[diffrence*i + j];
                tmpRight[diffrence*i + j] = sampleToAdd.getRight()[i] + sampleToBeAdded.getRight()[diffrence*i + j];
            }
        }
        sampleToBeAdded.setLeft(tmpLeft);
        sampleToBeAdded.setRight(tmpRight);
    }

    private static void limiter(AudioSample audioSample) {
        float[] left = new float[audioSample.getLeft().length];
        float[] right = new float[audioSample.getRight().length];

        float maximumLeft = audioSample.getLeft()[0];
        float maximumRight = audioSample.getRight()[0];

        for (float v : audioSample.getLeft()) {
            if (Math.abs(v) > maximumLeft) {
                maximumLeft = Math.abs(v);
            }
        }

        for (float v : audioSample.getRight()) {
            if (Math.abs(v) > maximumRight) {
                maximumRight = Math.abs(v);
            }
        }

        for (int i = 0; i < left.length; i++) {
            left[i] = audioSample.getLeft()[i]/maximumLeft;
            right[i] = audioSample.getRight()[i]/maximumRight;
        }

        audioSample.setLeft(left);
        audioSample.setRight(right);
    }
}
