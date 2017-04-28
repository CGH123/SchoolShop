package schoolshop.cgh.com.schoolshop.common.utils;

import android.widget.Toast;

import static schoolshop.cgh.com.schoolshop.base.BaseApplication.getAppContext;

/**
 * Created by HugoXie on 16/5/23.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * Info:
 */
public class ToastUtil {

    public static void showShort(String msg) {
        Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(getAppContext(), msg, Toast.LENGTH_LONG).show();
    }
}
