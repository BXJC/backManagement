package org.csu.mypetstore.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
public class Account {
    private int suppid;
    @NotNull(message = "用户名不能为空")
    private String username;
    private String password;
    @NotNull(message = "状态不能为空")
    private String status;
    @NotNull(message = "请填写地址")
    private String address1;
    private String address2;
    @NotNull(message = "请填写城市信息")
    private String city;
    @NotNull(message = "请填写州信息")
    private String state;
    @NotNull(message = "请填写zip信息")
    private String zip;
    @NotNull(message = "电话号码不能为空")
    private String phone;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getSuppid() {
        return suppid;
    }

    public void setSuppid(int suppid) {
        this.suppid = suppid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
