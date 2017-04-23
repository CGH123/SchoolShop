package schoolshop.cgh.com.schoolshop.modules.my.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.base.BaseActivity;

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
                this.finish();
                break;
            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(this , RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}

