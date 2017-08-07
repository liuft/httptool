package com.jx.commontool.db;

import com.bj58.sfft.utility.dao.annotation.Column;
import com.bj58.sfft.utility.dao.annotation.Table;
import com.jx.spat.gaea.serializer.component.annotation.GaeaSerializable;

/**
 * Created by xiaowei on 17/7/18.
 */

@GaeaSerializable
@Table(name = "t_data")
public class EnterEntites {
    @Column(name = "record_id")
    private long record_id;
    @Column(name = "enter_name")
    private String enter_name;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "email")
    private String email;
    @Column(name = "url")
    private String url;
    @Column(name = "faren")
    private String faren;
    @Column(name = "zhuce_ziben")
    private String zhuce_ziben;
    @Column(name = "reg_date")
    private String reg_date;
    @Column(name = "enter_state")
    private String enter_state;
    @Column(name = "union_code")
    private String union_code;
    @Column(name = "reg_code")
    private String reg_code;
    @Column(name = "org_code")
    private String org_code;
    @Column(name = "enter_type")
    private String enter_type;
    @Column(name = "tax_code")
    private String tax_code;
    @Column(name = "cate")
    private String cate;
    @Column(name = "start_open")
    private String start_open;
    @Column(name = "end_open")
    private String end_open;
    @Column(name = "check_date")
    private String check_date;
    @Column(name = "reg_org")
    private String reg_org;
    @Column(name = "reg_address")
    private String reg_address;
    @Column(name = "jinyingfawei")
    private String jinyingfawei;

    public long getRecord_id() {
        return record_id;
    }

    public void setRecord_id(long record_id) {
        this.record_id = record_id;
    }

    public String getEnter_name() {
        return enter_name;
    }

    public void setEnter_name(String enter_name) {
        this.enter_name = enter_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFaren() {
        return faren;
    }

    public void setFaren(String faren) {
        this.faren = faren;
    }

    public String getZhuce_ziben() {
        return zhuce_ziben;
    }

    public void setZhuce_ziben(String zhuce_ziben) {
        this.zhuce_ziben = zhuce_ziben;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getEnter_state() {
        return enter_state;
    }

    public void setEnter_state(String enter_state) {
        this.enter_state = enter_state;
    }

    public String getUnion_code() {
        return union_code;
    }

    public void setUnion_code(String union_code) {
        this.union_code = union_code;
    }

    public String getReg_code() {
        return reg_code;
    }

    public void setReg_code(String reg_code) {
        this.reg_code = reg_code;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getEnter_type() {
        return enter_type;
    }

    public void setEnter_type(String enter_type) {
        this.enter_type = enter_type;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getStart_open() {
        return start_open;
    }

    public void setStart_open(String start_open) {
        this.start_open = start_open;
    }

    public String getEnd_open() {
        return end_open;
    }

    public void setEnd_open(String end_open) {
        this.end_open = end_open;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public String getReg_org() {
        return reg_org;
    }

    public void setReg_org(String reg_org) {
        this.reg_org = reg_org;
    }

    public String getReg_address() {
        return reg_address;
    }

    public void setReg_address(String reg_address) {
        this.reg_address = reg_address;
    }

    public String getJinyingfawei() {
        return jinyingfawei;
    }

    public void setJinyingfawei(String jinyingfawei) {
        this.jinyingfawei = jinyingfawei;
    }
}
