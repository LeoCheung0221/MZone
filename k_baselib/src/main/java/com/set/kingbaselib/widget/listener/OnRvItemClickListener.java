package com.set.kingbaselib.widget.listener;

import android.view.View;

/**
 * author： LeoCheung4ever on 2016/5/1 10:13
 * email： leocheung4ever@gmail.com
 * description: 为RecyclerView Item添加事件监听器
 * 扩展功能:
 * 单击-> 跳转 or 弹出
 * 长按->
 * 左滑-> 删除
 * 右滑-> 完成
 * 长按拖动-> 排序
 * what & why is modified:
 */
public interface OnRvItemClickListener {

    /**
     * 点击行为事件
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 长按行为事件
     *
     * @param view
     * @param position
     */
    void onItemLongClick(View view, int position);

    /**
     * 左滑行为事件
     *
     * @param view
     * @param position
     */
    void onItemLeftSwipe(View view, int position);

    /**
     * 右滑行为事件
     *
     * @param view
     * @param position
     */
    void onItemRightSwipe(View view, int position);

    /**
     * 长按拖动行为事件
     *
     * @param view
     * @param position
     */
    void onItemLongDrag(View view, int position);
}
