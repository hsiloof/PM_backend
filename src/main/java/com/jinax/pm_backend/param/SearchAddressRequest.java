package com.jinax.pm_backend.param;

public class SearchAddressRequest {
    private String city;
    private String district;
    private String province;
    private String street;
    private Integer page;
    private Integer size;

    public SearchAddressRequest() {
    }

    public SearchAddressRequest(String city, String district, String province, String street, Integer page, Integer size) {
        this.city = city;
        this.district = district;
        this.province = province;
        this.street = street;
        this.page = page;
        this.size = size;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
