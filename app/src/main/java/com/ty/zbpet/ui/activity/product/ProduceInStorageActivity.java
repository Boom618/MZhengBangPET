package com.ty.zbpet.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ty.zbpet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 生产入库
 * @author TY
 */
public class ProduceInStorageActivity extends AppCompatActivity {

    @BindView(R.id.tv_operator)
    TextView tvOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce_in_storage);
        ButterKnife.bind(this);

        tvOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProduceInStorageActivity.this,
                        ProduceInStorageDetailActivity.class));
            }
        });
    }
}
