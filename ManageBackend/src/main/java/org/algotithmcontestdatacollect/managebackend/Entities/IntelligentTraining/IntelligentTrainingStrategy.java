package org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "intelligent_training_strategy", schema = "graduate", catalog = "")
public class IntelligentTrainingStrategy {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "use_function", nullable = true)
    private String useFunction;
    @Basic
    @Column(name = "strategy_name", nullable = true, length = 255)
    private String strategyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseFunction() {
        return useFunction;
    }


    public void setUseFunction(String useFunction) {
        this.useFunction = useFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntelligentTrainingStrategy that = (IntelligentTrainingStrategy) o;
        return id == that.id && Objects.equals(useFunction, that.useFunction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, useFunction);
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }
}
