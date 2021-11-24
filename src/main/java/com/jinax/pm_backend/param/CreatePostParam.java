package com.jinax.pm_backend.param;

import com.jinax.pm_backend.Entity.Post;

import java.util.ArrayList;

public class CreatePostParam {
    private String title;
    private String content;
    private Integer owner_id;
    private Double longitude;
    private Double latitude;
    private String city;
    private String district;
    private String province;
    private String street;
    private ArrayList<String> tagsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<String> getTagsList() {
        return tagsList;
    }

    public void setTagsList(ArrayList<String> tagsList) {
        this.tagsList = tagsList;
    }

    public CreatePostParam() {

    }

    public CreatePostParam(String title, String content, Integer owner_id, Double longitude, Double latitude, String city, String district, String province, String street, ArrayList<String> tagsList) {
        this.title = title;
        this.content = content;
        this.owner_id = owner_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
        this.tagsList = tagsList;
    }
}
