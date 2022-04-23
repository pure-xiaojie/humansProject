package com.human.service.msg;

import com.human.mapper.msg.MsgContentMapper;
import com.human.pojo.MsgContent;
import com.human.pojo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2021/4/3 21:14
 */
@Service
public class MsgContentService {
    @Autowired
    private MsgContentMapper msgContentMapper;

    /**
     * 获取公告列表
     * @param page
     * @param size
     * @return
     */
    public RespPageBean getMsgContentList(Integer page, Integer size) {
        if(page != null && size != null) {
            page = (page - 1) * size;
        }
        Long total = msgContentMapper.getMsgContentTotal();
        List<MsgContent> list = msgContentMapper.getMsgPageList(page, size);

        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setTotal(total);
        respPageBean.setData(list);
        return respPageBean;
    }

    /**
     * 新增公告
     * @param msgContent
     * @return
     */
    public int insertMsgContent(MsgContent msgContent) {
        return msgContentMapper.insertSelective(msgContent);
    }

    /**
     * 更新公告
     * @param msgContent
     * @return
     */
    public int updateMsgContent(MsgContent msgContent) {
        return msgContentMapper.updateByPrimaryKeySelective(msgContent);
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    public int deleteMsgContentById(Integer id) {
        return msgContentMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int deleteMsgContentByIds(Integer[] ids) {
        return msgContentMapper.deleteMsgContentByIds(ids);
    }
}
