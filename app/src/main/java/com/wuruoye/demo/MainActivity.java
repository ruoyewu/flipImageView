package com.wuruoye.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wuruoye.flipimageview.FlipImageView;
import com.wuruoye.flipimageview.OnFlipListener;

/**
 * @Created : wuruoye
 * @Date : 2018/5/26 22:47.
 * @Description :
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FlipImageView fiv;
    private boolean flip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fiv = findViewById(R.id.fiv);

        fiv.setOnClickListener(this);
        fiv.setOnFlipListener(new OnFlipListener() {
            @Override
            public void onFlipStart() {
                log("onFlipStart");
            }

            @Override
            public void onFlipEnd() {
                log("onFlipEnd");
            }

            @Override
            public void onSecondStart() {
                log("onSecondStart");
            }

            @Override
            public void onThirdStart() {
                log("onThirdStart");
            }
        });
    }

    @Override
    public void onClick(View view) {
        fiv.flip((flip = !flip));
    }

    private void log(String message) {
        Log.e(getClass().getName(), message);
    }
}
