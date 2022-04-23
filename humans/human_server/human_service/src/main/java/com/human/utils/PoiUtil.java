package com.human.utils;

import com.human.pojo.*;
import com.human.pojo.emp.Employee;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li JieGui
 * @date 2020/12/7 15:06
 */
public class PoiUtil {
    /**
     * 将职工数据导出Excel表
     * @param data
     * @return
     */
    public static ResponseEntity<byte[]> employeeToExcel(List<Employee> data) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取配置文档信息，设置类别、管理员、公司信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        docInfo.setCategory("员工信息");
        docInfo.setManager("李白");
        docInfo.setCompany("duxing.online");

        //4.获取文档摘要信息，设置标题、作者、内容
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        sumInfo.setTitle("员工信息表");
        sumInfo.setAuthor("李白");
        sumInfo.setComments("本文档由 李白 提供");

        //5.创建表单sheet
        HSSFSheet sheet = workbook.createSheet("员工信息表");

        //6.创建样式
        //标题行:背景颜色、填充模式
        HSSFCellStyle headRowStyle = workbook.createCellStyle();
        headRowStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置日期格式
        HSSFCellStyle dateCellStyle =  workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        //设置列宽
        sheet.setColumnWidth(0,5*256);
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(2,10*256);
        sheet.setColumnWidth(3,5*256);
        sheet.setColumnWidth(4,15*256);
        sheet.setColumnWidth(5,20*256);
        sheet.setColumnWidth(6,10*256);
        sheet.setColumnWidth(7,10*256);
        sheet.setColumnWidth(8,16*256);
        sheet.setColumnWidth(9,12*256);
        sheet.setColumnWidth(10,20*256);
        sheet.setColumnWidth(11,15*256);
        sheet.setColumnWidth(12,20*256);
        sheet.setColumnWidth(13,15*256);
        sheet.setColumnWidth(14,15*256);
        sheet.setColumnWidth(15,15*256);
        sheet.setColumnWidth(16,12*256);
        sheet.setColumnWidth(17,12*256);
        sheet.setColumnWidth(18,12*256);
        sheet.setColumnWidth(19,12*256);
        sheet.setColumnWidth(20,12*256);
        sheet.setColumnWidth(21,10*256);
        sheet.setColumnWidth(22,8*256);
        sheet.setColumnWidth(23,20*256);
        sheet.setColumnWidth(24,20*256);
        sheet.setColumnWidth(25,15*256);


        //7.创建标题行
        HSSFRow row0 = sheet.createRow(0);

        HSSFCell cel0 = row0.createCell(0);
        cel0.setCellStyle(headRowStyle);
        cel0.setCellValue("编号");

        HSSFCell cel1 = row0.createCell(1);
        cel1.setCellStyle(headRowStyle);
        cel1.setCellValue("姓名");

        HSSFCell cel2 = row0.createCell(2);
        cel2.setCellStyle(headRowStyle);
        cel2.setCellValue("工号");

        HSSFCell cel3 = row0.createCell(3);
        cel3.setCellStyle(headRowStyle);
        cel3.setCellValue("性别");

        HSSFCell cel4 = row0.createCell(4);
        cel4.setCellStyle(headRowStyle);
        cel4.setCellValue("出生日期");

        HSSFCell cel5 = row0.createCell(5);
        cel5.setCellStyle(headRowStyle);
        cel5.setCellValue("身份证号码");

        HSSFCell cel6 = row0.createCell(6);
        cel6.setCellStyle(headRowStyle);
        cel6.setCellValue("婚姻状况");

        HSSFCell cel7 = row0.createCell(7);
        cel7.setCellStyle(headRowStyle);
        cel7.setCellValue("民族");

        HSSFCell cel8 = row0.createCell(8);
        cel8.setCellStyle(headRowStyle);
        cel8.setCellValue("籍贯");

        HSSFCell cel9 = row0.createCell(9);
        cel9.setCellStyle(headRowStyle);
        cel9.setCellValue("政治面貌");

        HSSFCell cel10 = row0.createCell(10);
        cel10.setCellStyle(headRowStyle);
        cel10.setCellValue("邮箱");

