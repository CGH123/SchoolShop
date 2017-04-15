package schoolshop.cgh.com.schoolshop.component;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.common.User;

/**
 * Created by HUI on 2017-04-15.
 */

public interface ApiInterface {

    String HOST = "http://127.0.0.1:8080/";

    @GET("http://192.168.93.2:8080/ssm/user/test")
    Observable<List<User>> mUserAPI();

}
