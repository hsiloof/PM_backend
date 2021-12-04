package com.jinax.pm_backend.Entity;

import javax.persistence.*;

@Entity
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "post_id")
    private Integer postId;
    @Column(name = "block_id")
    private Integer blockId;
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;
    @Column(name = "is_read", nullable = false)
    private Short isRead;

    public Notify() {
    }

    public Notify(Integer id, Integer postId, Integer blockId, Integer ownerId, Short isRead) {
        this.id = id;
        this.postId = postId;
        this.blockId = blockId;
        this.ownerId = ownerId;
        this.isRead = isRead;
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

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Short getIsRead() {
        return isRead;
    }

    public void setIsRead(Short isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "id=" + id +
                ", postId=" + postId +
                ", blockId=" + blockId +
                ", ownerId=" + ownerId +
                ", isRead=" + isRead +
                '}';
    }
}
