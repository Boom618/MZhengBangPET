package com.ty.zbpet.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.ty.zbpet.R;
import com.ty.zbpet.ui.activity.material.ArrivalInStorageActivity;
import com.ty.zbpet.ui.activity.material.PickOutStorageActivity;
import com.ty.zbpet.ui.base.BaseActivity;

import butterknife.OnClick;

/**
 * @author TY
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initOneData() {

    }

    @Override
    protected void initTwoView() {

    }

    @OnClick({R.id.tv_arrival_in_storage, R.id.tv_pick_out_storage, R.id.tv_purchase_returns, R.id.tv_purchase_in_storage,
            R.id.tv_produce_in_storage, R.id.tv_send_out_storage, R.id.tv_return_in_storage, R.id.tv_inventory,
            R.id.tv_transfer_storage, R.id.tv_person_center})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_arrival_in_storage:
                //原辅料——到货入库
                gotoActivity(ArrivalInStorageActivity.class);
//                startActivity(new Intent(MainActivity.this,ScanBoxCodeActivity.class));
                break;
            case R.id.tv_pick_out_storage:
                //原辅料——领料出库
                gotoActivity(PickOutStorageActivity.class);
                break;
            case R.id.tv_purchase_returns:
                //原辅料——采购退货
                gotoActivity(PurchaseReturnsActivity.class);
                break;
            case R.id.tv_purchase_in_storage:
                //成品——外采入库
                gotoActivity(PurchaseInStorageActivity.class);
                break;
            case R.id.tv_produce_in_storage:
                //成品——生产入库
                gotoActivity(ProduceInStorageActivity.class);
                break;
            case R.id.tv_send_out_storage:
                //成品——发货出库
                gotoActivity(SendOutStorageActivity.class);
                break;
            case R.id.tv_return_in_storage:
                //成品——退货入库
                gotoActivity(ReturnInStorageActivity.class);
                break;
            case R.id.tv_inventory:
                //仓库管理——盘点
                gotoActivity(InventoryActivity.class);
                break;
            case R.id.tv_transfer_storage:
                //仓库管理——移库
                gotoActivity(TransferStorageActivity.class);
                break;
            case R.id.tv_person_center:
                //个人中心
                gotoActivity(PersonCenterActivity.class);
                break;
//            case R.id.tv_quality_check:
//                //质检
//                gotoActivity(QualityCheckActivity.class);
//                break;
//            case R.id.tv_check_person:
//                //质检个人中心
//                gotoActivity(PersonCenterActivity.class);
//                break;
            default:
                break;
        }
    }

}
