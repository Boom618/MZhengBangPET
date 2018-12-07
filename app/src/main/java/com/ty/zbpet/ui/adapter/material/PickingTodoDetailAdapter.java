package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ViewSetValue;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 领料出库 待办详情
 */
public class PickingTodoDetailAdapter extends CommonAdapter<MaterialDetailsIn.ListBean> {

    private Context context;

    public PickingTodoDetailAdapter(Context context, int layoutId, List<MaterialDetailsIn.ListBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDetailsIn.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getMaterialName())
                .setText(R.id.tv_num, list.getOrderNumber() + "  " + list.getUnitS())
                .setText(R.id.tv_box_num, "含量：" + list.getConcentration() + "%")
                .setText(R.id.tv_box_num_unit, list.getZKG() + " ZKG ? ")
                .setText(R.id.bulk_num, "库存量：?? ");

        // 1、库位码
        final EditText etCode = holder.itemView.findViewById(R.id.et_code);
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.setInputType(InputType.TYPE_NULL);
        // ScanObservable.scanBox 扫码成功保存的 ID 和 Value
        final String value = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY);

        ViewSetValue.setValue(value, position, etCode);
        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // 关闭软键盘
                ZBUiUtils.hideInputWindow(context, view);
                // 焦点改变 接口回调
                listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, etCode);
            }
        });

        // 2、数量
        EditText etNumber = holder.itemView.findViewById(R.id.et_number);
//        String numString = etNumber.getText().toString().trim();
//        etNumber.setText(numString);

        etNumber.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, etNumber));

        // 3、Ssp NO
        EditText etSapNo = holder.itemView.findViewById(R.id.et_batch_no);
//        String sapNo = etSapNo.getText().toString().trim();
//        etSapNo.setText(sapNo);

        etSapNo.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, etSapNo));

    }


    SaveEditListener listener = (SaveEditListener) mContext;


    /**
     * 输入框 Focus Listener
     */
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
                ZBUiUtils.hideInputWindow(context, view);
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
