package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ac_stucontest_with_cinfo_userinfo", schema = "graduate")
public class AcStucontestWithCinfoUserinfo {
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;

    @Basic
    @Column(name = "acid", nullable = false)
    private Long acid;

    @Basic
    @Column(name = "t_rank", nullable = false)
    private int tRank;

    @Basic
    @Column(name = "solve", nullable = false)
    private int solve;

    @Basic
    @Column(name = "diff", nullable = false)
    private int diff;

    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @Column(name = "atcoder_id", nullable = true, length = 255)
    private String atcoderId;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "stu_no", nullable = true, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "start_time_stamp", nullable = true)
    private Long startTimeStamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getAcid() {
        return acid;
    }

    public void setAcid(Long acid) {
        this.acid = acid;
    }

    public int gettRank() {
        return tRank;
    }

    public void settRank(int tRank) {
        this.tRank = tRank;
    }

    public int getSolve() {
        return solve;
    }

    public void setSolve(int solve) {
        this.solve = solve;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAtcoderId() {
        return atcoderId;
    }

    public void setAtcoderId(String atcoderId) {
        this.atcoderId = atcoderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getName() {
        return name;
    }

    public void setName(String nickname) {
        this.name = nickname;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcStucontestWithCinfoUserinfo that = (AcStucontestWithCinfoUserinfo) o;
        return tRank == that.tRank && solve == that.solve && diff == that.diff && rating == that.rating && Objects.equals(id, that.id) && Objects.equals(cid, that.cid) && Objects.equals(acid, that.acid) && Objects.equals(atcoderId, that.atcoderId) && Objects.equals(username, that.username) && Objects.equals(classname, that.classname) && Objects.equals(stuNo, that.stuNo) && Objects.equals(school, that.school) && Objects.equals(realname, that.realname) && Objects.equals(name, that.name) && Objects.equals(startTimeStamp, that.startTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cid, acid, tRank, solve, diff, rating, atcoderId, username, classname, stuNo, school, realname, name, startTimeStamp);
    }
}
