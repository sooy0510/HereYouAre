package com.example.hereyouare;

import java.util.Objects;

public class Train {
    String num;
    String title;
    String arrive;
    String seat;

    public Train() {
    }

    public Train(String num, String title, String arrive, String seat) {
        this.num = num;
        this.title = title;
        this.arrive = arrive;
        this.seat = seat;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;
        Train train = (Train) o;
        return Objects.equals(getNum(), train.getNum()) &&
                Objects.equals(getTitle(), train.getTitle()) &&
                Objects.equals(getArrive(), train.getArrive()) &&
                Objects.equals(getSeat(), train.getSeat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNum(), getTitle(), getArrive(), getSeat());
    }
}
