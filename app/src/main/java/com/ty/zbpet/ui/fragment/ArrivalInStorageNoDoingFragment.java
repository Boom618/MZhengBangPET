//package com.ty.zbpet.ui.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.ty.zbpet.R;
//import com.ty.zbpet.bean.MaterialData;
//import com.ty.zbpet.bean.MaterialInWarehouseOrderList;
//import com.ty.zbpet.net.HttpMethods;
//import com.ty.zbpet.ui.activity.ArrivalInStorageDetailActivity;
//import com.ty.zbpet.ui.base.BaseResponse;
//import com.ty.zbpet.ui.widght.SpaceItemDecoration;
//import com.ty.zbpet.util.ResourceUtil;
//import com.ty.zbpet.util.UIUtils;
//import com.ty.zbpet.util.Utils;
//import com.zhouyou.http.exception.ApiException;
//import com.zhouyou.http.subsciber.BaseSubscriber;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * 原辅料——到货入库——待办
// *
// * @author TY
// */
//public class ArrivalInStorageNoDoingFragment extends Fragment {
//
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    //private MaterialInWarehouseAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
//
//        ButterKnife.bind(this,view);
//
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        //getHttpData();
//
//        ArrayList<MaterialInWarehouseOrderList.DataBean.ListBean> data = Utils.getData("待办");
//
//        refreshUI(data);
//    }
//
//    private void getHttpData() {
//
//        HttpMethods.getInstance().getMaterialTodoList(new BaseSubscriber<BaseResponse<MaterialData>>() {
//            @Override
//            public void onError(ApiException e) {
//                UIUtils.showToast("原辅料——到货入库——待办 == onError ");
//            }
//
//            @Override
//            protected void onStart() {
//                UIUtils.showToast("原辅料——到货入库——待办 == onStart ");
//            }
//
//            @Override
//            public void onNext(BaseResponse<MaterialData> materialInWarehouseOrderList) {
//                UIUtils.showToast("原辅料——到货入库——待办 == onNext ");
//            }
//
//            @Override
//            public void onComplete() {
//                super.onComplete();
//                UIUtils.showToast("原辅料——到货入库——待办 == onComplete ");
//            }
//        });
//    }
//
//
//    private void refreshUI(List<MaterialInWarehouseOrderList.DataBean.ListBean> list) {
////        if (adapter == null) {
////            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
////            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
////            recyclerView.setLayoutManager(manager);
////            adapter = new MaterialInWarehouseAdapter(ResourceUtil.getContext(), list);
////            recyclerView.setAdapter(adapter);
////            adapter.setOnItemClickListener(new MaterialInWarehouseAdapter.OnItemClickListener() {
////                @Override
////                public void onItemClick(int position, MaterialInWarehouseOrderList.DataBean.ListBean data) {
////                    Intent intent=new Intent(getActivity(), ArrivalInStorageDetailActivity.class);
////                    intent.putExtra("orderId",data.getOrderId());
////                    intent.putExtra("sapOrderNo",data.getSapOrderNo());
////                    startActivity(intent);
////                }
////            });
////        }
//    }
//}
