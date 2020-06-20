package com.zuoxiaolong.blog.entity;

import javax.persistence.*;

/**
 * @author xiaolongzuo
 */
@Entity
@Table(name = "dictionary_city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(30) NOT NULL")
    private String name;

    @Column(columnDefinition = "int(10) unsigned NOT NULL")
    private Integer provinceId;

    @Column(columnDefinition = "varchar(1) default '1' COMMENT '0 - 禁用\\r\\n1 - 启用 \\r\\n'")
    private String state;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
