package schoolshop.cgh.com.schoolshop.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
import schoolshop.cgh.com.schoolshop.common.entity.Good;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Order;
import schoolshop.cgh.com.schoolshop.common.entity.OrderDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
import schoolshop.cgh.com.schoolshop.common.entity.UserDetail;
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
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2) {
                        return new JsonPrimitive(src.getTime());
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

    public Observable<UserDetail> postRegisterPerson(UserDetail userDetail){
        return sApiService.postRegisterUser(userDetail)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Person> postRegisterPerson(int personId , List<MultipartBody.Part> parts){
        return sApiService.postRegisterIcon(personId , parts)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Person> postLoginPerson(User user){
        return sApiService.postLoginUser(user)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<List<GoodDetail>> getSellingList(int personId){
        return sApiService.getSellingList(personId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<List<GoodDetail>> getSelledList(int personId){
        return sApiService.getSelledList(personId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<List<GoodDetail>> getBoughtList(int personId){
        return sApiService.getBoughtList(personId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Good> postSell(Good good){
        return sApiService.postSell(good)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Good> postSellImage(int goodId , List<MultipartBody.Part> partList){
        return sApiService.postSellImage(goodId , partList)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Void> getUpvote(int goodId){
        return sApiService.getUpvote(goodId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Void> getView(int goodId){
        return sApiService.getView(goodId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Order> getOrderNotice(int personId){
        return sApiService.getOrderNotice(personId)
                .flatMap(orders -> Observable.from(orders))
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<List<OrderDetail>> getOrderDetailList(int personId){
        return sApiService.getOrderDetailList(personId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Void> postOrderState(int orderId , int state){
        return sApiService.postOrderState(orderId , state)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<GoodDetail> getGoodDetail(int goodId){
        return sApiService.getGoodDetail(goodId)
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<Order> postBuy(Order order){
        return sApiService.postOrder(order)
                .compose(RxUtils.rxSchedulerHelper());
    }

}
