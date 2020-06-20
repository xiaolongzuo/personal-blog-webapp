package com.zuoxiaolong.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "dictionary_province")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(30) NOT NULL")
    private String name;

    @Column(columnDefinition = "varchar(1) default NULL COMMENT '1 - 直辖市\\r\\n2 - 行政省\\r\\n3 - 自治区\\r\\n4 - 特别行政区\\r\\n5 - 其他国家\\r\\n见全局数据字典[省份类型] \\r\\n'")
    private String type;

    @Column(columnDefinition = "varchar(1) default NULL COMMENT '0 - 禁用\\r\\n1 - 启用'")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
