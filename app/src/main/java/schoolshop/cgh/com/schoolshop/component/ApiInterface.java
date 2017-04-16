package schoolshop.cgh.com.schoolshop.component;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.User;

/**
 * Created by HUI on 2017-04-15.
 */

public interface ApiInterface {
    //主机服务器 String HOST = http://101.200.223.115:8080/
    String HOST = "http://192.168.93.2:8080/";

    @GET("ssm/user/test")
    Observable<List<User>> mUserAPI();

    @POST("ssm/user/test1")
    Observable<Result<Void>> mUserAPI1(@Body User user);

    @Multipart
    @POST("ssm/user/test2")
    Observable<List<User>> uploadMemberIcon(@Part List<MultipartBody.Part> partList);

}
