package org.algotithmcontestdatacollect.crawlerdispatcher.TableEntity;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ac_submit")
public class AcSubmit {
    @Id
    @Column(name = "sid")
    private Long sid;
    @Basic
    @Column(name = "cid")
    private Long cid;
    @Basic
    @Column(name = "acid")
    private Long acid;
    @Basic
    @Column(name = "q_index")
    private String Qindex;
    @Basic
    @Column(name = "submit_time")
    private long submitTime;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "is_after")
    private byte isAfter;
    @Basic
    @Column(name = "language")
    private String language;

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

    public Long getAcid() {
        return acid;
    }

    public void setAcid(Long acid) {
        this.acid = acid;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long ctime) {
        this.submitTime = ctime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcSubmit that = (AcSubmit) o;
        return submitTime == that.submitTime && isAfter == that.isAfter && Objects.equals(sid, that.sid) && Objects.equals(cid, that.cid) && Objects.equals(acid, that.acid) && Objects.equals(Qindex, that.Qindex) && Objects.equals(status, that.status) && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, cid, acid, Qindex, submitTime, status, isAfter, language);
    }

    public String getQindex() {
        return Qindex;
    }

    public void setQindex(String qindex) {
        Qindex = qindex;
    }


    public  static AcSubmit fromJSONObject(JSONObject jsonObject){
        AcSubmit acSubmit = new AcSubmit();
        acSubmit.setSid(jsonObject.getLong("sid"));
        acSubmit.setCid(jsonObject.getLong("cid"));
        acSubmit.setAcid(jsonObject.getLong("acid"));
        acSubmit.setSubmitTime(jsonObject.getLong("ctime"));
        acSubmit.setIsAfter(jsonObject.getByte("isAfter"));
        acSubmit.setLanguage(jsonObject.getString("language"));
        acSubmit.setStatus(jsonObject.getString("status"));
        acSubmit.setQindex(jsonObject.getString("index"));
        return acSubmit;
    }
}
