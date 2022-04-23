package com.human.controller.emp;

import com.human.pojo.emp.Employeetrain;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.TrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li JieGui
 * @date 2021/3/7 10:35
 */
@Api(tags = "员工培训")
@RestController
@RequestMapping("/personnel/train")
public class EmployeeTrainController {
    @Autowired
    private TrainService trainService;
    
    /**
     * 分页获取员工培训信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取员工培训信息")
    @GetMapping("/")
    public RespPageBean getTrainPageList(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return trainService.getTrainPageList(page,size);
    }

    /**
     * 添加职工培训信息
     * @param employeetrain
     * @return
     */
    @ApiOperation("添加职工培训信息")
    @PostMapping("/")
    public RespBean addTrain(@RequestBody Employeetrain employeetrain) {
        if(trainService.addTrain(employeetrain) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职工培训
     * @param id
     * @return
     */
    @ApiOperation("删除职工培训")
    @DeleteMapping("/{id}")
    public RespBean deleteTrain(@PathVariable Integer id){
        if(trainService.deleteTrain(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新培训
     * @param employeetrain
     * @return
     */
    @ApiOperation("修改培训信息")
    @PutMapping("/")
    public RespBean updateTrain(@RequestBody Employeetrain employeetrain) {
        if(trainService.updateTrain(employeetrain) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
