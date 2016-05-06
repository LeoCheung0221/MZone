package com.set.kingbaselib.widget.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/2 23:20
 * email： leocheung4ever@gmail.com
 * description: 从底部向上滑出动画
 * what & why is modified:
 */
public class SlideInBottomAnimation extends BaseAnimation {

    public SlideInBottomAnimation() {
    }

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0)
        };
    }
}
