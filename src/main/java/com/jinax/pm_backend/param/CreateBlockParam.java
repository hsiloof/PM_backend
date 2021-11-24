package com.jinax.pm_backend.param;

import java.util.ArrayList;

public class CreateBlockParam {
    private Integer post_id;
    private String content;
    private Integer owner_id;
    private Double longitude;
    private Double latitude;
    private String city;
    private String district;
    private String province;
    private String street;

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
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

    public CreateBlockParam(){

    }

    public CreateBlockParam(Integer post_id, String content, Integer owner_id, Double longitude, Double latitude, String city, String district, String province, String street) {
        this.post_id = post_id;
        this.content = content;
        this.owner_id = owner_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
    }
}
