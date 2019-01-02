package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 生产入库 待办详情
 */
public class ProductTodoDetailAdapter extends CommonAdapter<ProductDetails.ListBean> {


    public ProductTodoDetailAdapter(Context context, int layoutId, List<ProductDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetails.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_num, list.getOrderNumber() + "  " + list.getUnitS());

        // 入库数量
        EditText boxNumber = holder.itemView.findViewById(R.id.et_box_num);
        boxNumber.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_NUMBER,position,boxNumber));

        // 文本
        EditText content = holder.itemView.findViewById(R.id.et_bulk_num);
        content.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_CONTENT,position,content));

        // 开始值
        EditText startCode = holder.itemView.findViewById(R.id.et_start_code);
        startCode.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_START_CODE,position,startCode));

        // 结束值
        EditText endCode = holder.itemView.findViewById(R.id.et_end_code);
        endCode.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_END_CODE,position,endCode));

        // sap 物料
        EditText sap = holder.itemView.findViewById(R.id.et_sap);
        sap.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO,position,sap));

    }

    SaveEditListener listener = (SaveEditListener) mContext;

    class EditTextOnFocusChangeListener implements View.OnFocusChangeListener {

        private String etType;
        private int position;
        private EditText editText;


        public EditTextOnFocusChangeListener(String etType, int position, EditText editText) {
            this.etType = etType;
            this.position = position;
            this.editText = editText;
        }


        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            if (CodeConstant.ET_BATCH_NO.equals(etType) && !hasFocus) {
                // 关闭软键盘
                ZBUiUtils.hideInputWindow(view.getContext(), view);
            }

            listener.saveEditAndGetHasFocusPosition(etType, hasFocus, position, editText);

        }
    }

    /**
     * 列表输入框处理：焦点、位置、内容
     */
    public interface SaveEditListener {

        /**
         * 输入框的处理
         *
         * @param etType   输入框标识
         * @param hasFocus 有无焦点
         * @param position 位置
         * @param editText 控件
         */
        void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText);

    }

}
