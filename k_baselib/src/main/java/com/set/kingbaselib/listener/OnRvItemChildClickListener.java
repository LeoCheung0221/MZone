package com.set.kingbaselib.listener;

import android.view.View;

import com.set.kingbaselib.BaseAdapter;

/**
 * author： LeoCheung4ever on 2016/5/1 10:13
 * email： leocheung4ever@gmail.com
 * description: 为RecyclerView Item添加事件监听器
 * what & why is modified:
 */
public interface OnRvItemChildClickListener {
    void onItemChildClick(BaseAdapter adapter, View view, int position);
}
