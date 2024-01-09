package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "intelligent_training_question_click_time")
public class IntelligentTrainingQuestionClickTime {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "cid")
    private Long cid;

    @Column(name = "qindex")
    private String qindex;

    @Column(name = "click_time")
    private Date clickTime;

    @Column(name = "generate_name")
    private String generateName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCid() {
        return cid;
    }

    public void setQindex(String qindex) {
        this.qindex = qindex;
    }

    public String getQindex() {
        return qindex;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }

    public String getGenerateName() {
        return generateName;
    }

    public IntelligentTrainingQuestionClickTime(Long uid, Long cid, String qindex, Date clickTime, String generateName) {
        this.uid = uid;
        this.cid = cid;
        this.qindex = qindex;
        this.clickTime = clickTime;
        this.generateName = generateName;
    }

    public IntelligentTrainingQuestionClickTime() {
    }

    @Override
    public String toString() {
        return "IntelligentTrainingQuestionClickTime{" +
               "id=" + id + '\'' +
               "uid=" + uid + '\'' +
               "cid=" + cid + '\'' +
               "qindex=" + qindex + '\'' +
               "clickTime=" + clickTime + '\'' +
               "generateName=" + generateName + '\'' +
               '}';
    }
}
