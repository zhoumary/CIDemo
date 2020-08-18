package com.example.cidemo.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cidemo.R;
import com.example.cidemo.model.SportsMatch;

import java.util.LinkedList;
import java.util.zip.Inflater;

public class SportsMatchAdapter extends ArrayAdapter<String> {

    private LinkedList<String> teamNames;
    private Context mContext;

    public SportsMatchAdapter(Context mContext, LinkedList<String> teams) {
        super(mContext, R.layout.item_list_match, R.id.txt_team, teams);
        this.teamNames = teams;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.item_list_match, parent, false);
        TextView teamName = row.findViewById(R.id.txt_team);
        teamName.setText(teamNames.get(position));


        return row;
    }
}
