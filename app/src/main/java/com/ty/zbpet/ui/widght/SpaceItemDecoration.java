package com.ty.zbpet.ui.widght;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author TY
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private boolean isGridManager;

    public SpaceItemDecoration(int space, boolean isGridManager) {
        this.space = space;
        this.isGridManager = isGridManager;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isGridManager) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;

        } else {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = space;
            }
        }
    }
}