package com.helio.cypher.models;

public class Mood {

    private int radius;
    private Point point;
    private String key = "";

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    private int count = 1;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public int hashCode() {
        return point.getX() * point.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mood))
            return false;
        if (obj == this)
            return true;
        return (((Mood) obj).point.getX() == this.point.getX()) && (((Mood) obj).point.getY() == this.point.getY());
    }
}
