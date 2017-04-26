package schoolshop.cgh.com.schoolshop.modules.sell.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.Good;
import schoolshop.cgh.com.schoolshop.common.utils.ImageUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;
import schoolshop.cgh.com.schoolshop.modules.main.ui.MainActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HUI on 2017-04-13.
 */

public class SellFragment extends BaseFragment implements AdapterView.OnItemClickListener , View.OnClickListener , TagView.OnTagClickListener{
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> gridImageList = new ArrayList<>();
    private String TAG =MainActivity.class.getSimpleName();
    private GridAdapter gridAdapter;

    @BindView(R.id.sell_tag)
    TagContainerLayout sell_tag;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.sell_radioButton)
    RadioButton radioButton;
    @BindView(R.id.sell_name)
    EditText sell_name;
    @BindView(R.id.sell_number)
    EditText sell_number;
    @BindView(R.id.sell_price)
    EditText sell_price;
    @BindView(R.id.sell_cost_price)
    EditText sell_cost_price;
    @BindView(R.id.et_context)
    EditText et_context;

    private List<String> tags;
    private String good_name;
    private Integer good_num;
    private String good_detail;
    private Long good_price;
    private Long good_original_price;
    private Date good_time;
    private Integer good_kind;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGrid();
        initTag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sell , container , false);
        ButterKnife.bind(this , view);
        return view;
    }


    @Override
    public void onClick(View v) {
        //状态判断
        if(Constant.PERSON == null){
            Toast.makeText(getActivity() , "请先登录" , Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()){
            case R.id.sell_radioButton:
                //TODO 要进行对数据的验证才能点击上传的按钮
                if(!sellInit())  return;
                Good good = new Good(0, Constant.PERSON.getPersonId(), good_name, good_num, good_price,
                        good_original_price , new Date(), good_detail, ImageUtils.listToString(imagePaths), good_kind);

                fetchSell(good)
                        .subscribe(new Subscriber<Good>() {
                            @Override
                            public void onCompleted() {
                                Toast.makeText(getActivity() , "发布成功" , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error" , e.toString());
                            }

                            @Override
                            public void onNext(Good good) {
                                if(good.getGoodId() == 0){
                                    Toast.makeText(getActivity() , "发布失败" , Toast.LENGTH_SHORT).show();
                                }else{
                                    fetchSellImage(good.getGoodId() , ImageUtils.getPartList(imagePaths))
                                            .subscribe(good1 -> {
                                                Toast.makeText(getActivity() , "图片上传成功" , Toast.LENGTH_SHORT).show();
                                            });
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

    private void initTag(){
        tags = new ArrayList<>();
        tags.add("书籍");
        tags.add("数码");
        tags.add("服饰");
        tags.add("日用");
        tags.add("其他");
        sell_tag.setTags(tags , colorInit(-1));
        sell_tag.setOnTagClickListener(this);
    }

    private void initGrid(){
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
        gridView.setOnItemClickListener(this);
        gridImageList.addAll(imagePaths);
        gridImageList.add("000000");
        gridAdapter = new GridAdapter(gridImageList);
        gridView.setAdapter(gridAdapter);
        radioButton.setOnClickListener(this);
    }

    private boolean sellInit(){
        String good_num_str , good_price_str , good_original_price_str;
        good_name = sell_name.getText().toString();
        good_num_str = sell_number.getText().toString();
        good_detail = et_context.getText().toString();
        good_price_str = sell_price.getText().toString();
        good_original_price_str = sell_cost_price.getText().toString();
        if(good_name == null || good_name.equals("")){
            Toast.makeText(getActivity() , "商品不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(good_num_str == null || good_num_str.equals("")){
            Toast.makeText(getActivity() , "商品数量不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(good_price_str == null || good_price_str.equals("")){
            Toast.makeText(getActivity() , "价钱不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(imagePaths.size() == 0){
            Toast.makeText(getActivity() , "商品照片不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(good_kind == 0){
            Toast.makeText(getActivity() , "商品类型不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }

        if(good_original_price_str == null || good_original_price_str.equals("")){
            good_original_price = 0L;
        }else{
            good_original_price = Long.valueOf(good_original_price_str);
        }
        good_price = Long.valueOf(good_price_str);
        good_num = Integer.valueOf(good_num_str);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);

                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imgs = (String) parent.getItemAtPosition(position);
        if ("000000".equals(imgs) ){
            PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
            intent.setSelectModel(SelectModel.MULTI);
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(6); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }else{
            PhotoPreviewIntent intent = new PhotoPreviewIntent(getActivity());
            intent.setCurrentItem(position);
            intent.setPhotoPaths(imagePaths);
            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
        }
    }

    @Override
    public void onTagClick(int position, String text) {
        sell_tag.setTags(tags , colorInit(position));
        good_kind = position + 1;
    }

    @Override
    public void onTagLongClick(int position, String text) {
        //标签的长按事件
    }

    @Override
    public void onTagCrossClick(int position) {
        //标签的cross事件
    }

    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        imagePaths.addAll(paths);
        System.out.println("test=" + imagePaths);
        if (gridImageList!=null&& gridImageList.size()>0){
            gridImageList.clear();
        }
        gridImageList.addAll(paths);
        gridImageList.add("000000");
        gridAdapter  = new GridAdapter(gridImageList);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(gridImageList);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * GridView的适配器
     */
    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;
        private LayoutInflater inflater;
        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
            if(listUrls.size() == 7){
                listUrls.remove(listUrls.size()-1);
            }
            inflater = LayoutInflater.from(getActivity());
        }

        public int getCount(){
            return  listUrls.size();
        }
        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent,false);
                holder.image = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final String path=listUrls.get(position);
            if (path.equals("000000")){
                holder.image.setImageResource(R.mipmap.ic_launcher);
            }else {
                Glide.with(getActivity())
                        .load(path)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(holder.image);
            }
            return convertView;
        }
        class ViewHolder {
            ImageView image;
        }
    }

    /**
     * 网络部分
     */
    private Observable<Good> fetchSell(Good good){
        return RetrofitSingleton.getInstance()
                .postSell(good)
                .compose(this.bindToLifecycle());
    }

    private Observable<Good> fetchSellImage(Integer goodId , List<MultipartBody.Part> partList){
        return RetrofitSingleton.getInstance()
                .postSellImage(goodId , partList)
                .compose(this.bindToLifecycle());
    }

    /**
     * 初始化颜色
     */
    private ArrayList<int[]> colorInit(int position){
        ArrayList<int[]> colors = new ArrayList<>();
        int[] color1 = {Color.CYAN, Color.WHITE, Color.WHITE};
        int[] color2 = {Color.MAGENTA, Color.WHITE, Color.WHITE};
        switch (position){
            case -1:
                colors.add(color1);colors.add(color1);colors.add(color1);
                colors.add(color1);colors.add(color1);
                break;
            case 0:
                colors.add(color2);colors.add(color1);colors.add(color1);
                colors.add(color1);colors.add(color1);
                break;
            case 1:
                colors.add(color1);colors.add(color2);colors.add(color1);
                colors.add(color1);colors.add(color1);
                break;
            case 2:
                colors.add(color1);colors.add(color1);colors.add(color2);
                colors.add(color1);colors.add(color1);
                break;
            case 3:
                colors.add(color1);colors.add(color1);colors.add(color1);
                colors.add(color2);colors.add(color1);
                break;
            case 4:
                colors.add(color1);colors.add(color1);colors.add(color1);
                colors.add(color1);colors.add(color2);
                break;
        }
        return colors;
    }
}