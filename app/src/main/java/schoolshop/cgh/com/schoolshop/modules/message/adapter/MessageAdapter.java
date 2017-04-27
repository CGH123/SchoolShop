package schoolshop.cgh.com.schoolshop.modules.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.common.entity.OrderDetail;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;
import schoolshop.cgh.com.schoolshop.component.AnimRecyclerViewAdapter;

/**
 * Created by HUI on 2017-04-27.
 */

public class MessageAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    private Context mContext;
    private List<OrderDetail> orderList;

    public MessageAdapter(List<OrderDetail> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int getItemViewType(int position) {
        //多item可拓展
        return MessageAdapter.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case TYPE_ITEM:
                return new ViewHolder1(
                        LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false));
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
                ((ViewHolder1) holder).bind(orderList.get(position));
                //监听器设置
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
        return orderList.size();
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
        @BindView(R.id.msg_detail)
        TextView msg_detail;
        @BindView(R.id.msg_date)
        TextView msg_date;


        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }

        private void bind (OrderDetail orderDetail){
            msg_detail.setText("有用户拍下您的商品:\"" + orderDetail.getGoodName() + "\"，请尽快处理");
            msg_date.setText(TimeUtils.getMD(orderDetail.getOrderTime()));
        }


    }

}
