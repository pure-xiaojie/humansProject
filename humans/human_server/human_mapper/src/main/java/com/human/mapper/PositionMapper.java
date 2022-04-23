package com.human.mapper;

import com.human.pojo.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/1 15:44
 */
@Repository
public interface PositionMapper {
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加一个职位
     * @param record
     * @return
     */
    int insert(Position record);

    /**
     * 增加一个职位,根据字段不为空条件选择
     * @param record
     * @return
     */
    int insertSelective(Position record);

    /**
     * 根据id查询职位
     * @param id
     * @return
     */
    Position selectByPrimaryKey(Integer id);

    /**
     * 更新职位内容，判断空情况
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Position record);

    /**
     * 更新职位，不判断空情况
     * @param record
     * @return
     */
    int updateByPrimaryKey(Position record);

    /**
     * 查询职位列表
     * @return
     */
    List<Position> getPositionList();

    /**
     * 批量删除职位
     * @param ids
     * @return
     */
    int deletePositionByIds(@Param("ids") Integer[] ids);
}