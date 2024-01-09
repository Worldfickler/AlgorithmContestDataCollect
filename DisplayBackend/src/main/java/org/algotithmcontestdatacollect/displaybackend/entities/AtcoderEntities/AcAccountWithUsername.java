package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;

@Entity
@Table(name = "ac_account_with_username", schema = "graduate")
public class AcAccountWithUsername {
    @Id
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "classname")
    private String classname;
    @Basic
    @Column(name = "school")
    private Long school;
    @Basic
    @Column(name = "status")
    private Byte status;
    @Basic
    @Column(name = "stu_no")
    private String stuNo;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Basic
    @Column(name = "realname")
    private String realname;
    @Basic
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "atcoder_id")
    private String atcoderId;
    @Basic
    @Column(name = "rating")
    private long rating;
    @Basic
    @Column(name = "is_main")
    private byte isMain;
    @Basic
    @Column(name = "is_using")
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

        AcAccountWithUsername that = (AcAccountWithUsername) o;

        if (rating != that.rating) return false;
        if (isMain != that.isMain) return false;
        if (isUsing != that.isUsing) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (classname != null ? !classname.equals(that.classname) : that.classname != null) return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (stuNo != null ? !stuNo.equals(that.stuNo) : that.stuNo != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (realname != null ? !realname.equals(that.realname) : that.realname != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (atcoderId != null ? !atcoderId.equals(that.atcoderId) : that.atcoderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (stuNo != null ? stuNo.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (atcoderId != null ? atcoderId.hashCode() : 0);
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        result = 31 * result + (int) isMain;
        result = 31 * result + (int) isUsing;
        return result;
    }
}
