package org.algotithmcontestdatacollect.displaybackend.entities.AtcoderEntities;

import javax.persistence.*;

@Entity
@Table(name = "ac_contest_with_participate", schema = "graduate")
public class AcContestWithParticipate {
    @Id
    @Basic
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "level")
    private Integer level;
    @Basic
    @Column(name = "start_time_stamp")
    private long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp")
    private long endTimeStamp;
    @Basic
    @Column(name = "duration")
    private long duration;
    @Basic
    @Column(name = "is_normal")
    private byte isNormal;
    @Basic
    @Column(name = "participate")
    private long participate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
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

    public long getParticipate() {
        return participate;
    }

    public void setParticipate(long participate) {
        this.participate = participate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcContestWithParticipate that = (AcContestWithParticipate) o;

        if (startTimeStamp != that.startTimeStamp) return false;
        if (endTimeStamp != that.endTimeStamp) return false;
        if (duration != that.duration) return false;
        if (isNormal != that.isNormal) return false;
        if (participate != that.participate) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (int) (startTimeStamp ^ (startTimeStamp >>> 32));
        result = 31 * result + (int) (endTimeStamp ^ (endTimeStamp >>> 32));
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (int) isNormal;
        result = 31 * result + (int) (participate ^ (participate >>> 32));
        return result;
    }
}
