package com.jx.commontool.db;

import com.bj58.sfft.utility.dao.annotation.Column;
import com.bj58.sfft.utility.dao.annotation.Table;

import java.util.Date;

/**
 * Created by liufeitian on 17/7/24.
 */
@Table(name = "t_leads")
public class LeadsEntity {
    @Column(name = "id")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "userphone")
    private String userphone;
    @Column(name = "library_type")
    private int library_type;
    @Column(name = "source")
    private long source;//
    @Column(name = "old_level")
    private int old_level;//3
    @Column(name = "status")
    private int status;//0
    @Column(name = "intention_code")
    private int intention_code;//0
    @Column(name = "location_city_id")
    private int location_city_id;//3
    @Column(name = "intentbusiness")
    private String intentbusiness;
    @Column(name = "comment")
    private String comment;
    @Column(name = "option_status")
    private int option_status;
    @Column(name = "city_id")
    private int city_id;
    @Column(name = "area_id")
    private int area_id;
    @Column(name = "creator")
    private int creator;//999999999
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "level")
    private int level;
    @Column(name = "sale_attribute")
    private int sale_attribute;

    public int getSale_attribute() {
        return sale_attribute;
    }

    public void setSale_attribute(int sale_attribute) {
        this.sale_attribute = sale_attribute;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }


    public int getOption_status() {
        return option_status;
    }

    public void setOption_status(int option_status) {
        this.option_status = option_status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public int getLibrary_type() {
        return library_type;
    }

    public void setLibrary_type(int library_type) {
        this.library_type = library_type;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public int getOld_level() {
        return old_level;
    }

    public void setOld_level(int old_level) {
        this.old_level = old_level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIntention_code() {
        return intention_code;
    }

    public void setIntention_code(int intention_code) {
        this.intention_code = intention_code;
    }

    public int getLocation_city_id() {
        return location_city_id;
    }

    public void setLocation_city_id(int location_city_id) {
        this.location_city_id = location_city_id;
    }

    public String getIntentbusiness() {
        return intentbusiness;
    }

    public void setIntentbusiness(String intentbusiness) {
        this.intentbusiness = intentbusiness;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
