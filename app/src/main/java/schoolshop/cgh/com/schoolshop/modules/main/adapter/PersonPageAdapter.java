package schoolshop.cgh.com.schoolshop.modules.main.adapter;

import android.content.Context;
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
 * Created by HUI on 2017-04-21.
 */

public class PersonPageAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = PersonPageAdapter.class.getSimpleName();

    private static final int TYPE_ITEM = 0;

    private Context mContext;
    private List<GoodDetail> goodList;

    public PersonPageAdapter(List<GoodDetail> goodList) {
        this.goodList = goodList;
    }

    @Override
    public int getItemViewType(int position) {
        //多item可拓展
        return PersonPageAdapter.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ITEM:
                return new ViewHolder1(
                        LayoutInflater.from(mContext).inflate(R.layout.fragment_perlist_detail, parent, false));
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
        @BindView(R.id.per_detail_img)
        SimpleDraweeView per_detail_img;
        @BindView(R.id.per_detail_info)
        TextView per_detail_info;
        @BindView(R.id.per_detail_money)
        TextView per_detail_money;
        @BindView(R.id.per_detail_time)
        TextView per_detail_time;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        private void bind (GoodDetail goodDetail){
            per_detail_img.setImageURI(goodDetail.getGoodImagelist());
            per_detail_info.setText(goodDetail.getGoodDetail());
            per_detail_money.setText(goodDetail.getGoodPrice() + "元");
            per_detail_time.setText(TimeUtils.getDiff(goodDetail.getGoodTime()));
        }

    }

}
