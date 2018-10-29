package com.ty.zbpet.ui.fragment.material;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.presenter.material.MaterialUiInterface;
import com.ty.zbpet.presenter.material.PickOutPresenter;
import com.ty.zbpet.ui.adapter.MaterialAdapter;
import com.ty.zbpet.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickOutTodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author TY
 */
public class PickOutTodoFragment extends BaseFragment implements MaterialUiInterface<PickOutDetailInfo.DetailsBean> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MaterialAdapter adapter;

    private PickOutPresenter materialPresenter = new PickOutPresenter(this);

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public PickOutTodoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PickOutTodoFragment.
     */
    public static PickOutTodoFragment newInstance(String tag) {
        PickOutTodoFragment fragment = new PickOutTodoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    protected void onBaseCreate(View view) {

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
