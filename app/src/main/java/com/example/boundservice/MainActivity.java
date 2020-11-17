package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_play, btn_stop;
    ServiceSong serviceSong;
    Boolean iServiceSong = false;
    Intent intent;
    Animation animation;
    ImageView img_Disc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        img_Disc = (ImageView) findViewById(R.id.img_disc);

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        intent = new Intent(this, ServiceSong.class);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceSong.PlayMusic();
                img_Disc.startAnimation(animation);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iServiceSong){
                    serviceSong.StopMusic();
                }
                img_Disc.clearAnimation();
            }
        });
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceSong.Song binder = (ServiceSong.Song) service;
            serviceSong = binder.getService();
            iServiceSong = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iServiceSong = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(iServiceSong){
            unbindService(serviceConnection);
            iServiceSong = false;
        }
    }
}