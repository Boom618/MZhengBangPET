package com.ty.zbpet.ui.activity.system;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.presenter.system.SystemPresenter;
import com.ty.zbpet.presenter.system.SystemUiListInterface;
import com.ty.zbpet.ui.adapter.system.QuaCheckDoneDetailAdapter;
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author TY on 2018/12/12.
 */
public class QualityCheckDoneDetailActivity extends BaseActivity implements SystemUiListInterface<QualityCheckTodoList.DataBean> {


    private RecyclerView reView;
    private TextView titleName;
    private TextView tvTime;

    private String arrivalOrderNo;
    private String selectTime;

    private QuaCheckDoneDetailAdapter adapter;

    private List<QualityCheckTodoList.DataBean> listBeans = new ArrayList<>();
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

        arrivalOrderNo = getIntent().getStringExtra("arrivalOrderNo");

        presenter.fetchQualityCheckDoneInfo(arrivalOrderNo);

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

        initToolBar(R.string.label_check);

    }

    @Override
    public void showSystem(List<QualityCheckTodoList.DataBean> list) {

    }
}
