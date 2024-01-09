package org.algotithmcontestdatacollect.displaybackend.entities.CodeforcesEntities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "codeforces_problems_with_tags", schema = "graduate")
public class CodeforcesProblemsWithTags {
    @Id
    @Basic
    @Column(name = "id", nullable = true)
    private Integer id;
    @Basic
    @Column(name = "cid", nullable = true)
    private Long cid;
    @Basic
    @Column(name = "qindex", nullable = true, length = 255)
    private String qindex;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "difficulty", nullable = true)
    private Integer difficulty;
    @Basic
    @Column(name = "tags", nullable = true, length = -1)
    private String tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeforcesProblemsWithTags)) return false;
        CodeforcesProblemsWithTags that = (CodeforcesProblemsWithTags) o;
        return Objects.equals(id, that.id) && Objects.equals(cid, that.cid) && Objects.equals(qindex, that.qindex) && Objects.equals(name, that.name) && Objects.equals(difficulty, that.difficulty) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cid, qindex, name, difficulty, tags);
    }
}
