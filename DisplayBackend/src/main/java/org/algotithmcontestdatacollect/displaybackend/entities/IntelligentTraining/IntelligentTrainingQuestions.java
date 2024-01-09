package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "intelligent_training_questions", schema = "graduate")
@IdClass(IntelligentTrainingQuestionsPK.class)
public class IntelligentTrainingQuestions {
    @Id
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Id
    @Column(name = "tid", nullable = false)
    private int tid;
    @Basic
    @Column(name = "problems", nullable = true, length = 255)
    private String problems;
    @Column(name = "generate_ids")
    private String generateIds;

    public Long getUid() {
        return uid;
    }


    public void setUid(Long uid) {
        this.uid = uid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntelligentTrainingQuestions that = (IntelligentTrainingQuestions) o;
        return tid == that.tid && Objects.equals(uid, that.uid) && Objects.equals(problems, that.problems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, tid, problems);
    }

    public void setGenerateIds(String generateIds) {
        this.generateIds = generateIds;
    }

    public String getGenerateIds() {
        return generateIds;
    }

    @Override
    public String toString() {
        return "IntelligentTrainingQuestions{" +
               "uid=" + uid + '\'' +
               "tid=" + tid + '\'' +
               "problems=" + problems + '\'' +
               "generateIds=" + generateIds + '\'' +
               '}';
    }
}
