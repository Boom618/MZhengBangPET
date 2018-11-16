package com.ty.zbpet.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ty.zbpet.ui.widght.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author TY
 */
public class ZBUiUtils {

    public static void showToast(String msg) {
        Toast.makeText(ResourceUtil.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置日期
     *
     * @param tvDate
     * @param activity
     */
    public static void showTimeDialog(final TextView tvDate, Activity activity) {
        SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        String now = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        // Calendar.SECOND 秒
        final int seconds = calendar.get(Calendar.SECOND);
        CustomDatePicker datePicker = new CustomDatePicker(activity, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                String s;
                if (seconds < 10) {
                    s = "0" + seconds;
                } else {
                    s = seconds + "";
                }
                tvDate.setText(time + ":" + s);
            }
        }, CodeConstant.DATE_START_TIME, CodeConstant.DATE_END_TIME);
        // 显示时和分
        datePicker.showSpecificTime(true);
        // 允许循环滚动
        datePicker.setIsLoop(false);
        datePicker.show(now);
    }


    /**
     * 时间戳 转 String
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M_S, Locale.CHINA);
        return format.format(date);
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
                .setType(new boolean[]{true, true, true, true, true, false})
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

    /**
     * 关闭软键盘
     *
     * @param context
     * @param view
     */
    public static void hideInputWindow(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 选择仓库
     *
     */
    public static void selectWarehouse() {

    }

}
