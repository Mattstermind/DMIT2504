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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgVideoView = (VideoView) findViewById(R.id.backgroundVideoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.background);

        bgVideoView.setVideoURI(uri);
        bgVideoView.start();

        bgVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
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
                break;
            }
            case R.id.forgotPasswordLink:
            {
                Intent intent = new Intent(this, ForgotPasswordStepOne.class);
                startActivity(intent);
                break;
            }
        }
    }


}
