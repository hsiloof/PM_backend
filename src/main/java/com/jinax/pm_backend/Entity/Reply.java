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
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "is_deleted", nullable = false)
    private short isDeleted;

    public Reply() {
    }

    public Reply(Integer id, Integer postId, Integer blockId, User owner, String content, Date createTime, String address, short isDeleted) {
        this.id = id;
        this.postId = postId;
        this.blockId = blockId;
        this.owner = owner;
        this.content = content;
        this.createTime = createTime;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", createTime=" + createTime +
                ", address='" + address + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
