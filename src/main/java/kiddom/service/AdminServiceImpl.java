package kiddom.service;

import kiddom.model.ProviderEntity;
import kiddom.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Arianna on 2/7/2017.
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService{
    @Qualifier("providerRepository")
    @Autowired
    private ProviderRepository providerRepository;



    @Transactional
    @Override
    public Page<ProviderEntity> findAllPageable(Pageable pageable) {
        return providerRepository.findAllByApproved(0,pageable);
    }
}

