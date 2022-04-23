package com.human.mapper;

import com.human.pojo.Politicsstatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/5 15:02
 * 政治面貌持久层
 */
@Repository
public interface PoliticsstatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Politicsstatus record);

    int insertSelective(Politicsstatus record);

    Politicsstatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Politicsstatus record);

    int updateByPrimaryKey(Politicsstatus record);

    /**
     * 获取政治面貌列表
     * @return
     */
    List<Politicsstatus> getPoliticsStatusList();
}