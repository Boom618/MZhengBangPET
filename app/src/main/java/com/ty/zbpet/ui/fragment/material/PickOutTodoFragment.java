package com.ty.zbpet.ui.fragment.material;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.presenter.material.MaterialUiObjlInterface;
import com.ty.zbpet.presenter.material.PickOutPresenter;
import com.ty.zbpet.ui.activity.material.ArrivalInStorageDetailActivity;
import com.ty.zbpet.ui.adapter.PickOutAdapter;
import com.ty.zbpet.ui.base.BaseFragment;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.UIUtils;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickOutTodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author TY
 */
public class PickOutTodoFragment extends BaseFragment implements MaterialUiObjlInterface<PickOutDetailInfo> {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private PickOutAdapter adapter;

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
    protected View onBaseCreate(View view) {

        return view;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.zb_content_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        materialPresenter.fetchPickOut();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishRefresh(2000);
                // 刷新数据
                materialPresenter.fetchPickOut();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                // 传入 false 表示刷新失败
                refreshLayout.finishLoadMore(2000);
                UIUtils.showToast("没有更多数据了");
            }
        });

    }


    @Override
    public void detailObjData(PickOutDetailInfo info) {
        LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
        recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
        recyclerView.setLayoutManager(manager);

        if (adapter == null) {
            List<PickOutDetailInfo.DetailsBean> details = info.getDetails();

            adapter = new PickOutAdapter(this.getContext(),R.layout.item_arrive_in_storage_complete,details);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PickOutAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent=new Intent(getActivity(), ArrivalInStorageDetailActivity.class);
                    // TODO 传参 跳转

                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }

}
