package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.HomePagerAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.shop_search)
    ImageView shop_search;
    @BindView(R.id.shop_search_content)
    EditText shop_search_content;

    //用于展示模糊搜索结果的fragment
    private MainItemFragment searchItem;
    private View view;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_home , container , false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        if(searchItem != null && searchItem.mRefreshLayout != null){
            searchItem.mRefreshLayout.setRefreshing(true);
            searchItem.onRefresh();
        }
    }

    private void initView(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.github);
        ab.setDisplayHomeAsUpEnabled(true);
        //下面添加Tab的属性
        HomePagerAdapter mHomePagerAdapter = new HomePagerAdapter(getActivity().getSupportFragmentManager());
        //曲线救国，不建议这么做，在fragment中设置好不同的参数
        Constant.Kind_Now = Constant.Kind_All;
        searchItem = new MainItemFragment();
        mHomePagerAdapter.addTab(searchItem , "全部");
        Constant.Kind_Now = Constant.Kind_Book;
        mHomePagerAdapter.addTab(new MainItemFragment() , "书籍");
        Constant.Kind_Now = Constant.Kind_Digit;
        mHomePagerAdapter.addTab(new MainItemFragment() , "数码");
        Constant.Kind_Now = Constant.Kind_Cloth;
        mHomePagerAdapter.addTab(new MainItemFragment() , "服饰");
        Constant.Kind_Now = Constant.Kind_Common;
        mHomePagerAdapter.addTab(new MainItemFragment() , "日用");
        Constant.Kind_Now = Constant.Kind_Other;
        mHomePagerAdapter.addTab(new MainItemFragment() , "其他");
        mViewPager.setAdapter(mHomePagerAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        shop_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_search:
                //模糊搜索只允许在全部搜索选项卡
                mViewPager.setCurrentItem(0);
                shop_search_content.setVisibility(View.VISIBLE);
                String searchName = shop_search_content.getText().toString();
                if(!searchName.equals("")){
                    searchItem.onRefreshType(searchName , true);
                    shop_search_content.setText("");
                    shop_search_content.setVisibility(View.GONE);
                }
                break;
        }
    }
}
