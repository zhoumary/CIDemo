package com.example.cidemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ActivityMainBinding;
import com.example.cidemo.databinding.ActivityMainMatchBinding;
import com.example.cidemo.model.FormationPositions;
import com.example.cidemo.model.MatchFormation;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.other.DatabaseHelper;
import com.example.cidemo.viewmodel.MainMatchViewModel;

import java.util.ArrayList;


public class MainMatchActivity extends AppCompatActivity {

    private static final String TAG = "MainMatchActivity";

    public ActivityMainMatchBinding mActivityMainMatchBinding;
    private MainMatchViewModel mMatchViewModel;

    private FormationView formationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_match);
        // 获取Match Item Adapter点击时的match item
        MatchItem matchItem = (MatchItem) getIntent().getSerializableExtra("EXTRA_MATCH_ITEM");
        // 临时的比赛阵型数据
        setMatchFormations(matchItem);

        // 设置navigation bar和action bar为透明
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 设置dataBinding、viewModel
        mActivityMainMatchBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_match);
        mMatchViewModel = new MainMatchViewModel(this);
        mActivityMainMatchBinding.setMatchViewModel(mMatchViewModel);

        // 加载数据
        mMatchViewModel.loadMatchData(matchItem);


        // 渲染阵型数据
        formationView = (FormationView) findViewById(R.id.formationView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ArrayList<FormationPositions> matchItemPositions = new ArrayList<>();
        ArrayList<MatchFormation> matchItemFormations = matchItem.getMatchFormations();
        if (matchItemFormations != null && matchItemFormations.size() != 0) {
            matchItemPositions = matchItemFormations.get(0).getPositions();
        }
        formationView.init(metrics, matchItemPositions);
    }

    private void setMatchFormations(MatchItem matchItem) {
        // set match positions
        ArrayList<FormationPositions> formationPositions = new ArrayList<>();
        FormationPositions one = new FormationPositions(1, (float) 217, (float) 97, "ST");
        FormationPositions two = new FormationPositions(2, (float) 97, (float) 211, "ST");
        FormationPositions three = new FormationPositions(3, (float) 217, (float) 221, "MF");
        FormationPositions four = new FormationPositions(4, (float) 336, (float) 221, "ST");
        FormationPositions five = new FormationPositions(5, (float) 178, (float) 346, "MF");
        formationPositions.add(one);
        formationPositions.add(two);
        formationPositions.add(three);
        formationPositions.add(four);
        formationPositions.add(five);

        // set match formations
        ArrayList<MatchFormation> formations = new ArrayList<>();
        MatchFormation matchFormation = new MatchFormation("026DCF7039F2EB4CB146095EF52D18FB",
                "4231", formationPositions);
        formations.add(matchFormation);
        
        matchItem.setMatchFormations(formations);
    }
}