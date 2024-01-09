package org.algotithmcontestdatacollect.displaybackend.entities;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PublishVersion {
    private List<Version> content;
    private String vid;
    private Timestamp pTime;
    private Long pAdmin;

    @Override
    public String toString() {
        return "PublishVersion{" +
                "content=" + content +
                ", vid=" + vid +
                ", pTime=" + pTime +
                ", pAdmin='" + pAdmin + '\'' +
                '}';
    }

    public List<Version> getContent() {
        return content;
    }

    public void setContent(List<Version> content) {
        this.content = content;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
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

    public PublishVersion(String vid, Timestamp pTime, Long pAdmin) {
        this.setContent(new ArrayList<>());
        this.vid = vid;
        this.pTime = pTime;
        this.pAdmin = pAdmin;
    }
}
