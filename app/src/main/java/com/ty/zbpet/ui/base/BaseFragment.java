package com.ty.zbpet.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author TY
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 功能列表：
     * 1、ButterKnife 初始化、解绑
     * 2、Layout
     * 3、
     */


    public BaseViewModel mViewModel;

    private Unbinder mUnbinder;

    protected ACache mCache;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getFragmentLayout(), container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mCache = ACache.get(view.getContext());
        // onBaseCreate(view);
        return onBaseCreate(view);
    }

    /**
     * 初始化 View
     *
     * @param view
     */
    protected abstract View onBaseCreate(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        // TODO: Use the ViewModel

    }

    /**
     * Fragment Layout
     *
     * @return
     */
    protected abstract int getFragmentLayout();


    /**
     * 重置 ACache 中保存的的数据
     */
    private void clearCache() {
        mCache.put(CodeConstant.SCAN_BOX_POSITION, "-1");
        mCache.put(CodeConstant.SCAN_BOX_KEY, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        clearCache();

        mUnbinder.unbind();
    }
}
