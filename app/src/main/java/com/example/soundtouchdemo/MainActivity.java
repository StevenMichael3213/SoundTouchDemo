package com.example.soundtouchdemo;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.example.SoundTouch;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory() + File.separator + "test.wav";
                String outPath = Environment.getExternalStorageDirectory() + File.separator + "test1.wav";
                File inputFile = new File(path);
                File outPutFile = new File(outPath);
                if (outPutFile.exists())
                    outPutFile.delete();
                if (inputFile.exists()) {
                    SoundTouch soundTouch = new SoundTouch();
                    soundTouch.doSoundTouch(path, outPath, 5);
                }
            }
        });

    }
}
