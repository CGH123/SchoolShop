package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;
import schoolshop.cgh.com.schoolshop.base.Constant;
import schoolshop.cgh.com.schoolshop.common.entity.Person;
import schoolshop.cgh.com.schoolshop.common.entity.User;
import schoolshop.cgh.com.schoolshop.component.RetrofitSingleton;

/**
 * Created by HUI on 2017-04-23.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    private String user_account;
    private String user_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                //向服务器注册信息，然后跳转到原来的activity?
                if(registerInit()){
                    fetchLogin(new User(user_account , user_password))
                            .subscribe(new Subscriber<Person>() {
                                @Override
                                public void onCompleted() {
                                    Log.e("error" , "直接complete");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("error" , e.toString());
                                }

                                @Override
                                public void onNext(Person person) {
                                    if(person.getPersonId() == 0){
                                        Toast.makeText(getApplicationContext() , "账号密码错误" , Toast.LENGTH_SHORT).show();
                                    }else{
                                        Constant.PERSON = person;
                                        Toast.makeText(getApplicationContext() , "登录成功" , Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                }
                break;
            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(this , RegisterActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    private boolean registerInit(){
        user_account  = username.getText().toString();
        user_password = password.getText().toString();
        if(user_account == null || user_account.equals("")){
            Toast.makeText(this , "账号不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user_password == null || user_password.equals("")){
            Toast.makeText(this , "密码不能为空" , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public Observable<Person> fetchLogin(User user){
        return RetrofitSingleton.getInstance()
                .postLoginPerson(user)
                .compose(this.bindToLifecycle());
    }
}

