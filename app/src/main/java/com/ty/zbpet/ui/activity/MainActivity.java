package com.ty.zbpet.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ty.zbpet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_storage)
    LinearLayout llStorage;
    @BindView(R.id.rl_check)
    RelativeLayout rlCheck;

    @Override
    protected void onBaseCreate() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rlCheck.setVisibility(View.GONE);
        llStorage.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_arrival_in_storage, R.id.tv_pick_out_storage, R.id.tv_purchase_returns, R.id.tv_purchase_in_storage,
            R.id.tv_produce_in_storage, R.id.tv_send_out_storage, R.id.tv_return_in_storage, R.id.tv_inventory,
            R.id.tv_transfer_storage, R.id.tv_person_center, R.id.tv_quality_check, R.id.tv_check_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_arrival_in_storage:
                //原辅料——到货入库
                startActivity(new Intent(MainActivity.this, ArrivalInStorageActivity.class));
                break;
            case R.id.tv_pick_out_storage:
                //原辅料——领料出库
                startActivity(new Intent(MainActivity.this, PickOutStorageActivity.class));
                break;
            case R.id.tv_purchase_returns:
                //原辅料——采购退货
                startActivity(new Intent(MainActivity.this, PurchaseReturnsActivity.class));
                break;
            case R.id.tv_purchase_in_storage:
                //成品——外采入库
                startActivity(new Intent(MainActivity.this, PurchaseInStorageActivity.class));
                break;
            case R.id.tv_produce_in_storage:
                //成品——生产入库
                startActivity(new Intent(MainActivity.this, ProduceInStorageActivity.class));
                break;
            case R.id.tv_send_out_storage:
                //成品——发货出库
                startActivity(new Intent(MainActivity.this, SendOutStorageActivity.class));
                break;
            case R.id.tv_return_in_storage:
                //成品——退货入库
                startActivity(new Intent(MainActivity.this, ReturnInStorageActivity.class));
                break;
            case R.id.tv_inventory:
                //仓库管理——盘点
                startActivity(new Intent(MainActivity.this, InventoryActivity.class));
                break;
            case R.id.tv_transfer_storage:
                //仓库管理——移库
                startActivity(new Intent(MainActivity.this, TransferStorageActivity.class));
                break;
            case R.id.tv_person_center:
                //个人中心
                startActivity(new Intent(MainActivity.this, PersonCenterActivity.class));
                break;
            case R.id.tv_quality_check:
                //质检
                startActivity(new Intent(MainActivity.this, QualityCheckActivity.class));
                break;
            case R.id.tv_check_person:
                //质检个人中心
                startActivity(new Intent(MainActivity.this, PersonCenterActivity.class));
                break;
        }
    }

}
