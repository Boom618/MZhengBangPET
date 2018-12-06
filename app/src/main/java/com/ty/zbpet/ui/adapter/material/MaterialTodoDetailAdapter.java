package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
 * @author TY on 2018/11/5.
 * <p>
 * 到货入库 （待办 详情）
 */
public class MaterialTodoDetailAdapter extends CommonAdapter {

    private List<MaterialDetailsIn.ListBean> infoList;
    private Context context;


    public MaterialTodoDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.context = context;
        this.infoList = datas;
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        View itemView = holder.itemView;
        MaterialDetailsIn.ListBean info = infoList.get(position);

        TextView tvName = itemView.findViewById(R.id.tv_name);
        tvName.setText(info.getMaterialName());

        TextView tvNum = itemView.findViewById(R.id.tv_num);
        tvNum.setText(info.getOrderNumber() + info.getUnitS());

        TextView tvBoxNum = itemView.findViewById(R.id.tv_box_num);
        tvBoxNum.setText("含量：" + info.getConcentration());

        // 1 ZKG
        EditText zkg = itemView.findViewById(R.id.et_zkg);

        String zkgString = ACache.get(context).getAsString(CodeConstant.ET_ZKG);
        ViewSetValue.setValue(zkgString, position, zkg);

        zkg.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_ZKG, position, zkg));

        // 2、库位码
        final EditText etCode = itemView.findViewById(R.id.et_code);
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

                listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, etCode);
            }
        });

        etCode.setTag(position);

        // 3、入库数量
        EditText etInNum = itemView.findViewById(R.id.et_bulk_num);
        // 通过设置tag，防止 position 紊乱
        etInNum.setTag(position);
        String numString = etInNum.getText().toString().trim();
        etInNum.setText(numString);
        etInNum.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, etInNum));

        // 4、sap 物料批次号
        EditText sap = itemView.findViewById(R.id.et_batch_no);
        sap.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, sap));

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
//            if (!hasFocus) {
//            }

        }
    }

    /**
     * 列表输入框处理：焦点、位置、内容
     */
    public interface SaveEditListener {

        /**
         * 输入框的处理
         *
         * @param etType   输入框标识  code  sap
         * @param hasFocus 有无焦点
         * @param position 位置
         * @param editText 内容控件
         */
        void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText);

    }
}
