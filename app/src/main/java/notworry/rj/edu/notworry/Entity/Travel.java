package notworry.rj.edu.notworry.Entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Travel {
    private Integer travelId; // 主键
    private String travelname;
    private String picture;
    private String destination;// 目的地
    private String time; // 出发时间
    private Integer duration;// 持续天数
    private List<Spots> spotList = new ArrayList<Spots> ();

    public Integer getTravelId() {
        return travelId;
    }

    public void setTravelId(Integer travelId) {
        this.travelId = travelId;
    }

    public String getTravelname() {
        return travelname;
    }

    public void setTravelname(String travelname) {
        this.travelname = travelname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Spots> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spots> spotList) {
        this.spotList = spotList;
    }

}
