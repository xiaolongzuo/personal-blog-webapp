package com.zuoxiaolong.blog.client;

public interface RecordIdVisitorIpDubboService {

    boolean save(final int recordId, final String visitorIp, final String username);

    boolean exists(final int recordId, final String visitorIp, final String username);

}
