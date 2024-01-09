package org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_contest", schema = "graduate")
public class CfContest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "type", nullable = false, length = 255)
    private String type;
    @Basic
    @Column(name = "level", nullable = false)
    private int level;
    @Basic
    @Column(name = "start_time_stamp", nullable = false)
    private long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp", nullable = false)
    private long endTimeStamp;
    @Basic
    @Column(name = "duration", nullable = false)
    private long duration;
    @Basic
    @Column(name = "is_normal", nullable = false)
    private byte isNormal;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public byte getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(byte isNormal) {
        this.isNormal = isNormal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfContest that = (CfContest) o;
        return level == that.level && startTimeStamp == that.startTimeStamp && endTimeStamp == that.endTimeStamp && duration == that.duration && isNormal == that.isNormal && Objects.equals(cid, that.cid) && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, name, type, level, startTimeStamp, endTimeStamp, duration, isNormal);
    }
}
