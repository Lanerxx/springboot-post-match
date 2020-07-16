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
import com.hankcs.hanlp.HanLP;
import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        log.debug("{}", enterpriseInoVo.getGenderCut());
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
        if (enterprise.getOtherRequirements()!=null){
            e.setOtherRequirements(enterprise.getOtherRequirements());
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
        }else {
            e.setForeignLanguageProficiency(Student.ForeignLanguageProficiency.NONE);
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
        }else {
            e.setGenderCut(Student.Gender.NONE);
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
        }else {
            e.setSchoolRankCut(Student.SchoolRank.OTHER);
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
        }else {
            e.setEducationCut(Student.Education.OTHER);
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
        }else {
            e.setEnterpriseNature(Enterprise.EnterpriseNature.PRIVATE_ENTERPRISE);
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
        log.debug("genfer:{} /genderCut:{}", gender,genderCut);
        log.debug("genferIndex:{} /genderCutIndex:{} /len:{}",getGenderIndex(gender),getGenderIndex(genderCut),Student.Gender.values().length);
        boolean qualified = false;
        log.debug("qualified init:{}",qualified);
        if (getGenderIndex(gender) == getGenderIndex(genderCut) || getGenderIndex(genderCut)==Student.Gender.values().length-1 ) qualified = true;
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
        || getForeignLanguageProficiencyIndex(foreignLanguageProficiencyCut) ==Student.ForeignLanguageProficiency.values().length-1)
            qualified = true;
        return qualified;
    }

    //HanLp短语分词
    public List<String> participlesByHanLPExtractPhrase(String s){
        return HanLP.extractPhrase(s, 5);
    }

    //标点符号分词（专业/岗位名/期望行业）
    public List<String> participlesByPunctuation(String s){
        return Arrays.asList(s.split("、|，|。|；|？|！|,|\\.|;|\\?|!|]"));
    }

    //结巴普通分词（个人陈述/地名）
    public List<String> participlesByJiebaSimple(String s){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        return segmenter.sentenceProcess(s);
    }

    //计算词组的余玄相似度
    public double getSimilarity(List<String> PhraseList1,List<String> PhraseList2){
//        System.out.println("PhraseList1 :"+PhraseList1);
//        System.out.println("PhraseList2 :"+PhraseList2);

        //合并分词结果
        List<String> resultMergeList = new ArrayList<String>();
        resultMergeList.addAll(PhraseList1);
        resultMergeList.addAll(PhraseList2);
//        System.out.println("resultMergeList :"+resultMergeList);

        //去掉重复元素
        for  ( int  i  =   0 ; i  <  resultMergeList.size()  -   1 ; i ++ )  {
            for  ( int  j  =  resultMergeList.size()  -   1 ; j  >  i; j -- )  {
                if  (resultMergeList.get(j).equals(resultMergeList.get(i)))  {
                    resultMergeList.remove(j);
                }
            }
        }
//        System.out.println("resultMergeList2 :"+resultMergeList);

        //计算词频
        int[] resultStatistic1 = new int[resultMergeList.size()];
//        System.out.println("resultStatistic1 :1111");
        for (int i = 0; i < resultMergeList.size(); i++) {
//            System.out.println("resultStatistic1[] :"+ resultStatistic1[i]);
            resultStatistic1[i] = Collections.frequency(PhraseList1, resultMergeList.get(i));
//            System.out.println("resultStatistic1[] :"+ resultStatistic1[i]);
        }
        int[] resultStatistic2 = new int[resultMergeList.size()];
        for (int i = 0; i < resultMergeList.size(); i++) {
            resultStatistic2[i] = Collections.frequency(PhraseList2,  resultMergeList.get(i));
        }
//        System.out.print("[");
//        for (int i = 0; i < resultStatistic1.length; i++) {
//            System.out.print(resultStatistic1[i]+",");
//        }
//        System.out.print("]");System.out.println();
//        System.out.print("[");
//        for (int i = 0; i < resultStatistic2.length; i++) {
//            System.out.print(resultStatistic2[i]+",");
//        }
//        System.out.print("]");

        //计算余弦值
        double dividend = 0;
        double divisor1 = 0;
        double divisor2 = 0;
        for (int i = 0; i < resultStatistic1.length; i++) {
            dividend += resultStatistic1[i] * resultStatistic2[i];
            divisor1 += Math.pow(resultStatistic1[i], 2);
            divisor2 += Math.pow(resultStatistic2[i], 2);
        }
        double similarity = dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
//        System.out.println("余弦值："+similarity);
        return similarity;
    }

    //标点符号分词 + 计算词组的余玄相似度
    public double getSimilarityByPunctuation(String stu,String cut){
        return getSimilarity(participlesByPunctuation(stu),participlesByPunctuation(cut));
    }

    //结巴普通分词 + 计算词组的余玄相似度
    public double getSimilarityByJiebaSimple(String stu,String cut){
        return getSimilarity(participlesByJiebaSimple(stu), participlesByJiebaSimple(cut));
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
                System.out.println("属性类型："+fieldType+",属性名："+fieldName);
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

    //筛选硬性要求：性别、学校等级、文凭等级、外语水平，返回合格学生
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

    //计算软性要求：专业、岗位名、期望行业、地点、个人陈述，返回学生以及队员余玄相似度加权值
    public List<StudentMatchVo> getWeightedStudents(List<Student> students,Enterprise enterprise,Post post){
        double totalScore = 6.5;
        List<StudentMatchVo> studentInoVos = new ArrayList<>();
        String majorEnterprise = enterprise.getMajorCut();
        String namePost = post.getName();
        String industryEnterprise = enterprise.getIndustry();
        String locationEnterprise = enterprise.getLocation();
        String otherRequirements = enterprise.getOtherRequirements();
        int highestSalary = enterprise.getHighestSalery();
        int salary = post.getSalary();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int date = Integer.parseInt(df.format(new Date()).substring(0,4));
        log.debug("{}", date);
        students.forEach(student -> {
            StudentMatchVo StudentMatchVo = new StudentMatchVo();
            StudentMatchVo.setScore(0);
            StudentMatchVo.setStudent(student);
            String major = student.getMajor();
            String postS = student.getExpectedPosition();
            String industry = student.getExpectedIndustry();
            String nativePlace = student.getNativePlace();
            String intentionPlace = student.getEmploymentIntentionPlace();
            String personalStatement = student.getPersonalStatement();
            int graduationDate = student.getGraduationDate();
            int expectedSalary = student.getExpectedSalary();
            int paperCount = student.getPaperCount();
            int workExperience = student.getWorkExperience();
            float grade = student.getGrade();

            //标点符号分词 + 计算余玄相似度（专业/岗位名/期望行业）
            double majorScore = getSimilarityByPunctuation(major,majorEnterprise)*2;
            double postScore = getSimilarityByPunctuation(namePost, postS);
            double industryScore = getSimilarityByPunctuation(industry, industryEnterprise);
//            log.debug("majorScore:{} /postScore:{} /industryScore:{}",majorScore,postScore,industryScore);

            //结巴普通分词 + 计算余玄相似度（个人陈述/地名）
            double nativeLocationScore = getSimilarityByJiebaSimple(nativePlace, locationEnterprise);
            double intentionLocationScore = getSimilarityByJiebaSimple(intentionPlace, locationEnterprise);
            double otherScore = getSimilarityByJiebaSimple(personalStatement, otherRequirements);
//            log.debug("nativeLocationScore:{} /intentionLocationScore:{} /otherScore:{}"
//                    ,nativeLocationScore,intentionLocationScore);

            //数值类型，副权重记分（薪水/毕业时间/论文数/工作经验）
            double salaryScore = salary > expectedSalary ? 0.2 : 0;
            if (salaryScore == 0 && highestSalary > expectedSalary) salaryScore =0.1;
            double dateScore = 0;
            if (date == graduationDate ||Math.abs(date-graduationDate)<2) dateScore = 0.1;
            double paperScore = paperCount/10;
            if (paperScore > 1) paperScore = 1;
            double workScore = workExperience/10;
            if (workScore > 1) workScore = 1;
            double gradeScore = 0;
            if (grade > 90) gradeScore =1;
            else if (grade >88) gradeScore = 0.85;
            else if (grade > 85) gradeScore = 0.7;
            else if (grade > 80) gradeScore = 0.5;
            else if (grade > 70) gradeScore = 0.3;
            else if (grade > 60) gradeScore = 0.1;

//            log.debug("salaryScore:{} /dateScore:{} /paperScore:{} /workScore:{}"
//                    ,salaryScore,dateScore,paperScore,workScore);

            //总分
            double score = (majorScore + postScore + industryScore +
                    nativeLocationScore + intentionLocationScore +
                    salaryScore + dateScore + paperScore + workScore + gradeScore)/totalScore;
            if (score >=1) score = 0.99;
            log.debug("name:{} ----:score{}", student.getName(),score);

            StudentMatchVo.setScore(score);
            studentInoVos.add(StudentMatchVo);
        });
        return studentInoVos;
    }

    //根据计算好的权重值给学生排序排序
    public List<StudentMatchVo> getRankingStudents(List<StudentMatchVo> studentMatchVos){
        for (int i = 1; i < studentMatchVos.size(); i++) {  //第一层for循环,用来控制冒泡的次数
            for (int j = 0; j < studentMatchVos.size()-1; j++) { //第二层for循环,用来控制冒泡一层层到最后
                    //如果前一个数比后一个数大,两者调换 ,意味着泡泡向上走了一层
                if (studentMatchVos.get(j).getScore() < studentMatchVos.get(j+1).getScore() ){
                    double tempScore = studentMatchVos.get(j).getScore();
                    Student tempStudent = studentMatchVos.get(j).getStudent();

                    studentMatchVos.get(j).setStudent(studentMatchVos.get(j+1).getStudent());
                    studentMatchVos.get(j).setScore(studentMatchVos.get(j+1).getScore());
                    studentMatchVos.get(j+1).setStudent(tempStudent);
                    studentMatchVos.get(j+1).setScore(tempScore);
                }
            }
        }
        return studentMatchVos;
    }

    //岗位匹配
    public List<StudentMatchVo> postMatch(int pid,Enterprise enterprise){
        if (objCheckIsNull(enterprise)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再匹配");
        }
        Post post = enterpriseService.getPost(pid);
        List<StudentMatchVo> studentMatchVos =new ArrayList<>();
        if (post!=null){
            List<Student> qualifiedStudents = getQualifiedStudents(enterprise);
            qualifiedStudents.forEach(student -> {
                log.debug("name:{} /Education:{} /Gender:{} /SchoolRank:{} /FL:{}",
                        student.getName(),student.getEducation(),student.getGender(),student.getGraduationSchoolRank()
                ,student.getForeignlanguageproficiency());
            });

            studentMatchVos = getRankingStudents(getWeightedStudents(qualifiedStudents, enterprise, post));
            studentMatchVos.forEach(studentMatchVo -> {
                log.debug("name:{} ----:score{}", studentMatchVo.getStudent().getName(),studentMatchVo.getScore());
            });
        }
        return studentMatchVos;
    }
}
