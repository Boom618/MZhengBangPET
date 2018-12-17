package com.ty.zbpet.presenter.user;

import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.UserInfo;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.ui.MainApp;
import com.ty.zbpet.ui.base.BaseResponse;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.SimpleCache;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/12/14.
 */
public class UserPresenter {


    private UserInterface userInterface;

    /**
     * API 网络
     */
    private HttpMethods httpMethods;

    private Disposable disposable;

    public UserPresenter(UserInterface userInterface) {
        this.userInterface = userInterface;
        httpMethods = HttpMethods.getInstance();
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


    /**
     * 登录
     *
     * @param userName
     * @param password
     */
    public void userLogin(String userName, String password) {

        httpMethods.userLogin(new SingleObserver<BaseResponse<UserInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(BaseResponse<UserInfo> responseInfo) {

                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {

                    UserInfo data = responseInfo.getData();
                    String sessionId = data.getSessionId();

                    userInterface.onSuccess();

                    SimpleCache.putObject(CodeConstant.USER_DATA, data);
                    SimpleCache.putString(CodeConstant.SESSION_ID_KEY, sessionId);
                }
            }

            @Override
            public void onError(Throwable e) {
                userInterface.onError(e);
            }
        }, userName, password);
    }

    /**
     * 退出登录
     */
    public void userLogOut() {

        httpMethods.userLogOut(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    userInterface.onSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

                userInterface.onError(e);
            }
        });
    }

    /**
     * 用户修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param newPasswordAgain
     */
    public void userUpdatePass(String oldPassword, String newPassword, String newPasswordAgain) {

        httpMethods.userUpdatePass(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    userInterface.onSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                userInterface.onError(e);
            }
        }, oldPassword, newPassword, newPasswordAgain);
    }


    /**
     * 个人中心
     */
    public void userCenter() {

        httpMethods.userCenter(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    userInterface.onSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

                userInterface.onError(e);
            }
        });
    }
}
