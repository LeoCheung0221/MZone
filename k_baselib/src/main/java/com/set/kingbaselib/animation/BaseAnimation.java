package com.set.kingbaselib.animation;

import android.animation.Animator;
import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/1 12:30
 * email： leocheung4ever@gmail.com
 * description: 动画基类 抽象出获得动画器数组的一个方法
 * what & why is modified:
 */
public abstract class BaseAnimation {
    abstract public Animator[] getAnimators(View view);
}
