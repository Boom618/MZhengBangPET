package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialTodoDetailsData;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author TY on 2018/11/5.
 * <p>
 * 到货入库 （待办/已办 详情）
 */
public class MaterialTodoDetailAdapter extends CommonAdapter {

    private List<MaterialTodoDetailsData.DetailsBean> infoList;
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
        MaterialTodoDetailsData.DetailsBean info = infoList.get(position);

        TextView tvName = itemView.findViewById(R.id.tv_name);
        tvName.setText(info.getMaterialName());

        TextView tvNum = itemView.findViewById(R.id.tv_num);
        tvNum.setText(info.getOrderNumber() + info.getUnit());

        TextView tvBoxNum = itemView.findViewById(R.id.tv_box_num);
        tvBoxNum.setText("含量：" + info.getConcentration());

        // 1、入库数量
        EditText etInNum = itemView.findViewById(R.id.et_bulk_num);
        // 通过设置tag，防止 position 紊乱
        etInNum.setTag(position);
        String numString = etInNum.getText().toString().trim();
        etInNum.setText(numString);
        etInNum.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, numString));

        // 2、库位码
        final EditText etCode = itemView.findViewById(R.id.et_code);
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.setInputType(InputType.TYPE_NULL);
        // ScanObservable.scanBox 扫码成功保存的 ID 和 Value
        final String value = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY);

        etCode.setText(value);


        // 清除车库码
        final ImageView ivDel = itemView.findViewById(R.id.iv_del_code);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCode.getText().clear();
                ivDel.setVisibility(View.GONE);
            }
        });

        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                // 关闭软键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String tempCode;
                // 方式一 ：手动输入值
                //tempCode = etCode.getText().toString().trim();
                // 方式二 ：扫码输入值
                //tempCode = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY);
                if (TextUtils.isEmpty(value)) {
                    return;
                }

                if (value.length() > 0) {
                    ivDel.setVisibility(View.VISIBLE);
                } else {
                    ivDel.setVisibility(View.GONE);
                }
                // 焦点改变 接口回调
                listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, value);
            }
        });

        etCode.setTag(position);

        // 3、sap 物料批次号
        EditText batchNo = itemView.findViewById(R.id.et_batch_no);
        batchNo.setTag(position);
        String batchNoStr = batchNo.getText().toString().trim();
        batchNo.setText(batchNoStr);
        batchNo.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, batchNoStr));

    }

    SaveEditListener listener = (SaveEditListener) mContext;


    /**
     * 输入框 Focus Listener
     */
    class EditTextOnFocusChangeListener implements View.OnFocusChangeListener {

        private String etType;
        private int position;
        private String content;

        public EditTextOnFocusChangeListener(String etType, int position, String content) {
            this.etType = etType;
            this.position = position;
            this.content = content;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            if (CodeConstant.ET_BATCH_NO.equals(etType) && !hasFocus) {
                // 关闭软键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            listener.saveEditAndGetHasFocusPosition(etType, hasFocus, position, content);
        }
    }

    /**
     * 列表输入框处理：焦点、位置、内容
     */
    public interface SaveEditListener {

        /**
         * 输入框的处理
         *
         * @param etType      输入框标识
         * @param hasFocus    有无焦点
         * @param position    位置
         * @param textContent 内容
         */
        void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, String textContent);

    }
}
