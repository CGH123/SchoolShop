package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sackcentury.shinebuttonlib.ShineButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;

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
    @BindView(R.id.shop_shineButton)
    ShineButton shop_shineButton;
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
        shop_img1.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));
        shop_img2.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));
        shop_img3.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));
        shop_img4.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));
        shop_img5.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));
        shop_img6.setImageURI(Uri.parse(goodDetail.getGoodImagelist()));

        icon_skip.setVisibility(View.VISIBLE);
        person_layout.setOnClickListener(this);
        shop_group.setOnCheckedChangeListener(this);
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
            default:
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
                break;
        }
    }
}
