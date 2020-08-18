package com.example.cidemo.model;

public class SportsMatch {
    private String match_id;
    private String home_team;
    private String home_team_score;
    private String guest_team;
    private String guest_team_score;

    public SportsMatch(String match_id, String home_team, String home_team_score, String guest_team, String guest_team_score) {
        this.match_id = match_id;
        this.home_team = home_team;
        this.home_team_score = home_team_score;
        this.guest_team = guest_team;
        this.guest_team_score = guest_team_score;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public void setHome_team_score(String home_team_score) {
        this.home_team_score = home_team_score;
    }

    public void setGuest_team(String guest_team) {
        this.guest_team = guest_team;
    }

    public void setGuest_team_score(String guest_team_score) {
        this.guest_team_score = guest_team_score;
    }

    public String getMatch_id() {
        return match_id;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getHome_team_score() {
        return home_team_score;
    }

    public String getGuest_team() {
        return guest_team;
    }

    public String getGuest_team_score() {
        return guest_team_score;
    }
}
