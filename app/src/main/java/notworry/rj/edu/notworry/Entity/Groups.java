package notworry.rj.edu.notworry.Entity;

import java.util.ArrayList;
import java.util.List;

public class Groups {
    private Integer groupId;// 主键
    private String picture;
    private String groupName;// 队伍名称
    private Integer realNum;// 总人数
    private Integer needNum;// 所需人数
    private String groupIntro;  //队伍介绍
    private List<Users> userIdList = new ArrayList<Users> ();

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getRealNum() {
        return realNum;
    }

    public void setRealNum(Integer realNum) {
        this.realNum = realNum;
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public List<Users> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Users> userIdList) {
        this.userIdList = userIdList;
    }
}
