package com.set.kingbaselib.widget.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/1 13:20
 * email： leocheung4ever@gmail.com
 * description: 渐隐渐现动画-渐渐呈现
 * what & why is modified:
 */
public class AlphaInAnimation extends BaseAnimation {

    private static final float DEFAULT_ALPHA_FROM = 0f; //透明
    private final float mFrom;

    public AlphaInAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f)};
    }
}
