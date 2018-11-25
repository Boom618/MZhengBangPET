package com.ty.zbpet.ui.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * @author TY on 2018/11/23.
 * 1 、创建 LiveData 实例（LiveData 配合 ViewModel 一起使用 ）
 *          你也可以不使用 ViewModel，但是一定要做到 LiveData 中保存的 数据 和
 *          组件 （ Activity /Fg 实现 LifecycleObserver 接口）分离
 */
public class NameViewModel extends ViewModel {

    private MutableLiveData<String> mCurrentName;

    private MutableLiveData<List<String>> mNameListData;

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
        }
        return mCurrentName;
    }

    public MutableLiveData<List<String>> getNameListData() {
        if (mNameListData == null) {
            mNameListData = new MutableLiveData<>();
        }
        return mNameListData;
    }
}
