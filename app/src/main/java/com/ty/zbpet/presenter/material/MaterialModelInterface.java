package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.ui.base.BaseResponse;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * Model 接口
 */
public interface MaterialModelInterface {

    void saveMaterial(List<MaterialData> list);
}
