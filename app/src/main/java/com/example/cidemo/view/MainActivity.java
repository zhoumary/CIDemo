package com.example.cidemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ActivityMainBinding;
import com.example.cidemo.model.Login;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.other.MatchItemAdapter;
import com.example.cidemo.viewmodel.MainViewModel;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FILE_NAME = "login.json";

    DatabaseHelper mDatabaseHelper;

    public ActivityMainBinding mActivityMainBinding;
    private MainViewModel mViewModel;

    public MatchItemAdapter mMatchItemAdapter;
    public List<MatchItem> mMatchList = new ArrayList<>();

    public LoginDialog loginDialog;

    JSONObject lastLoginItem;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置navigation bar和action bar为透明
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 设置dataBinding、viewModel
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new MainViewModel(this);
        mActivityMainBinding.setViewModel(mViewModel);

        // 初始化RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mActivityMainBinding.recyclerView.setLayoutManager(layoutManager);
        mMatchItemAdapter = new MatchItemAdapter(this, mMatchList);
        mActivityMainBinding.recyclerView.setAdapter(mMatchItemAdapter);

        // 加载数据
        mViewModel.loadMatches();

        // 保存url, userName, password到SQLite的login_table中
        mDatabaseHelper = new DatabaseHelper(this);
    }

    public void onOpenDownloadDialog(View view) {
        // 获取login_table最后一条登录数据
        Cursor loginCursor = mDatabaseHelper.getData();

        String urlStringBuilder = "";
        String userNameStringBuilder = "";
        String passwordStringBuilder = "";


        while (loginCursor.moveToNext()) {
            if (loginCursor.moveToLast()) {
                urlStringBuilder = loginCursor.getString(1);
                userNameStringBuilder = loginCursor.getString(2);
                passwordStringBuilder = loginCursor.getString(3);
            }
        }


        loginDialog = new LoginDialog(urlStringBuilder, userNameStringBuilder, passwordStringBuilder);
        loginDialog.show(getSupportFragmentManager(), "login dialog");
    }

    public void onCancelLoginDialog(View view) {
        loginDialog.dismiss();
    }

    public void onLoginLoginDialog(View view) {
        // add login data into the SQLite
        AddData(loginDialog.url, loginDialog.userName, loginDialog.password);

        // 根据URL登录对应S1系统
        
    }

    // 添加登陆数据到SQLite
    public void AddData(String url, String userName, String password) {
        boolean insertData = mDatabaseHelper.addData(url, userName, password);

        if (insertData) {
            toastMessage("Data insert successfully!");
        } else {
            toastMessage("Data insert failed!");
        }
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // 对assets中的login.json文件进行读, assets - read-only
    public void getLoginJson() {
        String loginJson;

        try {
            InputStream is = getAssets().open(FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            loginJson = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(loginJson);

            // 获取最后一项登录数据
            int jsonArrayLen = jsonArray.length();
            lastLoginItem = jsonArrayLen != 0 ? jsonArray.getJSONObject(jsonArrayLen - 1) : null;



        }  catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}