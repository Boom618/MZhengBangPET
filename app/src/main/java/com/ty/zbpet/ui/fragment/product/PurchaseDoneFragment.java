package com.ty.zbpet.ui.fragment.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成品——外采入库——已办
 * @author TY
 */
public class PurchaseDoneFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected View onBaseCreate(View view) {
        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_recyclerview;
    }
}
