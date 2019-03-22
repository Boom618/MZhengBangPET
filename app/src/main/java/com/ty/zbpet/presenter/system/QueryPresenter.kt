package com.ty.zbpet.presenter.system

import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.system.PositionCode
import com.ty.zbpet.bean.system.ProductQuery
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.ui.base.BaseResponse
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * @author TY on 2019/3/22.
 */
class QueryPresenter {

    private val httpMethods: HttpMethods = HttpMethods.getInstance()

    private var disposable: Disposable? = null

    fun dispose() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }

    /**
     * 成品查询
     */
    fun productQuery(url: String) {
        httpMethods.getProductQuery(object : SingleObserver<BaseResponse<ProductQuery>> {
            override fun onSuccess(response: BaseResponse<ProductQuery>) {
                if (CodeConstant.SERVICE_SUCCESS == response.tag) {
                    val data = response.data
                    EventBus.getDefault().post(data)
                } else {
                    EventBus.getDefault().post(ErrorMessage(response.message))
                }
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                EventBus.getDefault().post(ErrorMessage(e.message))
            }
        }, url)
    }

    /**
     * 库位码查询
     */
    fun positionQuery(positionNo: String) {
        httpMethods.getPositionQuery(object : SingleObserver<BaseResponse<PositionCode>> {
            override fun onSuccess(response: BaseResponse<PositionCode>) {
                if (CodeConstant.SERVICE_SUCCESS == response.tag) {
                    val data = response.data
                    EventBus.getDefault().post(data)
                } else {
                    EventBus.getDefault().post(ErrorMessage(response.message))
                }
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                EventBus.getDefault().post(ErrorMessage(e.message))
            }
        }, positionNo)
    }
}