package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.PersonPageAdapter;

/**
 * Created by HUI on 2017-04-21.
 */

public class PersonPageActivity extends BaseActivity{
    @BindView(R.id.icon_icon)
    SimpleDraweeView icon_icon;
    @BindView(R.id.icon_name)
    TextView icon_name;
    @BindView(R.id.icon_sex)
    SimpleDraweeView icon_sex;
    @BindView(R.id.icon_info)
    TextView icon_info;
    @BindView(R.id.per_score)
    TextView per_score;
    @BindView(R.id.per_sell)
    TextView per_sell;
    @BindView(R.id.per_buy)
    TextView per_buy;
    @BindView(R.id.per_mList)
    RecyclerView mRecyclerView;

    private Intent intent;
    private Integer personId;
    private PersonPageAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<GoodDetail> goodList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perlist);
        ButterKnife.bind(this);

        init();
        network();
    }

    public void init(){
        intent = getIntent();
        personId = intent.getExtras().getInt("personId");
        layoutManager = new LinearLayoutManager(this);
        goodList = new ArrayList<>();
        mAdapter = new PersonPageAdapter(goodList);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //设置监听事件
        mAdapter.setOnItemClickListener(position -> {
            intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodDetail", goodList.get(position));
            intent.putExtras(bundle);
            intent.setClass(this, ShopDetailActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 网络功能部分
     */
    public void network(){
        fetchPerson(personId)
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
                        Log.e("error" , "Person done");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(Person person) {
                        icon_icon.setImageURI(Uri.parse(person.getPersonIcon()));
                        icon_name.setText(person.getPersonName());
                        if(person.getPersonSex()){
                            icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.man));
                        }else{
                            icon_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.woman));
                        }
                        per_score.setText(String.valueOf(person.getPersonGrade()));
                        per_sell.setText(String.valueOf(person.getPersonSellnum()));
                        per_buy.setText(String.valueOf(person.getPersonBuynum()));
                    }
                });


        fetchGoodList(personId)
                .doOnSubscribe(() -> {
                    goodList.clear();
                })
                .subscribe(new Subscriber<GoodDetail>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "Good done="+goodList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(GoodDetail goodDetail) {
                        goodList.add(goodDetail);
                    }
                });
    }


    /**
     * 根据personId获取商品信息类
     */
    private Observable<GoodDetail> fetchGoodList(int personId){
        return  RetrofitSingleton.getInstance()
                .getPersonGoodList(personId)
                .compose(this.bindToLifecycle());
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
