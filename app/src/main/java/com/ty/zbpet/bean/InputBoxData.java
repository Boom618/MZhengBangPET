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


    public InputBoxData(long id, String value, int position) {
        this.id = id;
        this.value = value;
        this.position = position;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
