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
        log.debug("service :{}+{}", phoneNumber,name);
        return enterpriseRepository.getEnterpriseByPhoneNumberAndName(phoneNumber,name).orElse(null);
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
        return postRepository.listPostByEnterpriseId(eid).orElse(null);
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post getPost(int eid , String name){
        return postRepository.getPostByEnterpriseIdAndName(eid, name).orElse(null);
    }


}
