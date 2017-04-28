package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.Date;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Order;
import schoolshop.cgh.com.schoolshop.common.utils.SharedPreferenceUtil;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.my.ui.LoginActivity;

/**
 * Created by HUI on 2017-04-15.
 */

public class ShopDetailActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener , View.OnClickListener{
    @BindView(R.id.icon_icon)
    SimpleDraweeView icon_icon;
    @BindView(R.id.icon_name)
    TextView icon_name;
    @BindView(R.id.icon_sex)
    SimpleDraweeView icon_sex;
    @BindView(R.id.icon_info)
    TextView icon_info;
    @BindView(R.id.shop_upVote)
    ShineButton shop_upVote;
    @BindView(R.id.shop_favorite)
    ShineButton shop_favorite;
    @BindView(R.id.shop_tradeName)
    TextView shop_tradeName;
    @BindView(R.id.shop_price)
    TextView shop_price;
    @BindView(R.id.shop_original_price)
    TextView shop_original_price;
    @BindView(R.id.shop_remained)
    TextView shop_remained;
    @BindView(R.id.shop_pageView)
    TextView shop_pageView;
    @BindView(R.id.shop_detail_time)
    TextView shop_detail_time;
    @BindView(R.id.shop_detail)
    TextView shop_detail;
    @BindView(R.id.shop_img1)
    SimpleDraweeView shop_img1;
    @BindView(R.id.shop_img2)
    SimpleDraweeView shop_img2;
    @BindView(R.id.shop_img3)
    SimpleDraweeView shop_img3;
    @BindView(R.id.shop_img4)
    SimpleDraweeView shop_img4;
    @BindView(R.id.shop_img5)
    SimpleDraweeView shop_img5;
    @BindView(R.id.shop_img6)
    SimpleDraweeView shop_img6;
    @BindView(R.id.shop_group)
    RadioGroup shop_group;
    @BindView(R.id.shop_buy)
    RadioButton shop_buy;
    @BindView(R.id.icon_skip)
    ImageView icon_skip;
    @BindView(R.id.layout_icon)
    LinearLayout person_layout;

    //其他Activity传递过来的信息
    GoodDetail goodDetail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        goodDetail = (GoodDetail)intent.getSerializableExtra("goodDetail");

