package schoolshop.cgh.com.schoolshop.component;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.Order;
import schoolshop.cgh.com.schoolshop.modules.main.ui.MainActivity;

/**
 * Created by HUI on 2017-04-27.
 */

public class PollingService extends Service {
    public static final String ACTION = "com.schoolshop.Services";
    private Notification mNotification;//消息通知器
    private NotificationManager mManager;//消息通知管理器
    private Notification.Builder builder;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.e("result", "轮询服务被创建onBind");
        return null;
    }

    @Override
    public void onCreate()  {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e("result", "轮询服务被创建onCreate");
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        //Log.e("result", "onStart");
        new PollingThread().start();
    }

    //初始化消息管理器
    private void initNotifiManager() {
        // TODO Auto-generated method stub
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new Notification.Builder(this);//新建Notification.Builder对象
        builder.setContentTitle("新订单通知");
        builder.setContentText("你发布的商品有新的消息，请注意查收");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);//执行intent
    }

    //弹出Notification
    private void showNotification() {
        mNotification = builder.build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mManager.notify(1, mNotification);
    }

    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...");
            //网络部分检查服务器的推送
            if(Constant.PERSON != null){
                fetchOrder(Constant.PERSON.getPersonId())
                        .subscribe(new Subscriber<Order>() {
                            @Override
                            public void onCompleted() {
                                Log.e("error" , "polling finished");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error" , e.toString());
                            }

                            @Override
                            public void onNext(Order order) {
                                //TODO 实现消息列表的数据更新
                                showNotification();
                                fetchOrderState(order.getOrderId() , 1)
                                        .subscribe(aVoid -> {
                                            Log.e("result" , "order state change");
                                        });
                            }
                        });
            }
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("result", "轮询服务销毁了");
    }

    private Observable<Order> fetchOrder(int personId){
        return RetrofitSingleton.getInstance()
                .getOrderNotice(personId);
    }

    private Observable<Void> fetchOrderState(int orderId , int state){
        return RetrofitSingleton.getInstance()
                .postOrderState(orderId , state);
    }

}
