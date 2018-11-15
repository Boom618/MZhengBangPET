package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialTodoData;

import java.util.LinkedList;
import java.util.List;

/**
 * @author PVer on 2018/10/28.
 */
public class MaterialModelInterfaceImpl implements MaterialModelInterface {

    List<MaterialTodoData.ListBean> materialData = new LinkedList<>();

    @Override
    public void saveMaterial(List<MaterialTodoData.ListBean> list) {

        materialData.addAll(list);

    }
}
