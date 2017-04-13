package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
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
    private List<Map<String,Object>> data;

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
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        data = getData();
        mAdapter = new HomeShopAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<10;i++)
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
        return list;
    }

    @Override
    public void onRefresh() {
        // start refresh
        Toast.makeText(getActivity() , "i am refreshing" , Toast.LENGTH_SHORT).show();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
