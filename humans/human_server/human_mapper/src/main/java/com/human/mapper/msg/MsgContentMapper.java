package com.human.mapper.msg;

import com.human.pojo.MsgContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/3 21:25
 */
@Repository
public interface MsgContentMapper {
    /**
     * 获取公告列表总数量
     * @return
     */
    Long getMsgContentTotal();

    /**
     * 分页获取公告
     * @param page
     * @param size
     * @return
     */
    List<MsgContent> getMsgPageList(Integer page, Integer size);

    /**
     * 插入一条公告
     * @param record
     * @return
     */
    int insertSelective(MsgContent record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    MsgContent selectByPrimaryKey(Integer id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MsgContent record);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteMsgContentByIds(@Param("ids") Integer[] ids);
}
