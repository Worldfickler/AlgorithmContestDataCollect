package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(AcMonthSubmitPK.class)
@Table(name = "ac_month_submit", schema = "graduate")
public class AcMonthSubmit {
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Id
    @Column(name = "acid", nullable = false)
    private Long acid;
    @Basic
    @Id
    @Column(name = "ym_date", nullable = true, length = 7)
    private String ymDate;
    @Basic
    @Column(name = "submit_cnt", nullable = false)
    private long submitCnt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAcid() {
        return acid;
    }

    public void setAcid(Long acid) {
        this.acid = acid;
    }

    public String getYmDate() {
        return ymDate;
    }

    public void setYmDate(String ymDate) {
        this.ymDate = ymDate;
    }

    public long getSubmitCnt() {
        return submitCnt;
    }

    public void setSubmitCnt(long submitCnt) {
        this.submitCnt = submitCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcMonthSubmit that = (AcMonthSubmit) o;
        return submitCnt == that.submitCnt && Objects.equals(username, that.username) && Objects.equals(acid, that.acid) && Objects.equals(ymDate, that.ymDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, acid, ymDate, submitCnt);
    }
}
