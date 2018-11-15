package com.ty.zbpet.presenter.material;

import com.ty.zbpet.bean.MaterialTodoData;

import java.util.List;

/**
 * @author PVer on 2018/10/28.
 *
 * Model 接口
 */
public interface MaterialModelInterface {

    void saveMaterial(List<MaterialTodoData.ListBean> list);
}
