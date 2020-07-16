package com.example.springbootpostmatch.service;

import com.example.springbootpostmatch.entity.Student;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.mining.word2vec.DocVectorModel;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.huaban.analysis.jieba.JiebaSegmenter;
import jxl.CellType;
import jxl.write.*;
import jxl.Workbook;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Slf4j
public class EnterpriseServicetTest {
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private StudentService studentService;

    @Test
    public void test_postMatch(){
        enterpriseService.postMatch(1, enterpriseService.getEnterprise(40));
    }

    @Test
    public void test_HanLP_AllKinds(){
        String s1 = "学生会主席职务，实习经验6个月，校三好学生奖 ";
        String s2 = "支书职务，实习经验7个月，校奖学金奖 ";
        String s3 = "生活委员职务，实习经验8个月，国家奖学金奖  ";
        String s4 = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。 ";

        //普通分词
        long start = System.currentTimeMillis();
        List<Term> termList = StandardTokenizer.segment(s1);
        System.out.println(termList);

        //关键词提取
        List<String> keywordList = HanLP.extractKeyword(s1, 5);
        System.out.println(keywordList);

        //短语提取（1）
        List<String> phraseList = HanLP.extractPhrase(s1, 10);
        System.out.println(phraseList);

        //文本推荐
        Suggester suggester = new Suggester();
        String[] titleArray =
                (
                        "威廉王子发表演说 呼吁保护野生动物\n" +
                                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
                                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
                                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
                                "英报告说空气污染带来“公共健康危机”"
                ).split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }

        System.out.println(suggester.suggest("发言", 1));       // 语义

        System.out.println(suggester.suggest("发言", 1));       // 语义
        System.out.println(suggester.suggest("危机公共", 1));   // 字符
        System.out.println(suggester.suggest("mayun", 1));      // 拼音





    }

    //HanLp短语分词
    public List<String> participlesByHanLPExtractPhrase(String s){
        return HanLP.extractPhrase(s, 5);
    }

    //标点符号分词（专业/岗位名）
    public List<String> participlesByPunctuation(String s){
        return Arrays.asList(s.split("、|，|。|；|？|！|,|\\.|;|\\?|!|]"));
    }

    //结巴普通分词（个人陈述）
    public List<String> participlesByJiebaSimple(String s){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        return segmenter.sentenceProcess(s);
    }


    public double get_similarity(List<String> PhraseList1,List<String> PhraseList2){
        System.out.println("PhraseList1 :"+PhraseList1);
        System.out.println("PhraseList2 :"+PhraseList2);

        //合并分词结果
        List<String> resultMergeList = new ArrayList<String>();
        resultMergeList.addAll(PhraseList1);
        resultMergeList.addAll(PhraseList2);
        System.out.println("resultMergeList :"+resultMergeList);

        //去掉重复元素
        for  ( int  i  =   0 ; i  <  resultMergeList.size()  -   1 ; i ++ )  {
            for  ( int  j  =  resultMergeList.size()  -   1 ; j  >  i; j -- )  {
                if  (resultMergeList.get(j).equals(resultMergeList.get(i)))  {
                    resultMergeList.remove(j);
                }
            }
        }
        System.out.println("resultMergeList2 :"+resultMergeList);

        //计算词频
        int[] resultStatistic1 = new int[resultMergeList.size()];
        System.out.println("resultStatistic1 :1111");
        for (int i = 0; i < resultMergeList.size(); i++) {
            System.out.println("resultStatistic1[] :"+ resultStatistic1[i]);
            resultStatistic1[i] = Collections.frequency(PhraseList1, resultMergeList.get(i));
            System.out.println("resultStatistic1[] :"+ resultStatistic1[i]);
        }
        int[] resultStatistic2 = new int[resultMergeList.size()];
        for (int i = 0; i < resultMergeList.size(); i++) {
            resultStatistic2[i] = Collections.frequency(PhraseList2,  resultMergeList.get(i));
        }
        System.out.print("[");
        for (int i = 0; i < resultStatistic1.length; i++) {
            System.out.print(resultStatistic1[i]+",");
        }
        System.out.print("]");System.out.println();
        System.out.print("[");
        for (int i = 0; i < resultStatistic2.length; i++) {
            System.out.print(resultStatistic2[i]+",");
        }
        System.out.print("]");

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
        System.out.println("余弦值："+similarity);
        return similarity;
    }

    @Test
    public void  test_hanlp(){
//        String s1 = "工业工程";
//        String s2 ="土木工程";
//        String s3 = "软件工程";
//        String s4 ="通信工程";
//        String s ="软件工程，计算机科学与技术，通信工程，电信工程";

//        String s1 = "工业HR，人事管理，技术总监,工程";
//        String s2 ="财务主管，人事管理，项目经理";
//        String s3 = "内控经理，财务主管，HR";
//        String s4 ="pythen工程师，开发工程师";
//        String s ="pythen工程师";

//        String s1 = "本人在字节不懂公司实习2年，在学校担任团支部书记职务，在机器人比赛获得金奖，爱好打篮球，善于交际\n";
//        String s2 ="本人在千达公司实习3年，在学校担任班长职务，在机器人比赛获得金奖，爱好打篮球，善于交际\n";
//        String s3 = "本人在华夏公司实习1年，在学校担任班长职务，在机器人比赛获得金奖，爱好踢足球，善于交际\n";
//        String s4 ="在机器人比赛获得金奖，爱好踢足球，";
//        String s ="担任班长、副班长、学习委员、体育委员、劳动委员、生活委员、纪律委员、文艺委员、心理委员、团支部书记、团支书、组织委员、宣传委员、通讯委员、主任、部长、社长、组长职务、奖学金、一等奖、二等奖、三等将、特等奖、国奖、省奖、国际奖、奖、金奖、银奖、铜奖、第一名、第二名、第三名、第四名、证书、专利、软件著作权、大赛、竞赛、比赛、联赛、获得、取得、荣获、获取、工作经验、实习经验、开发经验、实际经验、丰厚经验";

        String s1 = "软件工程，计算机科学与技术，通信工程，电信工程";
        String s2 ="汽车工程，车辆结构";
        String s3 = "沈阳省绵竹市";
        String s4 ="北京市，上海市";
        String s ="沈阳省大连市";
        List<String> strings1 = participlesByJiebaSimple(s1);
        List<String> strings2 = participlesByJiebaSimple(s2);
        List<String> strings3 = participlesByJiebaSimple(s3);
        List<String> strings4 = participlesByJiebaSimple(s4);
        List<String> strings = participlesByJiebaSimple(s);

        System.out.println(get_similarity(strings1, strings));
        System.out.println(get_similarity(strings2, strings));
        System.out.println(get_similarity(strings3, strings));
        System.out.println(get_similarity(strings4, strings));



    }

    @Test
    public void test_file()throws Exception{
        String basicPath = System.getProperty("user.dir");
        String frontedPath = "/Volumes/TOSHIBA EXT/Laner/大实习/vue-post-match/public/file/";
        log.debug(basicPath);

        //--------------读xls--------------
        String pathR = basicPath + "/src/main/resources/static.files/ResumeTemplate.xls";

        int sid = 2;
        String fileName = studentService.getStudent(sid).getName() + "简历";
//        String pathW = basicPath + "/src/main/resources/static.files/"+fileName+".xls";
        String pathW = frontedPath+fileName+".xls";

        Workbook wb = Workbook.getWorkbook(new File(pathR));
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



    }
}
