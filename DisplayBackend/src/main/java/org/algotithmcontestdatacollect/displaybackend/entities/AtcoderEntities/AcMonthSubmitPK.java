package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class AcMonthSubmitPK implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ym_date", nullable = true, length = 7)
    private String ymDate;
    @Column(name = "acid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acid;

    public String getYmDate() {
        return ymDate;
    }

    public void setYmDate(String ymDate) {
        this.ymDate = ymDate;
    }

    public Long getAcid() {
        return acid;
    }

    public void setAcid(Long acid) {
        this.acid = acid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcMonthSubmitPK)) return false;
        AcMonthSubmitPK that = (AcMonthSubmitPK) o;
        return Objects.equals(ymDate, that.ymDate) && Objects.equals(acid, that.acid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ymDate, acid);
    }
}
