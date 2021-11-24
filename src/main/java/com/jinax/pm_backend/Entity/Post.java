package com.jinax.pm_backend.Entity;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
//    @Column(name = "content_id", nullable = false)
//    private Integer contentId;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id",referencedColumnName = "id")
    private Content content;
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
    @Column(name = "view_time", nullable = false)
    private int viewTime;
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "post_tag_relation",
            joinColumns = {
                @JoinColumn(name = "post_id",referencedColumnName = "id"),},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",referencedColumnName = "id")})
    private Set<Tag> tags = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
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

    public int getViewTime() {
        return viewTime;
    }

    public void setViewTime(int viewTime) {
        this.viewTime = viewTime;
    }

//    @JoinTable(name = "post_tag_relation",
//            joinColumns = { @JoinColumn(name = "id", referencedColumnName = "post_id") },
//            inverseJoinColumns = { @JoinColumn(name = "id", referencedColumnName = "tag_id") })
    public Set<Tag> getTags(){
        return tags;
    }
    public void setTags(Set<Tag> tags){
        this.tags = tags;
    }

    public void addTag(Tag tag){
        if (this.tags.contains(tag)){
            this.tags.add(tag);
            tag.addPost(this);
        }
    }
    public void removeTag(Tag tag){
        if (this.tags.contains(tag)){
            this.tags.remove(tag);
            tag.removePost(this);
        }
    }

    public Post() {
    }

    public Post(Integer id, String title, Content content, User owner, Double longitude, Double latitude, String city, String district, String province, String street, Date createTime, short isDeleted, int viewTime, Set<Tag> tags) {
        this.id = id;
        this.title = title;
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
        this.viewTime = viewTime;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content=" + content +
//                ", ownerName='" + owner.getUsername() + '\'' +
                ", longitude='" + longitude +
                ", latitude='" + latitude +
                ", city='" + city +
                ", district='" + district +
                ", province='" + province +
                ", street='" + street + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                ", viewTime=" + viewTime +
                ", tags=" + tags +
                '}';
    }
}
