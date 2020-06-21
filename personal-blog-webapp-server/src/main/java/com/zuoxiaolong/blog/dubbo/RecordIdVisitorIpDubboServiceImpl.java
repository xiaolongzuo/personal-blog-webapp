package com.zuoxiaolong.blog.dubbo;

import com.zuoxiaolong.blog.client.RecordIdVisitorIpDubboService;
import com.zuoxiaolong.blog.service.RecordIdVisitorIpService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

@Service
public class RecordIdVisitorIpDubboServiceImpl implements RecordIdVisitorIpDubboService {

    @Resource
    private RecordIdVisitorIpService recordIdVisitorIpService;

    @Override
    public boolean save(int recordId, String visitorIp, String username) {
        return recordIdVisitorIpService.save(recordId, visitorIp, username);
    }

    @Override
    public boolean exists(int recordId, String visitorIp, String username) {
        return recordIdVisitorIpService.exists(recordId, visitorIp, username);
    }
}
