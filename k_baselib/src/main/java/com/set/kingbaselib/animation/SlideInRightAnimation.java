package com.set.kingbaselib.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/2 23:28
 * email： leocheung4ever@gmail.com
 * description: 从右向左划入动画
 * what & why is modified:
 */
public class SlideInRightAnimation extends BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        };
    }
}