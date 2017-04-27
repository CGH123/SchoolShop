package schoolshop.cgh.com.schoolshop.modules.message.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.trello.rxlifecycle.components.RxActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.OrderDetail;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.ui.ShopDetailActivity;

/**
 * Created by HUI on 2017-04-27.
 */

public class OrderDetailActivity extends RxActivity implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.msg_layout)
    RelativeLayout msg_layout;
    @BindView(R.id.msg_name)
    TextView msg_name;
    @BindView(R.id.msg_num)
    TextView msg_num;
    @BindView(R.id.msg_money)
    TextView msg_money;
    @BindView(R.id.msg_person_name)
    TextView msg_person_name;
    @BindView(R.id.msg_phone)
    TextView msg_phone;
    @BindView(R.id.msg_date)
    TextView msg_date;
    @BindView(R.id.msg_image)
    SimpleDraweeView msg_image;
    @BindView(R.id.msg_group)
    RadioGroup msg_group;

    private OrderDetail orderDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        orderDetail = (OrderDetail)intent.getSerializableExtra("orderDetail");

        init();
    }

    private void init(){
        msg_name.setText(orderDetail.getGoodName());
        msg_num.setText("数量:" + orderDetail.getGoodNum());
        msg_money.setText("合计:" + orderDetail.getGoodNum()*orderDetail.getGoodPrice() + "元");
        msg_person_name.setText(orderDetail.getPersonName());
        msg_phone.setText(orderDetail.getUserAccount());
        msg_date.setText(TimeUtils.getMD(orderDetail.getOrderTime()));
        msg_image.setImageURI(Uri.parse(orderDetail.getGoodImagelist().split(";")[0]));

        msg_group.setOnCheckedChangeListener(this);
        msg_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_layout:
                fetchGoodDetail(orderDetail.getGoodId())
                        .subscribe(goodDetail -> {
                            Intent intent = new Intent();
                            intent.putExtra("goodDetail", goodDetail);
                            intent.setClass(this, ShopDetailActivity.class);
                            startActivity(intent);
                        });
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.msg_configure:
                fetchOrderState(orderDetail.getOrderId() , 2)
                        .subscribe(aVoid -> {
                            Toast.makeText(this , "确认收货成功" , Toast.LENGTH_SHORT).show();
                            finish();
                        });
                break;
        }
    }

    /**
     * 网络部分
     */
    private Observable<GoodDetail> fetchGoodDetail(int goodId){
        return RetrofitSingleton.getInstance()
                .getGoodDetail(goodId)
                .compose(this.bindToLifecycle());
    }

    private Observable<Void> fetchOrderState(int orderId , int state){
        return RetrofitSingleton.getInstance()
                .postOrderState(orderId , state);
    }
}

