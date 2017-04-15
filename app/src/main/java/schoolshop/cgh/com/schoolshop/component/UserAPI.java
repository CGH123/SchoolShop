package schoolshop.cgh.com.schoolshop.component;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import schoolshop.cgh.com.schoolshop.common.User;

/**
 * Created by HUI on 2017-04-15.
 */

public class UserAPI {

    @Expose
    public List<User> mUser
            = new ArrayList<>();

}
