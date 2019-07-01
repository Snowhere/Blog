package me.snowhere.service;

import me.snowhere.dao.DataDao;
import me.snowhere.datasource.DataSource;
import me.snowhere.datasource.DataSourceEnum;
import me.snowhere.dto.StudentQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    private DataDao dataDao;
    /***
     * @Description:服务期内学员
     * @author suntenghao
     * @date 2019-05-07 17:25
     */
    @DataSource(DataSourceEnum.MASTER)
    public List<Integer> getAllStudent() {
        return dataDao.getAllStudent();
    }

    /***
     * @Description:单表学员做题数量
     * @author suntenghao
     * @date 2019-05-07 17:25
     */
    @DataSource(DataSourceEnum.SLAVE)
    public List<StudentQuestion> getQuestions(Integer index) {
        return dataDao.getStudentQuestions(index);
    }

}
