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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.base.WrapContentLinearLayoutManager;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.utils.ToastUtil;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.HomeShopAdapter;
import schoolshop.cgh.com.schoolshop.modules.my.ui.LoginActivity;

/**
 * Created by HUI on 2017-04-13.
 */

public class MainItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout mRefreshLayout;

    EditText shop_search_content;

    private View view;
    private HomeShopAdapter mAdapter;
    private List<GoodDetail> goodList = new ArrayList<>();
    private int good_kind;
    private String searchName = "";
    private boolean isSearch = false;
    private boolean isLoading = false;

    public MainItemFragment() {
        this.good_kind = Constant.Kind_Now;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_content_main, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    private void initView() {
        shop_search_content = (EditText) getActivity().findViewById(R.id.shop_search_content);

        if (mRecyclerView != null) {
            mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mRefreshLayout.setOnRefreshListener(this);
            //一开始就进行刷新,如果在进行模糊搜索，则取消自动刷新
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }

        final WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new HomeShopAdapter(goodList);
        mRecyclerView.setAdapter(mAdapter);

        //设置Adapter的item点击事件
        mAdapter.setOnItemClickListener((view, position) -> {
            //判断登录状态是否合法
            if (Constant.PERSON == null) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                ToastUtil.showShort("请先登录");
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("goodDetail", goodList.get(position));
            intent.putExtras(bundle);
            intent.setClass(getActivity(), ShopDetailActivity.class);
            startActivity(intent);
        });

        //设置Adapter的icon点击事件
        mAdapter.setOnIconClickListener((position) -> {
            if (Constant.PERSON == null) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                ToastUtil.showShort("请先登录");
                return;
            }
            Intent intent = new Intent();
            intent.setClass(getActivity(), PersonPageActivity.class);
            intent.putExtra("personId", goodList.get(position).getPersonId());
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
                                initRecycleView(goodList.size(), 20, good_kind, false, false);
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
        //模糊搜索只允许在全部搜索选项卡
        if (good_kind != Constant.Kind_All) {
            shop_search_content.setVisibility(View.GONE);
        }
        // start refresh
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isSearch) {
                    isSearch = false;
                    initRecycleViewByName(searchName, 0, 20, false, true);
                } else {
                    initRecycleView(0, 20, good_kind, false, true);
                }
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * 设置搜索刷新
     */
    public void onRefreshType(String searchName, boolean type) {
        if (type) {
            isSearch = true;
            this.searchName = searchName;
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    /**
     * 商品列表信息查询设置
     */
    private void initRecycleView(int offset, int limit, int kind, boolean goodDone, boolean clear) {
        fetchDataByGoodKind(offset, limit, kind, goodDone)
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error", "good finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetail) {
                        if (clear) {
                            goodList.clear();
                        }
                        goodList.addAll(goodDetail);
                    }
                });
    }

    /**
     * 商品列表信息模糊查询设置
     */
    private void initRecycleViewByName(String goodName, int offset, int limit, boolean goodDone, boolean clear) {
        fetchDataByGoodName(goodName, offset, limit, goodDone)
                .subscribe(new Subscriber<List<GoodDetail>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                        Log.e("error", "name finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(List<GoodDetail> goodDetail) {
                        if (clear) {
                            goodList.clear();
                        }
                        goodList.addAll(goodDetail);
                    }
                });
    }

    //完成生命周期同步，防止RxJava内存泄漏
    private Observable<List<GoodDetail>> fetchDataByGoodKind(int offset, int limit, int kind, boolean goodDone) {
        return RetrofitSingleton.getInstance()
                .getGoodKindList(offset, limit, kind, goodDone)
                .compose(this.bindToLifecycle());
    }

    private Observable<List<GoodDetail>> fetchDataByGoodName(String goodName, int offset, int limit, boolean goodDone) {
        return RetrofitSingleton.getInstance()
                .getGoodDetailByName(goodName, offset, limit, goodDone)
                .compose(this.bindToLifecycle());
    }

}
