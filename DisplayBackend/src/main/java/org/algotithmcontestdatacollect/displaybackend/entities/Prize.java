package org.algotithmcontestdatacollect.displaybackend.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "prize")
public class Prize {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "uid", nullable = false)
    private Long uid;
    @NotNull(message = "Year cannot be null")
    @Pattern(regexp = "^(200[1-9]|20[1-9][0-9]+)$", message = "Year should be greater than 2000")
    @Column(name = "year")
    private String year;
    @NotNull(message = "Contest name cannot be null")
    @Column(name = "contest_name")
    private String contestName;
    @NotNull(message = "Level cannot be null")
    @Min(value = 0, message = "Level should be between 0 to 8")
    @Max(value = 8, message = "Level should be between 0 to 8")
    @Column(name = "level")
    private String level;
    @NotNull(message = "Status cannot be null")
    @Min(value = 0, message = "Status should be either 0 or 1")
    @Max(value = 1, message = "Status should be either 0 or 1")
    @Column(name = "status")
    private String status;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestName() {
        return contestName;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Prize{" +
               "id='" + id + '\'' +
               ", uid=" + uid +
               ", year='" + year + '\'' +
               ", contestName='" + contestName + '\'' +
               ", level='" + level + '\'' +
               ", status='" + status + '\'' +
               '}';
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
