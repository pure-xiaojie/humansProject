package com.human.controller.emp;

import com.human.pojo.*;
import com.human.pojo.emp.Employee;
import com.human.service.*;
import com.human.service.emp.EmployeeService;
import com.human.service.emp.NationService;
import com.human.service.emp.PoliticsStatusService;
import com.human.utils.PoiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/4 16:08
 */
@Api(tags = "员工信息")
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private NationService nationService;
    @Autowired
    private PoliticsStatusService politicsStatusService;
    @Autowired
    private JobLevelService jobLevelService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * 分页查询员工信息，根据搜索字段模糊查询
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("分页获取员工信息")
    @GetMapping("/")
    public RespPageBean getEmployeePageList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size, Employee employee, Date[] beginDateScope) {

        return employeeService.getEmployeePageList(page,size,employee,beginDateScope);
    }

    /**
     * 添加职工
     * @param employee
     * @return
     */
    @ApiOperation("创建新员工")
    @PostMapping("/")
    public RespBean addEmployee(@RequestBody Employee employee) {
        if(employeeService.addEmployee(employee) == 1) {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    /**
     * 删除职工
     * @param id
     * @return
     */
    @ApiOperation("删除员工信息")
    @DeleteMapping("/{id}")
    public RespBean delEmployeeById(@PathVariable Integer id) {
        if(employeeService.delEmployeeById(id) == 1) {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 更新职工
     * @param employee
     * @return
     */
    @ApiOperation("修改员工信息")
    @PutMapping("/")
    public RespBean updateEmployee(@RequestBody Employee employee) {
        if(employeeService.updateEmployee(employee) == 1) {
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败!");
    }

    /**
     * 获取民族列表
     * @return
     */
    @ApiOperation("获取民族信息")
    @GetMapping("/nations")
    public List<Nation> getNationList() {
        return nationService.getNationList();
    }

    /**
     * 获取政治面貌列表
     * @return
     */
    @ApiOperation("获取政治面貌信息")
    @GetMapping("/politics")
    public List<Politicsstatus> getPoliticsStatusList() {
        return politicsStatusService.getPoliticsStatusList();
    }

    /**
     * 获取职称列表
     * @return
     */
    @ApiOperation("获取职称信息")
    @GetMapping("/levels")
    public List<JobLevel> getJobLevelList() {
        return jobLevelService.getJobLevelList();
    }

    /**
     * 获取职位信息
     * @return
     */
    @ApiOperation("获取职位信息")
    @GetMapping("/positions")
    public List<Position> getPositionList() {
        return positionService.getPositionList();
    }

    /**
     * 获取最大工号
     * @return
     */
    @ApiOperation("获取最大工号")
    @GetMapping("/wordId")
    public RespBean getMaxWorkId() {
        RespBean respBean = RespBean.build().setStatus(200)
                .setObj(String.format("%08d", employeeService.getMaxWorkId()+1));
        return respBean;
    }

    /**
     * 在职人数
     * @return
     */
    @ApiOperation("获取在职人数")
    @GetMapping("/count")
    public RespBean getEmpCount() {
        return employeeService.getEmpCount();
    }

    /**
     * 获取所有的部门
     * @return
     */
    @ApiOperation("获取部门信息")
    @GetMapping("/deps")
    public List<Department> getDepartmentList() {
        return departmentService.getDepartmentList();
    }

    /**
     * 导出员工数据，对应Excel
     * @return
     */
    @ApiOperation("导出员工数据，对应Excel")
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> data = (List<Employee>)employeeService.getEmployeePageList(null, null, null,null).getData();
        return PoiUtil.employeeToExcel(data);
    }

    /**
     * 导入职工数据
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("导入职工数据，对应Excel")
    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Nation> nationList = nationService.getNationList();
        List<Politicsstatus> pllList = politicsStatusService.getPoliticsStatusList();
        List<JobLevel> jobLevelList = jobLevelService.getJobLevelList();
        List<Position> positionList = positionService.getPositionList();
        List<Department> depList = departmentService.getAllDepartment();

        //解析Excel
        List<Employee> employeeList = PoiUtil.excelToEmployee(file,nationList,pllList,jobLevelList,positionList,depList);

        //插入到数据库
        if(employeeService.insertEmployeeList(employeeList) == employeeList.size()) {
            return RespBean.ok("上传成功！");
        }
        return RespBean.error("上传失败！");
    }
}
