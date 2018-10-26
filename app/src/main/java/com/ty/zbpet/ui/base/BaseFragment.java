package com.ty.zbpet.ui.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
import com.ty.zbpet.ui.activity.ArrivalInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.MaterialAdapter;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

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


    /**
     * 刷新 UI
     * @param adapter
     * @param recyclerView
     * @param list
     */
    public void refreshUI(CommonAdapter adapter,RecyclerView recyclerView, List<BaseResponse<?>> list) {
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            recyclerView.setLayoutManager(manager);
            adapter = new MaterialAdapter(ResourceUtil.getContext(), R.layout.item_arrive_in_storage_complete,list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new MaterialAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent=new Intent(getActivity(), ArrivalInStorageDetailActivity.class);
                    intent.putExtra("orderId",position);
                    intent.putExtra("sapOrderNo",position);
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }

            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mUnbinder.unbind();
    }
}
