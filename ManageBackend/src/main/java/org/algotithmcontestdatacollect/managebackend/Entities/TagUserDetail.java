package org.algotithmcontestdatacollect.managebackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag_user_detail", schema = "graduate")
@IdClass(TagUserMapPK.class)
public class TagUserDetail {
    @Basic
    @Id
    @Column(name = "uid", nullable = true)
    private Long uid;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "school", nullable = true)
    private Long school;
    @Basic
    @Column(name = "classname", nullable = true, length = 255)
    private String classname;
    @Basic
    @Column(name = "stu_no", nullable = true, length = 255)
    private String stuNo;
    @Basic
    @Column(name = "year", nullable = true)
    private Integer year;
    @Basic
    @Column(name = "status", nullable = true)
    private Byte status;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Id
    @Column(name = "tid", nullable = true)
    private Long tid;
    @Basic
    @Column(name = "tname", nullable = true, length = 255)
    private String tname;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagUserDetail that = (TagUserDetail) o;
        return Objects.equals(uid, that.uid) && Objects.equals(username, that.username) && Objects.equals(school, that.school) && Objects.equals(classname, that.classname) && Objects.equals(stuNo, that.stuNo) && Objects.equals(year, that.year) && Objects.equals(status, that.status) && Objects.equals(realname, that.realname) && Objects.equals(tid, that.tid) && Objects.equals(tname, that.tname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, username, school, classname, stuNo, year, status, realname, tid, tname);
    }
}
