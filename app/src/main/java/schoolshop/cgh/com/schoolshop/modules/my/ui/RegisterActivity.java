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
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
import schoolshop.cgh.com.schoolshop.common.utils.ImageUtils;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Ps: sex   0:男  1:女
 * Created by HUI on 2017-04-23.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener , RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.userAccount)
    EditText userAccount;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.userIcon)
    ImageView userIcon;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.userSex)
    RadioGroup userSex;
    @BindView(R.id.radio_male)
    RadioButton radio_male;

    private String TAG =RegisterActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private String imagePath = "";
    private String user_account;
    private String user_password;
    private String user_name;
    private Boolean user_sex = false;
    private List<MultipartBody.Part> partList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        userIcon.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        userSex.setOnCheckedChangeListener(this);
        radio_male.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Intent intent = new Intent();
                intent.setClass(this , LoginActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.register:
                //用户注册
                if(registerInit()){
                    User user = new User(user_account , user_password);
                    Person person = new Person(user_name , user_sex);
                    partList = ImageUtils.getPartList(imagePath);

                    fetchRegister(user , person , partList)
                            .subscribe(new Subscriber<Person>() {
                                @Override
                                public void onCompleted() {
                                    Log.e("error" , "register finished");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error" , e.toString());
                                }

                                @Override
                                public void onNext(Person person) {
                                    if(person.getPersonId() == 0){
                                        Toast.makeText(getApplicationContext() , "账号已被注册" , Toast.LENGTH_SHORT).show();
                                    }else{
                                        //TODO 进行对个人信息持久化操作的工作

                                    }
                                }
                            });
                }
                break;
            case R.id.userIcon:
                //图片设置问题，这里有进行验证和测试
                PhotoPickerIntent pIntent = new PhotoPickerIntent(this);
                pIntent.setSelectModel(SelectModel.SINGLE);
                pIntent.setShowCarema(true); // 是否显示拍照
                pIntent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(pIntent, REQUEST_CAMERA_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    if(list.size() > 0){
                        //把imageView给换图片
                        imagePath = list.get(0);
                        loadImage(imagePath);
                        Log.e("error" , "imagePath=" + imagePath);
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
    private void loadImage(String path){
        Glide.with(this)
                .load(path)
                .placeholder(R.mipmap.default_error)
                .error(R.mipmap.default_error)
                .centerCrop()
                .crossFade()
                .into(userIcon);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radio_male:
                user_sex = true;
                break;
            case R.id.radio_female:
                user_sex = false;
                break;
        }
    }

    /**
     * 注册前实现所有信息的验证
     */
    private boolean registerInit(){
        user_account  = userAccount.getText().toString();
        user_password = password.getText().toString();
        user_name = username.getText().toString();
        if(user_account == null || user_account == ""){
            Toast.makeText(this , "账号不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user_password == null || user_password == ""){
            Toast.makeText(this , "密码不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user_name == null || user_name == ""){
            Toast.makeText(this , "昵称不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(imagePath == null || imagePath.equals("")){
            Toast.makeText(this , "请选择头像" , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 网络部分的内容
     */
    public Observable<Person> fetchRegister(User user , Person person , List<MultipartBody.Part> partList){
        return RetrofitSingleton.getInstance()
                .postRegisterPerson(user , person , partList)
                .compose(this.bindToLifecycle());
    }

}
