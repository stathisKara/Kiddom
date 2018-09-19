package kiddom.service;

import kiddom.model.ParentEntity;
import kiddom.model.ParentReportsEntity;
import kiddom.model.ParentReportsEntity;
import kiddom.model.UserEntity;
import kiddom.repository.ParentReportRepository;
import kiddom.repository.ProgramRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stathis on 7/5/2017.
 */
@Service("parentReportsService")
public class ParentReportsServiceImpl implements ParentReportsService {

    @Qualifier("parentReportsRepo")
    @Autowired
    private kiddom.repository.ParentReportRepository parentReportRepository;

    @Override
    public void saveParentReport(ParentReportsEntity report){
        parentReportRepository.save(report);
    }

    @Override
    public List<ParentReportsEntity> getReportsByUser(ParentEntity user){
        return  parentReportRepository.getParentReportsEntityByParent(user);
    }

    @Override
    public List<ParentReportsEntity> getAll(){
        return parentReportRepository.findAll();
    }
}
