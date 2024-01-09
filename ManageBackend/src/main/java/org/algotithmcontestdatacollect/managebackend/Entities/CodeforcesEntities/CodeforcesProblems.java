package org.algotithmcontestdatacollect.managebackend.Entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "codeforces_problems")
public class CodeforcesProblems {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "cid")
    private String cid;

    @Column(name = "qindex")
    private String qindex;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Column(name = "name")
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setQindex(String qindex) {
        this.qindex = qindex;
    }

    public String getQindex() {
        return qindex;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CodeforcesProblems{" +
               "id=" + id + '\'' +
               "cid=" + cid + '\'' +
               "qindex=" + qindex + '\'' +
               "difficulty=" + difficulty + '\'' +
               "name=" + name + '\'' +
               '}';
    }
}
