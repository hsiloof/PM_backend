package com.jinax.pm_backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : chara
 */
@Entity
@Table(name="reply")
public class Reply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "post_id", nullable = false)
    private Integer postId;
    @Column(name = "block_id", nullable = false)
    private Integer blockId;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "city")
    private String city;
    @Column(name = "district")
    private String district;
    @Column(name = "province")
    private String province;
    @Column(name = "street")
    private String street;
    @Column(name = "is_deleted", nullable = false)
    private short isDeleted;

    public Reply() {
    }

    public Reply(Integer id, Integer postId, Integer blockId, User owner, String content, Date createTime, Double longitude, Double latitude, String city, String district, String province, String street, short isDeleted) {
        this.id = id;
        this.postId = postId;
        this.blockId = blockId;
        this.owner = owner;
        this.content = content;
        this.createTime = createTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blocktId) {
        this.blockId = blocktId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return owner.getUsername();
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", postId=" + postId +
                ", blockId=" + blockId +
                ", ownerName=" + getOwnerName() +
                ", content='" + content + '\'' +
                ", longitude='" + longitude +
                ", latitude='" + latitude +
                ", city='" + city +
                ", district='" + district +
                ", province='" + province +
                ", street='" + street + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
