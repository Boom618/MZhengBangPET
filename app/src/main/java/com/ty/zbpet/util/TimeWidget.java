package com.ty.zbpet.util;

import android.content.Context;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ty.zbpet.constant.CodeConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author TY on 2019/1/11.
 */
public class TimeWidget {

    /**
     * 时间戳 转 String
     *
     * @param type 格式
     * @param date 时间
     * @return string
     */
    public static String getTime(String type, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(type, Locale.CHINA);
        return format.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return 时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_Y_M_D, Locale.CANADA);
        return format.format(new Date());
    }

    /**
     * 获取当前年份
     *
     * @return 年
     */
    public static Integer getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return 月份
     */
    public static Integer getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 时间戳转 Date
     *
     * @param dateStr 时间戳
     * @return date
     */
    public static Date StringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_Y_M_D, Locale.CANADA);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 时间比较
     *
     * @param startDate 开始时间
     * @param endStart  结束时间
     * @return startDate before 小于 endStart
     */
    public static boolean DateComparison(Date startDate, Date endStart) {
        return startDate.before(endStart);
    }

    /**
     * 时间弹窗
     *
     * @param context
     * @param listener
     */
    public static void showPickDate(Context context, OnTimeSelectListener listener) {
        TimePickerView pvTime = new TimePickerBuilder(context, listener)
                // 默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                //.setCancelText("Cancel")//取消按钮文字
                // .setSubmitText("Sure")//确认按钮文字
                //.setContentSize(18)//滚轮文字大小
                //标题文字大小
                .setTitleSize(20)
                //标题文字
                .setTitleText("选择时间")
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setOutSideCancelable(true)
                //是否循环滚动
                .isCyclic(false)
                // .setTitleColor(Color.BLACK)//标题文字颜色
                // .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                // .setCancelColor(Color.BLUE)//取消按钮文字颜色
                // .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
                // .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                // .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                // .setRangDate(startDate,endDate)//起始终止年月日设定
                //默认设置为年月日时分秒
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();

        pvTime.show();
    }
}
