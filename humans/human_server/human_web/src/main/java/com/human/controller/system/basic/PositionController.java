package com.human.controller.system.basic;

import com.human.pojo.Position;
import com.human.pojo.RespBean;
import com.human.service.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/1 15:38
 */
@Api(tags = "职位信息")
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    private PositionService positionService;

    /**
     * 获取职位列表
     * @return
     */
    @ApiOperation("获取职位列表")
    @GetMapping("/")
    public List<Position> getPositionList() {
        return positionService.getPositionList();
    }

    /**
     * 添加一个职位
     * @param position
     * @return
     */
    @ApiOperation("添加一个职位")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        if (positionService.addPosition(position) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 更新职位
     * @param position
     * @return
     */
    @ApiOperation("更新职位")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        if (positionService.updatePosition(position) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    /**
     * 根据id删除职位
     * @param id
     * @return
     */
    @ApiOperation("根据id删除职位")
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id) {
        if (positionService.deletePositionById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 批量删除职位
     * @param ids
     * @return
     */
    @ApiOperation("批量删除职位")
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids) {
        if (positionService.deletePositionByIds(ids) == ids.length) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
