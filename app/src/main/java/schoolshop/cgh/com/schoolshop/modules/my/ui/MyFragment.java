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
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;

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
                intent.setClass(getActivity() , SellingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_selled:
                //intent.setClass(getActivity() , SelledActivity.class);
                intent.putExtra("type" , Constant.TYPE_Selled);
                intent.setClass(getActivity() , SellingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_buy:
                //intent.setClass(getActivity() , BuyActivity.class);
                intent.putExtra("type" , Constant.TYPE_Buy);
                intent.setClass(getActivity() , SellingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_favorite:
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
}
