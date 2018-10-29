package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialData;
import com.ty.zbpet.ui.base.BaseResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * @author PVer on 2018/10/28.
 */
public class MaterialModelInterfaceImpl implements MaterialModelInterface {

    List<MaterialData.ListBean> materialData = new LinkedList<>();

    @Override
    public void saveMaterial(List<MaterialData.ListBean> list) {

        materialData.addAll(list);

    }
}
