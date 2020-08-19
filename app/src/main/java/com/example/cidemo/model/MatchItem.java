package com.example.cidemo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchItem implements Serializable {
    private String title;
    private String item_id;
    private String source;
    private ArrayList<MatchFormation> matchFormations;

    public MatchItem(String title, String item_id, String source, ArrayList<MatchFormation> formations) {
        this.title = title;
        this.item_id = item_id;
        this.source = source;
        this.matchFormations = formations;
    }

    public MatchItem(String matchName, String matchID, String matchScore) {
        this.title = matchName;
        this.item_id = matchID;
        this.source = matchScore;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setMatchFormations(ArrayList<MatchFormation> matchFormations) {
        this.matchFormations = matchFormations;
    }

    public String getTitle() {
        return title;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getSource() {
        return source;
    }

    public ArrayList<MatchFormation> getMatchFormations() {
        return matchFormations;
    }
}
