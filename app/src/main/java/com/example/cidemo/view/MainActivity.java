package com.example.cidemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ActivityMainBinding;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.MatchItemAdapter;
import com.example.cidemo.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public ActivityMainBinding mActivityMainBinding;
    private MainViewModel mViewModel;

    public MatchItemAdapter mMatchItemAdapter;
    public List<MatchItem> mMatchList = new ArrayList<>();

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
    }
}