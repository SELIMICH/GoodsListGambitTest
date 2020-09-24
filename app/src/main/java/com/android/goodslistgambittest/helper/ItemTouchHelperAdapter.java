package com.android.goodslistgambittest.helper;

import android.graphics.Canvas;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
    void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive);
}
