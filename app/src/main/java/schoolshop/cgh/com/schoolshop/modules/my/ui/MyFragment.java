package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-04-13.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.my_selling)
    RelativeLayout my_selling;
    @BindView(R.id.my_selled)
    RelativeLayout my_selled;
    @BindView(R.id.my_buy)
    RelativeLayout my_buy;
    @BindView(R.id.my_favorite)
    RelativeLayout my_favorite;
    @BindView(R.id.my_help)
    RelativeLayout my_help;
    @BindView(R.id.layout_icon)
    LinearLayout layout_icon;
    @BindView(R.id.icon_name)
    TextView icon_name;
    @BindView(R.id.icon_sex)
    SimpleDraweeView icon_sex;
    @BindView(R.id.icon_icon)
    SimpleDraweeView icon_icon;
    @BindView(R.id.per_score)
    TextView per_score;
    @BindView(R.id.per_sell)
    TextView per_sell;
    @BindView(R.id.per_buy)
    TextView per_buy;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my , container , false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Constant.PERSON == null){
            icon_name.setText("点击登录");
        }else{
            icon_icon.setImageURI(Uri.parse(Constant.PERSON.getPersonIcon()));
            icon_name.setText(Constant.PERSON.getPersonName());
            if(Constant.PERSON.getPersonSex()){
                icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.man));
            }else{
                icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.woman));
            }

            //对评分、销售商品进行设置
            fetchPerson(Constant.PERSON.getPersonId())
                    .subscribe(person -> {
                        per_score.setText(String.valueOf(person.getPersonGrade()));
                        per_sell.setText(String.valueOf(person.getPersonSellnum()) + "件");
                        per_buy.setText(String.valueOf(person.getPersonBuynum()) + "件");
                    });
        }
    }

    private void init(){
        my_selling.setOnClickListener(this);
        my_selled.setOnClickListener(this);
        my_buy.setOnClickListener(this);
        my_favorite.setOnClickListener(this);
        my_help.setOnClickListener(this);
        layout_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if(Constant.PERSON == null){
            intent.setClass(getActivity() , LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity() , "请先登录" , Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()){
            case R.id.my_selling:
                intent.putExtra("type" , Constant.TYPE_Selling);
                intent.setClass(getActivity() , TradeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_selled:
                intent.putExtra("type" , Constant.TYPE_Selled);
                intent.setClass(getActivity() , TradeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_buy:
                intent.putExtra("type" , Constant.TYPE_Buy);
                intent.setClass(getActivity() , TradeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_favorite:
                intent.putExtra("type" , Constant.Type_Fav);
                intent.setClass(getActivity() , FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.my_help:
                intent.setClass(getActivity() , HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_icon:
                if(Constant.PERSON == null){
                    intent.setClass(getActivity() , LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 根据personId获取商品信息
     */
    private Observable<Person> fetchPerson(int personId){
        return RetrofitSingleton.getInstance()
                .getPersonInfo(personId)
                .compose(this.bindToLifecycle());
    }

}
