package org.algotithmcontestdatacollect.managebackend.Entities.AtcoderEntities;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "ac_account", schema = "graduate")
public class AcAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "atcoder_id")
    private String atcoderId;
    @Basic
    @Column(name = "is_main")
    private byte isMain;
    @Basic
    @Column(name = "is_using")
    private byte isUsing;
    @Basic
    @Column(name = "rating")
    private long rating;

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

    public String getAtcoderId() {
        return atcoderId;
    }

    public void setAtcoderId(String atcoderId) {
        this.atcoderId = atcoderId;
    }

    public byte getIsMain() {
        return isMain;
    }

    public void setIsMain(byte isMain) {
        this.isMain = isMain;
    }

    public byte getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(byte isUsing) {
        this.isUsing = isUsing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AcAccount that = (AcAccount) o;

        if (rating != that.rating) return false;
        if (isMain != that.isMain) return false;
        if (isUsing != that.isUsing) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (atcoderId != null ? !atcoderId.equals(that.atcoderId) : that.atcoderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (atcoderId != null ? atcoderId.hashCode() : 0);
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        result = 31 * result + (int) isMain;
        result = 31 * result + (int) isUsing;
        return result;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public static AcAccount fromParam(JSONObject params) {
        AcAccount acAccount = new AcAccount();
        acAccount.setUid(params.getLong("uid"));
        acAccount.setAtcoderId(params.getString("atcoderAccount"));
        acAccount.setIsMain(params.getByte("is_main"));
        acAccount.setIsUsing(params.getByte("is_using"));
        return acAccount;
    }
}
