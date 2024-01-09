package org.algotithmcontestdatacollect.displaybackend.entities.IntelligentTraining;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cf_problem_no_tag")
public class CfProblemNoTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cid_qindex")
    private String cidQindex;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCidQindex(String cidQindex) {
        this.cidQindex = cidQindex;
    }

    public String getCidQindex() {
        return cidQindex;
    }

    @Override
    public String toString() {
        return "CfProblemNoTag{" +
               "id=" + id + '\'' +
               "cidQindex=" + cidQindex + '\'' +
               '}';
    }
}
