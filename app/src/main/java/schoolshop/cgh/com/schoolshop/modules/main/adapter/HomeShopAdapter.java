package schoolshop.cgh.com.schoolshop.modules.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.component.AnimRecyclerViewAdapter;
import schoolshop.cgh.com.schoolshop.modules.main.ui.ShopDetailActivity;
import schoolshop.cgh.com.schoolshop.modules.sell.ui.TempActivity;

/**
 * Created by HUI on 2017-04-13.
 */

public class HomeShopAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = HomeShopAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Map<String,Object>> data;

    public HomeShopAdapter(List<Map<String, Object>> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount()){
            return HomeShopAdapter.TYPE_FOOTER;
        }else{
            return HomeShopAdapter.TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ITEM:
                return new ViewHolder1(
                        LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false));
            case TYPE_FOOTER:
                return new FootViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent, false));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ITEM:
                ((ViewHolder1) holder).bind(data.get(position));
                break;
            case TYPE_FOOTER:
                //nothing to do
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
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    class FootViewHolder extends RecyclerView.ViewHolder{
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        CardView layout;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

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
            shop_icon.setOnClickListener(this);
            layout.setOnClickListener(this);
            shop_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.shop_icon:
                    //Todo:留下来给intent跳转到相关的页面之中
                    Toast.makeText(mContext , "icon has been touch" , Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.setClass(mContext, TempActivity.class);
                    intent.putExtra("id" , "01");
                    mContext.startActivity(intent);
                    break;
                case R.id.shop_layout:
                    //Todo:留下来给intent跳转到相关的页面之中
                    Toast.makeText(mContext , "all has been touch" , Toast.LENGTH_SHORT).show();
                    intent = new Intent();
                    intent.putExtra("id" , "01");
                    intent.setClass(mContext, ShopDetailActivity.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
    }

}
