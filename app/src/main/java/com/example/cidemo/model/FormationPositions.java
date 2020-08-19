package com.example.cidemo.model;

public class FormationPositions {

    private int positionNumber;
    private Float x;
    private Float y;

    public FormationPositions(int positionNumber, Float x, Float y) {
        this.positionNumber = positionNumber;
        this.x = x;
        this.y = y;
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
