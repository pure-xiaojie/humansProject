package com.human.service.emp;

import com.human.mapper.NationMapper;
import com.human.pojo.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/5 14:55
 * 民族服务层
 */
@Service
public class NationService {
    @Autowired
    private NationMapper nationMapper;

    /**
     * 获取所有的民族
     * @return
     */
    public List<Nation> getNationList() {
        return nationMapper.getNationList();
    }
}
