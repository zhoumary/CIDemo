package com.example.cidemo.model;

public class FormationPositions {

    private int positionNumber;
    private Float x;
    private Float y;

    private String position;

    public FormationPositions(int positionNumber, Float x, Float y, String position) {
        this.positionNumber = positionNumber;
        this.x = x + 380;
        this.y = y;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