        initView();
        initShine();
    }

    /**
     * 实现浏览量增加的功能
     */
    @Override
    protected void onStart() {
        super.onStart();
        fetchView(goodDetail.getGoodId())
                .subscribe(aVoid -> {
                    Log.d("success" , "浏览量增加成功");
                });
    }

    private void initShine(){
        //检查收藏功能的button
        Set<String> favSet = SharedPreferenceUtil.getInstance().getFav();
        if(favSet.contains(String.valueOf(goodDetail.getGoodId()))){
            shop_favorite.setChecked(true);
        }

        //检查点赞功能的button
        Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote();
        if(upVoteSet.contains(String.valueOf(goodDetail.getGoodId()))){
            shop_upVote.setChecked(true);
        }

    }

    private void initView(){
        icon_icon.setImageURI(Uri.parse(goodDetail.getPersonIcon()));
        icon_name.setText(goodDetail.getPersonName());
        if(goodDetail.getPersonSex()){
            icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.man));
        }else{
            icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.woman));
        }
        shop_tradeName.setText(goodDetail.getGoodName());
        shop_price.setText(goodDetail.getGoodPrice() + "元");
        shop_original_price.setText(goodDetail.getGoodOriginalPrice() + "元");
        shop_remained.setText("剩余" + goodDetail.getGoodNum());
        shop_pageView.setText("浏览量：" + goodDetail.getGoodViews());
        shop_detail_time.setText(TimeUtils.getDiff(goodDetail.getGoodTime()));
        shop_detail.setText("  " +goodDetail.getGoodDetail());

        //对图片进行处理
        String[] imagePath = goodDetail.getGoodImagelist().split(";");
        switch (imagePath.length){
            case 0:
                break;
            case 1:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setVisibility(View.GONE);
                shop_img3.setVisibility(View.GONE);
                shop_img4.setVisibility(View.GONE);
                shop_img5.setVisibility(View.GONE);
                shop_img6.setVisibility(View.GONE);
                break;
            case 2:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setImageURI(Uri.parse(imagePath[1]));
                shop_img3.setVisibility(View.GONE);
                shop_img4.setVisibility(View.GONE);
                shop_img5.setVisibility(View.GONE);
                shop_img6.setVisibility(View.GONE);
                break;
            case 3:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setImageURI(Uri.parse(imagePath[1]));
                shop_img3.setImageURI(Uri.parse(imagePath[2]));
                shop_img4.setVisibility(View.GONE);
                shop_img5.setVisibility(View.GONE);
                shop_img6.setVisibility(View.GONE);
                break;
            case 4:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setImageURI(Uri.parse(imagePath[1]));
                shop_img3.setImageURI(Uri.parse(imagePath[2]));
                shop_img4.setImageURI(Uri.parse(imagePath[3]));
                shop_img5.setVisibility(View.GONE);
                shop_img6.setVisibility(View.GONE);
                break;
            case 5:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setImageURI(Uri.parse(imagePath[1]));
                shop_img3.setImageURI(Uri.parse(imagePath[2]));
                shop_img4.setImageURI(Uri.parse(imagePath[3]));
                shop_img5.setImageURI(Uri.parse(imagePath[4]));
                shop_img6.setVisibility(View.GONE);
                break;
            case 6:
                shop_img1.setImageURI(Uri.parse(imagePath[0]));
                shop_img2.setImageURI(Uri.parse(imagePath[1]));
                shop_img3.setImageURI(Uri.parse(imagePath[2]));
                shop_img4.setImageURI(Uri.parse(imagePath[3]));
                shop_img5.setImageURI(Uri.parse(imagePath[4]));
                shop_img6.setImageURI(Uri.parse(imagePath[5]));
                break;
        }
        if(goodDetail.getGoodDone()){
            shop_buy.setText("已下架");
        }else{

            shop_buy.setOnClickListener(this);
        }

        //shop_group.setOnCheckedChangeListener(this);
        shop_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        icon_skip.setVisibility(View.VISIBLE);
        person_layout.setOnClickListener(this);
        shop_upVote.setOnClickListener(this);
        shop_favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_icon:
                Intent intent = new Intent();
                intent.putExtra("personId", goodDetail.getPersonId());
                intent.setClass(this, PersonPageActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_buy:
                if(Constant.PERSON != null) {
                    Order order = new Order(0, goodDetail.getGoodId(), goodDetail.getPersonId(),
                            Constant.PERSON.getPersonId(), 0, new Date());
                    fetchOrder(order)
                            .subscribe(new Subscriber<Order>() {
                                @Override
                                public void onCompleted() {
                                    Toast.makeText(getApplicationContext() , "购买成功，等待卖家确认" , Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error" , e.toString());
                                }

                                @Override
                                public void onNext(Order order) {
                                    if(order == null || order.getOrderId() == 0){
                                        Toast.makeText(getApplicationContext() , "商品已购买" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Intent intent1 = new Intent();
                    intent1.setClass(this , LoginActivity.class);
                    startActivity(intent1);
                    Toast.makeText(this , "请先登录" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shop_upVote:
                //点赞功能实现
                if(!shop_upVote.isChecked()){
                    //已经点赞，执行取消点赞动作
                    fetchDownVote(goodDetail.getGoodId());
                }else{
                    //还没点赞，执行点赞动作
                    fetchUpVote(goodDetail.getGoodId());
                }
                break;
            case R.id.shop_favorite:
                //收藏功能实现
                if(!shop_favorite.isChecked()){
                    //已经收藏，执行取消收藏动作
                    fetchDeleteFav(Constant.PERSON.getPersonId() , goodDetail.getGoodId());
                }else{
                    //还没收藏，执行收藏动作
                    fetchInsertFav(Constant.PERSON.getPersonId() , goodDetail.getGoodId());
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.shop_contact:
                //TODO 联系商家
                break;
            case R.id.shop_buy:
                //TODO 立刻购买
                if(Constant.PERSON != null) {
                    Order order = new Order(0, goodDetail.getGoodId(), goodDetail.getPersonId(),
                            Constant.PERSON.getPersonId(), 0, new Date());
                    fetchOrder(order)
                            .subscribe(new Subscriber<Order>() {
                                @Override
                                public void onCompleted() {
                                    Toast.makeText(getApplicationContext() , "购买成功，等待卖家确认" , Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error" , e.toString());
                                }

                                @Override
                                public void onNext(Order order) {
                                    if(order == null || order.getOrderId() == 0){
                                        Toast.makeText(getApplicationContext() , "商品已购买" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Intent intent = new Intent();
                    intent.setClass(this , LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(this , "请先登录" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 网络部分,实现服务起中的浏览量增加
     */
    private void fetchUpVote(int goodId){
        Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote();
        RetrofitSingleton.getInstance()
            .getUpvote(goodId , 1)
            .compose(this.bindToLifecycle())
            .subscribe(aVoid ->{
                upVoteSet.add(String.valueOf(goodDetail.getGoodId()));
                SharedPreferenceUtil.getInstance().putUpvote(upVoteSet);
            });
    }

    private void fetchDownVote(int goodId){
        Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote();
        RetrofitSingleton.getInstance()
                .getUpvote(goodId , -1)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid ->{
                    upVoteSet.remove(String.valueOf(goodDetail.getGoodId()));
                    SharedPreferenceUtil.getInstance().putUpvote(upVoteSet);
                });
    }

    private void fetchDeleteFav(int personId , int goodId){
        Set<String> favSet = SharedPreferenceUtil.getInstance().getFav();
        RetrofitSingleton.getInstance()
            .getDeleteFav(personId , goodId)
            .compose(this.bindToLifecycle())
            .subscribe(integer -> {
                if(integer == 1){
                    favSet.remove(String.valueOf(goodDetail.getGoodId()));
                    SharedPreferenceUtil.getInstance().putFav(favSet);
                    Log.e("error" , "cancel upVote success");
                }
            });
    }

    public void fetchInsertFav(int personId , int goodId){
        Set<String> favSet = SharedPreferenceUtil.getInstance().getFav();
        RetrofitSingleton.getInstance()
            .getInsertFav(personId , goodId)
            .compose(this.bindToLifecycle())
            .subscribe(integer -> {
                if(integer == 1){
                    favSet.add(String.valueOf(goodDetail.getGoodId()));
                    SharedPreferenceUtil.getInstance().putFav(favSet);
                    Log.e("error" , "add upVote success");
                }
            });
    }

    private Observable<Void> fetchView(int goodId){
        return RetrofitSingleton.getInstance()
                .getView(goodId)
                .compose(this.bindToLifecycle());
    }

    private Observable<Order> fetchOrder(Order order){
        return RetrofitSingleton.getInstance()
                .postBuy(order)
                .compose(this.bindToLifecycle());
    }

}
