package com.ty.zbpet.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author TY
 */
public abstract class BaseFragment extends Fragment {

    public BaseViewModel mViewModel;

    private Unbinder mUnbinder;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getFragmentLayout(), container, false);

        mUnbinder = ButterKnife.bind(this, view);
        onBaseCreate(view);
        return view;
    }

    /**
     * 初始化 View
     * @param view
     */
    protected  abstract void onBaseCreate(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        // TODO: Use the ViewModel

    }

    /**
     * Fragment Layout
     * @return
     */
    protected abstract int getFragmentLayout();


    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
    }
}
