//package com.ty.zbpet.ui.activity.system;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.luck.picture.lib.PictureSelector;
//import com.luck.picture.lib.config.PictureConfig;
//import com.luck.picture.lib.config.PictureMimeType;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.ty.zbpet.R;
//import com.ty.zbpet.ui.activity.MainActivity;
//import com.ty.zbpet.ui.adapter.system.GridImageAdapter;
//import com.ty.zbpet.ui.base.BaseActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 移库
// *
// * @author TY
// */
//public class TransferStorageActivity extends BaseActivity {
//
//    private List<LocalMedia> selectList = new ArrayList<>();
//    private List<LocalMedia> temp = new ArrayList<>();
//
//    private RecyclerView recyclerView;
//    private GridImageAdapter adapter;
//
//    private GridLayoutManager gridLayoutManager;
//
//    private int themeId;
//
//    private int chooseMode = PictureMimeType.ofImage();
//
//
//
//    @Override
//    protected void onBaseCreate(Bundle savedInstanceState) {
//
//    }
//
//    @Override
//    protected int getActivityLayout() {
//        return R.layout.activity_transfer_storage;
//    }
//
//    @Override
//    protected void initOneData() {
////        setContentView(R.layout.activity_transfer_storage);
//
//
//        themeId = R.style.picture_default_style;
//        recyclerView = findViewById(R.id.recycler);
//
//        gridLayoutManager = new GridLayoutManager(this, 3);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        recyclerView.setLayoutManager(gridLayoutManager);
//        adapter = new GridImageAdapter(TransferStorageActivity.this, onAddPicClickListener);
//        adapter.setList(selectList);
//        adapter.setSelectMax(3);
//        recyclerView.setAdapter(adapter);
//
//    }
//
//    @Override
//    protected void initTwoView() {
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片、视频、音频选择结果回调
//                    temp.addAll(selectList);
//                    selectList = PictureSelector.obtainMultipleResult(data);
//                    selectList.addAll(temp);
//                    adapter.setList(selectList);
//                    temp.clear();
//                    adapter.notifyDataSetChanged();
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
//        @Override
//        public void onAddPicClick() {
//            boolean mode = true;
//            if (mode) {
//                // 进入相册 以下是例子：不需要的api可以不写
//                PictureSelector.create(TransferStorageActivity.this)
//                        .openGallery(chooseMode)
//                        .theme(themeId)
//                        .maxSelectNum(3)
//                        .minSelectNum(1)
//                        .selectionMode(PictureConfig.SINGLE)
//                        // 是否显示拍照按钮
//                        .isCamera(true)
//                        // 是否传入已选图片
////                        .selectionMedia(selectList)
//                        // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        .previewEggs(false)
//                        // 小于100kb的图片不压缩
//                        .minimumCompressSize(100)
//                        //结果回调onActivityResult code
//                        .forResult(PictureConfig.CHOOSE_REQUEST);
//            } else {
//                // 单独拍照
//                PictureSelector.create(TransferStorageActivity.this)
//                        .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
//                        .theme(themeId)// 主题样式设置 具体参考 values/styles
//                        .maxSelectNum(3)// 最大图片选择数量
//                        .minSelectNum(1)// 最小选择数量
//                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                        .previewImage(false)// 是否可预览图片
//                        .previewVideo(false)// 是否可预览视频
//                        .enablePreviewAudio(false) // 是否可播放音频
//                        .isCamera(true)// 是否显示拍照按钮
//                        .enableCrop(false)// 是否裁剪
//                        .compress(false)// 是否压缩
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(3, 2)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(false)// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(true)// 是否圆形裁剪
//                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(true)// 是否开启点击声音
//                        .selectionMedia(selectList)// 是否传入已选图片
//                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
//                        .minimumCompressSize(100)// 小于100kb的图片不压缩
//                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                        //.rotateEnabled() // 裁剪是否可旋转图片
//                        //.scaleEnabled()// 裁剪是否可放大缩小图片
//                        //.videoQuality()// 视频录制质量 0 or 1
//                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
//                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//            }
//        }
//
//    };
//}
