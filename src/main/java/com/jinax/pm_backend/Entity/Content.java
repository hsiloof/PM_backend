package com.jinax.pm_backend.Entity;
import javax.persistence.*;
@Entity
@Table(name="content")
public class Content {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content", nullable = false)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Content() {

    }
    public Content(Integer id, String content) {
        this.id = id;
        this.content = content;
    }
    @Override
    public String toString(){
        return "Post{"+
                "id="+id+
                ", content="+content+
                "}";
    }
}
