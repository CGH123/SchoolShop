package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.ui.ShopDetailActivity;
import schoolshop.cgh.com.schoolshop.modules.my.adapter.MyPageAdapter;

/**
 * Created by HUI on 2017-04-23.
 */

public class SellingActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar id_toolbar;
    @BindView(R.id.his_recyclerView)
    RecyclerView mRecyclerView;

    private ActionBar ab;
    private MyPageAdapter mAdapter;
    private List<GoodDetail> goodList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        setSupportActionBar(id_toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.github);
        ab.setDisplayHomeAsUpEnabled(true);
        int type = getIntent().getExtras().getInt("type");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyPageAdapter(goodList);
        mRecyclerView.setAdapter(mAdapter);

        //设置点击事件
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodDetail", goodList.get(position));
            intent.putExtras(bundle);
            intent.setClass(this, ShopDetailActivity.class);
            startActivity(intent);
        });

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
            case Constant.Type_Fav:
                fetchFavList(Constant.PERSON.getPersonId());
                break;
        }

    }


    /**
     * 发布中的商品
     */
    private void fetchSellingList(int personId){
         RetrofitSingleton.getInstance()
                .getSellingList(personId)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "selling finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetails) {
                        goodList.clear();
                        goodList.addAll(goodDetails);
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
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "selled finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetails) {
                        goodList.clear();
                        goodList.addAll(goodDetails);
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
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "buy finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetails) {
                        goodList.clear();
                        goodList.addAll(goodDetails);
                    }
                });
    }

    /**
     * 查看收藏夹中的内容
     */
    private void fetchFavList(int personId){
        RetrofitSingleton.getInstance()
                .getFavoriteGood(personId)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "fav finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error" , e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetails) {
                        goodList.clear();
                        goodList.addAll(goodDetails);
                    }
                });
    }

    /**
     * 查询订单的状态信息
     * TODO
     */

}
