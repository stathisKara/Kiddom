package kiddom.service;

import kiddom.model.ProviderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Arianna on 2/7/2017.
 */
@Service("adminService")
public interface AdminService {
    Page<ProviderEntity> findAllPageable(Pageable pageable);
}
