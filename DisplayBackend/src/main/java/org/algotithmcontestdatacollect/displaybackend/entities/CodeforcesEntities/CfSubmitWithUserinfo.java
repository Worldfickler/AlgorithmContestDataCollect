package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "cf_submit_with_userinfo", schema = "graduate", catalog = "")
public class CfSubmitWithUserinfo {
    @Id
    @Basic
    @Column(name = "sid", nullable = false)
    private Long sid;
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "cfid", nullable = false)
    private Long cfid;
    @Basic
    @Column(name = "q_index", nullable = false, length = 255)
    private String qIndex;
    @Basic
    @Column(name = "submit_time", nullable = false)
    private long submitTime;
    @Basic
    @Column(name = "status", nullable = false, length = 255)
    private String status;
    @Basic
    @Column(name = "is_after", nullable = false)
    private byte isAfter;
    @Basic
    @Column(name = "language", nullable = false, length = 255)
    private String language;
    @Basic
    @Column(name = "codeforces_id", nullable = true, length = 255)
    private String codeforcesId;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "realname", nullable = true, length = 255)
    private String realname;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "uid", nullable = true)
    private Long uid;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }


    public Long getCid() {
        return cid;
    }


    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCfid() {
        return cfid;
    }


    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    public String getqIndex() {
        return qIndex;
    }

    public void setqIndex(String qIndex) {
        this.qIndex = qIndex;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(byte isAfter) {
        this.isAfter = isAfter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCodeforcesId() {
        return codeforcesId;
    }

    public void setCodeforcesId(String codeforcesId) {
        this.codeforcesId = codeforcesId;
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

        CfSubmitWithUserinfo that = (CfSubmitWithUserinfo) o;

        if (submitTime != that.submitTime) return false;
        if (isAfter != that.isAfter) return false;
        if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (cfid != null ? !cfid.equals(that.cfid) : that.cfid != null) return false;
        if (qIndex != null ? !qIndex.equals(that.qIndex) : that.qIndex != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (codeforcesId != null ? !codeforcesId.equals(that.codeforcesId) : that.codeforcesId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (cfid != null ? cfid.hashCode() : 0);
        result = 31 * result + (qIndex != null ? qIndex.hashCode() : 0);
        result = 31 * result + (int) (submitTime ^ (submitTime >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) isAfter;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (codeforcesId != null ? codeforcesId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
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

    public void setName(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
