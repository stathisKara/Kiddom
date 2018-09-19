package kiddom.service;

/**
 * Created by Stathis on 6/12/2017.
 */
import kiddom.model.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("categoryService")
public interface CategoryService {

    /*-----------------------CRUD methods for categories----------------------*/

    List<CategoriesEntity> getCategories();
    List<String> getCategoriesNames();
    CategoriesEntity getCategoryByName(String name);

    void saveCategory(CategoriesEntity category);
    void saveCategory(List<CategoriesEntity> categories);

    void update(CategoriesEntity category);


    /*-----------------------CRUD methods for subcategories----------------------*/

    SubcategoriesEntity getSubCategoryByCategory(String category, String subcategory);
    List<String> getSubCategoryNamesByCategory(String category);
    List<List<String>> getALLSubCategoryNamesByCategory();
    void saveSubCategory(CategoriesEntity category, List<SubcategoriesEntity> subcategory);
    void saveSubCategory(CategoriesEntity category, SubcategoriesEntity subcategory);

    void delete(SubcategoriesEntity subcategory);





}

