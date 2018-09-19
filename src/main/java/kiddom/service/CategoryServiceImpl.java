package kiddom.service;


import kiddom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import kiddom.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.List;

/**
 * Created by Stathis on 6/12/2017.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Qualifier("categoryRepository")
    @Autowired
    private CategoryRepository categoryRepository;

    @Qualifier("subcategoryRepository")
    @Autowired
    private SubCategoryRepository subcategoryRepository;

    /*-----------------------CRUD methods for categories----------------------*/

    @Override
    public List<CategoriesEntity> getCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public CategoriesEntity getCategoryByName(String name) {
        return categoryRepository.findByName(name);
        //return null;
    }

    @Override
    public void saveCategory(CategoriesEntity category)
    {
        System.out.print("Trying to save category :"+category.getName());
        categoryRepository.save(category);
    }

    @Override
    public void saveCategory(List <CategoriesEntity> categories){

    }

    @Override
    public List<String> getCategoriesNames() {
        List<CategoriesEntity> list=categoryRepository.findAll();
        List<String> cat_return=new ArrayList<String>();
        for(CategoriesEntity cat:list)
        {
            cat_return.add(cat.getName());
        }
        return cat_return;
    }

    public void update(CategoriesEntity category)
    {
        categoryRepository.save(category);
    }

     /*-----------------------CRUD methods for subcategories----------------------*/

     @Override
     public SubcategoriesEntity getSubCategoryByCategory(String category, String subcategory){
        return null;
     }

    @Override
    public List<String> getSubCategoryNamesByCategory(String category)
    {
        List<SubcategoriesEntity> list=subcategoryRepository.findAll();
        List<String> subcat_return=new ArrayList<String>();
        for(SubcategoriesEntity sub:list)
        {
            if((sub.getCategory().getName()).equals(category)){
                subcat_return.add(sub.getSubName());
                System.out.println(category+" -> "+sub.getSubName());
            }

        }
        return subcat_return;
    }

    @Override
    public List<List<String>> getALLSubCategoryNamesByCategory()
    {
        List<String> cats=getCategoriesNames();
        List<List<String>> ret=new ArrayList<>();
        for (String category:cats)
        {
            ret.add(getSubCategoryNamesByCategory(category));
        }
        return ret;
    }


    @Override
    public void saveSubCategory(CategoriesEntity category, List<SubcategoriesEntity> subcategory)
    {
        categoryRepository.save(category);
        System.out.print("Trying to save category :"+category.getName());
        for(SubcategoriesEntity sub :subcategory) {
            //
            //sub.setCategory_name(category.getName());
            //sub.setCategoriesByCatId(category);
        }
        subcategoryRepository.save(subcategory);
        //category.setSubcategoriesByCatId(subcategory);
        categoryRepository.saveAndFlush(category);
    }


    @Override
    public void saveSubCategory(CategoriesEntity category, SubcategoriesEntity subcategory){
         subcategory.setCategory(category);
         subcategoryRepository.save(subcategory);
    }

    @Override
    public  void delete(SubcategoriesEntity subcat){
        subcategoryRepository.delete(subcat);
    }

}
