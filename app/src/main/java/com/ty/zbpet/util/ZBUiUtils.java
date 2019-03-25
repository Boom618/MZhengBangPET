package com.ty.zbpet.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.ui.widght.NormalSelectionDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author TY
 */
public class ZBUiUtils {

    public static void showToast(String msg) {
        Toast.makeText(ResourceUtil.getContext(), msg, Toast.LENGTH_SHORT).show();
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
     * 选择 Dialog
     *
     * @param context  上下文
     * @param type     仓库还是商品
     * @param position 列表中的哪一列
     * @param data     数据集
     * @param textView 控件
     */
    public static void selectDialog(Context context, final int type, final int position, final List<String> data, final TextView textView) {

        NormalSelectionDialog.Builder builder = new NormalSelectionDialog.Builder(context);

        builder.setTitleText("选择供应商")
                .setOnItemListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(data.get(which));

                        // 选择的仓库 还是 商品
                        if (CodeConstant.SELECT_HOUSE_BUY_IN == type) {
                            DataUtils.setHouseId(position, which);
                        } else {
                            DataUtils.setGoodsId(position, which);
                        }

                        dialog.dismiss();
                    }
                })
                .build()
                .setDatas(data)
                .show();

    }
}
