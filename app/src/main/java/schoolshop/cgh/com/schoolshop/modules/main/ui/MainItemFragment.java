package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.common.User;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.utils.ImageUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.HomeShopAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class MainItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    private View view;
    private HomeShopAdapter mAdapter;
    private List<GoodDetail> goodList = new ArrayList<>();

    private boolean isLoading = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_home_content_main , container , false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    private void initView(){
        if(mRecyclerView != null){
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mRefreshLayout.setOnRefreshListener(this);
            //一开始就进行刷新
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeShopAdapter(goodList);
        mRecyclerView.setAdapter(mAdapter);

        //设置Adapter的item点击事件
        mAdapter.setOnItemClickListener((view , position) -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodDetail", goodList.get(position));
            intent.putExtras(bundle);
            intent.setClass(getActivity(), ShopDetailActivity.class);
            startActivity(intent);
        });

        //设置Adapter的icon点击事件
        mAdapter.setOnIconClickListener((position) -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), PersonPageActivity.class);
            intent.putExtra("personId" , goodList.get(position).getPersonId());
            startActivity(intent);
        });

        //设置Adapter的滑动事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPosition;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState + " item= " + lastVisibleItemPosition);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = mRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mRefreshLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //执行加载更新更多的操作
                                initRecycleView(goodList.size() , 20 , false , false);
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                Log.d("test", "onScrolled = " + lastVisibleItemPosition);

            }
        });
    }


    @Override
    public void onRefresh() {
        // start refresh
        Toast.makeText(getActivity() , "i am refreshing" , Toast.LENGTH_SHORT).show();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO 网络部分的实现
                initRecycleView(0 , 20 , false , true);

                String filePath = "/storage/emulated/0/Pictures/JPEG_20170411_055724_.jpg";
                String filePath2 = "/storage/emulated/0/Pictures/JPEG_20170411_055701_.jpg";
                List<String> list = new ArrayList<String>();
                list.add(filePath);
                list.add(filePath2);
                List<MultipartBody.Part> parts = ImageUtils.getPartList(list);
                fetchDataByNetWork2(parts)
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                Log.e("error" , "finished");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error" , e.toString());
                            }

                            @Override
                            public void onNext(User user) {
                                Log.e("error:UserID=" , user.getId());
                            }
                        });


                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 商品列表信息查询设置
     */
    private void initRecycleView(int offset , int limit , boolean goodDone , boolean clear){
        fetchDataByGood(offset , limit , goodDone)
                .doOnSubscribe(() -> {
                    if(clear){
                        goodList.clear();
                    }
                })
                .subscribe(new Subscriber<GoodDetail>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error" , "good finished");
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

    //完成生命周期同步，防止RxJava内存泄漏

    private Observable<User> fetchDataByNetWork() {
        return RetrofitSingleton.getInstance()
                .fetchUser()
                .compose(this.bindToLifecycle());
    }

    private Observable<Result<Void>> fetchDataByNetWork1() {
        return RetrofitSingleton.getInstance()
                .postUser()
                .compose(this.bindToLifecycle());
    }

    private Observable<User> fetchDataByNetWork2(List<MultipartBody.Part> parts) {
        return RetrofitSingleton.getInstance()
                .postPicture(parts)
                .compose(this.bindToLifecycle());
    }

    private Observable<GoodDetail> fetchDataByGood(int offset , int limit , boolean goodDone) {
        return RetrofitSingleton.getInstance()
                .getGoodList(offset , limit , goodDone)
                .compose(this.bindToLifecycle());
    }


}
