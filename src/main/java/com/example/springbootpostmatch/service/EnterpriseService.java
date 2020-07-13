package com.example.springbootpostmatch.service;
import com.example.springbootpostmatch.component.vo.EnterpriseInoVo;
import com.example.springbootpostmatch.component.vo.PostVo;
import com.example.springbootpostmatch.entity.Enterprise;
import com.example.springbootpostmatch.entity.Post;
import com.example.springbootpostmatch.entity.Student;
import com.example.springbootpostmatch.repository.EnterpriseRepository;
import com.example.springbootpostmatch.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                if (rank ==Student.SchoolRank._985) schoolRankCut="985";
                if (rank ==Student.SchoolRank._211) schoolRankCut="211";
                if (rank ==Student.SchoolRank.PROVINCIAL_KEY) schoolRankCut="省重点";                if (rank ==Student.SchoolRank.PROVINCIAL_KEY) schoolRankCut="";
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
        return postRepository.listPostByEnterpriseId(eid).orElse(new ArrayList<>());
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post getPost(int eid , String name){
        return postRepository.getPostByEnterpriseIdAndName(eid, name).orElse(null);
    }


}
