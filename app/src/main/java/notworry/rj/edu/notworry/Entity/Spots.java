package notworry.rj.edu.notworry.Entity;

import java.util.ArrayList;
import java.util.List;


public class Spots {
    private int spotId;
    private String spotImg1;
    private String spotImg2;
    private String spotImg3;
    private String spotName;
    private int accessHeat;// 访问热度
    private int searchHeat;// 搜索热度
    private SpotsDetails spotDetail;


    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getSpotImg1() {
        return spotImg1;
    }

    public void setSpotImg1(String spotImg1) {
        this.spotImg1 = spotImg1;
    }

    public String getSpotImg2() {
        return spotImg2;
    }

    public void setSpotImg2(String spotImg2) {
        this.spotImg2 = spotImg2;
    }

    public String getSpotImg3() {
        return spotImg3;
    }

    public void setSpotImg3(String spotImg3) {
        this.spotImg3 = spotImg3;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public int getAccessHeat() {
        return accessHeat;
    }

    public void setAccessHeat(int accessHeat) {
        this.accessHeat = accessHeat;
    }

    public int getSearchHeat() {
        return searchHeat;
    }

    public void setSearchHeat(int searchHeat) {
        this.searchHeat = searchHeat;
    }

    public SpotsDetails getSpotDetail() {
        return spotDetail;
    }

    public void setSpotDetail(SpotsDetails spotDetail) {
        this.spotDetail = spotDetail;
    }

}
