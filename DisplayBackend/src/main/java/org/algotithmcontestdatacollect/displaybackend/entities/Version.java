package org.algotithmcontestdatacollect.displaybackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "version", schema = "acdc", catalog = "")
public class Version {
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @JsonIgnore
    @Basic
    @Column(name = "vid")
    private String vid;
    @Basic
    @Column(name = "contant")
    private String contant;
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "type")
    private int type;
    @JsonIgnore
    @Basic
    @Column(name = "c_time")
    private Timestamp cTime;
    @JsonIgnore
    @Basic
    @Column(name = "u_time")
    private Timestamp uTime;
    @JsonIgnore
    @Basic
    @Column(name = "p_time")
    private Timestamp pTime;
    @JsonIgnore
    @Basic
    @Column(name = "p_admin")
    private Long pAdmin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getcTime() {
        return cTime;
    }

    public void setcTime(Timestamp cTime) {
        this.cTime = cTime;
    }

    public Timestamp getuTime() {
        return uTime;
    }

    public void setuTime(Timestamp uTime) {
        this.uTime = uTime;
    }

    public Timestamp getpTime() {
        return pTime;
    }

    public void setpTime(Timestamp pTime) {
        this.pTime = pTime;
    }

    public Long getpAdmin() {
        return pAdmin;
    }

    public void setpAdmin(Long pAdmin) {
        this.pAdmin = pAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version that = (Version) o;
        return id == that.id && status == that.status && type == that.type && Objects.equals(vid, that.vid) && Objects.equals(contant, that.contant) && Objects.equals(cTime, that.cTime) && Objects.equals(uTime, that.uTime) && Objects.equals(pTime, that.pTime) && Objects.equals(pAdmin, that.pAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vid, contant, status, type, cTime, uTime, pTime, pAdmin);
    }
}
