package com.ty.zbpet.ui.activity.material;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.bean.material.MaterialDoneSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.material.MaterialPresenter;
import com.ty.zbpet.presenter.material.MaterialUiListInterface;
import com.ty.zbpet.ui.adapter.SwipeItemLayout;
import com.ty.zbpet.ui.adapter.material.MaterialDoneDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/11/14.
 * <p>
 * 已办 详情
 */
public class ArrivalInDoneDetailActivity extends BaseActivity implements MaterialUiListInterface<MaterialDetailsOut.ListBean> {

    @BindView(R.id.rv_in_storage_detail)
    RecyclerView detailRc;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.et_desc)
    EditText etDesc;

    /**
     * 时间选择
     */
    private String selectTime;


    private String orderId;
    private String mInWarehouseOrderId;
    private String warehouseId;
    private String sapOrderNo;
    private String positionId;

    private MaterialDoneDetailAdapter adapter;
    private MaterialPresenter materialPresenter = new MaterialPresenter(this);


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {
        SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = sdf.format(new Date());
        tvTime.setText(selectTime);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_two;
    }

    @Override
    protected void initOneData() {

        mInWarehouseOrderId = getIntent().getStringExtra("mInWarehouseOrderId");
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");
        warehouseId = getIntent().getStringExtra("warehouseId");
        orderId = getIntent().getStringExtra("orderId");
    }

    @Override
    protected void initTwoView() {

        findViewById(R.id.add_ship).setVisibility(View.GONE);
        TextView titleName = findViewById(R.id.in_storage_detail);
        titleName.setText("到货明细");
        etDesc.setInputType(InputType.TYPE_NULL);

        initToolBar(R.string.material_reversal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialDoneInSave(initRequestBody());

            }
        });


    }

    /**
     * 已办 保存
     * @param body
     */
    public void materialDoneInSave(RequestBody body) {
        HttpMethods.getInstance().materialDoneInSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(ResponseInfo responseInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                    finish();
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }
            }

        }, body);

    }

    /**
     * 初始化 请求参数
     *
     * @return
     */
    private RequestBody initRequestBody() {

        MaterialDoneSave bean = new MaterialDoneSave();

        bean.setWarehouseId(warehouseId);
        bean.setOrderId(mInWarehouseOrderId);
        bean.setSapOrderNo(sapOrderNo);
        bean.setPositionId(positionId);
        bean.setOrderId(orderId);

        String json = DataUtils.toJson(bean, 1);

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);

    }

    @Override
    protected void onStart() {
        super.onStart();
        materialPresenter.fetchDoneMaterialDetails(orderId);
    }

    @Override
    public void showMaterial(List<MaterialDetailsOut.ListBean> list) {

        positionId = list.get(0).getPositionId();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            detailRc.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            detailRc.setLayoutManager(manager);

            // TODO 侧滑删除
            // detailRc.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
            adapter = new MaterialDoneDetailAdapter(this, R.layout.item_material_done_detail, list);
            detailRc.setAdapter(adapter);
            adapter.setOnItemClickListener(new MaterialDoneDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    View rlDetail = holder.itemView.findViewById(R.id.view_gone);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
