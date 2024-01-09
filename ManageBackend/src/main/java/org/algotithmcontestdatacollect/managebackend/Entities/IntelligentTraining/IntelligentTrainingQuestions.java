package org.algotithmcontestdatacollect.managebackend.Entities.IntelligentTraining;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "intelligent_training_questions", schema = "graduate", catalog = "")
@IdClass(IntelligentTrainingQuestionsPK.class)
public class IntelligentTrainingQuestions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid", nullable = false)
    private Long uid;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tid", nullable = false)
    private int tid;
    @Basic
    @Column(name = "problems", nullable = true, length = 255)
    private String problems;

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
}
