package schoolshop.cgh.com.schoolshop.component;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.User;
import schoolshop.cgh.com.schoolshop.common.utils.RxUtils;

/**
 * Created by HUI on 2017-04-15.
 */

public class RetrofitSingleton {

    private static ApiInterface sApiService = null;
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;

    private void init(){
        initOkHttp();
        initRetrofit();
        sApiService = sRetrofit.create(ApiInterface.class);
    }

    private RetrofitSingleton(){
        init();
    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }


    private void initOkHttp(){

    }

    private void initRetrofit(){
        sRetrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public ApiInterface getApiService() {
        return sApiService;
    }

    public Observable<User> fetchUser() {

        return sApiService.mUserAPI()
                .flatMap(userAPI -> Observable.from(userAPI))
                .compose(RxUtils.rxSchedulerHelper());
    }



}
