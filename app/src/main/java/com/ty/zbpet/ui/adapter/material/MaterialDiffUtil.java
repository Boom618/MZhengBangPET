package com.ty.zbpet.ui.adapter.material;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.ty.zbpet.bean.material.MaterialDetailsIn;

import java.util.List;

/**
 * @author TY on 2018/11/27.
 * <p>
 * 待办详情 Different
 */
public class MaterialDiffUtil extends DiffUtil.Callback {

    private List<MaterialDetailsIn.ListBean> mOldList;
    private List<MaterialDetailsIn.ListBean> mNewList;

    public MaterialDiffUtil(List<MaterialDetailsIn.ListBean> mOldList, List<MaterialDetailsIn.ListBean> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }


    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    /**
     * id 相同
     *
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return true;
    }

    /**
     * 内容不同
     *
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areContentsTheSame(int i, int i1) {

        return false;
    }

    /**
     * 需要更新的数据
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {



        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
