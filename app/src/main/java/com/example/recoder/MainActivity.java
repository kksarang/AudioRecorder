package com.example.recoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button startRecord,stopRecord,startplaying,stopplaying;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String AudioSavePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startplaying = findViewById(R.id.startPlaying);
        stopplaying = findViewById(R.id.stopPlaying);

        startRecord = findViewById(R.id.startRecord);
        stopRecord = findViewById(R.id.stoprecord);


        startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissions() == true)
                {
                    AudioSavePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/"+"recordingAudio.mp3";

                    mediaRecorder = new MediaRecorder();

                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mediaRecorder.setOutputFile(AudioSavePath);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Toast.makeText(MainActivity.this, "Recording started", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1);
                }
            }
        });



        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaRecorder.stop();
                mediaRecorder.release();

                Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        });



        startplaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer=new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePath);
                    mediaPlayer.start();

                }
                catch (IOException e)

                {

                    e.printStackTrace();
                }

            }
        });


        stopplaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPlayer!=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    Toast.makeText(MainActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);

        int second =ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED&&
                second==PackageManager.PERMISSION_GRANTED;
    }


}