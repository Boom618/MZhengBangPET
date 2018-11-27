package com.ty.zbpet.ui.activity.product;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ty.zbpet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 生产入库详情（采购退货 - 待办详情）
 * @author TY
 */
public class ProduceInStorageDetailActivity extends AppCompatActivity {

    @BindView(R.id.rl_summary)
    RelativeLayout rlSummary;
    @BindView(R.id.rl_detail)
    RelativeLayout rlDetail;

    private boolean isExpand=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_in_storage_detail);
        ButterKnife.bind(this);
        rlSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpand=!isExpand;
                if (isExpand){
                    rlDetail.setVisibility(View.VISIBLE);
                }else {
                    rlDetail.setVisibility(View.GONE);
                }
            }
        });
    }
}
