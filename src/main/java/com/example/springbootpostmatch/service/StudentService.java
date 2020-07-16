package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.repository.StudentRepository;
import jxl.CellType;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jxl.CellType;
import jxl.write.*;
import jxl.Workbook;

import java.io.File;
import java.io.IOException;
import java.lang.Boolean;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    //---------"Student's CURD "-----------
    public Student addStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(int id){
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student){
        studentRepository.save(student);
        return student;
    }

    public Student updateStudent(StudentInoVo studentInoVo, int sid){
        Student s = studentService.getStudent(sid);
        Student student = studentInoVo.getStudent();
        if (student.getName() !=null){
            s.setName(student.getName());
        }
        if (student.getGraduationSchoolName()!=null){
            s.setGraduationSchoolName(student.getGraduationSchoolName());
        }
        if (student.getMajor()!=null){
            s.setMajor(student.getMajor());
        }
        if (student.getNativePlace()!=null){
            s.setNativePlace(student.getNativePlace());
        }
        if (student.getExpectedSalary()>0){
            s.setExpectedSalary(student.getExpectedSalary());
        }
        if (student.getExpectedPosition() !=null){
            s.setExpectedPosition(student.getExpectedPosition());
        }
        if (student.getExpectedIndustry() !=null){
            s.setExpectedIndustry(student.getExpectedIndustry());
        }
        if (student.getEmploymentIntentionPlace()!=null){
            s.setEmploymentIntentionPlace(student.getEmploymentIntentionPlace());
        }
        if (student.getPhoneNumber()!=null) {
            s.setPhoneNumber(student.getPhoneNumber());
        }
        if (student.getMajorCourse() !=null){
            s.setMajorCourse(student.getMajorCourse());
        }
        if (student.getSkill()!=null){
            s.setSkill(student.getSkill());
        }
        if (student.getPaperCount()>0){
            s.setPaperCount(student.getPaperCount());
        }
        if (student.getWorkExperience()>0){
            s.setWorkExperience(student.getWorkExperience());
        }
        if (student.getGraduationDate()>0){
            s.setGraduationDate(student.getGraduationDate());
        }
        if (student.getGrade()>0){
            s.setGrade(student.getGrade());
        }
        if (student.getPersonalStatement()!=null){
            s.setPersonalStatement(student.getPersonalStatement());
        }

        if (studentInoVo.getForeignLanguageProficiency()!=null){
            switch(studentInoVo.getForeignLanguageProficiency()){
                case "CET-6":
                    s.setForeignlanguageproficiency(Student.ForeignLanguageProficiency.ENGLISH_CET_6);
                    break;
                case "CET-4":
                    s.setForeignlanguageproficiency(Student.ForeignLanguageProficiency.ENGLISH_CET_4);
                    break;
                case "国外交流经验":
                    s.setForeignlanguageproficiency(Student.ForeignLanguageProficiency.ENGLISH_Foreign_Exchange_Experience);
                    break;
                case "无":
                    s.setForeignlanguageproficiency(Student.ForeignLanguageProficiency.NONE);
                    break;
            }

        }else {
            s.setForeignlanguageproficiency(Student.ForeignLanguageProficiency.NONE);
        }
        if (studentInoVo.getGender()!=null){
            switch(studentInoVo.getGender()) {
                case "男":
                    s.setGender(Student.Gender.MALE);
                    break;
                case "女":
                    s.setGender(Student.Gender.FEMALE);
                    break;
            }
        }

        if (studentInoVo.getSchoolRank()!=null){
            switch(studentInoVo.getSchoolRank()) {
                case "985":
                    s.setGraduationSchoolRank(Student.SchoolRank._985);
                    break;
                case "211":
                    s.setGraduationSchoolRank(Student.SchoolRank._211);
                    break;
                case "省重点":
                    s.setGraduationSchoolRank(Student.SchoolRank.PROVINCIAL_KEY);
                    break;
                case "普通本科":
                    s.setGraduationSchoolRank(Student.SchoolRank.GENERAL_UNDERGRADUATE);
                    break;
                case "专科":
                    s.setGraduationSchoolRank(Student.SchoolRank.JUNIOR_COLLEGE);
                    break;
                case "高职":
                    s.setGraduationSchoolRank(Student.SchoolRank.HIGHER_VOCATIONAL_COLLEGE);
                    break;
                case "其他":
                    s.setGraduationSchoolRank(Student.SchoolRank.OTHER);
                    break;
            }
        }else {
            s.setGraduationSchoolRank(Student.SchoolRank.OTHER);
        }

        if (studentInoVo.getEducation()!=null){
            switch(studentInoVo.getEducation()) {
                case "博士":
                    s.setEducation(Student.Education.DOCTOR);
                    break;
                case "硕士":
                    s.setEducation(Student.Education.MASTER);
                    break;
                case "本科":
                    s.setEducation(Student.Education.BACHELOR);
                    break;
                case "高职高专":
                    s.setEducation(Student.Education.HIGHER_VOCATIONAL_COLLEGE);
                    break;
                case "其他":
                    s.setEducation(Student.Education.OTHER);
                    break;
            }
        }else {
            s.setEducation(Student.Education.OTHER);
        }
        studentRepository.save(s);
        return s;
    }

    public Student getStudent(String phoneNumber,String name){
        return studentRepository.getStudentByNameAndPhoneNumber(phoneNumber,name).orElse(null);
    }

    public Student getStudentByUserNumber(int number){
        return studentRepository.getStudentsByUserNumber(number).orElse(null);
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public boolean downloadResume(int sid){
        Boolean flag = false;
        String basicPath = System.getProperty("user.dir");
        String frontedPath = "/Users/apple/Downloads/简历/";
        log.debug(basicPath);

        //--------------读xls--------------
        String pathR = basicPath + "/src/main/resources/static.files/ResumeTemplate.xls";

        String fileName = studentService.getStudent(sid).getName() + "简历";
//        String pathW = basicPath + "/src/main/resources/static.files/"+fileName+".xls";
        String pathW = frontedPath+fileName+".xls";

        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(new File(pathR));
            //创建可写入的Excel工作薄对象
            WritableWorkbook wwb = Workbook.createWorkbook(new File(pathW), wb);
            //读取第一张工作表
            WritableSheet ws = wwb.getSheet(0);
            //获得第一个单元格对象
            WritableCell wc = ws.getWritableCell(0, 0);
            //判断单元格的类型, 做出相应的转化
            if(wc.getType()== CellType.LABEL){
                Label l = (Label)wc;
                l.setString("好好学习，天天向上");
            }
            //关闭只读的Excel对象
            wb.close();

            //--------------写数据xls--------------
            Student student = studentService.getStudent(sid);

            // 获得行数
            int rows = ws.getRows();
            // 获得列数
            int cols = ws.getColumns();
            //5:单元格
            for (int row = 0; row < rows; row++)
            {
                for (int col = 0; col < cols; col++)
                {
                    System.out.printf("%10s", ws.getCell(col, row)
                            .getContents());
                    log.debug("row:{} / col{}", row,col);
                }
                System.out.println();
            }

            log.debug("{}", student.getGraduationSchoolRank());


            Label label=new Label(1,3,student.getName()); ws.addCell(label);
            label=new Label(2,3,student.getGender().toString());ws.addCell(label);
            label=new Label(3,3,String.valueOf(student.getGrade()));        ws.addCell(label);
            label=new Label(4,3,student.getNativePlace());        ws.addCell(label);
            label=new Label(5,3,student.getGraduationDate()+"年");        ws.addCell(label);

            label=new Label(1,5,student.getGraduationSchoolName());        ws.addCell(label);
            label=new Label(3,5,student.getGraduationSchoolRank().toString());        ws.addCell(label);
            label=new Label(4,5,student.getMajor());        ws.addCell(label);
            label=new Label(5,5,student.getEducation().toString());        ws.addCell(label);

            label=new Label(1,7,student.getPhoneNumber());        ws.addCell(label);
            label=new Label(2,7,student.getForeignlanguageproficiency().toString());        ws.addCell(label);
            label=new Label(3,7,String.valueOf(student.getPaperCount()));        ws.addCell(label);
            label=new Label(4,7,student.getMajorCourse());        ws.addCell(label);
            label=new Label(5,7,String.valueOf(student.getWorkExperience()));        ws.addCell(label);

            label=new Label(1,9,student.getEmploymentIntentionPlace());        ws.addCell(label);
            label=new Label(2,9,String.valueOf(student.getExpectedSalary()));        ws.addCell(label);
            label=new Label(3,9,student.getExpectedIndustry());        ws.addCell(label);
            label=new Label(4,9,student.getExpectedPosition());        ws.addCell(label);
            label=new Label(5,9,student.getSkill());        ws.addCell(label);

            label=new Label(1,11,student.getPersonalStatement());        ws.addCell(label);

            //写入数据，一定记得写入数据，不然你都开始怀疑世界了，excel里面啥都没有
            wwb.write();
            //关闭可写入的Excel对象
            wwb.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }catch (WriteException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //Because the User and Student share the primary key，you can input an Id from the User as well as an Id from the Student
    public Student getStudent(int id) {
        return studentRepository.findById(id).orElse(null);
    }
}
