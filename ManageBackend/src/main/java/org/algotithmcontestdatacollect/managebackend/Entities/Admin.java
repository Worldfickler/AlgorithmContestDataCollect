package org.algotithmcontestdatacollect.managebackend.Entities;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Entity
@Proxy(lazy = false)
@Table(name = "admin", schema = "graduate")
public class Admin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "school")
    private Long school;
    @Basic
    @Column(name = "is_super", nullable = false)
    private Short isSuper;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Short getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Short isSuper) {
        this.isSuper = isSuper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin that = (Admin) o;
        return isSuper == that.isSuper && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(school, that.school);
    }
    public static Admin fromHTTPRequest(HttpServletRequest request) {
        Admin admin =  new Admin();
        admin.setId(Long.parseLong((String) request.getAttribute("id")));
        admin.setUsername((String) request.getAttribute("username"));
        if (request.getAttribute("school") != null) {
            admin.setSchool(Long.parseLong((String) request.getAttribute("school")));
        }
        admin.setIsSuper(Short.parseShort((String) request.getAttribute("isSuper")));
        return admin;
    }
    public Map<String,String> toStringMap() {
        Map<String,String> mp = new HashMap<>();
        mp.put("username",getUsername());
        if (getSchool() != null) {
            mp.put("school",getSchool().toString());
        }
        mp.put("id",getId().toString());
        mp.put("isSuper",getIsSuper().toString());
        return mp;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, school, isSuper);
    }

    @Override
    public String toString() {
        return "AdminEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", school=" + school +
                ", isSuper=" + isSuper +
                '}';
    }
}
