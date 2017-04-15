package schoolshop.cgh.com.schoolshop.modules.sell.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseFragment;
import schoolshop.cgh.com.schoolshop.modules.main.ui.MainActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HUI on 2017-04-13.
 */

public class SellFragment extends BaseFragment implements AdapterView.OnItemClickListener , View.OnClickListener{
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<String> gridImageList = new ArrayList<>();
    private String TAG =MainActivity.class.getSimpleName();
    private GridAdapter gridAdapter;

    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.sell_radioButton)
    RadioButton radioButton;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sell , container , false);
        ButterKnife.bind(this , view);
        return view;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sell_radioButton:
                //TODO 要进行对数据的验证才能点击上传的按钮
                Toast.makeText(getActivity() , "i am selling" , Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void init(){
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
}