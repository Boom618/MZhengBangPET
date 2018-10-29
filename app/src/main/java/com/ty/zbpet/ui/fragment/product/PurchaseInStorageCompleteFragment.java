package com.ty.zbpet.ui.fragment.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ty.zbpet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 成品——外采入库——已办
 * @author TY
 */
public class PurchaseInStorageCompleteFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
