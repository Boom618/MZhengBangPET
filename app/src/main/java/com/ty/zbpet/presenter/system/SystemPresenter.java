package com.ty.zbpet.presenter.system;

import com.ty.zbpet.bean.system.QualityCheckTodoList;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/12/12.
 */
public class SystemPresenter {

    SystemUiListInterface listInterface;


    /**
     * API 网络
     */
    HttpMethods httpMethods;

    Disposable disposable;

    public SystemPresenter(SystemUiListInterface listInterface) {
        this.listInterface = listInterface;
        httpMethods = HttpMethods.getInstance();

    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    /**
     * 获取质检 待办列表
     */
    public void fetchQualityCheckTodoList() {
        httpMethods.getQualityCheckTodoList(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.ListBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        });
    }


    /**
     * 待办 详情
     * @param sapOrderNo
     */
    public void fetchQualityCheckTodoInfo(String sapOrderNo){

        httpMethods.getQualityCheckTodoInfo(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.ListBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        },sapOrderNo);
    }

    /**
     * 获取质检 待办列表
     */
    public void fetchQualityCheckDoneList() {
        httpMethods.getQualityCheckDoneList(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.ListBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        });
    }


    /**
     * 待办 详情
     * @param sapOrderNo
     */
    public void fetchQualityCheckDoneInfo(String sapOrderNo){

        httpMethods.getQualityCheckDoneInfo(new SingleObserver<BaseResponse<QualityCheckTodoList>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<QualityCheckTodoList> response) {

                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {

                    List<QualityCheckTodoList.ListBean> list = response.getData().getList();

                    listInterface.showSystem(list);
                }
            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        },sapOrderNo);
    }

}
