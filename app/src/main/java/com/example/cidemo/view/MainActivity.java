package com.example.cidemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ActivityMainBinding;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.model.SportsMatch;
import com.example.cidemo.other.BasicAuthInterceptor;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.other.MatchItemAdapter;
import com.example.cidemo.viewmodel.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String FILE_NAME = "login.json";

    private Context mContext;

    DatabaseHelper mDatabaseHelper;

    public ActivityMainBinding mActivityMainBinding;
    private MainViewModel mViewModel;

    public MatchItemAdapter mMatchItemAdapter;
    public List<MatchItem> mMatchList = new ArrayList<>();

    public LoginDialog loginDialog;

    JSONObject lastLoginItem;
    JSONArray jsonArray;

    private OkHttpClient client;

    public MatchListDialog matchListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

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

        // 保存url, userName, password到SQLite的login_table中
        mDatabaseHelper = new DatabaseHelper(this);

        // 加载数据
        mViewModel.loadMatches(mDatabaseHelper);
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
        AddLoginData(loginDialog.url, loginDialog.userName, loginDialog.password);

        // 根据URL登录对应S1系统, 首先需要获取x-csrf-token，然后再post登录数据
        client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(loginDialog.userName, loginDialog.password))
                .build();

        Request request = new Request.Builder()
                .url(loginDialog.url + "/sap/hana/xs/formLogin/token.xsjs")
                .addHeader("x-csrf-token", "fetch")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String mResponse = response.body().string();
                    if (mResponse == "") {
                        String csrfToken = response.headers().get("x-csrf-token");
                        Log.i("TOKEN" ,csrfToken);

                        // 登录
                        OkHttpClient loginClient = new OkHttpClient();

                        RequestBody loginBody = new FormBody.Builder()
                                .add("xs-username", loginDialog.userName)
                                .add("xs-password", loginDialog.password)
                                .build();

                        Request loginRequest = new Request.Builder()
                                .url(loginDialog.url + "/sap/hana/xs/formLogin/login.xscfunc")
                                .addHeader("x-csrf-token", csrfToken)
                                .post(loginBody)
                                .build();

                        try {
                            Response loginResp = loginClient.newCall(loginRequest).execute();
                            String respTest = loginResp.body().string();
                            Log.d("loginResponseCode", String.valueOf(loginResp.code()));

                            // 首先需要获取比赛准备列表
                            // - /sap/sports/mi/appsvc/entityMatchPreparation/service/rest/entityMatchPreparation/matches/team/CE8148E75C5C76408F130E1DB1D976EE
                            OkHttpClient matchClient = new OkHttpClient.Builder()
                                    .addInterceptor(new BasicAuthInterceptor(loginDialog.userName, loginDialog.password))
                                    .build();
                            Request matchRequest = new Request.Builder()
                                    .url(loginDialog.url + "/sap/sports/mi/appsvc/entityMatchPreparation/service/rest/entityMatchPreparation/matches/team/CE8148E75C5C76408F130E1DB1D976EE")
                                    .build();
                            matchClient.newCall(matchRequest).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    int respCode = response.code();
                                    if (response.isSuccessful()) {
                                        String matches = response.body().string();
                                        Headers headers = response.headers();
                                    }

                                    // 如果登录成功则跳转至比赛列表加载页面，仍然是一个Dialog。
                                    LinkedList<SportsMatch> s1Matches = new LinkedList<SportsMatch>();
                                    SportsMatch one = new SportsMatch("10BAD440EA84A34CB1D8D911BD138D9A", "北体大",
                                            "1", "U19 | 2002级梯队", "4");
                                    s1Matches.add(one);
                                    SportsMatch two = new SportsMatch("822BDCE0B6B0AA488B15D3385D199EC1", "陕西大秦之水",
                                            "2", "北体大", "1");
                                    s1Matches.add(two);

                                    onOpenMatchListDialog(s1Matches, mContext);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void onOpenMatchListDialog(LinkedList<SportsMatch> s1MatchesList, Context context) {
        loginDialog.dismiss();

        matchListDialog = new MatchListDialog(s1MatchesList, context);
        matchListDialog.show(getSupportFragmentManager(), "match download dialog");
    }

    public void onCancelMatchesDialog(View view) {
        matchListDialog.dismiss();
    }

    public void onDownloadMatchDialog(View view) {
        // 下载每个比赛的所有数据, 将其存入SQLite
        String currTeamName = (String) ((TextView) view).getText();
        AddMatchData("10BAD440EA84A34CB1D8D911BD138D9A", currTeamName, "1");
    }

    // 添加比赛数据到SQLite
    public void AddMatchData(String matchID, String matchName, String score) {
        boolean insertData = mDatabaseHelper.addMatchData(matchID, matchName, score);

        if (insertData) {
            toastMessage("Match Data insert successfully!");
            // 加载数据
            mViewModel.loadMatches(mDatabaseHelper);
            // 需要弹出下载动态process弹框
            matchListDialog.dismiss();
        } else {
            toastMessage("Match Data insert failed!");
        }
    }

    // 添加登陆数据到SQLite
    public void AddLoginData(String url, String userName, String password) {
        boolean insertData = mDatabaseHelper.addLoginData(url, userName, password);

        if (insertData) {
            toastMessage("Login Data insert successfully!");
        } else {
            toastMessage("Login Data insert failed!");
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