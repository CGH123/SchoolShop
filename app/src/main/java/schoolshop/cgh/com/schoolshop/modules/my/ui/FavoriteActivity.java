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
import schoolshop.cgh.com.schoolshop.modules.my.adapter.MyFavAdapter;

/**
 * Created by HUI on 2017-04-23.
 */

public class FavoriteActivity extends BaseActivity{

    @BindView(R.id.id_toolbar)
    Toolbar id_toolbar;
    @BindView(R.id.his_recyclerView)
    RecyclerView mRecyclerView;

    private int type;
    private ActionBar ab;
    private MyFavAdapter mAdapter;
    private List<GoodDetail> goodList = new ArrayList<>();

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
            case Constant.Type_Fav:
                fetchFavList(Constant.PERSON.getPersonId());
                break;
        }
    }

    private void init(){
        setSupportActionBar(id_toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.github);
        ab.setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyFavAdapter(goodList);
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
}
