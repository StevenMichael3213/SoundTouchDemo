package org.example;

import android.os.AsyncTask;

import java.io.File;

public class SoundTouch {
    // Native interface function that returns org.example.SoundTouch version string.
    // This invokes the native c++ routine defined in "soundtouch-jni.cpp".
    // Load the native library upon startup

    static {
        System.loadLibrary("soundTouch");
    }

    public native final static String getVersionString();

    private native final void setTempo(long handle, float tempo);

    private native final void setPitchSemiTones(long handle, float pitch);

    private native final void setSpeed(long handle, float speed);

    private native final int processFile(long handle, String inputFile, String outputFile);

    private native final static long newInstance();

    private native final static void setQuickSeek(long handle);

    private native final void deleteInstance(long handle);

    private native final double getProgress(long handle);

    private long handle;

    public SoundTouch() {
        handle = newInstance();
    }

    public void close() {
        deleteInstance(handle);
        handle = 0;
    }

    public int getProgress() {
        return (int) getProgress(handle);
    }

    public void doSoundTouch(String inputPath, String outPath, float pitchValue) {
        File file = new File(outPath);
        if (file.exists())
            file.delete();
        try {
            ProcessTask task = new ProcessTask();
            ProcessTask.Parameters parameters = task.new Parameters();
            parameters.inFileName = inputPath;
            parameters.outFileName = outPath;
            parameters.pitch = pitchValue;
            task.execute(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ProcessTask extends AsyncTask<ProcessTask.Parameters, Integer, Long> {
        final class Parameters {
            String inFileName;
            String outFileName;
            float pitch;
        }

        final long doSoundTouchProcessing(Parameters params) {
            setTempo(handle, 1.0f);
            setQuickSeek(handle);
            setPitchSemiTones(handle, params.pitch);
            processFile(handle, params.inFileName, params.outFileName);
            return 0L;
        }

        @Override
        protected Long doInBackground(Parameters... params) {
            return doSoundTouchProcessing(params[0]);
        }

    }
}
