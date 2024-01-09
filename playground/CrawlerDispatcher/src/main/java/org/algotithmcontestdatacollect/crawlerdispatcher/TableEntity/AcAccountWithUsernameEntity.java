package org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ac_account_with_username", schema = "graduate")
public class AcAccountWithUsernameEntity {
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
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
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Basic
    @Column(name = "atcoder_id", nullable = false, length = 255)
    private String atcoderId;
    @Basic
    @Column(name = "rating", nullable = false)
    private long rating;
    @Basic
    @Column(name = "is_main", nullable = false)
    private byte isMain;
    @Basic
    @Column(name = "is_using", nullable = false)
    private byte isUsing;

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getAtcoderId() {
        return atcoderId;
    }

    public void setAtcoderId(String atcoderId) {
        this.atcoderId = atcoderId;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcAccountWithUsernameEntity that = (AcAccountWithUsernameEntity) o;
        return rating == that.rating && isMain == that.isMain && isUsing == that.isUsing && Objects.equals(username, that.username) && Objects.equals(classname, that.classname) && Objects.equals(school, that.school) && Objects.equals(status, that.status) && Objects.equals(stuNo, that.stuNo) && Objects.equals(year, that.year) && Objects.equals(realname, that.realname) && Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(atcoderId, that.atcoderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, classname, school, status, stuNo, year, realname, id, uid, atcoderId, rating, isMain, isUsing);
    }

    @Override
    public String toString() {
        return "AcAccountWithUsernameEntity{" +
                "username='" + username + '\'' +
                ", classname='" + classname + '\'' +
                ", school=" + school +
                ", status=" + status +
                ", stuNo='" + stuNo + '\'' +
                ", year=" + year +
                ", realname='" + realname + '\'' +
                ", id=" + id +
                ", uid=" + uid +
                ", atcoderId='" + atcoderId + '\'' +
                ", rating=" + rating +
                ", isMain=" + isMain +
                ", isUsing=" + isUsing +
                '}';
    }
}
