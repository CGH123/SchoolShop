package schoolshop.cgh.com.schoolshop.modules.main.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.modules.message.ui.MessageFragment;
import schoolshop.cgh.com.schoolshop.modules.my.ui.MyFragment;
import schoolshop.cgh.com.schoolshop.modules.sell.ui.SellFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    private ActionBar ab;

    //底部选项卡的操作
    @BindView(R.id.main_bottom_tabs)
    RadioGroup group;
    @BindView(R.id.main_home)
    RadioButton main_home;
    private FragmentManager fragmentManager;

    //四个对应的Fragment的布局
    private MainFragment tab_home;
    private SellFragment tab_send;
    private MessageFragment tab_info;
    private MyFragment tab_my;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mNavigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ali_pay);
        ab.setDisplayHomeAsUpEnabled(true);
        //一开始先隐藏actionbar
        ab.hide();

        //设置一开始默认的选项卡
        fragmentManager=getSupportFragmentManager();
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        changeFragment(0);
    }

    /**
     * 使用不同的fragment来进行对选项卡的切换
     *  0:首页  1:发布 2:消息 3:我的
     */
    private void changeFragment(int index){
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 0:
                if(tab_home==null){
                    tab_home=new MainFragment();
                    beginTransaction.add(R.id.main_content,	tab_home);
                }else{
                    beginTransaction.show(tab_home);
                }
                break;
            case 1:
                if(tab_send==null){
                    tab_send=new SellFragment();
                    beginTransaction.add(R.id.main_content,	tab_send);
                }else{
                    beginTransaction.show(tab_send);
                }
                break;
            case 2:
                if(tab_info==null){
                    tab_info=new MessageFragment();
                    beginTransaction.add(R.id.main_content,	tab_info);
                }else{
                    beginTransaction.show(tab_info);
                }
                break;
            case 3:
                if(tab_my==null){
                    tab_my=new MyFragment();
                    beginTransaction.add(R.id.main_content,	tab_my);
                }else{
                    beginTransaction.show(tab_my);
                }
                break;

            default:
                break;
        }
        beginTransaction.commit();//需要提交事务
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (tab_home != null)
            transaction.hide(tab_home);
        if (tab_send != null)
            transaction.hide(tab_send);
        if (tab_info != null)
            transaction.hide(tab_info);
        if (tab_my   != null)
            transaction.hide(tab_my);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                ab.hide();
                changeFragment(0);
                break;
            case R.id.main_sell:
                ab.show();
                changeFragment(1);
                break;
            case R.id.main_info:
                ab.show();
                changeFragment(2);
                break;
            case R.id.main_my:
                ab.show();
                changeFragment(3);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    //点击操作可以弹出左边的菜单栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

}
