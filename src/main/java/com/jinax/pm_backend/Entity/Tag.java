package com.jinax.pm_backend.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "tags")
    private Set<Post> posts=new HashSet<>();

    public Tag() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    public void addPost(Post post){
        this.posts.add(post);
    }
    public void removePost(Post post){
        this.posts.remove(post);
    }
    @Override
    public String toString(){
        return "tag{"+
                "id="+id+
                ", name="+name+
                "}";
    }
}
