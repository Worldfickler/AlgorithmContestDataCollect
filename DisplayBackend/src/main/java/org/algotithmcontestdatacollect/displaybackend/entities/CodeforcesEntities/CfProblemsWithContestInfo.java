package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_problems_with_contest_info", schema = "graduate")
public class CfProblemsWithContestInfo {
    @Basic
    @Column(name = "cid", nullable = true)
    private Long cid;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "type", nullable = true, length = 255)
    private String type;
    @Basic
    @Column(name = "level", nullable = true)
    private Integer level;
    @Basic
    @Column(name = "start_time_stamp", nullable = true)
    private Long startTimeStamp;
    @Basic
    @Column(name = "end_time_stamp", nullable = true)
    private Long endTimeStamp;
    @Basic
    @Column(name = "duration", nullable = true)
    private Long duration;
    @Basic
    @Column(name = "is_normal", nullable = true)
    private Byte isNormal;
    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "problem_name", nullable = true, length = 255)
    private String problemName;
    @Basic
    @Column(name = "qindex", nullable = true, length = 255)
    private String qindex;
    @Basic
    @Column(name = "difficulty", nullable = true)
    private Integer difficulty;

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

    public Long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(Long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Byte getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Byte isNormal) {
        this.isNormal = isNormal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getQindex() {
        return qindex;
    }

    public void setQindex(String qindex) {
        this.qindex = qindex;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfProblemsWithContestInfo that = (CfProblemsWithContestInfo) o;
        return Objects.equals(cid, that.cid) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(level, that.level) && Objects.equals(startTimeStamp, that.startTimeStamp) && Objects.equals(endTimeStamp, that.endTimeStamp) && Objects.equals(duration, that.duration) && Objects.equals(isNormal, that.isNormal) && Objects.equals(id, that.id) && Objects.equals(problemName, that.problemName) && Objects.equals(qindex, that.qindex) && Objects.equals(difficulty, that.difficulty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid, name, type, level, startTimeStamp, endTimeStamp, duration, isNormal, id, problemName, qindex, difficulty);
    }
}
