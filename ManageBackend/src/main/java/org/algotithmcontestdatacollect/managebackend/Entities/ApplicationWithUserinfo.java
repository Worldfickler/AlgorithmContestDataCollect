package org.algotithmcontestdatacollect.managebackend.Entities;

import javax.persistence.*;

@Entity
@Table(name = "application_with_userinfo", schema = "graduate", catalog = "")
public class ApplicationWithUserinfo {
    @Id
    @Basic
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "time")
    private long time;
    @Basic
    @Column(name = "opertation")
    private String opertation;
    @Basic
    @Column(name = "parameter")
    private String parameter;
    @Basic
    @Column(name = "status")
    private byte status;
    @Basic
    @Column(name = "admin_name")
    private String adminName;
    @Basic
    @Column(name = "operation_time")
    private Long operationTime;
    @Basic
    @Column(name = "school")
    private Long school;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "stu_no")
    private String stuNo;
    @Basic
    @Column(name = "classname")
    private String classname;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Basic
    @Column(name = "realname")
    private String realname;
    @Basic
    @Column(name = "ustatus")
    private Byte ustatus;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOpertation() {
        return opertation;
    }

    public void setOpertation(String opertation) {
        this.opertation = opertation;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
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

    public Byte getUstatus() {
        return ustatus;
    }

    public void setUstatus(Byte ustatus) {
        this.ustatus = ustatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationWithUserinfo that = (ApplicationWithUserinfo) o;

        if (time != that.time) return false;
        if (status != that.status) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (opertation != null ? !opertation.equals(that.opertation) : that.opertation != null) return false;
        if (parameter != null ? !parameter.equals(that.parameter) : that.parameter != null) return false;
        if (adminName != null ? !adminName.equals(that.adminName) : that.adminName != null) return false;
        if (operationTime != null ? !operationTime.equals(that.operationTime) : that.operationTime != null)
            return false;
        if (school != null ? !school.equals(that.school) : that.school != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (stuNo != null ? !stuNo.equals(that.stuNo) : that.stuNo != null) return false;
        if (classname != null ? !classname.equals(that.classname) : that.classname != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (realname != null ? !realname.equals(that.realname) : that.realname != null) return false;
        if (ustatus != null ? !ustatus.equals(that.ustatus) : that.ustatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (opertation != null ? opertation.hashCode() : 0);
        result = 31 * result + (parameter != null ? parameter.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + (adminName != null ? adminName.hashCode() : 0);
        result = 31 * result + (operationTime != null ? operationTime.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (stuNo != null ? stuNo.hashCode() : 0);
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (ustatus != null ? ustatus.hashCode() : 0);
        return result;
    }
}
