package schoolshop.cgh.com.schoolshop.modules.sell.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import schoolshop.cgh.com.schoolshop.R;

/**
 * Created by HUI on 2017-04-14.
 */

public class TempActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_test);
        textView = (TextView) findViewById(R.id.test);

        Intent intent = this.getIntent();
        Integer personId = intent.getExtras().getInt("personId");
        String text = personId.toString();
        textView.setText(text);
    }
}

    /*private void getData(int num)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<num;i++)
        {
            map = new HashMap<String, Object>();
            map.put("shop_icon", R.mipmap.ali_pay);
            map.put("shop_tradeName", "我有：八成的白色小绵羊，无任何毛病，48v，有意华农面交");
            map.put("shop_price", "1500元");
            map.put("shop_original_price", "1300元");
            map.put("shop_personName", "梁谷苳");
            map.put("shop_sex", R.mipmap.woman);
            map.put("shop_time", "2小时前");
            map.put("shop_deta1", R.mipmap.erro);
            map.put("shop_deta2", R.mipmap.erro);
            map.put("shop_deta3", R.mipmap.erro);
            map.put("shop_detail", "这是一款哈雷，我觉得可以比你在校园开保时捷还要猛");
            map.put("shop_pageView", "浏览量 100");
            list.add(map);
        }
        data.clear();
        data.addAll(list);
        mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }*/