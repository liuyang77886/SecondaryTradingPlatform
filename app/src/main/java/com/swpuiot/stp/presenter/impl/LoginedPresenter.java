package com.swpuiot.stp.presenter.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.swpuiot.stp.base.IView;

import com.swpuiot.stp.entities.FindGoodsEntity;
import com.swpuiot.stp.entities.GoodsEntity;
import com.swpuiot.stp.injector.scrope.ContextLifeCycle;
import com.swpuiot.stp.interfaces.ILoginedView;
import com.swpuiot.stp.presenter.IPresenter;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

/**
 * Created by DELL on 2016/11/29.
 */
public class LoginedPresenter implements IPresenter {
    public static final String TAG = "LoginedActivity";
    private Context mContext;
    private ILoginedView mILoginedView;

    @Inject
    public LoginedPresenter(@ContextLifeCycle("Activity") Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(mContext);
        mILoginedView.showSnackBarMsg("登录成功");

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void attachView(IView v) {
        mILoginedView = (ILoginedView) v;
    }

    public void btnShoppingcarOnClick() {
        mILoginedView.gotoShoppingCarFragment();
    }

    public void btnMainOnClick() {
        mILoginedView.showMain();
    }

    public void itemLoginedSetting() {
        mILoginedView.startLoginedMenuSetting();
    }

    public void btnMyOnClick() {
        mILoginedView.startMyFragment();
    }

    public void btnRecommendOnClick() {
        mILoginedView.startRecommendFragment();
    }

    public void btnShareOnClick() {
        mILoginedView.startShareFragment();
    }
}

