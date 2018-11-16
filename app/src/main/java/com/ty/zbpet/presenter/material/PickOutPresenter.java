package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.UIUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

/**
 * @author TY on 2018/10/29.
 * <p>
 * 领料出库 Presenter
 */
public class PickOutPresenter {

    MaterialUiObjInterface pickOutUi;

    public PickOutPresenter(MaterialUiObjInterface pickOutUi) {
        this.pickOutUi = pickOutUi;
    }

    // TODO 数据保存 、UI 加载

    public void fetchPickOut() {


        HttpMethods.getInstance().pickOutDetail(new BaseSubscriber<BaseResponse<PickOutDetailInfo>>() {
            @Override
            public void onError(ApiException e) {


            }


            @Override
            public void onNext(BaseResponse<PickOutDetailInfo> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    //pickOutUi.showMaterial(list);
                    PickOutDetailInfo data = pickOutDetailInfo.getData();
                    pickOutUi.detailObjData(data);

                }else{
                    UIUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
                }


            }
        });

    }
}
