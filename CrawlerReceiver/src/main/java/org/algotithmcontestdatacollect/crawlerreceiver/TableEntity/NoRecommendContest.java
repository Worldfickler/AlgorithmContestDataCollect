package org.algotithmcontestdatacollect.crawlerreceiver.TableEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "no_recommend_contest")
@IdClass(NoRecommendContestPK.class)
public class NoRecommendContest{

    public NoRecommendContest(Long cid, Integer reason) {
        this.cid = cid;
        this.reason = reason;
    }

    public NoRecommendContest() {
    }

    /**
     * 比赛id
     */
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

    /**
     * 比赛id
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * 比赛id
     */
    public Long getCid() {
        return cid;
    }

    /**
     * 1:difficulty=0
     * 2:专门语言比赛不推荐
     * 3.无法提交或无法查看该比赛
     */
    public void setReason(Integer reason) {
        this.reason = reason;
    }

    /**
     * 1:difficulty=0
     * 2:专门语言比赛不推荐
     * 3.无法提交或无法查看该比赛
     */
    public Integer getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "NoRecommendContest{" +
               "cid=" + cid + '\'' +
               "reason=" + reason + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoRecommendContest that = (NoRecommendContest) o;
        return cid.equals(that.cid) && reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, reason);
    }
}
