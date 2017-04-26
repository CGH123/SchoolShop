package schoolshop.cgh.com.schoolshop.component;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.User1;
import schoolshop.cgh.com.schoolshop.common.entity.Good;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
import schoolshop.cgh.com.schoolshop.common.entity.UserDetail;

/**
 * 和服务器交互数据的API接口
 * Created by HUI on 2017-04-15.
 */

public interface ApiInterface {
    //主机服务器
    //String HOST = "http://101.200.223.115:8080/ssm/";
    String HOST = "http://192.168.93.2:8080/ssm/";

    @GET("user/test")
    Observable<List<User1>> mUserAPI();

    @POST("user1/test1")
    Observable<Result<Void>> mUserAPI1(@Body User1 user1);

    @Multipart
    @POST("user/test2")
    Observable<List<User1>> uploadMemberIcon(@Part List<MultipartBody.Part> partList);

    /**
     * 获取商品列表信息
     */
    @GET("good/info/list/{offset}/{limit}/{goodDone}")
    Observable<List<GoodDetail>> getGoodList(@Path("offset") int offset, @Path("limit") int limit, @Path("goodDone") boolean goodDone);

    /**
     * 获取分类后的商品列表信息
     */
    @GET("good/info/list/{offset}/{limit}/{kind}/{goodDone}")
    Observable<List<GoodDetail>> getGoodKindList(@Path("offset") int offset, @Path("limit") int limit, @Path("kind") int kind, @Path("goodDone") boolean goodDone);

    /**
     * 获取用户销售商品列表信息
     */
    @GET("good/info/person/{personId}")
    Observable<List<GoodDetail>> getPersonGoodList(@Path("personId") int personId);

    /**
     * 发布销售的商品
     */
    @POST("good/sell")
    Observable<Good> postSell(@Body Good good);

    /**
     * 发布销售的商品
     */
    @Multipart
    @POST("good/sell/{goodId}")
    Observable<Good> postSellImage(@Path("goodId") int goodId , @Part List<MultipartBody.Part> partList);

    /**
     * 根据personId获取Person类
     */
    @GET("person/info/{personId}")
    Observable<Person> getPersonInfo(@Path("personId") int personId);

    /**
     * 用户Icon上传
     */
    @Multipart
    @POST("user/register/icon/{personId}")
    Observable<Person> postRegisterIcon(@Path("personId") int personId , @Part List<MultipartBody.Part> partList);

    /**
     * 用户注册
     */
    @POST("user/register/user")
    Observable<UserDetail> postRegisterUser(@Body UserDetail userDetail);

    /**
     * 用户登录
     */
    @POST("user/login")
    Observable<Person> postLoginUser(@Body User user);

    /**
     * 查看我发布中的商品
     */
    @GET("order/selling/{personId}")
    Observable<List<GoodDetail>> getSellingList(@Path("personId") int personId);

    /**
     * 查看我已售出的商品
     */
    @GET("order/selled/{personId}")
    Observable<List<GoodDetail>> getSelledList(@Path("personId") int personId);

    /**
     * 查看我买到的商品
     */
    @GET("order/buy/{personId}")
    Observable<List<GoodDetail>> getBuyList(@Path("personId") int personId);

}
