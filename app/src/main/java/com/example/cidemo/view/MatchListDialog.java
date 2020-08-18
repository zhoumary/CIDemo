package com.example.cidemo.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cidemo.R;
import com.example.cidemo.model.SportsMatch;
import com.example.cidemo.other.SportsMatchAdapter;
import java.util.LinkedList;

public class MatchListDialog extends AppCompatDialogFragment {
    private static final String TAG = "MatchListDialog";

    private LinkedList<SportsMatch> s1Matches;

    private Context mContext;
    private SportsMatchAdapter mAdapter = null;
    private ListView sports_matches;

    public MatchListDialog(LinkedList<SportsMatch> matches, Context context) {
        this.s1Matches = matches;
        this.mContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_matches, null);

        builder.setView(view)
                .setTitle("");


        sports_matches = (ListView) view.findViewById(R.id.sports_matches);
        LinkedList<String> matchNames = new LinkedList<String>();
        for (int i = 0; i < s1Matches.size(); i++) {
            matchNames.add(s1Matches.get(i).getHome_team());
        }
        mAdapter = new SportsMatchAdapter(mContext, (LinkedList<String>) matchNames);
        sports_matches.setAdapter(mAdapter);


        return builder.create();
    }
}
