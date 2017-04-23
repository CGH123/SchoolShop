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
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;

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
     * 根据personId获取Person类
     */
    @GET("person/info/{personId}")
    Observable<Person> getPersonInfo(@Path("personId") int personId);

    /**
     * 用户注册
     */
    @Multipart
    @POST("register")
    Observable<Person> postRegisterUser(@Body User user1, @Body Person person , @Part List<MultipartBody.Part> partList);


}
