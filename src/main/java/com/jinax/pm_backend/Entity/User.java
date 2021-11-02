package com.jinax.pm_backend.Entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "pw", nullable = false)
    private String pw;
    @Column(name = "role", nullable = false)
    private Short role;
    @Column(name = "mail", nullable = false)
    private String mail;
    @Column(name = "signature")
    private String signature;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User() {
    }

    public User(Integer id, String username, String pw, Short role, String mail, String signature) {
        this.id = id;
        this.username = username;
        this.pw = pw;
        this.role = role;
        this.mail = mail;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pw='" + pw + '\'' +
                ", role=" + role +
                ", mail='" + mail + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }


}
