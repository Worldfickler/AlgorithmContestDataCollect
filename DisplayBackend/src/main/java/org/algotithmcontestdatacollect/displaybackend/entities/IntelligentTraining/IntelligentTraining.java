package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "intelligent_training", schema = "graduate", catalog = "")
public class IntelligentTraining {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "strategy_id", nullable = true)
    private Integer strategyId;
    @Basic
    @Column(name = "start_timestamp", nullable = true)
    private Timestamp startTimestamp;
    @Basic
    @Column(name = "end_timestamp", nullable = true)
    private Timestamp endTimestamp;
    @Column(name = "p_num")
    private Integer pNum;
    @Column(name = "s_num")
    private Integer sNum;
    @Column(name = "s_rating")
    private Long sRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntelligentTraining that = (IntelligentTraining) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(strategyId, that.strategyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, strategyId);
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Timestamp startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Timestamp endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString() {
        return "IntelligentTraining{" +
               "id=" + id + '\'' +
               "name=" + name + '\'' +
               "strategyId=" + strategyId + '\'' +
               "startTimestamp=" + startTimestamp + '\'' +
               "endTimestamp=" + endTimestamp + '\'' +
               '}';
    }

    public void setPNum(Integer pNum) {
        this.pNum = pNum;
    }

    public Integer getPNum() {
        return pNum;
    }

    public void setSNum(Integer sNum) {
        this.sNum = sNum;
    }

    public Integer getSNum() {
        return sNum;
    }

    public void setSRating(Long sRating) {
        this.sRating = sRating;
    }

    public Long getSRating() {
        return sRating;
    }
}
