package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;

@Entity
@Table(name = "ac_stucontest_with_userinfo", schema = "graduate")
public class AcStucontestWithUserinfo {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcStucontestWithUserinfo that = (AcStucontestWithUserinfo) o;

        if (tRank != that.tRank) return false;
        if (solve != that.solve) return false;
        if (diff != that.diff) return false;
        if (rating != that.rating) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (acid != null ? !acid.equals(that.acid) : that.acid != null) return false;
        if (atcoderId != null ? !atcoderId.equals(that.atcoderId) : that.atcoderId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (acid != null ? acid.hashCode() : 0);
        result = 31 * result + tRank;
        result = 31 * result + solve;
        result = 31 * result + diff;
        result = 31 * result + rating;
        result = 31 * result + (atcoderId != null ? atcoderId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
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
}
