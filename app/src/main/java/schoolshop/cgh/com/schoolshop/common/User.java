package schoolshop.cgh.com.schoolshop.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HUI on 2017-04-15.
 */

public class User {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public User() {

    }

    public User(String id, String name) {
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
