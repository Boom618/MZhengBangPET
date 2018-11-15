package com.ty.zbpet.ui.fragment.material;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.ui.adapter.MaterialTodoAdapter;
import com.ty.zbpet.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * @author TY
 */
public class PickOutDoneFragment extends BaseFragment implements MaterialUiListInterface<PickOutDetailInfo.DetailsBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MaterialTodoAdapter adapter;

    private MaterialPresenter materialPresenter = new MaterialPresenter(this);

    private static final String ARG_PARAM = "ARG_PARAM";

    public PickOutDoneFragment() {
        // Required empty public constructor

    }

    public static PickOutDoneFragment newInstance(String tag) {
        PickOutDoneFragment fragment = new PickOutDoneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, tag);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View onBaseCreate(View view) {

        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_fragment;
    }


    @Override
    public void showMaterial(List<PickOutDetailInfo.DetailsBean> list) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
