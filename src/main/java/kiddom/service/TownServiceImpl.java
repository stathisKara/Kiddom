package kiddom.service;

import kiddom.model.AreasEntity;
import kiddom.model.SubcategoriesEntity;
import kiddom.model.TownsEntity;
import kiddom.repository.AreaRepository;
import kiddom.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stathis on 7/6/2017.
 */
@Service("townService")
public class TownServiceImpl implements TownsService{
    @Autowired
    @Qualifier("areaRepository")
    AreaRepository areaRepository;

    @Autowired
    @Qualifier("townRepository")
    TownRepository townRepository;

    @Override
    public List<TownsEntity> GetAllTowns(){
        return townRepository.findAll();
    }

    @Override
    public List<AreasEntity> areasByTown(String town){
        List<AreasEntity> allAreas = areaRepository.findAll();
        List<AreasEntity> ret_areas = new ArrayList<>();
        for (AreasEntity area: allAreas){
            if (area.getPk().getTownName().getName().equals(town))
                ret_areas.add(area);
        }
        return ret_areas;
    }

    @Override
    public List<List<AreasEntity>> GetAllAreasByCategory(){
        List<TownsEntity> towns = GetAllTowns();
        List<List<AreasEntity>> areas = new ArrayList<>();
        for (TownsEntity town: towns){
            areas.add(areasByTown(town.getName()));
        }
        return areas;
    }

}
