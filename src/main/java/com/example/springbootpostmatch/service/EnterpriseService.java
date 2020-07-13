package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.PostVo;
import com.example.springbootpostmatch.component.vo.StudentInoVo;
import com.example.springbootpostmatch.component.vo.StudentMatchVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.repository.EnterpriseRepository;
import com.example.springbootpostmatch.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PostRepository postRepository;

    //---------"Enterprise's CURD "-----------
    public Enterprise addEnterprise(Enterprise enterprise){
        enterpriseRepository.save(enterprise);
        return enterprise;
    }

    public void deleteEnterprise(int id){
        enterpriseRepository.deleteById(id);
    }

    public Enterprise updateEnterprise(Enterprise enterprise){
        enterpriseRepository.save(enterprise);
        return enterprise;
    }
    public Enterprise updateEnterprise(EnterpriseInoVo enterpriseInoVo,int eid){
        Enterprise e = enterpriseService.getEnterprise(eid);
        Enterprise enterprise = enterpriseInoVo.getEnterprise();
        if (enterprise.getName() !=null){
            e.setName(enterprise.getName());
        }
        if (enterprise.getDetail() !=null){
            e.setDetail(enterprise.getDetail());
        }
        if (enterprise.getIndustry()!=null){
            e.setIndustry(enterprise.getIndustry());
        }
        if (enterprise.getMajorCut() !=null){
            e.setMajorCut(enterprise.getMajorCut());
        }
        if (enterprise.getLowestSalery()>0){
            e.setLowestSalery(enterprise.getLowestSalery());
        }
        if (enterprise.getHighestSalery()>0){
            e.setHighestSalery(enterprise.getHighestSalery());
        }
        if (enterprise.getLocation() !=null){
            e.setLocation(enterprise.getLocation());
        }
        if (enterprise.getPhoneNumber() !=null){
            e.setPhoneNumber(enterprise.getPhoneNumber());
        }

        if(enterpriseInoVo.getForeignLanguageProficiency()!=null){
            switch(enterpriseInoVo.getForeignLanguageProficiency()) {
                case "CET-6":
                    e.setForeignLanguageProficiency(Student.ForeignLanguageProficiency.ENGLISH_CET_6);
                    break;
                case "CET-4":
                    e.setForeignLanguageProficiency(Student.ForeignLanguageProficiency.ENGLISH_CET_4);
                    break;
                case "国外交流经验":
                    e.setForeignLanguageProficiency(Student.ForeignLanguageProficiency.ENGLISH_Foreign_Exchange_Experience);
                    break;
                case "无":
                    e.setForeignLanguageProficiency(Student.ForeignLanguageProficiency.NONE);
                    break;
            }
        }
        if (enterpriseInoVo.getGenderCut()!=null){
            switch(enterpriseInoVo.getGenderCut()) {
                case "男":
                    e.setGenderCut(Student.Gender.MALE);
                    break;
                case "女":
                    e.setGenderCut(Student.Gender.FEMALE);
                    break;
                case "无":
                    e.setGenderCut(Student.Gender.NONE);
                    break;
            }
        }
        if (enterpriseInoVo.getSchoolRankCut()!=null){
            switch(enterpriseInoVo.getSchoolRankCut()) {
                case "985":
                    e.setSchoolRankCut(Student.SchoolRank._985);
                    break;
                case "211":
                    e.setSchoolRankCut(Student.SchoolRank._211);
                    break;
                case "省重点":
                    e.setSchoolRankCut(Student.SchoolRank.PROVINCIAL_KEY);
                    break;
                case "普通本科":
                    e.setSchoolRankCut(Student.SchoolRank.GENERAL_UNDERGRADUATE);
                    break;
                case "专科":
                    e.setSchoolRankCut(Student.SchoolRank.JUNIOR_COLLEGE);
                    break;
                case "高职":
                    e.setSchoolRankCut(Student.SchoolRank.HIGHER_VOCATIONAL_COLLEGE);
                    break;
                case "其他":
                    e.setSchoolRankCut(Student.SchoolRank.OTHER);
                    break;
            }
        }
        if (enterpriseInoVo.getEducationCut()!=null){
            switch(enterpriseInoVo.getEducationCut()) {
                case "博士":
                    e.setEducationCut(Student.Education.DOCTOR);
                    break;
                case "硕士":
                    e.setEducationCut(Student.Education.MASTER);
                    break;
                case "本科":
                    e.setEducationCut(Student.Education.BACHELOR);
                    break;
                case "高职高专":
                    e.setEducationCut(Student.Education.HIGHER_VOCATIONAL_COLLEGE);
                    break;
                case "其他":
                    e.setEducationCut(Student.Education.OTHER);
                    break;
            }
        }
        if (enterpriseInoVo.getEnterpriseNature()!=null){
            switch (enterpriseInoVo.getEnterpriseNature()){
                case "民企":
                    e.setEnterpriseNature(Enterprise.EnterpriseNature.PRIVATE_ENTERPRISE);
                    break;
                case "外企":
                    e.setEnterpriseNature(Enterprise.EnterpriseNature.FOREIGN);
                    break;
                case "国企":
                    e.setEnterpriseNature(Enterprise.EnterpriseNature.STATE_OWNED);
                    break;
            }
        }
        enterpriseRepository.save(e);
        return enterpriseInoVo.getEnterprise();
    }

    public Enterprise getEnterpriseByUserNumber(int number){
        return enterpriseRepository.getEnterpriseByUserNumber(number).orElse(null);
    }
    public List<Enterprise> listEnterprises() {
        return enterpriseRepository.findAll();
    }
    public Enterprise getEnterprise(String phoneNumber,String name){
        return enterpriseRepository.getEnterpriseByPhoneNumberAndName(phoneNumber,name).orElse(null);
    }

    public EnterpriseInoVo getEnterpriseInoVo(int id){
        Enterprise enterprise = enterpriseService.getEnterprise(id);
        String enterpriseNature=null;
        String genderCut=null;
        String schoolRankCut=null;
        String educationCut=null;
        String foreignLanguageProficiency=null;
        if (enterprise ==null){
            return null;
        }else {
            EnterpriseInoVo enterpriseInoVo = new EnterpriseInoVo();
            enterpriseInoVo.setEnterprise(enterprise);
            Enterprise.EnterpriseNature nature = enterprise.getEnterpriseNature();
            if(nature != null){
                if (nature == Enterprise.EnterpriseNature.FOREIGN) enterpriseNature="外企";
                if (nature == Enterprise.EnterpriseNature.STATE_OWNED) enterpriseNature="国企";
                if (nature == Enterprise.EnterpriseNature.PRIVATE_ENTERPRISE) enterpriseNature="民企";
            }else {
                enterpriseNature="民企";
            }
            Student.Gender gender = enterprise.getGenderCut();
            if(gender!=null){
                if (gender ==Student.Gender.MALE) genderCut="男";
                if (gender ==Student.Gender.FEMALE) genderCut="女";
                if (gender ==Student.Gender.NONE) genderCut="无";
            }else {
                genderCut="无";
            }
            Student.Education education = enterprise.getEducationCut();
            if (education!=null){
                if (education ==Student.Education.DOCTOR) educationCut="博士";
                if (education ==Student.Education.MASTER) educationCut="硕士";
                if (education ==Student.Education.BACHELOR) educationCut="本科";
                if (education ==Student.Education.HIGHER_VOCATIONAL_COLLEGE) educationCut="高职高专";
                if (education ==Student.Education.OTHER) educationCut="其他";
            }else {
                educationCut="其他";
            }
            Student.SchoolRank rank = enterprise.getSchoolRankCut();
            if (rank !=null){
                log.debug("{}", rank);
                if (rank ==Student.SchoolRank._985) schoolRankCut="985";
                if (rank ==Student.SchoolRank._211) schoolRankCut="211";
                if (rank ==Student.SchoolRank.PROVINCIAL_KEY) schoolRankCut="省重点";
                if (rank ==Student.SchoolRank.GENERAL_UNDERGRADUATE) schoolRankCut="普通本科";
                if (rank ==Student.SchoolRank.JUNIOR_COLLEGE) schoolRankCut="专科";
                if (rank ==Student.SchoolRank.HIGHER_VOCATIONAL_COLLEGE) schoolRankCut="高职";
                if (rank ==Student.SchoolRank.OTHER) schoolRankCut="其他";
            }else {
                schoolRankCut="其他";
            }
            Student.ForeignLanguageProficiency FLP = enterprise.getForeignLanguageProficiency();
            if (FLP!=null){
                if (FLP == Student.ForeignLanguageProficiency.ENGLISH_Foreign_Exchange_Experience) foreignLanguageProficiency="国外交流经验";
                if (FLP == Student.ForeignLanguageProficiency.ENGLISH_CET_6) foreignLanguageProficiency="CET-6";
                if (FLP == Student.ForeignLanguageProficiency.ENGLISH_CET_4) foreignLanguageProficiency="CET-4";
                if (FLP == Student.ForeignLanguageProficiency.NONE) foreignLanguageProficiency="无";
            }else {
                foreignLanguageProficiency="无";
            }
            enterpriseInoVo.setEducationCut(educationCut);
            enterpriseInoVo.setSchoolRankCut(schoolRankCut);
            enterpriseInoVo.setGenderCut(genderCut);
            enterpriseInoVo.setForeignLanguageProficiency(foreignLanguageProficiency);
            enterpriseInoVo.setEnterpriseNature(enterpriseNature);
            return enterpriseInoVo;
        }
    }
    public Enterprise getEnterprise(int id) {
        return enterpriseRepository.findById(id).orElse(null);
    }

    //---------"Post's CURD "-----------
    public Post addPost(Post post){
        postRepository.save(post);
        return post;
    }

    public void deletePost(int id){
        postRepository.deleteById(id);
    }

    public Post updatePost(PostVo postVo, int pid){
        Post p = enterpriseService.getPost(pid);
        if (postVo.getPost()!=null){
            Post post = postVo.getPost();
            if (post.getName()!=null){
                p.setName(post.getName());
            }
            if (post.getCount()>0){
                p.setCount(post.getCount());
            }
            if (post.getSalary()>0){
                p.setSalary(post.getSalary());
            }
            if (post.getDetail()!=null){
                p.setDetail(post.getDetail());
            }
        }


        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (postVo.getEndTime()!=null){
            LocalDateTime endTime = LocalDateTime.parse(postVo.getEndTime(), df);
            p.setEndTime(endTime);
        }
        if (postVo.getStartTime()!=null){
            LocalDateTime startTime = LocalDateTime.parse(postVo.getStartTime(), df);
            p.setStartTime(startTime);
        }
        postRepository.save(p);
        return p;
    }

    public Post updatePost(Post post){
        postRepository.save(post);
        return post;
    }

    public List<Post> listPosts() {
        return postRepository.findAll();
    }

    public List<Post> listPost(int eid){
        log.debug("{}", eid);
        List<Post> posts = postRepository.listPostByEnterpriseId(eid).orElse(new ArrayList<>());
        posts.forEach(post -> {
            post.getName();
        });
        log.debug("{}",posts.size());

        return postRepository.listPostByEnterpriseId(eid).orElse(new ArrayList<>());
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post getPost(int eid , String name){
        return postRepository.getPostByEnterpriseIdAndName(eid, name).orElse(null);
    }

    //------------Post Match-------------
    //获取枚举类型对应的索引值(其他同理)
    //index默认值取枚举类型最大长度：
    //1.若学生未填写该值，则默认该值为最低水平
    //2.若企业未填写该值，则默认该值为最低要求
    public int getGenderIndex(Student.Gender gender){
        Student.Gender[] genders = Student.Gender.values();
        int n = genders.length;
        int index = n;
        for(int i = 0;i <n ; i++) {
            if (genders[i] == gender) index = i;
        }
        return index;
    }
    //验证性别是否符合要求
    //性别与要求一致或性别要求为无
    public boolean verifyGender(Student.Gender gender,Student.Gender genderCut){
        boolean qualified = false;
        log.debug("qualified init:{}",qualified);
        if (getGenderIndex(gender) == getGenderIndex(genderCut) || getGenderIndex(genderCut)==Student.Gender.values().length ) qualified = true;
        log.debug("qualified return:{}",qualified);
        return qualified;
    }

    //验证性学校等级是否符合要求
    public int getSchoolRankIndex(Student.SchoolRank schoolRank){
        Student.SchoolRank[] schoolRanks = Student.SchoolRank.values();
        int n = schoolRanks.length;
        int index = n;
        log.debug("n :{}",n);
        for(int i = 0;i <n ; i++) {
            if (schoolRanks[i] == schoolRank) index = i;
        }
        return index;
    }
    public boolean verifySchoolRank(Student.SchoolRank schoolRank,Student.SchoolRank schoolRankCut){
        boolean qualified = false;
        log.debug("qualified init:{}",qualified);
        if (getSchoolRankIndex(schoolRank) <= getSchoolRankIndex(schoolRankCut) ) qualified = true;
        log.debug("qualified return:{}",qualified);
        return qualified;
    }

    //验证性文凭等级是否符合要求
    public int getEducationIndex(Student.Education education){
        Student.Education[] educations = Student.Education.values();
        int n = educations.length;
        int index = n;
        for(int i = 0;i <n ; i++) {
            if (educations[i] == education) index = i;
        }
        return index;
    }
    public boolean verifyEducation(Student.Education education,Student.Education educationCut){
        boolean qualified = false;
        if (getEducationIndex(education) <= getEducationIndex(educationCut) ) qualified = true;
        return qualified;
    }

    //验证性外语水平是否符合要求
    public int getForeignLanguageProficiencyIndex(Student.ForeignLanguageProficiency foreignLanguageProficiency){
        Student.ForeignLanguageProficiency[] foreignLanguageProficiencies = Student.ForeignLanguageProficiency.values();
        int n = foreignLanguageProficiencies.length;
        int index = n;
        for(int i = 0;i <n ; i++) {
            if (foreignLanguageProficiencies[i] == foreignLanguageProficiency) index = i;
        }
        return index;
    }
    public boolean verifyForeignLanguageProficiency(Student.ForeignLanguageProficiency foreignLanguageProficiency,Student.ForeignLanguageProficiency foreignLanguageProficiencyCut){
        boolean qualified = false;
        if (getForeignLanguageProficiencyIndex(foreignLanguageProficiency) <= getForeignLanguageProficiencyIndex(foreignLanguageProficiencyCut)
        || getForeignLanguageProficiencyIndex(foreignLanguageProficiencyCut) ==Student.ForeignLanguageProficiency.values().length)
            qualified = true;
        return qualified;
    }

    /**
     * 判断对象是否为空，且对象的所有属性都为空
     * ps: boolean类型会有默认值false 判断结果不会为null 会影响判断结果
     *     序列化的默认值也会影响判断结果
     * @param object
     * @return
     */
    public boolean objCheckIsNull(Object object){
        Class clazz = (Class)object.getClass(); // 得到类对象
        Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
        boolean flag = false; //定义返回结果，默认为false
        for(Field field : fields){
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object); //得到属性值
                Type fieldType =field.getGenericType();//得到属性类型
                String fieldName = field.getName(); // 得到属性名
                System.out.println("属性类型："+fieldType+",属性名："+fieldName+",属性值："+fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldValue == null){  //只要有一个属性值为null 就返回false 表示对象中有属性为null
                flag = true;
                break;
            }
        }
        return flag;
    }

    public List<Student> getQualifiedStudents(Enterprise enterprise){
        List<Student> qualifiedStudents = new ArrayList<>();
        Student.Gender genderCut = enterprise.getGenderCut();
        Student.SchoolRank schoolRankCut = enterprise.getSchoolRankCut();
        Student.Education educationCut = enterprise.getEducationCut();
        Student.ForeignLanguageProficiency foreignLanguageProficiencyCut = enterprise.getForeignLanguageProficiency();
        List<Student> students = studentService.listStudents();
        students.forEach(student -> {
            if (verifyEducation(student.getEducation(), educationCut)
                    && verifyForeignLanguageProficiency(student.getForeignlanguageproficiency(), foreignLanguageProficiencyCut)
                    &&verifyGender(student.getGender(), genderCut)
                    &&verifySchoolRank(student.getGraduationSchoolRank(), schoolRankCut)){
                qualifiedStudents.add(student);
            }
        });
        return qualifiedStudents;
    }
    public List<StudentMatchVo> postMatch(int pid,Enterprise enterprise){
        if (objCheckIsNull(enterprise)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再匹配");
        }
        Post post = enterpriseService.getPost(pid);
        List<StudentMatchVo> studentMatchVos =new ArrayList<>();
        if (post!=null){
            String location = enterprise.getLocation();
            String majorCut = enterprise.getMajorCut();
            int lowestSalery = enterprise.getLowestSalery();
            int highestSalery = enterprise.getHighestSalery();

            int count = post.getCount();
            int salary = post.getSalary();
            List<Student> qualifiedStudents = getQualifiedStudents(enterprise);
            log.debug("完成第一步");
            qualifiedStudents.forEach(student -> {
                log.debug("name:{} /Education:{} /Gender:{} /SchoolRank:{} /FL:{}",
                        student.getName(),student.getEducation(),student.getGender(),student.getGraduationSchoolRank()
                ,student.getForeignlanguageproficiency());
            });
        }
        return null;
    }




}
