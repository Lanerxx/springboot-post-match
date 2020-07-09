package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.entity.User;
import com.example.springbootpostmatch.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (student.getBirthday() !=null){
            s.setBirthday(student.getBirthday());
        }
        if (student.getGraduationSchoolName()!=null){
            s.setGraduationSchoolName(student.getGraduationSchoolName());
        }
        if (student.getMajor()!=null){
            s.setMajor(student.getMajor());
        }
        if (student.getGraduationDate() !=null){
            s.setGraduationDate(student.getGraduationDate());
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
        }
        studentRepository.save(s);
        return s;
    }

    public Student getStudent(String name){
        return studentRepository.findByName(name).orElse(null);
    }
    public Student getStudentByUserNumber(int number){
        return studentRepository.getStudentsByUserNumber(number).orElse(null);
    }
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
    //Because the User and Student share the primary key，you can input an Id from the User as well as an Id from the Student
    public Student getStudent(int id) {
        return studentRepository.findById(id).orElse(null);
    }
}
