package schoolshop.cgh.com.schoolshop.common;

import com.google.gson.annotations.SerializedName;

/**
 * 测试网络部分使用到的一个局部类
 * Created by HUI on 2017-04-15.
 */

public class User1 {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public User1() {

    }

    public User1(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
