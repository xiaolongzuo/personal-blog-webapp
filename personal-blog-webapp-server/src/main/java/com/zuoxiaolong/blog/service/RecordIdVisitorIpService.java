package com.zuoxiaolong.blog.service;

import com.zuoxiaolong.blog.dao.RecordIdVisitorIpRepository;
import com.zuoxiaolong.blog.entity.RecordIdVisitorIp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecordIdVisitorIpService {

    @Resource
    private RecordIdVisitorIpRepository recordIdVisitorIpRepository;

    public boolean save(final int recordId, final String visitorIp, final String username) {
        RecordIdVisitorIp recordIdVisitorIp = new RecordIdVisitorIp();
        recordIdVisitorIp.setRecordId(recordId);
        recordIdVisitorIp.setVisitorIp(visitorIp);
        recordIdVisitorIp.setUsername(username);
        recordIdVisitorIpRepository.save(recordIdVisitorIp);
        return true;
    }

    public boolean exists(final int recordId, final String visitorIp, final String username) {
        RecordIdVisitorIp recordIdVisitorIp1 = recordIdVisitorIpRepository.findByRecordIdAndAndVisitorIp(recordId, visitorIp);
        RecordIdVisitorIp recordIdVisitorIp2 = recordIdVisitorIpRepository.findByRecordIdAndUsername(recordId, username);
        return recordIdVisitorIp1 != null || recordIdVisitorIp2 != null;
    }

}
