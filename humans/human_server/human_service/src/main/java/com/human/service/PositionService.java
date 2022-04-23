package com.human.service;

import com.human.mapper.PositionMapper;
import com.human.pojo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/1 15:42
 */
@Service
public class PositionService {
    @Autowired
    private PositionMapper positionMapper;

    /**
     * 查询所有职位
     * @return
     */
    public List<Position> getPositionList() {
        return positionMapper.getPositionList();
    }

    /**
     * 添加一个职位
     * @param position
     * @return
     */
    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    /**
     * 更新职位
     * @param position
     * @return
     */
    public int updatePosition(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    /**
     * 删除一个职位
     * @param id
     * @return
     */
    public int deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ids数组批量删除职位信息
     * @param ids
     * @return
     */
    public int deletePositionByIds(Integer[] ids) {
        return positionMapper.deletePositionByIds(ids);
    }
}
