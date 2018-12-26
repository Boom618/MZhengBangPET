package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDetailsIn;
import com.ty.zbpet.listener.EditWatcher;
import com.ty.zbpet.ui.adapter.viewholder.BottomViewHolder;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ViewSetValue;
import com.ty.zbpet.util.ZBUiUtils;

import java.util.List;


/**
 * @author TY on 2018/11/5.
 * <p>
 * 到货入库 （待办 详情）
 */
public class MaterialTodoDetailAdapterR extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MaterialDetailsIn.ListBean> lists;
    private RecyclerView.ViewHolder holder;
    private Context context;

    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;

    public MaterialTodoDetailAdapterR(Context context, List<MaterialDetailsIn.ListBean> lists) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (ONE_ITEM == viewType) {
            View view = inflater.inflate(R.layout.item_main_middle_todo, viewGroup, false);
            holder = new MiddleViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_main_bottom_other, viewGroup, false);
            holder = new BottomViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        int itemViewType = viewHolder.getItemViewType();

        if (itemViewType == ONE_ITEM) {

            View itemView = viewHolder.itemView;
            MaterialDetailsIn.ListBean info = lists.get(position);

            TextView name = itemView.findViewById(R.id.tv_name);
            name.setText(info.getMaterialName());

            TextView tvNum = itemView.findViewById(R.id.tv_num);
            tvNum.setText(info.getOrderNumber() + info.getUnitS());

            TextView tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            tvBoxNum.setText("含量：" + info.getConcentration() + "%");

            // 1 ZKG
            EditText zkg = itemView.findViewById(R.id.et_zkg);
            zkg.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_ZKG_INT));

            // 2、库位码
            EditText etCode = itemView.findViewById(R.id.et_code);
            etCode.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_CODE_INT));

            etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    DataUtils.setCodeFocus(position, hasFocus);
                }
            });
            String value = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY);

            ViewSetValue.setValue(value, position, etCode);

            // 3、入库数量
            EditText etInNum = itemView.findViewById(R.id.et_bulk_num);
            etInNum.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_NUMBER_INT));

            // 4、sap 物料批次号
            EditText sap = itemView.findViewById(R.id.et_batch_no);
            sap.addTextChangedListener(new EditWatcher(position, CodeConstant.ET_SAP_INT));
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (TextUtils.isEmpty(lists.get(position).getTag())) {
            return ONE_ITEM;
        }
        return TWO_ITEM;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    /**
     * 中间 服务器的 list 列表
     */
    private static class MiddleViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvNum;
        TextView tvBoxNum;
        View rlDetail;
        ImageView ivArrow;
        EditText zkg;
        EditText etCode;
        EditText etInNum;
        EditText sap;

        public MiddleViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            rlDetail = itemView.findViewById(R.id.rl_detail);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            zkg = itemView.findViewById(R.id.et_zkg);
            etCode = itemView.findViewById(R.id.et_code);
            etInNum = itemView.findViewById(R.id.et_bulk_num);
            sap = itemView.findViewById(R.id.et_batch_no);

            // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
            etCode.setInputType(InputType.TYPE_NULL);


            ivArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                    ZBUiUtils.hideInputWindow(v.getContext(), v);
                }
            });
        }
    }

}
