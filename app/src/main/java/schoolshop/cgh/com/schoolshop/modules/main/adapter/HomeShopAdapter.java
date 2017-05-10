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
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.GoodDetail;
import schoolshop.cgh.com.schoolshop.common.utils.SharedPreferenceUtil;
import schoolshop.cgh.com.schoolshop.common.utils.TimeUtils;
import schoolshop.cgh.com.schoolshop.common.utils.ToastUtil;
import schoolshop.cgh.com.schoolshop.component.AnimRecyclerViewAdapter;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-04-13.
 */

public class HomeShopAdapter extends AnimRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private static String TAG = HomeShopAdapter.class.getSimpleName();

    private Context mContext;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<GoodDetail> goodList;

    public HomeShopAdapter(List<GoodDetail> goodList) {
        this.goodList = goodList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return HomeShopAdapter.TYPE_FOOTER;
        } else {
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
                ((ViewHolder1) holder).bind(goodList.get(position));

                //设置item监听事件
                if (mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener((view) ->
                            mOnItemClickListener.onItemClick(holder.itemView, position));
                }

                //设置icon监听事件
                if (mOnIconClickListener != null) {
                    holder.itemView.findViewById(R.id.shop_icon)
                            .setOnClickListener(v -> mOnIconClickListener.onIconClick(position));
                }

                break;
            case TYPE_FOOTER:
                //nothing to do
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return goodList.size() == 0 ? 0 : goodList.size() + 1;
    }

    public void refresh(List<GoodDetail> goodList) {
        this.goodList = goodList;
        notifyDataSetChanged();
    }

    /**
     * 内部item接口
     */
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 内部icon实现接口
     */
    private OnIconClickListener mOnIconClickListener;

    public interface OnIconClickListener {
        void onIconClick(int position);
    }

    public void setOnIconClickListener(OnIconClickListener mOnIconClickListener) {
        this.mOnIconClickListener = mOnIconClickListener;
    }

    /**
     * Holder类
     */
    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        @BindView(R.id.shop_upVote)
        TextView shop_upVote;
        @BindView(R.id.shop_shineButton)
        ShineButton shop_shineButton;

        //设置Layout中的点击事件 shop_upVote
        @BindView(R.id.shop_layout)
        CardView layout;

        private GoodDetail goodDetail;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(GoodDetail goodDetail) {
            this.goodDetail = goodDetail;

            shop_icon.setImageURI(Uri.parse(goodDetail.getPersonIcon()));
            shop_tradeName.setText(goodDetail.getGoodName());
            shop_price.setText(goodDetail.getGoodPrice() + "元");
            shop_original_price.setText(goodDetail.getGoodOriginalPrice() + "元");
            shop_personName.setText(goodDetail.getPersonName());
            if (goodDetail.getPersonSex()) {
                shop_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.man));
            } else {
                shop_sex.setImageURI(Uri.parse("res://schoolshop.cgh.com.schoolshop/" + R.mipmap.woman));
            }
            shop_time.setText(TimeUtils.getDiff(goodDetail.getGoodTime()));
            shop_detail.setText(goodDetail.getGoodDetail());
            shop_pageView.setText("浏览量:" + goodDetail.getGoodViews());
            shop_upVote.setText("点赞量:" + goodDetail.getGoodUpvote());

            //下面对进行格式化
            String[] imagePath = goodDetail.getGoodImagelist().split(";");
            switch (imagePath.length) {
                case 0:
                    shop_deta1.setVisibility(View.INVISIBLE);
                    shop_deta2.setVisibility(View.INVISIBLE);
                    shop_deta3.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    shop_deta1.setImageURI(Uri.parse(imagePath[0]));
                    shop_deta1.setVisibility(View.VISIBLE);
                    shop_deta2.setVisibility(View.INVISIBLE);
                    shop_deta3.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    shop_deta1.setImageURI(Uri.parse(imagePath[0]));
                    shop_deta2.setImageURI(Uri.parse(imagePath[1]));
                    shop_deta1.setVisibility(View.VISIBLE);
                    shop_deta2.setVisibility(View.VISIBLE);
                    shop_deta3.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    shop_deta1.setImageURI(Uri.parse(imagePath[0]));
                    shop_deta2.setImageURI(Uri.parse(imagePath[1]));
                    shop_deta3.setImageURI(Uri.parse(imagePath[2]));
                    shop_deta1.setVisibility(View.VISIBLE);
                    shop_deta2.setVisibility(View.VISIBLE);
                    shop_deta3.setVisibility(View.VISIBLE);
                    break;

            }

            //绑定空间中相关的监听器
            //shop_icon.setOnClickListener(this);
            shop_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            shop_shineButton.setOnClickListener(this);
            if (Constant.PERSON != null) {
                Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote(Constant.PERSON.getPersonId());
                if (upVoteSet.contains(String.valueOf(goodDetail.getGoodId()))) {
                    shop_shineButton.setChecked(true);
                }
            }
        }

        @Override
        public void onClick(View v) {
            //判断登录状态是否合法
            if (Constant.PERSON == null) {
                shop_shineButton.setChecked(false);
                ToastUtil.showShort("请先登录");
                return;
            }

            switch (v.getId()) {
                case R.id.shop_shineButton:
                    //实现点赞功能
                    //点赞功能实现
                    if (!shop_shineButton.isChecked()) {
                        //已经点赞，执行取消点赞动作
                        fetchDownVote(goodDetail.getGoodId());
                        goodDetail.setGoodUpvote(goodDetail.getGoodUpvote() - 1);
                        shop_upVote.setText("点赞量:" + goodDetail.getGoodUpvote());
                    } else {
                        //还没点赞，执行点赞动作
                        fetchUpVote(goodDetail.getGoodId());
                        goodDetail.setGoodUpvote(goodDetail.getGoodUpvote() + 1);
                        shop_upVote.setText("点赞量:" + goodDetail.getGoodUpvote());
                    }
                    break;
            }
        }
    }

    /**
     * 点赞的网络部分
     */
    private void fetchUpVote(int goodId) {
        Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote(Constant.PERSON.getPersonId());
        RetrofitSingleton.getInstance()
                .getUpvote(goodId, 1)
                .subscribe(aVoid -> {
                    upVoteSet.add(String.valueOf(goodId));
                    SharedPreferenceUtil.getInstance().putUpvote(Constant.PERSON.getPersonId(), upVoteSet);
                });
    }

    private void fetchDownVote(int goodId) {
        Set<String> upVoteSet = SharedPreferenceUtil.getInstance().getUpvote(Constant.PERSON.getPersonId());
        RetrofitSingleton.getInstance()
                .getUpvote(goodId, -1)
                .subscribe(aVoid -> {
                    upVoteSet.remove(String.valueOf(goodId));
                    SharedPreferenceUtil.getInstance().putUpvote(Constant.PERSON.getPersonId(), upVoteSet);
                });
    }

}
