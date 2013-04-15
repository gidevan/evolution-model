package com.vano.projects.evolution.common;

/**
 * Created by IntelliJ IDEA.
 * User: Ivan_Pukhau
 * Date: 11/24/11
 * Time: 3:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Position {

    private int x;

    private int y;

    public int getX() {
        return x;
    }

    public Position(){};

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

}
