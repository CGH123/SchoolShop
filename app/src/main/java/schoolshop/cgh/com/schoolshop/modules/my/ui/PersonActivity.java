package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.utils.ImageUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-05-11.
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userIcon)
    ImageView userIcon;
    @BindView(R.id.userSex)
    RadioGroup userSex;
    @BindView(R.id.radio_male)
    RadioButton radio_male;
    @BindView(R.id.updateInfo)
    Button updateInfo;
    @BindView(R.id.infoCancel)
    Button infoCancel;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private String imagePath = "";
    private String user_name;
    private Boolean user_sex = false;
    private List<MultipartBody.Part> partList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        updateInfo.setOnClickListener(this);
        infoCancel.setOnClickListener(this);
        userIcon.setOnClickListener(this);
        userSex.setOnCheckedChangeListener(this);
        radio_male.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userIcon:
                //使用读取存储相机功能
                PhotoPickerIntent pIntent = new PhotoPickerIntent(this);
                pIntent.setSelectModel(SelectModel.SINGLE);
                pIntent.setShowCarema(true); // 是否显示拍照
                pIntent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(pIntent, REQUEST_CAMERA_CODE);
                break;
            case R.id.updateInfo:
                //dialog的使用地方
                if (!registerInit()) return;
                partList = ImageUtils.getPartList(imagePath);
                fetchRegister(Constant.PERSON.getPersonId(), user_sex, user_name, partList);
                break;
            case R.id.infoCancel:
                //把用户信息给注销掉
                Constant.PERSON = null;
                finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    if (list.size() > 0) {
                        //把imageView给换图片
                        imagePath = list.get(0);
                        loadImage(imagePath);
                        Log.e("error", "imagePath=" + imagePath);
                    }
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    //单张照片所以不预览，直接break
                    break;
            }
        }
    }

    /**
     * 使用Glide配合PhotoActivity插件快速加载相册图片
     */
    private void loadImage(String path) {
        Glide.with(this)
                .load(path)
                .placeholder(R.mipmap.default_error)
                .error(R.mipmap.default_error)
                .centerCrop()
                .crossFade()
                .into(userIcon);
    }

    /**
     * 注册前实现所有信息的验证
     */
    private boolean registerInit() {
        user_name = userName.getText().toString();
        if (user_name == null || user_name.equals("")) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (imagePath == null || imagePath.equals("")) {
            Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_male:
                user_sex = true;
                break;
            case R.id.radio_female:
                user_sex = false;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //网络部分
    private void fetchRegister(int personId, boolean personSex, String personName, List<MultipartBody.Part> partList) {
        RetrofitSingleton.getInstance()
                .postRegisterIcon(personId, personSex, personName, partList)
                .compose(this.bindToLifecycle())
                .subscribe(new Subscriber<Person>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(), "用户信息修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }

                    @Override
                    public void onNext(Person person) {
                        Constant.PERSON = person;
                    }
                });
    }

}
