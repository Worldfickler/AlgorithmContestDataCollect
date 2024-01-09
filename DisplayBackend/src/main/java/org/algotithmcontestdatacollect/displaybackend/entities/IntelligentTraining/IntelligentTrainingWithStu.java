package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining.IntelligentTrainingQuestionsPK;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "intelligent_training_with_stu")
@IdClass(IntelligentTrainingQuestionsPK.class)
public class IntelligentTrainingWithStu {
    @Id
    @Column(name = "tid", nullable = false)
    private Integer tid;
    @Id
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "training_name")
    private String trainingName;

    @Column(name = "start_timestamp")
    private Date startTimestamp;

    @Column(name = "end_timestamp")
    private Date endTimestamp;

    @Column(name = "classname")
    private String classname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "problems")
    private String problems;

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getTid() {
        return tid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassname() {
        return classname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getProblems() {
        return problems;
    }

    @Override
    public String toString() {
        return "IntelligentTrainingWithStuEntity{" +
               "tid=" + tid + '\'' +
               "uid=" + uid + '\'' +
               "trainingName=" + trainingName + '\'' +
               "startTimestamp=" + startTimestamp + '\'' +
               "endTimestamp=" + endTimestamp + '\'' +
               "classname=" + classname + '\'' +
               "realname=" + realname + '\'' +
               "problems=" + problems + '\'' +
               '}';
    }
}