        HSSFCell cel11 = row0.createCell(11);
        cel11.setCellStyle(headRowStyle);
        cel11.setCellValue("手机号");

        HSSFCell cel12 = row0.createCell(12);
        cel12.setCellStyle(headRowStyle);
        cel12.setCellValue("联系地址");

        HSSFCell cel13 = row0.createCell(13);
        cel13.setCellStyle(headRowStyle);
        cel13.setCellValue("所属部门");

        HSSFCell cel14 = row0.createCell(14);
        cel14.setCellStyle(headRowStyle);
        cel14.setCellValue("职位");

        HSSFCell cel15 = row0.createCell(15);
        cel15.setCellStyle(headRowStyle);
        cel15.setCellValue("职称");

        HSSFCell cel16 = row0.createCell(16);
        cel16.setCellStyle(headRowStyle);
        cel16.setCellValue("聘用形式");

        HSSFCell cel17 = row0.createCell(17);
        cel17.setCellStyle(headRowStyle);
        cel17.setCellValue("入职日期");

        HSSFCell cel18 = row0.createCell(18);
        cel18.setCellStyle(headRowStyle);
        cel18.setCellValue("转正日期");

        HSSFCell cel19 = row0.createCell(19);
        cel19.setCellStyle(headRowStyle);
        cel19.setCellValue("合同起始日期");

        HSSFCell cel20 = row0.createCell(20);
        cel20.setCellStyle(headRowStyle);
        cel20.setCellValue("合同终止日期");

        HSSFCell cel21 = row0.createCell(21);
        cel21.setCellStyle(headRowStyle);
        cel21.setCellValue("合同期限");

        HSSFCell cel22 = row0.createCell(22);
        cel22.setCellStyle(headRowStyle);
        cel22.setCellValue("最高学历");

        HSSFCell cel23 = row0.createCell(23);
        cel23.setCellStyle(headRowStyle);
        cel23.setCellValue("毕业院校");

        HSSFCell cel24 = row0.createCell(24);
        cel24.setCellStyle(headRowStyle);
        cel24.setCellValue("专业");

        HSSFCell cel25 = row0.createCell(25);
        cel25.setCellStyle(headRowStyle);
        cel25.setCellValue("在职状态");

        //遍历集合数据，放到对应的行
        for (int i = 0; i < data.size(); i++) {
            Employee emp = data.get(i);
            //行
            HSSFRow row = sheet.createRow(i + 1);
            //列
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
            HSSFCell c4 = row.createCell(4);
            c4.setCellStyle(dateCellStyle);
            c4.setCellValue(emp.getBirthday());

            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getEmail());
            row.createCell(11).setCellValue(emp.getPhone());
            row.createCell(12).setCellValue(emp.getAddress());
            row.createCell(13).setCellValue(emp.getDepartment().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getJobLevel().getName());
            row.createCell(16).setCellValue(emp.getEngageForm());
            HSSFCell c17 = row.createCell(17);
            c17.setCellStyle(dateCellStyle);
            c17.setCellValue(emp.getBeginDate());

            HSSFCell c18 = row.createCell(18);
            c18.setCellStyle(dateCellStyle);
            c18.setCellValue(emp.getConversionTime());

            HSSFCell c19 = row.createCell(19);
            c19.setCellStyle(dateCellStyle);
            c19.setCellValue(emp.getBeginContract());

            HSSFCell c20 = row.createCell(20);
            c20.setCellStyle(dateCellStyle);
            c20.setCellValue(emp.getEndContract());

