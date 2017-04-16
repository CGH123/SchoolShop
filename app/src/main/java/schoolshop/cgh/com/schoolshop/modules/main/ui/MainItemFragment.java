package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.common.User;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.HomeShopAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class MainItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    private View view;
    private HomeShopAdapter mAdapter;
    private List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

    private boolean isLoading = false;
    private int num = 10;


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
        mAdapter = new HomeShopAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        //getData(10);

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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                num = num + 10;
                                getData(num);
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



    private void getData(int num)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<num;i++)
        {
            map = new HashMap<String, Object>();
            map.put("shop_icon", R.mipmap.ali_pay);
            map.put("shop_tradeName", "我有：八成的白色小绵羊，无任何毛病，48v，有意华农面交");
            map.put("shop_price", "1500元");
            map.put("shop_original_price", "1300元");
            map.put("shop_personName", "梁谷苳");
            map.put("shop_sex", R.mipmap.woman);
            map.put("shop_time", "2小时前");
            map.put("shop_deta1", R.mipmap.erro);
            map.put("shop_deta2", R.mipmap.erro);
            map.put("shop_deta3", R.mipmap.erro);
            map.put("shop_detail", "这是一款哈雷，我觉得可以比你在校园开保时捷还要猛");
            map.put("shop_pageView", "浏览量 100");
            list.add(map);
        }
        data.clear();
        data.addAll(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        Result result;
    }

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

    @Override
    public void onRefresh() {
        // start refresh
        Toast.makeText(getActivity() , "i am refreshing" , Toast.LENGTH_SHORT).show();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO 网络部分的实现
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);//表单类型
                String filePath = "/storage/emulated/0/Pictures/JPEG_20170411_055724_.jpg";
                File file = new File(filePath);//filePath 图片地址
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart("imgfile", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
                String filePaht2 = "/storage/emulated/0/Pictures/JPEG_20170411_055701_.jpg";
                File file2 = new File(filePaht2);//filePath 图片地址
                RequestBody imageBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                builder.addFormDataPart("imgfile", file2.getName(), imageBody2);//imgfile 后台接收图片流的参数名

                List<MultipartBody.Part> parts = builder.build().parts();
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

                fetchDataByNetWork1()
                        .subscribe(new Subscriber<Result<Void>>() {
                            @Override
                            public void onCompleted() {
                                Log.e("error" , "finished");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error" , e.toString());
                            }

                            @Override
                            public void onNext(Result<Void> result) {
                                System.out.println("result=" + result);
                                Log.e("error:UserID=" , "test Result");
                            }
                        });


                fetchDataByNetWork()
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


                getData(num);
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
