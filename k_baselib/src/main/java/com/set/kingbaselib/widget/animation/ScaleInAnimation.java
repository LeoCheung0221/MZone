package com.set.kingbaselib.widget.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/2 23:13
 * email： leocheung4ever@gmail.com
 * description: 由小到大缩放进入界面动画
 * what & why is modified:
 */
public class ScaleInAnimation extends BaseAnimation {

    private static final float DEFAULT_SCALE_FROM = .5f; //默认是原来大小一半进行缩放进入
    private final float mFrom;

    public ScaleInAnimation() {
        this(DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimation(float from) {
        this.mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        return new ObjectAnimator[]{scaleX, scaleY};
    }
}
