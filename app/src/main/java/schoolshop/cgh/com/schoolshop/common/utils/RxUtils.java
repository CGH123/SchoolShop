package schoolshop.cgh.com.schoolshop.common.utils;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HugoXie on 16/5/19.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * Info: 封装 Rx 的一些方法
 */
public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 可自定义线程
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper(Scheduler scheduler) {
        return tObservable -> tObservable.subscribeOn(scheduler)
            .unsubscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread());
    }

}
