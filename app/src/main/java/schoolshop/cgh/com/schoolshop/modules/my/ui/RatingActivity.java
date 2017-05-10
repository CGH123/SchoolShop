package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hedgehog.ratingbar.RatingBar;

import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.common.utils.ToastUtil;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-05-10.
 */

public class RatingActivity extends Activity implements View.OnClickListener{
    RatingBar mBar;
    Button confirm;
    Button cancel;

    float count;
    int orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        orderId = getIntent().getExtras().getInt("orderId");
        initView();
    }

    private void initView(){
        mBar = (RatingBar) findViewById(R.id.ratingBar);
        mBar.setOnRatingChangeListener(RatingCount -> {
            count = RatingCount;
        });

        confirm = (Button) findViewById(R.id.rating_confirm);
        cancel  = (Button) findViewById(R.id.rating_cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rating_confirm:
                fetchOrderState(orderId , 3)
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                ToastUtil.showShort("评分出错，请重新评分");
                                finish();
                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
                break;
            case R.id.rating_cancel:
                finish();
                break;
        }
    }

    private Observable<Void> fetchOrderState(int orderId , int state){
        return RetrofitSingleton.getInstance()
                .postOrderState(orderId , state);
    }
}
