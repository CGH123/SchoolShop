package schoolshop.cgh.com.schoolshop.modules.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.component.AnimRecyclerViewAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class HomeShopAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = HomeShopAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ONE = 0;

    private List<Map<String,Object>> data;

    public HomeShopAdapter(List<Map<String, Object>> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HomeShopAdapter.TYPE_ONE) {
            return HomeShopAdapter.TYPE_ONE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ONE:
                return new ViewHolder1(
                        LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ONE:
                ((ViewHolder1) holder).bind(data.get(position));
                break;
            default:
                break;
        }

        /*if (SharedPreferenceUtil.getInstance().getMainAnim()) {
            showItemAnim(holder.itemView, position);
        }*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
        @BindView(R.id.shop_icon)
        SimpleDraweeView shop_icon;
        @BindView(R.id.shop_tradeName)
        TextView shop_tradeName;
        @BindView(R.id.shop_price)
        TextView shop_price;
        @BindView(R.id.shop_original_price)
        TextView shop_original_price;
        @BindView(R.id.shop_personName)
        TextView shop_personName;
        @BindView(R.id.shop_sex)
        SimpleDraweeView shop_sex;
        @BindView(R.id.shop_time)
        TextView shop_time;
        @BindView(R.id.shop_deta1)
        SimpleDraweeView shop_deta1;
        @BindView(R.id.shop_deta2)
        SimpleDraweeView shop_deta2;
        @BindView(R.id.shop_deta3)
        SimpleDraweeView shop_deta3;
        @BindView(R.id.shop_detail)
        TextView shop_detail;
        @BindView(R.id.shop_pageView)
        TextView shop_pageView;

        //设置Layout中的点击事件
        @BindView(R.id.shop_layout)
        CardView linearLayout;

        private void bind (Map<String,Object> data){
            shop_icon.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + data.get("shop_icon")));
            shop_tradeName.setText((String)data.get("shop_tradeName"));
            shop_price.setText((String)data.get("shop_price"));
            shop_original_price.setText((String)data.get("shop_original_price"));
            shop_personName.setText((String)data.get("shop_personName"));
            shop_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + data.get("shop_sex")));
            shop_time.setText((String)data.get("shop_time"));
            shop_deta1.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + data.get("shop_deta1")));
            shop_deta2.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + data.get("shop_deta2")));
            shop_deta3.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + data.get("shop_deta3")));
            shop_detail.setText((String)data.get("shop_detail"));
            shop_pageView.setText((String)data.get("shop_pageView"));

            //绑定空间中相关的监听器
            //shop_icon.setOnClickListener(SettingFragment.this);
            //linearLayout.setOnClickListener(SettingFragment.this);
            shop_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }

}
