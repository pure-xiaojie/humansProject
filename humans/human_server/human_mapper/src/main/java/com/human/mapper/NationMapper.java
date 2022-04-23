package com.human.mapper;

import com.human.pojo.Nation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/5 14:59
 * 民族持久层
 */
@Repository
public interface NationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Nation record);

    int insertSelective(Nation record);

    Nation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Nation record);

    int updateByPrimaryKey(Nation record);

    /**
     * 获取所有的民族
     * @return
     */
    List<Nation> getNationList();
}