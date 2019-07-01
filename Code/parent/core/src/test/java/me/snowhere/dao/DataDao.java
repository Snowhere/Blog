package me.snowhere.dao;

import me.snowhere.dto.StudentQuestion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDao {
    @Select({"SELECT distinct o.stu_id FROM ent_stu_service_period p LEFT JOIN ent_ord_details d on p.ord_detail_id=d.id LEFT JOIN ent_order o on d.ord_id=o.id ",
            " where p.service_end_date>now() "})
    List<Integer> getAllStudent() ;

    @Select({"select count(1) questionNum, stu_id studentId from t_tiku_user_question_#{index} GROUP BY stu_id"})
    List<StudentQuestion> getStudentQuestions(@Param("index") Integer index);
}
