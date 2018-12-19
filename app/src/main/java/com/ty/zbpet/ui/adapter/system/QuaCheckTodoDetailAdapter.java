package com.ty.zbpet.ui.adapter.system;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 * <p>
 * 质检 待办详情
 */
public class QuaCheckTodoDetailAdapter extends CommonAdapter<QualityCheckTodoDetails.DataBean> {


    public QuaCheckTodoDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckTodoDetails.DataBean dataBean, int position) {


        EditText etPercent = holder.itemView.findViewById(R.id.et_content);
        EditText etNumber = holder.itemView.findViewById(R.id.et_check_number);
        holder.setText(R.id.tv_name, dataBean.getMaterialName())
                .setText(R.id.tv_num, dataBean.getUnit() + dataBean.getUnitName());

        // 数量
        etNumber.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_NUMBER_INT));

        // 含量
        etPercent.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_PERCENT_INT));


    }


    class EditWatcher implements TextWatcher {

        private int type;
        private int position;

        public EditWatcher(int position, int type) {
            this.type = type;
            this.position = position;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (CodeConstant.ET_NUMBER_INT == type) {
                DataUtils.setNumber(position, s.toString().trim());
            } else {
                DataUtils.setPercent(position, s.toString().trim());
            }
        }
    }
}
