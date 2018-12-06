package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 外采入库 待办详情
 */
public class BuyInTodoDetailAdapter extends CommonAdapter<ProductDetailsIn.ListBean> {


    public BuyInTodoDetailAdapter(Context context, int layoutId, List<ProductDetailsIn.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsIn.ListBean list, final int position) {
        String warehouseName = DataUtils.getUserInfo().getWarehouseList().get(0).getWarehouseName();

        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_num, "/?" + list.getUnitS())// list.getOrderNumber() +
                .setText(R.id.tv_select_ware, warehouseName);


        // 入库数量
        EditText number = holder.itemView.findViewById(R.id.et_number);
        number.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, number));

        // 开始码
        EditText startCode = holder.itemView.findViewById(R.id.et_start_code);
        startCode.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_START_CODE, position, startCode));

        // 结束码
        EditText endCode = holder.itemView.findViewById(R.id.et_end_code);
        endCode.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_END_CODE, position, endCode));

        // sap 物料批次号
        EditText batchNo = holder.itemView.findViewById(R.id.et_sap);
        batchNo.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, batchNo));
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
