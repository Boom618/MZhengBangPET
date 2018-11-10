package com.ty.zbpet.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.MaterialDetailsData;
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
public class MaterialDetailAdapter extends CommonAdapter {

    private List<MaterialDetailsData.DetailsBean> infoList;
    private Context context;


    public MaterialDetailAdapter(Context context, int layoutId, List datas) {
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
        MaterialDetailsData.DetailsBean info = infoList.get(position);

        TextView tvName = itemView.findViewById(R.id.tv_name);
        tvName.setText(info.getMaterialName());

        ImageView ivArrow = itemView.findViewById(R.id.iv_arrow);

        TextView tvNum = itemView.findViewById(R.id.tv_num);
        tvNum.setText(info.getOrderNumber() + info.getUnit());

        RelativeLayout rlSummary = itemView.findViewById(R.id.rl_summary);

        TextView tvBoxNum = itemView.findViewById(R.id.tv_box_num);
        tvBoxNum.setText("含量：" + info.getConcentration());

        // 1、入库数量
        EditText etInNum = itemView.findViewById(R.id.et_bulk_num);
        // 通过设置tag，防止 position 紊乱
        etInNum.setTag(position);
        String numString = etInNum.getText().toString().trim();
//        etInNum.addTextChangedListener(new TextSwitcher(position, CodeConstant.ET_BULK_NUM));
        etInNum.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, numString));

        // 2、库位码
        final EditText etCode = itemView.findViewById(R.id.et_code);
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.setInputType(InputType.TYPE_NULL);

        // 清除车库码
        final ImageView ivDel = itemView.findViewById(R.id.iv_del_code);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCode.getText().clear();
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
                tempCode = etCode.getText().toString().trim();
                // 方式二 ：扫码输入值
                tempCode = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY);

                if (tempCode.length() > 0) {
                    ivDel.setVisibility(View.VISIBLE);
                } else {
                    ivDel.setVisibility(View.GONE);
                }
                // 焦点改变 接口回调
                listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, tempCode);
            }
        });

        etCode.setTag(position);

        // 3、sap 物料批次号
        EditText batchNo = itemView.findViewById(R.id.et_batch_no);
        batchNo.setTag(position);
//        batchNo.addTextChangedListener(new TextSwitcher(position, CodeConstant.ET_BATCH_NO));
        String batchNoStr = batchNo.getText().toString().trim();
        batchNo.setOnFocusChangeListener(new EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, batchNoStr));


        RelativeLayout rlDetail = itemView.findViewById(R.id.rl_detail);

        LinearLayout llRoot = itemView.findViewById(R.id.ll_root);

    }

    SaveEditListener listener = (SaveEditListener) mContext;

    // 自定义EditText的监听类
//    class TextSwitcher implements TextWatcher {
//
//        private int position;
//        private String etType;
//
//        public TextSwitcher(int position,String etType) {
//            this.position = position;
//            this.etType = etType;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            //用户输入完毕后，处理输入数据，回调给主界面处理
//            //SaveEditListener listener = (SaveEditListener) context;
//
//            if (!TextUtils.isEmpty(s)) {
//                listener.SaveEdit(etType, position, s.toString());
//            }
//
//        }
//    }
    private void text() {
    }

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

        //void getHasFocusAndPosition(Boolean hasFocus,String textContent,int position);
    }
}
