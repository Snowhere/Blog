package me.snowhere;

import me.snowhere.dto.StudentQuestion;
import me.snowhere.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class DataTest {

    @Autowired
    private DataService dataService;

    @Test
    public void getDetail() {
        List<Integer> studentIds = dataService.getAllStudent();
        List<StudentQuestion> questions = dataService.getQuestions(88);
        System.out.println(studentIds.size());
        System.out.println(questions.size());
        int sum=0;
        for (StudentQuestion question : questions) {
            if (!studentIds.contains(question.getStudentId())) {
                sum+=question.getQuestionNum();
            }
        }
        System.out.println(sum);
    }
}
