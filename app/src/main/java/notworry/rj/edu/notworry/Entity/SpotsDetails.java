package notworry.rj.edu.notworry.Entity;

import java.sql.Timestamp;

public class SpotsDetails {
    private int spotsDetailsId;
    private String introduction;
    private double price;
    private String openingTime;
    private String playTime;
    private String spotsPosition;

    public int getSpotsDetailsId() {
        return spotsDetailsId;
    }

    public void setSpotsDetailsId(int spotsDetailsId) {
        this.spotsDetailsId = spotsDetailsId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getSpotsPosition() {
        return spotsPosition;
    }

    public void setSpotsPosition(String spotsPosition) {
        this.spotsPosition = spotsPosition;
    }

}
