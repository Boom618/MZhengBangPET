package com.ty.zbpet.ui.activity.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.system.QuaCheckModify;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.constant.ApiNameConstant;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.system.SystemPresenter;
import com.ty.zbpet.presenter.system.SystemUiListInterface;
import com.ty.zbpet.ui.adapter.system.GridImageAdapter;
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

/**
 * @author TY on 2018/12/12.
 * 质检待办详情
 */
public class QualityCheckTodoDetailActivity extends BaseActivity implements SystemUiListInterface<QualityCheckTodoDetails.DataBean> {

    private RecyclerView reView;
    private RecyclerView reImage;
    private TextView titleName;
    private TextView tvTime;
    private EditText etDesc;

    private GridLayoutManager gridLayoutManager;
    private String arrivalOrderNo;
    private String selectTime;

    private QuaCheckTodoDetailAdapter adapter;

    private int themeId;
    private int chooseMode = PictureMimeType.ofImage();

    private GridImageAdapter imageAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> temp = new ArrayList<>();

    /**
     * 中断请求
     */
    private Disposable disposable;
    private List<QualityCheckTodoDetails.DataBean> listBeans = new ArrayList<>();
    private SystemPresenter presenter = new SystemPresenter(this);

    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        // 质检 单独 layout
        return R.layout.activity_content_qua_row_two;
    }

    @Override
    protected void initOneData() {


        arrivalOrderNo = getIntent().getStringExtra("arrivalOrderNo");

        presenter.fetchQualityCheckTodoInfo(arrivalOrderNo);

        // 质检图片样式
        themeId = R.style.picture_default_style;
        reImage = findViewById(R.id.recycler_image);

        gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        reImage.setLayoutManager(gridLayoutManager);
        imageAdapter = new GridImageAdapter(QualityCheckTodoDetailActivity.this, onAddPicClickListener);
        imageAdapter.setList(selectList);
        imageAdapter.setSelectMax(3);
        reImage.setAdapter(imageAdapter);

    }

    @Override
    protected void initTwoView() {


        reView = findViewById(R.id.rv_in_storage_detail);
        titleName = findViewById(R.id.in_storage_detail);
        etDesc = findViewById(R.id.et_desc);
        tvTime = findViewById(R.id.tv_time);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        titleName.setText("质检明细");

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZBUiUtils.showPickDate(v.getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        selectTime = ZBUiUtils.getTime(date);
                        tvTime.setText(selectTime);

                        ZBUiUtils.showToast(selectTime);
                    }
                });
            }
        });

        initToolBar(R.string.label_quality_check, "保存",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZBUiUtils.hideInputWindow(v.getContext(), v);
                quaCheckTodoSave(initRequestBody());

            }
        });

    }


    /**
     * 构建请求 RequestBody
     *
     * @return
     */
    private RequestBody initRequestBody() {

        QuaCheckModify requestBody = new QuaCheckModify();

        QuaCheckModify.MaterialCheckReportInfoBean infoBean = new QuaCheckModify.MaterialCheckReportInfoBean();
        List<QuaCheckModify.MaterialInfosBean> list = new ArrayList<>();

        SparseArray<String> fileName = DataUtils.getImageFileName();
        SparseArray<String> percentValue = DataUtils.getPercent();
        SparseArray<String> checkNumber = DataUtils.getNumber();

        int size = listBeans.size();
        // fileName : 0d43f2c6a15f2587f81d23e6e3a2e5ae.jpg,da5c82971d620334025195f262733812.png
        String fileString = "";

        for (int i = 0; i < size; i++) {

            QuaCheckModify.MaterialInfosBean bean = new QuaCheckModify.MaterialInfosBean();
            QualityCheckTodoDetails.DataBean dataBean = listBeans.get(i);
            String imageName = fileName.get(i);
            String percent = percentValue.get(i);
            String number = checkNumber.get(i);
            if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(percent)) {

                bean.setUnit(dataBean.getUnit());
                bean.setArrivalOrderNo(dataBean.getArrivalOrderNo());
                bean.setMaterialNo(dataBean.getMaterialNo());
                bean.setMaterialName(dataBean.getMaterialName());
                // 含量
                bean.setPercent(percent);
                bean.setCheckNum(number);
                list.add(bean);
            }
            if (!TextUtils.isEmpty(imageName)) {
                fileString += imageName + ",";
            }
        }

        if (list.size() == 0) {
            ZBUiUtils.showToast("请完善你要质检的信息");
            return null;
        }

        String desc = etDesc.getText().toString().trim();

        String tempFile = "";
        if (!TextUtils.isEmpty(fileString)) {
            tempFile = fileString.substring(0, fileString.length() - 1);
        }

        String orderNo = listBeans.get(0).getArrivalOrderNo();

        infoBean.setCheckDesc(desc);
        infoBean.setCheckTime(selectTime);
        infoBean.setFileName(tempFile);
        infoBean.setArrivalOrderNo(orderNo);

        requestBody.setMaterialInfos(list);
        requestBody.setMaterialCheckReportInfo(infoBean);

        String json = DataUtils.toJson(requestBody, 1);

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);

    }

    /**
     * 质检新增
     */
    private void quaCheckTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods httpMethods = new HttpMethods(ApiNameConstant.BASE_URL2);

        httpMethods.getQualityCheckTodoSave(new SingleObserver<ResponseInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;

            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    finish();
                }

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }
        }, body);

    }


    @Override
    public void showSystem(final List<QualityCheckTodoDetails.DataBean> list) {

        listBeans = list;
        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new QuaCheckTodoDetailAdapter(this, R.layout.item_sys_qua_check, list);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new QuaCheckTodoDetailAdapter.OnItemClickListener() {
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
                    presenter.updateImage(QualityCheckTodoDetailActivity.this, selectList.size() - 1, path);
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
            PictureSelector.create(QualityCheckTodoDetailActivity.this)
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        DataUtils.clearId();

        if (disposable != null) {
            disposable.dispose();
        }
    }
}
