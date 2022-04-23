package com.human.controller.emp;

import com.human.pojo.emp.Employeeec;
import com.human.pojo.RespBean;
import com.human.pojo.RespPageBean;
import com.human.service.RewardsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Li JieGui
 * @date 2021/2/26 16:07
 * 员工奖惩设置
 */
@Api(tags = "员工奖惩")
@RestController
@RequestMapping("/personnel/ec")
public class EmployeeRewardsController {
    @Autowired
    private RewardsService rewardsService;

    /**
     * 分页获取员工奖惩信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取员工奖惩信息")
    @GetMapping("/")
    public RespPageBean getRewardsPageList(@RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return rewardsService.getRewardsPageList(page,size);
    }

    /**
     * 添加职工奖惩信息
     * @param employeeec
     * @return
     */
    @ApiOperation("添加职工奖惩信息")
    @PostMapping("/")
    public RespBean addEmployeeec(@RequestBody Employeeec employeeec) {
        if(rewardsService.addEmployeeRewards(employeeec) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职工奖惩
     * @param id
     * @return
     */
    @ApiOperation("删除职工奖惩")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeec(@PathVariable Integer id){
        if(rewardsService.deleteEmployeeec(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新奖惩
     * @param employeeec
     * @return
     */
    @ApiOperation("修改奖惩信息")
    @PutMapping("/")
    public RespBean updateEmployeeec(@RequestBody Employeeec employeeec) {
        if(rewardsService.updateEmployeeec(employeeec) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

}