            row.createCell(21).setCellValue(emp.getContractTerm());
            row.createCell(22).setCellValue(emp.getTiptopDegree());
            row.createCell(23).setCellValue(emp.getSchool());
            row.createCell(24).setCellValue(emp.getSpecialty());
            row.createCell(25).setCellValue(emp.getWorkState());
        }

        //返回数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();

        //下载头设置
        try {
            headers.setContentDispositionFormData("attachment",
                    new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(outputStream.toByteArray(),headers, HttpStatus.CREATED);
    }

    /**
     * 将Excel对应的行转成employee对象，返回list集合
     * @param file
     * @param nationList    民族
     * @param pllList       政治面貌
     * @param jobLevelList  职称
     * @param positionList  职位
     * @param depList       部门
     * @return
     */
    public static List<Employee> excelToEmployee(MultipartFile file,
                  List<Nation> nationList, List<Politicsstatus> pllList,
                  List<JobLevel> jobLevelList, List<Position> positionList,
                  List<Department> depList) {
        ArrayList<Employee> empList = new ArrayList<>();
        Employee employee = null;

        try {
            //1.创建一个workbook对象
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //2.获取workbook中表单的数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //3.获取每个表单
                HSSFSheet sheetAt = workbook.getSheetAt(i);
                //4.获取表单中的行数,对其遍历,j=1跳过标题行
                int rows = sheetAt.getPhysicalNumberOfRows();
                for (int j = 1; j < rows; j++) {
                    //5.获取行数据,无数据则跳过，防止空行
                    HSSFRow row = sheetAt.getRow(j);
                    if (row == null) {
                        continue;
                    }
                    //6.获取列,对其遍历
                    employee = new Employee();
                    int cells = row.getPhysicalNumberOfCells();
                    for (int k = 0; k < cells; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()) {
                            case STRING:
                                String value = cell.getStringCellValue();
                                switch (k) {
                                    case 1:
                                        employee.setName(value);
                                        break;
                                    case 2:
                                        employee.setWorkID(value);
                                        break;
                                    case 3:
                                        employee.setGender(value);
                                        break;
                                    case 5:
                                        employee.setIdCard(value);
                                        break;
                                    case 6:
                                        employee.setWedlock(value);
                                        break;
                                    case 7:
                                        int ni = nationList.indexOf(new Nation(value));
                                        employee.setNationId(nationList.get(ni).getId());
                                        break;
                                    case 8:
                                        employee.setNativePlace(value);
                                        break;
                                    case 9:
                                        int pi = pllList.indexOf(new Politicsstatus(value));
                                        employee.setPoliticId(pllList.get(pi).getId());
                                        break;
                                    case 10:
                                        employee.setEmail(value);
                                        break;
                                    case 11:
                                        employee.setPhone(value);
                                        break;
                                    case 12:
                                        employee.setAddress(value);
                                        break;
                                    case 13:
                                        //部门
                                        int di = depList.indexOf(new Department(value));
                                        employee.setDepartmentId(depList.get(di).getId());
                                        break;
                                    case 14:
                                        //职位
                                        int poi = positionList.indexOf(new Position(value));
                                        employee.setPosId(positionList.get(poi).getId());
                                        break;
                                    case 15:
                                        //职称
                                        int ji = jobLevelList.indexOf(new JobLevel(value));
                                        employee.setJobLevelId(jobLevelList.get(ji).getId());
                                        break;
                                    case 16:
                                        employee.setEngageForm(value);
                                        break;
                                    case 22:
                                        employee.setTiptopDegree(value);
                                        break;
                                    case 23:
                                        employee.setSchool(value);
                                        break;
                                    case 24:
                                        employee.setSpecialty(value);
                                        break;
                                    case 25:
                                        employee.setWorkState(value);
                                        break;

                                }
                                break;
                            default: {
                                //日期
                                switch (k) {
                                    case 4:
                                        employee.setBirthday(cell.getDateCellValue());
                                        break;
                                    case 17:
                                        employee.setBeginDate(cell.getDateCellValue());
                                        break;
                                    case 18:
                                        employee.setConversionTime(cell.getDateCellValue());
                                        break;
                                    case 19:
                                        employee.setBeginContract(cell.getDateCellValue());
                                        break;
                                    case 20:
                                        employee.setEndContract(cell.getDateCellValue());
                                        break;
                                    case 21:
                                        employee.setContractTerm(cell.getNumericCellValue());
                                        break;
                                }
                            }
                            break;
                        }
                    }
                    employee.setImgUrl("default.gif");
                    empList.add(employee);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return empList;
    }
}
