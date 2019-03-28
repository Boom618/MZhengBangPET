package com.ty.zbpet.ui.adapter.system;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.eventbus.system.ImageEvent;
import com.ty.zbpet.util.DataUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @author TY on 2019/3/27.
 * 质检图片 adapter
 */
public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.ViewHolder> {

    /**
     * 最大数目
     */
    private static final int MAX_ITEM = 3;
    /**
     * 添加图片  ： 0 不显示 1 显示
     */
    private static final int NO_ADD_ITEM = 0;
    private static final int SHOW_ADD_ITEM = 1;

    private LayoutInflater mInflater;
    /**
     * 图片路径
     */
    private ArrayList<String> pathList;

    public RecyclerImageAdapter(Context context, ArrayList<String> path) {
        mInflater = LayoutInflater.from(context);
        this.pathList = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = mInflater.inflate(R.layout.item_delete_image, viewGroup, false);
        return new ViewHolder(view);
    }

    private RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String path = "";
        // 最大值
        if (getItemViewType(position) == NO_ADD_ITEM) {
            path = pathList.get(position);
            Glide.with(viewHolder.itemView.getContext())
                    .load(path)
                    .apply(options)
                    .into(viewHolder.srcImage);

        } else {
            //  当 getItemCount 等于（position + 1）说明是最后一个。  position 从 0 开始
            if (getItemCount() == position + 1) {
                viewHolder.srcImage.setImageResource(R.mipmap.tjzpicon);
                viewHolder.delImage.setVisibility(View.GONE);

                viewHolder.srcImage.setOnClickListener(v -> {
                    // 拍照和进入相册
                    EventBus.getDefault().post(new ImageEvent(viewHolder.srcImage));
                });
            } else {
                path = pathList.get(position);
                Glide.with(viewHolder.itemView.getContext())
                        .load(path)
                        .apply(options)
                        .into(viewHolder.srcImage);
            }
        }

        // int index = viewHolder.getAdapterPosition();
        // 在 ViewHolder 中可以直接调用 getAdapterPosition 获取 position
    }

    @Override
    public int getItemCount() {
        if (pathList.size() == MAX_ITEM) {
            return pathList.size();
        } else {
            return pathList.size() + 1;
        }
    }


    @Override
    public int getItemViewType(int position) {
        // 满图片列表，不显示增加图片
        if (pathList.size() == MAX_ITEM) {
            return NO_ADD_ITEM;
        } else {
            return SHOW_ADD_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 原图
         */
        ImageView srcImage;
        ImageView delImage;

        public ViewHolder(View view) {
            super(view);
            srcImage = view.findViewById(R.id.src_image);
            delImage = view.findViewById(R.id.delete_image);



            delImage.setOnClickListener(v -> {
                // 删除图片
                int index = getAdapterPosition();
                // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，
                if (index != RecyclerView.NO_POSITION) {
                    pathList.remove(index);
                    ArrayList<String> imageList = DataUtils.getImagePathList();
                    imageList.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, pathList.size());
                }
            });
        }
    }

}
