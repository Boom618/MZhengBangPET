package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialTodoData;
import com.ty.zbpet.bean.PickOutDetailInfo;
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

    private HttpMethods  httpMethods;

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
    public void fetchPickOutTodoList(){
        httpMethods.pickOutTodoList(new BaseSubscriber<BaseResponse<MaterialTodoData>>() {
            @Override
            public void onError(ApiException e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onNext(BaseResponse<MaterialTodoData> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<MaterialTodoData.ListBean> list = response.getData().getList();
                    listInterface.showMaterial(list);

                }else{
                    ZBUiUtils.showToast("失败 : =" + response.getMessage());
                }
            }
        });
    }

    //

    public void fetchPickOut() {


        httpMethods.pickOutDetail(new BaseSubscriber<BaseResponse<PickOutDetailInfo>>() {
            @Override
            public void onError(ApiException e) {


            }


            @Override
            public void onNext(BaseResponse<PickOutDetailInfo> pickOutDetailInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(pickOutDetailInfo.getTag())) {

                    //pickOutUi.showMaterial(list);
                    PickOutDetailInfo data = pickOutDetailInfo.getData();
                    objInterface.detailObjData(data);

                }else{
                    ZBUiUtils.showToast("失败 : =" + pickOutDetailInfo.getMessage());
                }


            }
        });

    }
}
