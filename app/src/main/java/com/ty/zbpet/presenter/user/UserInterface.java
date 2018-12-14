package com.ty.zbpet.presenter.user;

/**
 * @author TY on 2018/12/14.
 * <p>
 * 用户接口
 */
public interface UserInterface {

    /**
     * 成功
     */
    void onSuccess();


    /**
     * 失败
     *
     * @param e 错误信息
     */
    void onError(Throwable e);

}
