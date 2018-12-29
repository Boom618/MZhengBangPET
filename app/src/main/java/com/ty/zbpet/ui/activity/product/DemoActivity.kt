package com.ty.zbpet.ui.activity.product

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.ty.zbpet.ui.activity.MainActivity

import java.util.ArrayList

/**
 * @author TY on 2018/12/29.
 */
class DemoActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = ArrayList<String>()

        list.add("1111")

        val intent = Intent(this, MainActivity::class.java)
        intent.putStringArrayListExtra("name", list)
    }
}
