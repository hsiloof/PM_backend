package com.jinax.pm_backend.Entity;

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
    @Column(name = "content_id", nullable = false)
    private Integer contentId;
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "is_deleted", nullable = false)
    private short isDeleted;
    @Column(name = "view_time", nullable = false)
    private int viewTime;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @JoinTable(name = "post_tag_relation",
            joinColumns = { @JoinColumn(name = "id", referencedColumnName = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "id", referencedColumnName = "tag_id") })
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

    public Post(Integer id, String title, Integer contentId, Integer ownerId, String address, Date createTime, short isDeleted, int viewTime) {
        this.id = id;
        this.title = title;
        this.contentId = contentId;
        this.ownerId = ownerId;
        this.address = address;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
        this.viewTime = viewTime;
    }

    @Override
    public String toString(){
        return "Post{"+
                "id="+id+
                ", title="+title+
                ", contentId="+contentId+
                ", ownerId="+ownerId+
                ", address="+address+
                ", createTime="+createTime.toString()+
                ", isDeleted="+ isDeleted+
                ", viewTime="+viewTime+
                "}";
    }
}
