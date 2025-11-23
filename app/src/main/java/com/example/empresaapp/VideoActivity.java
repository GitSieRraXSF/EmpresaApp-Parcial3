package com.example.empresaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {
    VideoView videoView;
    ImageButton btnPlayPause;
    SeekBar seekBar;
    TextView txtCurrentTime, txtTotalTime;
    boolean isPlaying = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        inicializarComponentes();
        configurarVideoView();
        configurarControles();
        Button button = findViewById(R.id.button21);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
    private void inicializarComponentes() {
        videoView = findViewById(R.id.videoView);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        seekBar = findViewById(R.id.seekBar);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        txtTotalTime = findViewById(R.id.txtTotalTime);
    }

    private void configurarVideoView() {
        String url = "https://www.youtube.com/shorts/96vA4rrblHU?feature=share";
        videoView.setVideoURI(Uri.parse(url));
        // Listeners del VideoView
        videoView.setOnPreparedListener(mp -> {
            // Video listo para reproducir
            int duration = mp.getDuration();
            seekBar.setMax(duration);
            txtTotalTime.setText(milisegundosATiempo(duration));
            // Iniciar actualización de seekbar
            handler.post(updateSeekBar);
        });
        videoView.setOnCompletionListener(mp -> {
            // Video terminado
            isPlaying = false;
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            seekBar.setProgress(0);
        });
    }
    private void configurarControles() {
        // Botón Play/Pause
        btnPlayPause.setOnClickListener(v -> togglePlayPause());
        // SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                    txtCurrentTime.setText(milisegundosATiempo(progress));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateSeekBar);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.post(updateSeekBar);
            }
        });
    }
    private void togglePlayPause() {
        if (isPlaying) {
            videoView.pause();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
        } else {
            videoView.start();
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        }
        isPlaying = !isPlaying;
    }
    // Runnable para actualizar la seekbar
    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (videoView.isPlaying()) {
                int currentPosition = videoView.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                txtCurrentTime.setText(milisegundosATiempo(currentPosition));
            }
            handler.postDelayed(this, 1000);
        }
    };
    private String milisegundosATiempo(int milisegundos) {
        int segundos = (milisegundos / 1000) % 60;
        int minutos = (milisegundos / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(updateSeekBar);
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            handler.post(updateSeekBar);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateSeekBar);
        videoView.stopPlayback();
    }
}