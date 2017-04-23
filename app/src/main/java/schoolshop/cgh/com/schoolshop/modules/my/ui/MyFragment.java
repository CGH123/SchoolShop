package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;

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

    private void init(){
        my_selling.setOnClickListener(this);
        my_selled.setOnClickListener(this);
        my_buy.setOnClickListener(this);
        my_favorite.setOnClickListener(this);
        my_help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.my_selling:
                intent.setClass(getActivity() , SellingActivity.class);
                startActivity(intent);
                break;
            case R.id.my_selled:
                intent.setClass(getActivity() , SelledActivity.class);
                startActivity(intent);
                break;
            case R.id.my_buy:
                intent.setClass(getActivity() , BuyActivity.class);
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
        }
    }
}
