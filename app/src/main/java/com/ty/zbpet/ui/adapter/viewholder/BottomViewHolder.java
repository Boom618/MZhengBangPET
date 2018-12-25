package com.ty.zbpet.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 底部 item ViewHolder
 * @author TY
 */
public class BottomViewHolder extends RecyclerView.ViewHolder {

        TextView tvPath;
        TextView tvType;
        TextView tvTime;
        EditText etDesc;

        private String selectTime;

        public BottomViewHolder(View view) {
            super(view);
            tvPath = view.findViewById(R.id.tv_path);
            tvType = view.findViewById(R.id.tv_type);
            tvTime = view.findViewById(R.id.tv_time);
            etDesc = view.findViewById(R.id.et_desc);

            SimpleDateFormat sdf = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
            selectTime = sdf.format(new Date());


            tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ZBUiUtils.showPickDate(v.getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            //选中事件回调
                            selectTime = ZBUiUtils.getTime(date);
                            tvTime.setText(selectTime);
                            ZBUiUtils.showToast(selectTime);
                        }
                    });
                }
            });
        }
    }