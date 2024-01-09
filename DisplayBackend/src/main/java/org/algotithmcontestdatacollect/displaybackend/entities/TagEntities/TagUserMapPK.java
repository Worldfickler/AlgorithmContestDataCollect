package org.algotithmcontestdatacollect.displaybackend.entities.TagEntities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TagUserMapPK implements Serializable {
    @Column(name = "uid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(name = "tid", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagUserMapPK that = (TagUserMapPK) o;
        return Objects.equals(uid, that.uid) && Objects.equals(tid, that.tid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, tid);
    }
}
