/*
 * Copyright (C) 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zuoxiaolong.blog.entity;

import javax.persistence.*;

/**
 * @author xiaolongzuo
 */
@Entity
@Table(name = "answer_id_visitor_ip")
@IdClass(AnswerIdVisitorIpId.class)
public class AnswerIdVisitorIp {

    @Id
    private Integer answerId;

    @Id
    @Column(columnDefinition = "char(20) NOT NULL")
    private String visitorIp;

    @Column(columnDefinition = "VARCHAR(40)")
    private String username;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
