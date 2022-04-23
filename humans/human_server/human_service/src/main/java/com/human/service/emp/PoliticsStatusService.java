package com.human.service.emp;

import com.human.mapper.PoliticsstatusMapper;
import com.human.pojo.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/5 14:55
 * 政治面貌服务层
 */
@Service
public class PoliticsStatusService {
    @Autowired
    private PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getPoliticsStatusList() {
        return politicsstatusMapper.getPoliticsStatusList();
    }
}
