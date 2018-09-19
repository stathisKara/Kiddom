package kiddom.service;

import kiddom.model.AreasEntity;
import kiddom.model.TownsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Stathis on 7/6/2017.
 */
@Service("townService")
public interface TownsService {

    List<List<AreasEntity>> GetAllAreasByCategory();
    List<AreasEntity> areasByTown(String town);
    List<TownsEntity> GetAllTowns();
}
