package com.swpuiot.stp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.swpuiot.stp.R;
import com.swpuiot.stp.base.BaseActivity;
import com.swpuiot.stp.base.BaseApplication;
import com.swpuiot.stp.entities.LoginEntity;
import com.swpuiot.stp.entities.RegisterEntity;
import com.swpuiot.stp.injector.component.ActivityComponent;
import com.swpuiot.stp.injector.component.DaggerActivityComponent;
import com.swpuiot.stp.injector.module.ActivityModule;
import com.swpuiot.stp.interfaces.IMainView;
import com.swpuiot.stp.presenter.impl.MainPresenter;
import com.swpuiot.stp.utils.GsonUtils;
import com.swpuiot.stp.utils.SnackBarUtils;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView {


    @Inject
    MainPresenter mMainPresenter;
    @BindView(R.id.cl_main)
    CoordinatorLayout mClMain;
    private Button btn_login;
    private Button btn_register;
    private Button btn_fingpassword;
    private EditText et_username;
    private EditText et_password;
    private Button btn_tomy;
    private AsyncHttpClient client;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMainPresenter.onCreate(savedInstanceState);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_fingpassword = (Button) findViewById(R.id.btn_findpassword);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        client = new AsyncHttpClient();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.btnLoginOnClick();
            }
        });

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.btnRegisterOnClick();
            }
        });

        btn_fingpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.btnFindPasswordOnClick();
            }
        });

    }


    @Override
    public void showSnackBarMsg(@StringRes int msg) {
        SnackBarUtils.show(mClMain, msg);
    }

    @Override
    public void showSnackBarMsg(String msg) {
        SnackBarUtils.show(mClMain, msg);
    }

    @Override
    public void initializeInjector() {
        ActivityComponent component = DaggerActivityComponent
                .builder()
                .appComponent(((BaseApplication) getApplication())
                        .getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);

    }

    @Override
    protected void initializePresenter() {
        mMainPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void startLoginedActivity() {
        if (et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()) {
            showSnackBarMsg("账号密码不能为空");
        } else {
            LoginEntity entity = new LoginEntity("login", et_username.getText().toString().trim(), et_password.getText().toString());
            String json = GsonUtils.toJson(entity);
            ByteArrayEntity arrayEntity = null;
            try {
                arrayEntity = new ByteArrayEntity(json.getBytes("UTF-8"));
                arrayEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            client.post(this, "http://www.deardull.com/BookStore/appuser", arrayEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String src = new String(bytes);
                    LoginEntity loginEntity = GsonUtils.fromJson(src, LoginEntity.class);

                    Log.d("RegisterActivity", src);
                    Log.d("RegisterActivity", "success");
                    showSnackBarMsg("登录成功");
                    Intent intent = new Intent(MainActivity.this, LoginedActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("RegisterActivity", "Failed");
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void startFindPasswordActicity() {
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }
}
