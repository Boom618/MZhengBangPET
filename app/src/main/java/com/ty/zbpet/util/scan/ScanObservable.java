package com.ty.zbpet.util.scan;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.pda.scanner.Scanner;
import com.ty.zbpet.ui.MainApp;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.TLog;
import com.ty.zbpet.util.UIUtils;

import java.nio.charset.Charset;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author TY on 2018/11/9.
 * <p>
 * 扫码 Observable
 */
public class ScanObservable {


    static ACache mCache = MainApp.mCache;
    /**
     * 扫描成功提示音
     */
    static ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM,
            ToneGenerator.MAX_VOLUME);

    private ScanBoxInterface scanBox;

    public ScanObservable(ScanBoxInterface scanBoxInterface) {
        this.scanBox = scanBoxInterface;
    }

    /**
     * 扫码功能，采用 RxJava 不用考虑线程问题
     * @param scanReader 扫码器
     * @param position  不在列表中处理，可以不用传 Position ，扫码的结果保证在 mCache 缓存中了
     * @return
     */
    public Disposable scanBox(final Scanner scanReader, final int position) {
        /**
         * subscribeOn 只会调用一次，subscribeOn() 的线程控制可以从事件发出的开端就造成影响
         * observeOn 观察者（订阅的角色）可以多次指定线程，observeOn() 控制的是它后面的线程
         *
         * 【注意】
         *  没有 AndroidSchedulers.mainThread ，不能 Toast
         *
         */
        Disposable disposable = Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> emitter) throws Exception {

                byte[] id = scanReader.decode(1000);
                if (null != id) {
                    emitter.onNext(id);
                    //toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
                } else {

                    //toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
                    //emitter.onError(new Throwable("车库码为空"));
                }

            }
        })

                // 指定 subscribe() 发生在 IO 线程
                .subscribeOn(Schedulers.io())
                // 类似 newThread 扫描解码
                .observeOn(Schedulers.io())
                .map(new Function<byte[], String>() {
                    @Override
                    public String apply(@NonNull byte[] bytes) throws Exception {

                        String utf8 = new String(bytes, Charset.forName(CodeConstant.CHARSET_UTF8));
                        if (utf8.contains(CodeConstant.UNICODE_STRING)) {
                            utf8 = new String(bytes, Charset.forName(CodeConstant.CHARSET_GBK));
                        }
                        String resultStr = utf8 + "\n";

                        return resultStr;
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        TLog.e("doOnNext: 保存成功：" + s + "\n");

                        // 存 position ，解决第一次进来所有列表都是同样的值
                        mCache.put(CodeConstant.SCAN_BOX_POSITION,position);
                        mCache.put(CodeConstant.SCAN_BOX_KEY, s);
                    }
                })
                // 指定 Subscriber 的回调发生在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        scanBox.ScanSuccess(position,s);

                        // 存 position ，解决第一次进来所有列表都是同样的值
                        //mCache.put(CodeConstant.SCAN_BOX_POSITION,position);
                        //mCache.put(CodeConstant.SCAN_BOX_KEY, s);
                        UIUtils.showToast("扫码成功 " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        TLog.e("失败：" + throwable.getMessage() + "\n");
                        // toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_AUTOREDIAL_LITE);
                        UIUtils.showToast("扫码失败" + throwable.getMessage());
                    }
                });

        return disposable;
    }
}
