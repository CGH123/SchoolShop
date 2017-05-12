package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hedgehog.ratingbar.RatingBar;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.common.entity.Eval;
import schoolshop.cgh.com.schoolshop.common.entity.Order;
import schoolshop.cgh.com.schoolshop.common.utils.ToastUtil;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-05-10.
 */

public class RatingActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.ratingBar)
    RatingBar mBar;
    @BindView(R.id.rating_eval)
    EditText rating_eval;
    @BindView(R.id.rating_confirm)
    Button confirm;
    @BindView(R.id.rating_cancel)
    Button cancel;

    double count;
    int orderId;
    String eval_detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        orderId = getIntent().getExtras().getInt("orderId");
        initView();
    }

    private void initView(){
        mBar.setOnRatingChangeListener(RatingCount -> {
            count = RatingCount;
        });

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rating_confirm:
                eval_detail = rating_eval.getText().toString();
                fetchOrderState(orderId , 3)
                        .subscribe(order -> {
                            fetchInsertEval(new Eval(order.getGoodId(), order.getOrderSellid(), count, eval_detail, new Date()))
                                    .subscribe(integer -> {
                                        if(integer == 1){
                                            setResult(RESULT_OK);
                                            ToastUtil.showShort("评价成功");
                                            finish();
                                        }else{
                                            ToastUtil.showShort("评分出错，请重新评分");
                                            finish();
                                        }
                                    });
                        });
                break;
            case R.id.rating_cancel:
                finish();
                break;
        }
    }

    private Observable<Order> fetchOrderState(int orderId , int state){
        return RetrofitSingleton.getInstance()
                .postOrderState(orderId , state);
    }

    private Observable<Integer> fetchInsertEval(Eval eval){
        return RetrofitSingleton.getInstance()
                .postInsertEval(eval);
    }

}
