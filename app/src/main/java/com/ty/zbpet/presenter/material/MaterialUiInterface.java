package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.ui.base.BaseResponse;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * UI 接口
 */
public interface MaterialUiInterface {

    void showMaterial(List<MaterialData> list);

    void showLoading();

    void hideLoading();
}
