package com.ty.zbpet.ui.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.presenter.system.SystemPresenter;
import com.ty.zbpet.presenter.system.SystemUiListInterface;
import com.ty.zbpet.ui.adapter.system.GridImageAdapter;
import com.ty.zbpet.ui.adapter.system.QuaCheckDoneDetailAdapter;
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ResourceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

/**
 * @author TY on 2018/12/12.
 */
public class QualityCheckDoneDetailActivity extends BaseActivity implements SystemUiListInterface<QualityCheckDoneDetails.DataBean> {


    private RecyclerView reView;
    private RecyclerView reImage;
    private TextView titleName;
    private TextView tvTime;
    private EditText etDesc;

    private GridLayoutManager gridLayoutManager;
    private String arrivalOrderNo;
    private String selectTime;

    private QuaCheckDoneDetailAdapter adapter;

    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();

    private GridImageAdapter imageAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> temp = new ArrayList<>();

    /**
     * 中断请求
     */
    private Disposable disposable;
    private List<QualityCheckDoneDetails.DataBean> listBeans = new ArrayList<>();
    private SystemPresenter presenter = new SystemPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
//        return R.layout.activity_content_row_two;
        return R.layout.activity_content_qua_row_two;
    }

    @Override
    protected void initOneData() {

        arrivalOrderNo = getIntent().getStringExtra("arrivalOrderNo");

        presenter.fetchQualityCheckDoneInfo(arrivalOrderNo);
        // 质检图片样式
        themeId = R.style.picture_default_style;
        reImage = findViewById(R.id.recycler_image);

        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reImage.setLayoutManager(gridLayoutManager);
        imageAdapter = new GridImageAdapter(QualityCheckDoneDetailActivity.this, onAddPicClickListener);


    }

    @Override
    protected void initTwoView() {

        reView = findViewById(R.id.rv_in_storage_detail);
        titleName = findViewById(R.id.in_storage_detail);
        tvTime = findViewById(R.id.tv_time);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        titleName.setText("质检明细");

        initToolBar(R.string.label_check);

    }

    @Override
    public void showSystem(List<QualityCheckDoneDetails.DataBean> list) {

        listBeans = list;
        if (adapter == null) {
            // 图片 RecyclerView

//            selectList.clear();
//            for (int i = 0; i < list.size(); i++) {
//                LocalMedia media = new LocalMedia();
//                media.setPath("");
//                selectList.add(media);
//            }
//
//            imageAdapter.setList(selectList);
//            imageAdapter.setSelectMax(3);
//            reImage.setAdapter(imageAdapter);


            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new QuaCheckDoneDetailAdapter(this, R.layout.item_product_detail_two_done, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new QuaCheckDoneDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);

                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }

    }


    //========================  onActivityResult ======================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    temp.addAll(selectList);
                    selectList = PictureSelector.obtainMultipleResult(data);
                    String path = selectList.get(0).getPath();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    selectList.addAll(temp);
                    presenter.updateImage(QualityCheckDoneDetailActivity.this, selectList.size() - 1, path);
                    imageAdapter.setList(selectList);
                    temp.clear();
                    imageAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(QualityCheckDoneDetailActivity.this)
                    .openGallery(chooseMode)
                    .theme(themeId)
                    .maxSelectNum(3)
                    .minSelectNum(1)
                    .selectionMode(PictureConfig.SINGLE)
                    // 是否显示拍照按钮
                    .isCamera(true)
                    // 是否传入已选图片
//                        .selectionMedia(selectList)
                    // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .previewEggs(false)
                    // 小于100kb的图片不压缩
                    .minimumCompressSize(100)
                    //结果回调onActivityResult code
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }

    };
}
