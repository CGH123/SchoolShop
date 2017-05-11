package schoolshop.cgh.com.schoolshop.modules.about.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;

/**
 * Created by HUI on 2017-05-11.
 */

public class KnowActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
