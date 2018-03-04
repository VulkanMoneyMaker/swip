package wlmart.onl.gepapptt;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class GameActivity extends Activity {

    private ImageView imageRoulette;
    private Button btnPlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rule);
        imageRoulette = findViewById(R.id.img_rule);
        btnPlay = findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(__->actionRoulette(imageRoulette));

    }

    public void actionRoulette(View view) {
        int corner = 360/12; // corner for point
        int randPosition = corner * new Random().nextInt(12); // random point
        int MIN = 5; // min rotation
        int MAX = 9; // max rotation
        long TIME_IN_WHEEL = 1000;  // time in one rotation
        int randRotation = MIN + new Random().nextInt(MAX-MIN); // random rotation
        int truePosition =  randRotation * 360 + randPosition;
        long totalTime = TIME_IN_WHEEL * randRotation + (TIME_IN_WHEEL/360) * randPosition;

        Log.d("ROULETTE_ACTION","randPosition : " + randPosition
                + " randRotation : " + randRotation
                + " totalTime : " + totalTime
                + " truePosition : " + truePosition);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0f,(float)truePosition);
        animator.setDuration(totalTime);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                btnPlay.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                btnPlay.setEnabled(true);
                showDialogWin();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void showDialogWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulation")
                .setMessage("If you want to continue to play, sign up, or log in.")
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> {
                            openRegistration();
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openRegistration() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
