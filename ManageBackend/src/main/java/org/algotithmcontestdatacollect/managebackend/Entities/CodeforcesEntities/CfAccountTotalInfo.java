package org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_account_total_info", schema = "graduate")
public class CfAccountTotalInfo {
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "status", nullable = true)
    private Byte status;
    @Basic
    @Column(name = "stu_no", nullable = true, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "year", nullable = true)
    private Integer year;
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Basic
    @Column(name = "codeforces_id", nullable = false, length = 255)
    private String codeforcesId;
    @Basic
    @Column(name = "rating", nullable = true)
    private Integer rating;
    @Basic
    @Column(name = "is_main", nullable = false)
    private byte isMain;
    @Basic
    @Column(name = "is_using", nullable = false)
    private byte isUsing;
    @Basic
    @Column(name = "participate_time", nullable = false)
    private long participateTime;
    @Basic
    @Column(name = "solve", nullable = true)
    private Long solve;
    @Basic
    @Column(name = "after", nullable = true)
    private Long after;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCodeforcesId() {
        return codeforcesId;
    }

    public void setCodeforcesId(String codeforcesId) {
        this.codeforcesId = codeforcesId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public byte getIsMain() {
        return isMain;
    }

    public void setIsMain(byte isMain) {
        this.isMain = isMain;
    }

    public byte getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(byte isUsing) {
        this.isUsing = isUsing;
    }

    public long getParticipateTime() {
        return participateTime;
    }

    public void setParticipateTime(long participateTime) {
        this.participateTime = participateTime;
    }

    public Long getSolve() {
        return solve;
    }

    public void setSolve(Long solve) {
        this.solve = solve;
    }

    public Long getAfter() {
        return after;
    }

    public void setAfter(Long after) {
        this.after = after;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfAccountTotalInfo that = (CfAccountTotalInfo) o;
        return isMain == that.isMain && isUsing == that.isUsing && participateTime == that.participateTime && Objects.equals(username, that.username) && Objects.equals(realname, that.realname) && Objects.equals(classname, that.classname) && Objects.equals(school, that.school) && Objects.equals(status, that.status) && Objects.equals(stuNo, that.stuNo) && Objects.equals(year, that.year) && Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(codeforcesId, that.codeforcesId) && Objects.equals(rating, that.rating) && Objects.equals(solve, that.solve) && Objects.equals(after, that.after);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, realname, classname, school, status, stuNo, year, id, uid, codeforcesId, rating, isMain, isUsing, participateTime, solve, after);
    }
}
