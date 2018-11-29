package com.ty.zbpet.ui.adapter.material;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import com.ty.zbpet.bean.material.MaterialDetailsIn;

import java.util.List;

/**
 * @author TY on 2018/11/27.
 * <p>
 * Different
 */
public class MaterialDiffUtil extends DiffUtil.Callback {

    private List<String> mOldList, mNewList;

    public MaterialDiffUtil(List<String> mOldList, List<String> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }


    @Override
    public int getOldListSize() {
        return mOldList == null ? 0 : mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList == null ? 0 : mNewList.size();
    }

    /**
     * 比较 ID
     *
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return mOldList.get(i).getClass().equals(mNewList.get(i1).getClass());
    }

    /**
     * 比较 内容
     *
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areContentsTheSame(int i, int i1) {

        String oldStr = mOldList.get(i);
        String newStr = mNewList.get(i1);

        return oldStr.equals(newStr);
    }

    /**
     * 需要更新的数据
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
//    @Nullable
//    @Override
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//
//
//
//
//
//        return super.getChangePayload(oldItemPosition, newItemPosition);
//    }
}
