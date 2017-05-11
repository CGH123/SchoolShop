package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.GoodOrder;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.ui.ShopDetailActivity;
import schoolshop.cgh.com.schoolshop.modules.my.adapter.MyPageAdapter;

/**
 * Created by HUI on 2017-04-23.
 */

public class TradeActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar id_toolbar;
    @BindView(R.id.his_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.error)
    TextView error;

    private ActionBar ab;
    private MyPageAdapter mAdapter;
    private List<GoodOrder> goodList = new ArrayList<>();
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getInt("type");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //网络获取数据
        switch (type){
            case Constant.TYPE_Selling:
                fetchSellingList(Constant.PERSON.getPersonId());
                break;
            case Constant.TYPE_Selled:
                fetchSelledList(Constant.PERSON.getPersonId());
                break;
            case Constant.TYPE_Buy:
                fetchBuyList(Constant.PERSON.getPersonId());
                break;
        }
    }

    private void init(){
        setSupportActionBar(id_toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.app_icon);
        ab.setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyPageAdapter(goodList);
        mRecyclerView.setAdapter(mAdapter);

        //设置点击事件
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodDetail", goodList.get(position).getGoodDetail());
            bundle.putSerializable("order", goodList.get(position).getOrder());
            intent.putExtras(bundle);
            intent.setClass(this, ShopDetailActivity.class);
            startActivity(intent);
        });
    }


    /**
     * 发布中的商品
     */
    private void fetchSellingList(int personId){
         RetrofitSingleton.getInstance()
                .getSellingList(personId)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<List<GoodOrder>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        if(goodList.size() == 0){
                            error.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        Log.e("error" , "selling finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodOrder> goodOrders) {
                        goodList.clear();
                        goodList.addAll(goodOrders);
                    }
                });
    }

    /**
     * 已售出的商品
     */
    private void fetchSelledList(int personId){
        RetrofitSingleton.getInstance()
                .getSelledList(personId)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<List<GoodOrder>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        if(goodList.size() == 0){
                            error.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        Log.e("error" , "selled finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodOrder> goodOrders) {
                        goodList.clear();
                        goodList.addAll(goodOrders);
                    }
                });
    }

    /**
     * 已购买的商品
     */
    private void fetchBuyList(int personId){
        RetrofitSingleton.getInstance()
                .getBoughtList(personId)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<List<GoodOrder>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        if(goodList.size() == 0){
                            error.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }else{
                            error.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        Log.e("error" , "buy finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodOrder> goodOrders) {
                        goodList.clear();
                        goodList.addAll(goodOrders);
                    }
                });
    }

}
