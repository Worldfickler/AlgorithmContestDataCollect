package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "codeforces_ok_submit_with_tags", schema = "graduate")
public class CodeforcesOkSubmitWithTags {
    @Id
    @Basic
    @Column(name = "sid", nullable = true)
    private Long sid;
    @Basic
    @Column(name = "uid", nullable = true)
    private Long uid;
    @Basic
    @Column(name = "q_index", nullable = false, length = 255)
    private String qIndex;
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "first_submit_time", nullable = true)
    private Long firstSubmitTime;
    @Basic
    @Column(name = "tags", nullable = true, length = -1)
    private String tags;
    @Basic
    @Column(name = "difficulty", nullable = true)
    private Integer difficulty;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getqIndex() {
        return qIndex;
    }

    public void setqIndex(String qIndex) {
        this.qIndex = qIndex;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getFirstSubmitTime() {
        return firstSubmitTime;
    }

    public void setFirstSubmitTime(Long firstSubmitTime) {
        this.firstSubmitTime = firstSubmitTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
        CodeforcesOkSubmitWithTags that = (CodeforcesOkSubmitWithTags) o;
        return Objects.equals(sid, that.sid) && Objects.equals(uid, that.uid) && Objects.equals(qIndex, that.qIndex) && Objects.equals(cid, that.cid) && Objects.equals(firstSubmitTime, that.firstSubmitTime) && Objects.equals(tags, that.tags) && Objects.equals(difficulty, that.difficulty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, uid, qIndex, cid, firstSubmitTime, tags, difficulty);
    }
}
