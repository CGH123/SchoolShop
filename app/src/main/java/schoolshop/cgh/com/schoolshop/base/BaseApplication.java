package schoolshop.cgh.com.schoolshop.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import schoolshop.cgh.com.schoolshop.common.entity.Person;

/**
 * Created by HUI on 2017-04-13.
 */

public class BaseApplication extends Application {

    private static Context sAppContext;
    private static Person sAppSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static Person getAppSession(){
        if(sAppSession == null){
            sAppSession = new Person();
        }
        return sAppSession;
    }

}
