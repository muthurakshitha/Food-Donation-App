package com.example.projcopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView text;
    Animation top_anim;
    View first,second,third,fourth,fifth,sixth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        top_anim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        first=findViewById(R.id.first_line);
        second=findViewById(R.id.second_line);
        third=findViewById(R.id.third_line);
        fourth=findViewById(R.id.fourth_line);
        fifth=findViewById(R.id.fifth_line);
        sixth=findViewById(R.id.sixth_line);
        text=findViewById(R.id.textView);
        first.setAnimation(top_anim);
        second.setAnimation(top_anim);
        third.setAnimation(top_anim);
        fourth.setAnimation(top_anim);
        fifth.setAnimation(top_anim);
        sixth.setAnimation(top_anim);
        text.animate().translationY(-800).setDuration(2300).setStartDelay(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this , HomeActivity.class));
                finish();
            }
        }, 2500);
    }
}