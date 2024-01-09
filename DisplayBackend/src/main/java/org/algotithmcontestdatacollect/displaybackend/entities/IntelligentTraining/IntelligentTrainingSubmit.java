package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "intelligent_training_submit")
public class IntelligentTrainingSubmit {
    @Id
    @Column(name = "sid", nullable = false)
    private String sid;

    @Column(name = "problem_id")
    private String problemId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "LANGUAGE", nullable = false)
    private String language;

    @Column(name = "submit_time", nullable = false)
    private Long submitTime;

    @Column(name = "uid", nullable = false)
    private Long uid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "classname")
    private String classname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "difficulty")
    private Integer difficulty;

    @Column(name = "tag_names")
    private String tagNames;

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getTagNames() {
        return tagNames;
    }

    @Override
    public String toString() {
        return "IntelligentTrainingSubmitEntity{" +
               "sid=" + sid + '\'' +
               "problemId=" + problemId + '\'' +
               "status=" + status + '\'' +
               "language=" + language + '\'' +
               "submitTime=" + submitTime + '\'' +
               "uid=" + uid + '\'' +
               "username=" + username + '\'' +
               "classname=" + classname + '\'' +
               "realname=" + realname + '\'' +
               "difficulty=" + difficulty + '\'' +
               "tagNames=" + tagNames + '\'' +
               '}';
    }
}
