package org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ac_contest", schema = "graduate")
public class AcContest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;
    @Basic
    @Column(name = "type", nullable = true, length = 255)
    private String type;
    @Basic
    @Column(name = "level", nullable = true)
    private Integer level;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcContest that = (AcContest) o;
        return startTimeStamp == that.startTimeStamp && endTimeStamp == that.endTimeStamp && duration == that.duration && isNormal == that.isNormal && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(nickname, that.nickname) && Objects.equals(type, that.type) && Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickname, type, level, startTimeStamp, endTimeStamp, duration, isNormal);
    }
}
