package com.jinax.pm_backend.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="block")
public class Block {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "post_id", nullable = false)
    private Integer postId;
    @Column(name = "content", nullable = false)
    private String content;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;

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
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "is_deleted", nullable = false)
    private short isDeleted;
    @OneToMany(targetEntity = Reply.class)
    @JoinColumn(name = "block_id",referencedColumnName = "id")
    private Set<Reply> replySet = new HashSet<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Set<Reply> getReplySet() {
        return replySet;
    }

    public void setReplySet(Set<Reply> replySet) {
        this.replySet = replySet;
    }

    public Block() {

    }

    public Block(Integer id, Integer postId, String content, User owner, Double longitude, Double latitude, String city, String district, String province, String street, Date createTime, short isDeleted, Set<Reply> replySet) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.owner = owner;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
        this.replySet = replySet;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                ", ownerName=" + getOwnerName() +
                ", longitude='" + longitude +
                ", latitude='" + latitude +
                ", city='" + city +
                ", district='" + district +
                ", province='" + province +
                ", street='" + street + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                ", replySet=" + replySet +
                '}';
    }
}
