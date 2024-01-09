package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;

@Entity
@Table(name = "ac_submit", schema = "graduate")
public class AcSubmit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sid", nullable = false)
    private Long sid;
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "acid", nullable = false)
    private Long acid;
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

    public String getqIndex() {
        return qIndex;
    }

    public void setqIndex(String qIndex) {
        this.qIndex = qIndex;
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

        if (submitTime != that.submitTime) return false;
        if (isAfter != that.isAfter) return false;
        if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (acid != null ? !acid.equals(that.acid) : that.acid != null) return false;
        if (qIndex != null ? !qIndex.equals(that.qIndex) : that.qIndex != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (acid != null ? acid.hashCode() : 0);
        result = 31 * result + (qIndex != null ? qIndex.hashCode() : 0);
        result = 31 * result + (int) (submitTime ^ (submitTime >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) isAfter;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
