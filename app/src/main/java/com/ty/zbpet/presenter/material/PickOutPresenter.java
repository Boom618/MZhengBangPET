package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.PickOutDetailInfo;
import com.ty.zbpet.bean.PickOutTodoData;
import com.ty.zbpet.bean.PickOutTodoDetailsData;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.BaseSubscriber;

import java.util.List;

/**
 * @author TY on 2018/10/29.
 * <p>
 * 领料出库 Presenter
 */
public class PickOutPresenter {

    private MaterialUiListInterface listInterface;
    private MaterialUiObjInterface objInterface;

    private HttpMethods httpMethods;

    public PickOutPresenter(MaterialUiObjInterface objInterface) {
        this.objInterface = objInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public PickOutPresenter(MaterialUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();
    }

    /**
     * 待办列表
     */
    public void fetchPickOutTodoList() {
        httpMethods.pickOutTodoList(new BaseSubscriber<BaseResponse<PickOutTodoData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<PickOutTodoData> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<PickOutTodoData.ListBean> list = response.getData().getList();
                    listInterface.showMaterial(list);

                } else {
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        });
    }


    /**
     * 待办详情
     *
     * @param sapOrderNo
     */
    public void fetchPickOutTodoListDetails(String sapOrderNo) {


        httpMethods.pickOutTodoListDetails(new BaseSubscriber<BaseResponse<PickOutTodoDetailsData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast("失败 : =" + e.getMessage());

            }


            @Override
            public void onNext(BaseResponse<PickOutTodoDetailsData> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    PickOutTodoDetailsData data = pickOutDetailInfo.getData();
                    objInterface.detailObjData(data);

                } else {
                    ZBUiUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
                }
            }
        }, sapOrderNo);
    }
}
