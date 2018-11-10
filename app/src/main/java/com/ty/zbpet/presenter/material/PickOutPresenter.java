package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author TY on 2018/10/29.
 * <p>
 * 领料出库 Presenter
 */
public class PickOutPresenter {

    MaterialUiInterface pickOutUi;

    public PickOutPresenter(MaterialUiInterface pickOutUi) {
        this.pickOutUi = pickOutUi;
    }

    // TODO 数据保存 、UI 加载

    public void fetchPickOut() {

        pickOutUi.showLoading();

        HttpMethods.getInstance().pickOutDetail(new BaseSubscriber<BaseResponse<PickOutDetailInfo>>() {
            @Override
            public void onError(ApiException e) {
                pickOutUi.hideLoading();


            }


            @Override
            public void onNext(BaseResponse<PickOutDetailInfo> pickOutDetailInfo) {
                pickOutUi.hideLoading();

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    List<PickOutDetailInfo.DetailsBean> list = pickOutDetailInfo.getData().getDetails();

                    pickOutUi.showMaterial(list);

                }else{
                    UIUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
                }


            }
        });

    }
}
