package notworry.rj.edu.notworry.Entity;

import java.util.ArrayList;
import java.util.List;


public class Users {
    private Integer userId; // 主键
    private String picture; // 头像
    private String userName; // 用户名
    private String passWord; // 密码
    private String realName; // 真实姓名
    private String idNumber; // 身份证
    private String phoneNum; // 手机号
    private School school;
    private SearchRecord searchRecord;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public SearchRecord getSearchRecord() {
        return searchRecord;
    }

    public void setSearchRecord(SearchRecord searchRecord) {
        this.searchRecord = searchRecord;
    }
}