package schoolshop.cgh.com.schoolshop.modules.message.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.OrderDetail;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.message.adapter.MessageAdapter;

/**
 * Created by HUI on 2017-04-13.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.cardView)
    CardView cardView;

    private View view;
    private MessageAdapter mAdapter;
    private List<OrderDetail> orderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_message, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MessageAdapter(orderList);
        mRecyclerView.setAdapter(mAdapter);

        //设置Adapter的item点击事件
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("orderDetail", orderList.get(position));
            intent.putExtras(bundle);
            intent.setClass(getActivity(), OrderDetailActivity.class);
            startActivity(intent);
        });

        updateAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateAdapter();
    }

    /**
     * 网络部分功能
     */
    private Observable<List<OrderDetail>> fetchOrderDetail(int personId){
        return RetrofitSingleton.getInstance()
                .getOrderDetailList(personId)
                .compose(this.bindToLifecycle());
    }

    /**
     * 网路更新adapter
     */
    private void updateAdapter(){
        //网络读取部分 , 前面已经做好了登录状态的检测
        if(Constant.PERSON != null) {
            fetchOrderDetail(Constant.PERSON.getPersonId())
                    .subscribe(new Subscriber<List<OrderDetail>>() {
                        @Override
                        public void onCompleted() {
                            if(orderList.size() == 0){
                                mRecyclerView.setVisibility(View.GONE);
                                cardView.setVisibility(View.VISIBLE);
                            }else{
                                mRecyclerView.setVisibility(View.VISIBLE);
                                cardView.setVisibility(View.GONE);
                            }
                            mAdapter.notifyDataSetChanged();
                            Log.e("error", "orderDetail finished");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", e.toString());
                        }

                        @Override
                        public void onNext(List<OrderDetail> orderDetails) {
                            orderList.clear();
                            orderList.addAll(orderDetails);
                        }
                    });
        }else{
            //显示没有新的消息
            mRecyclerView.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        }
    }

}
