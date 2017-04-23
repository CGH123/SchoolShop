package schoolshop.cgh.com.schoolshop.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.User1;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
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
        //TODO 自定义自己的oktttp3
    }

    private void initRetrofit(){
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                })
                .create();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public ApiInterface getApiService() {
        return sApiService;
    }


    /**
     * 下面为对与服务器交互的数据进行线程调度
     */

    public Observable<User1> fetchUser() {
        return sApiService.mUserAPI()
                .flatMap(userAPI -> Observable.from(userAPI))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Result<Void>> postUser() {
        return sApiService.mUserAPI1(new User1("300" , "chenguagnhui"))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<User1> postPicture(List<MultipartBody.Part> parts) {
        return sApiService.uploadMemberIcon(parts)
                .flatMap(userList -> Observable.from(userList))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<GoodDetail> getGoodList(int offset , int limit , boolean goodDone) {
        return sApiService.getGoodList(offset , limit , goodDone)
                .flatMap(goodDetails -> Observable.from(goodDetails))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<GoodDetail> getGoodKindList(int offset , int limit , int kind , boolean goodDone){
        return sApiService.getGoodKindList(offset , limit , kind , goodDone)
                .flatMap(goodDetails -> Observable.from(goodDetails))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<GoodDetail> getPersonGoodList(int personId){
        return sApiService.getPersonGoodList(personId)
                .flatMap(goodDetails -> Observable.from(goodDetails))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Person> getPersonInfo(int personId){
        return sApiService.getPersonInfo(personId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Person> postRegisterPerson(User user, Person person , List<MultipartBody.Part> parts){
        return sApiService.postRegisterUser(user, person , parts)
                .compose(RxUtils.rxSchedulerHelper());
    }

}
