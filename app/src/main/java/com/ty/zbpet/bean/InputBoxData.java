package com.ty.zbpet.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author PVer on 2018/12/16.
 * <p>
 * 用户输入框数据
 */

@Entity
public class InputBoxData {

    @Id
    public long id;

    public String value;
    public int position;
}
