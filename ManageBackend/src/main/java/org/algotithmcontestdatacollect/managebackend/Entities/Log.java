package org.algotithmcontestdatacollect.managebackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "log", schema = "graduate", catalog = "")
public class Log {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Basic
    @Column(name = "time", nullable = false)
    private long time;
    @Basic
    @Column(name = "detail", nullable = false, length = -1)
    private String detail;
    @Basic
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }


    public void setUid(Long uid) {
        this.uid = uid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return time == log.time && Objects.equals(id, log.id) && Objects.equals(uid, log.uid) && Objects.equals(detail, log.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, time, detail);
    }

    public String getUsername() {
        return username;
    }
    public static Log createOne(Long uid, long time, String detail, String username) {
        Log log = new Log();
        log.setUid(uid);
        log.setTime(time);
        log.setDetail(detail);
        log.setUsername(username);
        return log;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
