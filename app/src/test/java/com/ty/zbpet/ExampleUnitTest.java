package com.ty.zbpet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.zbpet.util.JsonStringMerge;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {

        int value = Integer.parseInt("00101");
        // value = 101
        System.out.println("value = " + value);

        try {
            String json1 = "{\"a\":\"aaa\",\"b1\":\"bb1\"}";
            String json2 = "{\"b\":\"bbb\"}";

            JsonStringMerge merge = new JsonStringMerge();
            String stringMerge = merge.StringMerge(json1, json2);

            System.out.println(stringMerge);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}