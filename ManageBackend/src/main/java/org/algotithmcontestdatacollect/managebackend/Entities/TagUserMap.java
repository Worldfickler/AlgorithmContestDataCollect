package org.algotithmcontestdatacollect.managebackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tag_user_map", schema = "graduate", catalog = "")
@IdClass(TagUserMapPK.class)
public class TagUserMap {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "uid", nullable = false)
    private Long uid;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "tid", nullable = false)
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
        TagUserMap that = (TagUserMap) o;
        return Objects.equals(uid, that.uid) && Objects.equals(tid, that.tid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, tid);
    }
}
