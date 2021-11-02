package com.jinax.pm_backend.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="block")
public class Block {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "post_id", nullable = false)
    private Integer postId;
    @Column(name = "content", nullable = false)
    private Integer content;
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "create_time", nullable = false)
    private Date createTime;
    @Column(name = "is_deleted", nullable = false)
    private short isDeleted;

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

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
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

    public Block() {

    }

    public Block(Integer id, Integer postId, Integer content, Integer ownerId, String address, Date createTime, short isDeleted) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.ownerId = ownerId;
        this.address = address;
        this.createTime = createTime;
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Floor{"+
                "id="+id+
                ", postId="+postId+
                ", content="+content+
                ", ownerId="+ownerId+
                ", address="+address+
                ", createTime="+createTime.toString()+
                ", isDeleted="+ isDeleted+
                "}";
    }
}
