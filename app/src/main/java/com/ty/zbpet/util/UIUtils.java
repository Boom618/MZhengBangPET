package com.ty.zbpet.util;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.ty.zbpet.ui.widght.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author TY
 */
public class UIUtils {

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
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
        }, "2010-01-01 00:00", "2999-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        // 显示时和分
        datePicker.showSpecificTime(true);
        // 允许循环滚动
        datePicker.setIsLoop(false);
        datePicker.show(now);
    }

}
