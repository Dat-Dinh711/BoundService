package com.example.boundservice;

        import android.app.Service;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Binder;
        import android.os.IBinder;
        import android.widget.Toast;

        import androidx.annotation.Nullable;

public class ServiceSong extends Service {
    MediaPlayer mediaPlayer;
    private IBinder iBinder = new Song();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnBind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public class Song extends Binder {
        public ServiceSong getService(){
            return ServiceSong.this;
        }
    }

    public void PlayMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.songgio);
        mediaPlayer.start();
    }

    public void StopMusic() {
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
