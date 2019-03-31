package com.ty.zbpet.presenter.system;

import com.ty.zbpet.bean.system.BoxCodeUrl;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.base.BaseResponse;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/12/12.
 */
public class CommPresenter {

    private CommInterface commInterface;


    /**
     * API 网络
     */
    private HttpMethods httpMethods;

    private Disposable disposable;

    public CommPresenter(CommInterface commInterface) {
        this.commInterface = commInterface;
        httpMethods = HttpMethods.getInstance();

    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    /**
     * url 解析
     */
    public void urlAnalyze(String url,String goodsNo) {
        httpMethods.urlAnalyze(new SingleObserver<BaseResponse<BoxCodeUrl>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<BoxCodeUrl> response) {
                if (CodeConstant.SERVICE_SUCCESS.equals(response.getTag())) {
                    BoxCodeUrl data = response.getData();
                    commInterface.urlAnalyze(data.getBoxQrCode());
                } else {
                    commInterface.showError(response.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                commInterface.showError(e.getMessage());
            }
        }, url,goodsNo);
    }

}
