package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CfMonthSubmitPK implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ym_date", nullable = true, length = 7)
    private String ymDate;

    @Column(name = "cfid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cfid;

    public String getYmDate() {
        return ymDate;
    }

    public void setYmDate(String ymDate) {
        this.ymDate = ymDate;
    }

    public Long getCfid() {
        return cfid;
    }

    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CfMonthSubmitPK)) return false;
        CfMonthSubmitPK that = (CfMonthSubmitPK) o;
        return Objects.equals(ymDate, that.ymDate) && Objects.equals(cfid, that.cfid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ymDate, cfid);
    }
}
