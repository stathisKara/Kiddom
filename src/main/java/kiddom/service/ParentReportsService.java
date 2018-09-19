package kiddom.service;

import kiddom.model.ParentEntity;
import kiddom.model.ParentReportsEntity;
import kiddom.model.UserEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stathis on 7/5/2017.
 */
@Service("parentReportsService")
public interface ParentReportsService {
    public void saveParentReport(ParentReportsEntity report);
    public List<ParentReportsEntity> getReportsByUser(ParentEntity user);
    public List<ParentReportsEntity> getAll();
}
