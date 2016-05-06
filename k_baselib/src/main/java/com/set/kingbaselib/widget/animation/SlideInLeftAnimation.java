package com.set.kingbaselib.widget.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/2 23:26
 * email： leocheung4ever@gmail.com
 * description: 从左向右划入动画
 * what & why is modified:
 */
public class SlideInLeftAnimation extends BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        };
    }
}
