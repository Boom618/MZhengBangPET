package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.net.HttpMethods;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author TY on 2018/10/29.
 *
 * 领料出库 Presenter
 */
public class PickOutPresenter {

    MaterialUiInterface pickOutUi;

    public PickOutPresenter(MaterialUiInterface pickOutUi) {
        this.pickOutUi = pickOutUi;
    }

    // TODO 数据保存 、UI 加载

    public void fetchPickOut(){

        pickOutUi.showLoading();

        HttpMethods.getInstance().pickOutDetail(new BaseSubscriber<PickOutDetailInfo>() {
            @Override
            public void onError(ApiException e) {
                pickOutUi.hideLoading();


            }

            @Override
            public void onNext(PickOutDetailInfo pickOutDetailInfo) {
                pickOutUi.hideLoading();

                List<PickOutDetailInfo.DetailsBean> list = pickOutDetailInfo.getDetails();

                pickOutUi.showMaterial(list);

            }
        });

    }
}
