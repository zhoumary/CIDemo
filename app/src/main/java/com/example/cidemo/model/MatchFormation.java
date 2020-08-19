package com.example.cidemo.model;

import java.util.ArrayList;
import java.util.List;

public class MatchFormation {

    private String formationID;
    private String formationTitle;
    private ArrayList<FormationPositions> positions;

    public MatchFormation(String formationID, String formationTitle, ArrayList<FormationPositions> positions) {
        this.formationID = formationID;
        this.formationTitle = formationTitle;
        this.positions = positions;
    }

    public String getFormationID() {
        return formationID;
    }

    public void setFormationID(String formationID) {
        this.formationID = formationID;
    }

    public String getFormationTitle() {
        return formationTitle;
    }

    public void setFormationTitle(String formationTitle) {
        this.formationTitle = formationTitle;
    }

    public void setPositions(ArrayList<FormationPositions> positions) {
        this.positions = positions;
    }

    public ArrayList<FormationPositions> getPositions() {
        return positions;
    }

}
