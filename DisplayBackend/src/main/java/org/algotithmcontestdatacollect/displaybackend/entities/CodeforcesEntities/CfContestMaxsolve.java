package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_contest_maxsolve", schema = "graduate")
public class CfContestMaxsolve {
    @Id
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "username", nullable = true, length = 255)
    private String username;
    @Basic
    @Column(name = "max_solve", nullable = true, length = 255)
    private String maxSolve;
    @Basic
    @Column(name = "level", nullable = true)
    private Integer level;
    @Basic
    @Column(name = "start_time_stamp", nullable = true)
    private Long startTimeStamp;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMaxSolve() {
        return maxSolve;
    }

    public void setMaxSolve(String maxSolve) {
        this.maxSolve = maxSolve;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfContestMaxsolve that = (CfContestMaxsolve) o;
        return Objects.equals(cid, that.cid) && Objects.equals(username, that.username) && Objects.equals(maxSolve, that.maxSolve) && Objects.equals(level, that.level) && Objects.equals(startTimeStamp, that.startTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, username, maxSolve, level, startTimeStamp);
    }
}
