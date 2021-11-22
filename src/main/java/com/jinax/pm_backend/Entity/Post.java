package com.jinax.pm_backend.Entity;

import com.fasterxml.jackson.annotation.*;

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
    @Column(name = "address", nullable = false)
    private String address;
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

    public Post(Integer id, String title, Content content, User owner, String address, Date createTime, short isDeleted, int viewTime, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.address = address;
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
                ", ownerName='" + owner.getUsername() + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                ", viewTime=" + viewTime +
                ", tags=" + tags +
                '}';
    }
}
