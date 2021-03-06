package schoolshop.cgh.com.schoolshop.modules.my.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;
import schoolshop.cgh.com.schoolshop.component.AnimRecyclerViewAdapter;

/**
 * Created by HUI on 2017-04-25.
 */

public class MyFavAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private Context mContext;
    private List<GoodDetail> goodList;

    public MyFavAdapter(List<GoodDetail> goodList) {
        this.goodList = goodList;
    }

    @Override
    public int getItemViewType(int position) {
        //多item可拓展
        return MyFavAdapter.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ITEM:
                return new ViewHolder1(
                        LayoutInflater.from(mContext).inflate(R.layout.adapter_his_item, parent, false));
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
                ((ViewHolder1) holder).bind(goodList.get(position));

                if(mOnItemClickListener != null){
                    holder.itemView.setOnClickListener( (v) -> {
                        mOnItemClickListener.onItemClick(position);
                    });
                }

                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    /**
     * 内部item接口实现
     */
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    /**
     * ViewHolder适配器
     */
    class ViewHolder1 extends RecyclerView.ViewHolder{
        @BindView(R.id.his_images)
        SimpleDraweeView his_images;
        @BindView(R.id.his_name)
        TextView his_name;
        @BindView(R.id.his_price)
        TextView his_price;
        @BindView(R.id.his_date)
        TextView his_date;
        @BindView(R.id.his_state)
        TextView his_state;


        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        private void bind (GoodDetail goodDetail){
            his_images.setImageURI(Uri.parse(goodDetail.getGoodImagelist().split(";")[0]));
            his_name.setText(goodDetail.getPersonName());
            his_price.setText("$" + String.valueOf(goodDetail.getGoodPrice()) + "元");
            his_date.setText(TimeUtils.getMD(goodDetail.getGoodTime()));
            if(goodDetail.getGoodDone()){
                his_state.setText("已下架");
            }else{
                his_state.setText("发布中");
            }
        }

    }

}
