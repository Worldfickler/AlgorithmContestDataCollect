package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class IntelligentTrainingQuestionsPK implements Serializable {
    @Column(name = "uid", nullable = false)
    @Id
    private Long uid;
    @Column(name = "tid", nullable = false)
    @Id
    private int tid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntelligentTrainingQuestionsPK that = (IntelligentTrainingQuestionsPK) o;
        return tid == that.tid && Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, tid);
    }
}
