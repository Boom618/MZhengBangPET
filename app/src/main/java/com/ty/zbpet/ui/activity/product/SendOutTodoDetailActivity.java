package com.ty.zbpet.ui.activity.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.ResponseInfo;
import com.ty.zbpet.bean.product.ProductTodoDetails;
import com.ty.zbpet.bean.product.ProductTodoSave;
import com.ty.zbpet.net.HttpMethods;
import com.ty.zbpet.presenter.product.ProductUiListInterface;
import com.ty.zbpet.presenter.product.SendOutPresenter;
import com.ty.zbpet.ui.activity.ScanBoxCodeActivity;
import com.ty.zbpet.ui.adapter.diffadapter.SendOutDiffUtil;
import com.ty.zbpet.ui.adapter.product.SendOutTodoDetailAdapter;
import com.ty.zbpet.ui.base.BaseActivity;
import com.ty.zbpet.ui.widght.NormalAlertDialog;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBLog;
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
 * 发货出库 待办详情
 *
 * @author TY
 */
public class SendOutTodoDetailActivity extends BaseActivity implements ProductUiListInterface<ProductTodoDetails.ListBean>
        , SendOutTodoDetailAdapter.SaveEditListener {


    private RecyclerView reView;
    private TextView tvTime;
    private TextView backGoods;
    private TextView tvPath;
    private TextView tvType;
    private EditText etDesc;
    private Button addShip;

    private SendOutTodoDetailAdapter adapter;

    private String selectTime;
    private String sapOrderNo;

    /**
     * 生产、客户、成品 信息
     */
    private String productInfo;
    private String customerInfo;
    private String goodsInfo;

    /**
     * 商品种类 原数据
     */
    private List<ProductTodoDetails.ListBean> rawData = new ArrayList<>();

    private List<ProductTodoDetails.ListBean> oldList = new ArrayList<>();
    private List<ProductTodoDetails.ListBean> newList = new ArrayList<>();

    private final static int REQUEST_SCAN_CODE = 1;
    private final static int RESULT_SCAN_CODE = 2;

    private SendOutPresenter presenter = new SendOutPresenter(this);

    /**
     * 箱码
     */
    private ArrayList<String> boxCodeList = new ArrayList<>();

    /**
     * 列表 ID
     */
    private int itemId = -1;

    /**
     * 保存用户在输入框中的数据
     */
    private SparseArray<String> numberArray = new SparseArray(10);
    private SparseArray<String> startCodeArray = new SparseArray(10);
    private SparseArray<String> endCodeArray = new SparseArray(10);
    private SparseArray<String> sapArray = new SparseArray(10);
    private SparseArray<ArrayList<String>> carCodeArray = new SparseArray(10);
    /**
     * 库位码 ID
     */
    private SparseArray<String> positionId = new SparseArray(10);

    /**
     * 仓库 ID
     */
    private String warehouseId;

    /**
     * 商品种类 用户 下拉选择
     */
    private List<String> goodsName;


    @Override
    protected void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_content_row_two;
    }

    @Override
    protected void initOneData() {
        sapOrderNo = getIntent().getStringExtra("sapOrderNo");

        productInfo = getIntent().getStringExtra("productInfo");
        customerInfo = getIntent().getStringExtra("customerInfo");
        goodsInfo = getIntent().getStringExtra("goodsInfo");

        presenter.fetchSendOutTodoInfo(sapOrderNo);
    }

    @Override
    protected void initTwoView() {

        initToolBar(R.string.label_send_out_storage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZBUiUtils.hideInputWindow(SendOutTodoDetailActivity.this, view);
                sendOutTodoSave(initTodoBody());
            }
        });

        reView = findViewById(R.id.rv_in_storage_detail);
        tvTime = findViewById(R.id.tv_time);
        backGoods = findViewById(R.id.in_storage_detail);
        addShip = findViewById(R.id.add_ship);

        tvPath = findViewById(R.id.tv_path);
        tvType = findViewById(R.id.tv_type);
        etDesc = findViewById(R.id.et_desc);

        SimpleDateFormat format = new SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA);
        selectTime = format.format(new Date());

        tvTime.setText(selectTime);
        backGoods.setText("发货明细");

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

        addShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rawSize = rawData.size();
                int oldSize = oldList.size();
                if (oldSize < rawSize) {
                    // 有列表删除操作 ，保证 newList 只有 oldList 中的数据 + 添加的一个数据
                    newList.clear();
                    newList.addAll(oldList);
                    ProductTodoDetails.ListBean bean = new ProductTodoDetails.ListBean();
                    ProductTodoDetails.ListBean info = rawData.get(0);

                    bean.setSapOrderNo(info.getSapOrderNo());
                    bean.setGoodsName(info.getGoodsName());
                    bean.setGoodsId(info.getGoodsId());
                    bean.setGoodsNo(info.getGoodsNo());
                    bean.setUnitS(info.getUnitS());
                    bean.setOrderNumber(info.getOrderNumber());
                    newList.add(bean);

                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SendOutDiffUtil(oldList, newList));
                    diffResult.dispatchUpdatesTo(adapter);

                    // TODO　清除老数据,更新原数据,清除临时保存数据
                    oldList.clear();
                    oldList.addAll(newList);
                    //newList.clear();

                    ZBUiUtils.showToast("添加发货出库");
                } else {
                    ZBUiUtils.showToast("谢谢");
                }
            }
        });


    }

    /**
     * 出库 保存
     */
    private void sendOutTodoSave(RequestBody body) {

        if (body == null) {
            return;
        }

        HttpMethods.getInstance().getShipTodoSave(new SingleObserver<ResponseInfo>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onError(Throwable e) {
                ZBUiUtils.showToast(e.getMessage());
            }

            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                if (CodeConstant.SERVICE_SUCCESS.equals(responseInfo.getTag())) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                } else {
                    ZBUiUtils.showToast(responseInfo.getMessage());
                }
            }
        }, body);
    }

    /**
     * 构建 保存 的 Body
     *
     * @return
     */
    private RequestBody initTodoBody() {

        ProductTodoSave requestBody = new ProductTodoSave();

        List<ProductTodoSave.DetailsBean> detail = new ArrayList<>();

        // 获取 用户选择商品的信息: 【那一列中的第几个】
        SparseArray<Integer> goodsArray = DataUtils.getGoodsId();

        // int size = rawData.size();
        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            List<String> boxQrCode = carCodeArray.get(i);
            String number = numberArray.get(i);
            String startCode = startCodeArray.get(i);
            String endCode = endCodeArray.get(i);
            String sap = sapArray.get(i);
            String id = positionId.get(i);

            // 商品信息
            String goodsId;
            String goodsNo;
            String goodsName;

            ProductTodoSave.DetailsBean bean = new ProductTodoSave.DetailsBean();
            if (!TextUtils.isEmpty(number) && boxQrCode != null) {

                // 默认只有第一个
                int which = 0;
                try {
                    which = goodsArray.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 从原数据中的列表中获取
                goodsId = rawData.get(which).getGoodsId();
                goodsNo = rawData.get(which).getGoodsNo();
                goodsName = rawData.get(which).getGoodsName();

                bean.setPositionId(id);
                bean.setStartQrCode(startCode);
                bean.setEndQrCode(endCode);
                bean.setNumber(number);
                bean.setSapMaterialBatchNo(sap);

                bean.setGoodsId(goodsId);
                bean.setGoodsNo(goodsNo);

                bean.setBoxQrCode(boxQrCode);

                detail.add(bean);
            } else {
                // 跳出当前循环、不处理
                continue;
            }
        }
        // 没有合法的操作数据,不请求网络
        if (detail.size() == 0) {
            ZBUiUtils.showToast("请完善您要出库的信息");
            return null;
        }

        String remark = etDesc.getText().toString().trim();
        String time = tvTime.getText().toString().trim();

        requestBody.setProductInfo(productInfo);
        requestBody.setCustomerInfo(customerInfo);
        requestBody.setGoodsInfo(goodsInfo);

        requestBody.setDetails(detail);
        requestBody.setWarehouseId(warehouseId);
        requestBody.setSapOrderNo(sapOrderNo);
        requestBody.setInTime(time);
        requestBody.setRemark(remark);


        String json = DataUtils.toJson(requestBody, 1);
        ZBLog.e("JSON " + json);
        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json);
    }


    @Override
    public void showProduct(final List<ProductTodoDetails.ListBean> list) {

        // 保存原数据
        rawData.addAll(list);

        goodsName = new ArrayList<>();
        int size = rawData.size();
        for (int i = 0; i < size; i++) {
            goodsName.add(rawData.get(i).getGoodsName());
        }

        oldList = list;
        // 列表最开始不显示数据，所以 用 rawData 保存数据后，清除 oldList
        oldList.clear();

        if (adapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(ResourceUtil.getContext());
            reView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(10), false));
            reView.setLayoutManager(manager);
            adapter = new SendOutTodoDetailAdapter(this, R.layout.item_product_detail_send_out_todo, newList);
            reView.setAdapter(adapter);

            adapter.setOnItemClickListener(new SendOutTodoDetailAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final View view, RecyclerView.ViewHolder holder, final int position) {

                    View rlDetail = holder.itemView.findViewById(R.id.gone_view);
                    ImageView ivArrow = holder.itemView.findViewById(R.id.iv_arrow);
                    TextView tvName = holder.itemView.findViewById(R.id.tv_name);
                    Button bindingCode = holder.itemView.findViewById(R.id.btn_binding_code);
                    bindingCode.setText("箱码出库");

                    // 选择商品
                    final TextView selectGoods = holder.itemView.findViewById(R.id.tv_select_ware);
                    selectGoods.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ZBUiUtils.selectDialog(view.getContext(), CodeConstant.SELECT_GOODS, position, goodsName, selectGoods);
                        }
                    });
                    //SparseArray<Integer> goodsId = DataUtils.getGoodsId();
                    //int which = goodsId.get(position);
                    if (rlDetail.getVisibility() == View.VISIBLE) {
                        rlDetail.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.ic_collapse);
                        //tvName.setText(list.get(which).getGoodsName());
                    } else {
                        rlDetail.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.ic_expand);
                    }

                    bindingCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemId = position;
                            Intent intent = new Intent(SendOutTodoDetailActivity.this, ScanBoxCodeActivity.class);
                            intent.putExtra("itemId", itemId);
                            intent.putExtra(CodeConstant.PAGE_STATE, true);
                            intent.putStringArrayListExtra("boxCodeList", carCodeArray.get(itemId));
                            startActivityForResult(intent, REQUEST_SCAN_CODE);
                        }
                    });

                    //List<BuyInTodoDetails.ListBean.WarehouseListBean> warehouseList = list.get(position).getWarehouseList();


                    ZBUiUtils.hideInputWindow(view.getContext(), view);

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, final int position) {

                    TextView tvGoods = holder.itemView.findViewById(R.id.tv_select_ware);
                    String goodsName = tvGoods.getText().toString().trim();

                    ZBUiUtils.deleteItemDialog(view.getContext(), goodsName, new NormalAlertDialog.onNormalOnclickListener() {
                        @Override
                        public void onNormalClick(NormalAlertDialog dialog) {
                            dialog.dismiss();
                            newList.clear();
                            // TODO 犯错： 不能 new ,导致 DiffUtil 更新出错
                            //newList = new ArrayList<>(oldList);
                            newList.addAll(oldList);
                            newList.remove(position);

                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SendOutDiffUtil(oldList, newList), true);
                            diffResult.dispatchUpdatesTo(adapter);
                            // oldList 也应该删除一列数据
                            oldList.remove(position);
                        }
                    });


                    return true;
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_CODE && resultCode == RESULT_SCAN_CODE) {
            itemId = data.getIntExtra("itemId", -1);
            warehouseId = data.getStringExtra("warehouseId");
            boxCodeList = data.getStringArrayListExtra("boxCodeList");
            carCodeArray.put(itemId, boxCodeList);
        }
    }


    @Override
    public void saveEditAndGetHasFocusPosition(String etType, Boolean hasFocus, int position, EditText editText) {

        String textContent = editText.getText().toString().trim();

        if (CodeConstant.ET_NUMBER.equals(etType)) {
            numberArray.put(position, textContent);
        } else if (CodeConstant.ET_BATCH_NO.equals(etType)) {
            sapArray.put(position, textContent);
        } else if (CodeConstant.ET_START_CODE.equals(etType)) {
            startCodeArray.put(position, textContent);
        } else if (CodeConstant.ET_END_CODE.equals(etType)) {
            endCodeArray.put(position, textContent);
        }

    }
}
