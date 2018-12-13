package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.presenter.system.SystemPresenter;
import com.ty.zbpet.presenter.system.SystemUiListInterface;
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author TY on 2018/12/12.
 * 质检待办详情
 */
public class QualityCheckTodoDetailActivity extends BaseActivity implements SystemUiListInterface<QualityCheckTodoList.ListBean> {

    private RecyclerView reView;
    private TextView titleName;
    private TextView tvTime;

    private String sapOrderNo;
    private String selectTime;

    private QuaCheckTodoDetailAdapter adapter;

    private List<QualityCheckTodoList.ListBean> listBeans = new ArrayList<>();
    private SystemPresenter presenter = new SystemPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_two;
    }

    @Override
    protected void initOneData() {


        sapOrderNo = getIntent().getStringExtra("sapOrderNo");

        presenter.fetchQualityCheckTodoInfo(sapOrderNo);

    }

    @Override
    protected void initTwoView() {

        reView = findViewById(R.id.rv_in_storage_detail);
        titleName = findViewById(R.id.in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        findViewById(R.id.add_ship).setVisibility(View.GONE);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        titleName.setText("质检明细");

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZBUiUtils.showPickDate(v.getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        selectTime = ZBUiUtils.getTime(date);
                        tvTime.setText(selectTime);

                        ZBUiUtils.showToast(selectTime);
                    }
                });
            }
        });

        initToolBar(R.string.label_check);

    }

    @Override
    public void showSystem(final List<QualityCheckTodoList.ListBean> list) {

        listBeans = list;
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new QuaCheckTodoDetailAdapter(this, R.layout.item_sys_qua_check, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new QuaCheckTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    ImageView addImage = holder.itemView.findViewById(R.id.add_image);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                    addImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZBUiUtils.showToast("添加图片");
                        }
                    });
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }
}
