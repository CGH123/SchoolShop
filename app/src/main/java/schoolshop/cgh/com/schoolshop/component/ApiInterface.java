package schoolshop.cgh.com.schoolshop.component;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.entity.Eval;
import schoolshop.cgh.com.schoolshop.common.entity.Good;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.GoodOrder;
import schoolshop.cgh.com.schoolshop.common.entity.Order;
import schoolshop.cgh.com.schoolshop.common.entity.OrderDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
import schoolshop.cgh.com.schoolshop.common.entity.UserDetail;

/**
 * 和服务器交互数据的API接口
 * Created by HUI on 2017-04-15.
 */

public interface ApiInterface {
    //主机服务器
    //String HOST = "http://localhost:8080/ssm/";
    String HOST = "http://101.200.223.115:8080/ssm/";
    //String HOST = "http://192.168.71.2:8080/ssm/";

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
     * 根据goodId获取商品详细信息
     */
    @GET("good/info/detail/{goodId}")
    Observable<GoodDetail> getGoodDetail(@Path("goodId") int goodId);

    /**
     * 获取用户销售商品列表信息
     */
    @GET("good/info/person/{personId}")
    Observable<List<GoodDetail>> getPersonGoodList(@Path("personId") int personId);

    /**
     * 获取模糊字查询的功能
     */
    @GET("good/fuzzy/search/{goodName}/{offset}/{limit}/{goodDone}")
    Observable<List<GoodDetail>> getGoodDetailByName(@Path("goodName") String goodName, @Path("offset") int offset, @Path("limit") int limit, @Path("goodDone") boolean goodDone);

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
    Observable<Good> postSellImage(@Path("goodId") int goodId, @Part List<MultipartBody.Part> partList);

    /**
     * 删除发布的商品
     */
    @GET("good/delete/{goodId}")
    Observable<Integer> postDeleteGood(@Path("goodId") int goodId);

    /**
     * 点赞量增加
     */
    @GET("good/info/upvote/{goodId}/{nums}")
    Observable<Void> getUpvote(@Path("goodId") int goodId, @Path("nums") int nums);

    /**
     * 浏览量增加
     */
    @GET("good/info/view/{goodId}")
    Observable<Void> getView(@Path("goodId") int goodId);

    /**
     * 根据personId获取Person类
     */
    @GET("person/info/{personId}")
    Observable<Person> getPersonInfo(@Path("personId") int personId);

    /**
     * 用户信息修改
     */
    @Multipart
    @POST("person/update/{personId}/{personSex}/{personName}")
    Observable<Person> postRegisterIcon(@Path("personId") int personId, @Path("personSex") Boolean personSex, @Path("personName") String personName, @Part List<MultipartBody.Part> partList);

    /**
     * 用户Icon上传
     */
    @Multipart
    @POST("user/register/icon/{personId}")
    Observable<Person> postRegisterIcon(@Path("personId") int personId, @Part List<MultipartBody.Part> partList);

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
    Observable<List<GoodOrder>> getSellingList(@Path("personId") int personId);

    /**
     * 查看我已售出的商品
     */
    @GET("order/selled/{personId}")
    Observable<List<GoodOrder>> getSelledList(@Path("personId") int personId);

    /**
     * 查看我买到的商品
     */
    @GET("order/bought/{personId}")
    Observable<List<GoodOrder>> getBoughtList(@Path("personId") int personId);

    /**
     * 检查服务器消息通知
     */
    @GET("order/check/{personId}")
    Observable<List<Order>> getOrderNotice(@Path("personId") int personId);

    /**
     * 删除订单信息
     */
    @GET("order/delete/{orderId}")
    Observable<Integer> getOrderDelete(@Path("orderId") int orderId);

    /**
     * 查看还没处理的订单信息
     */
    @GET("order/check/message/{personId}")
    Observable<List<OrderDetail>> getOrderDetailList(@Path("personId") int personId);

    /**
     * 回应订单的状态更新
     */
    @POST("order/state/{orderId}/{state}")
    Observable<Order> postOrderState(@Path("orderId") int orderId, @Path("state") int state);

    /**
     * 购买下单
     */
    @POST("order/buy")
    Observable<Order> postOrder(@Body Order order);

    /**
     * 查看收藏夹中的信息
     */
    @GET("favorite/good/list/{personId}")
    Observable<List<GoodDetail>> getFavoriteGood(@Path("personId") int personId);

    /**
     * 删除收藏夹中的信息
     */
    @GET("favorite/delete/{personId}/{goodId}")
    Observable<Integer> getDeleteFav(@Path("personId") int personId, @Path("goodId") int goodId);

    /**
     * 添加收藏夹中的信息
     */
    @GET("favorite/insert/{personId}/{goodId}")
    Observable<Integer> getInsertFav(@Path("personId") int personId, @Path("goodId") int goodId);

    /**
     * 客户评价信息记录
     */
    @POST("eval/insert")
    Observable<Integer> postInsertEval(@Body Eval eval);

}
