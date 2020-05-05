package protzek.sebastian.mastermindlogicgame.media;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import protzek.sebastian.mastermindlogicgame.R;

public class Animations {
    public void zoomIn(final View view) {
        final ValueAnimator anim = ValueAnimator.ofFloat(1f, 12f);
        anim.setDuration(700);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setScaleX((Float) animation.getAnimatedValue());
                view.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        anim.setRepeatCount(1);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();
    }

    public void blink(final TextView view) {
        int color = ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark);
        int color2 = ContextCompat.getColor(view.getContext(), R.color.gold);
        final ObjectAnimator anim = ObjectAnimator.ofArgb(view, "textColor", color, color2);
        anim.setDuration(1000);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();
    }
}
