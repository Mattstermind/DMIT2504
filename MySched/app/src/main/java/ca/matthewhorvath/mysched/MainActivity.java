package ca.matthewhorvath.mysched;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView bgVideoView;

    @Override
    protected void onResume() {
        super.onResume();
        backgroundVideo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundVideo();



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginButton:
            {
                /* Validation must be completed
                * Once Validation is complete we must check the users Access level to ensure
                * we load the right Activity
                * */
                Intent intent = new Intent(this, TheSchedule.class);
                startActivity(intent);
                break;
            }
            case R.id.forgotPasswordLink:
            {
                Intent intent = new Intent(this, forgot_password_step_one.class);
                startActivity(intent);
                break;
            }
        }
    }

    public void backgroundVideo()
    {
        bgVideoView = (VideoView) findViewById(R.id.backgroundVideoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background);

        bgVideoView.setVideoURI(uri);
        bgVideoView.start();

        bgVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }


}
