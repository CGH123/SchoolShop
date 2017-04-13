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

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.modules.main.adapter.HomePagerAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class MainFragment extends BaseFragment{
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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

    private void initView(){
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ali_pay);
        ab.setDisplayHomeAsUpEnabled(true);
        //下面添加Tab的属性
        HomePagerAdapter mHomePagerAdapter = new HomePagerAdapter(getActivity().getSupportFragmentManager());
        mHomePagerAdapter.addTab(new MainItemFragment() , "全部");
        mHomePagerAdapter.addTab(new MainItemFragment() , "书籍");
        mHomePagerAdapter.addTab(new MainItemFragment() , "数码");
        mHomePagerAdapter.addTab(new MainItemFragment() , "服饰");
        mHomePagerAdapter.addTab(new MainItemFragment() , "日用");
        mHomePagerAdapter.addTab(new MainItemFragment() , "其他");
        mViewPager.setAdapter(mHomePagerAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
