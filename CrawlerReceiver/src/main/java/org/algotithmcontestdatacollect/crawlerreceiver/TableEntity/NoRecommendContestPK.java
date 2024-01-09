package org.algotithmcontestdatacollect.crawlerreceiver.TableEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class NoRecommendContestPK implements Serializable {
    @Id
    @Column(name = "cid", nullable = false)
    private Long cid;

    /**
     * 1:difficulty=0
     * 2:专门语言比赛不推荐
     * 3.无法提交或无法查看该比赛
     */
    @Id
    @Column(name = "reason", nullable = false)
    private Integer reason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoRecommendContestPK that = (NoRecommendContestPK) o;
        return cid.equals(that.cid) && reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, reason);
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
}
