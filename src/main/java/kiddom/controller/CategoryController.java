package kiddom.controller;

import kiddom.model.CategoriesEntity;
import kiddom.model.SubcategoriesEntity;
import kiddom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /*******************************************May be usefull***************************************************************/

    private List<SubcategoriesEntity> manageSubCategories(CategoriesEntity category) {
        // Store the categories which shouldn't be persisted
        List<SubcategoriesEntity> categories2remove = new ArrayList<SubcategoriesEntity>();
//        if (category.getSubcategoriesByCatId() != null) {
//            for (Iterator<SubcategoriesEntity> i = category.getSubcategoriesByCatId().iterator(); i.hasNext();) {
//                SubcategoriesEntity subcat = i.next();
//                // If the remove flag is true, remove the employee from the list
//                if (subcat.getRemove() == 1) {
//                    categories2remove.add(subcat);
//                    i.remove();
//                    // Otherwise, perform the links
//                } else {
//                    subcat.setCategoriesByCatId(category);
//                    //subcat.setCategory_name(category.getName());
//                }
//            }
//        }
        return categories2remove;
    }

    // -- Creating a new category ----------

    @RequestMapping(value = "/category_submit", method = RequestMethod.GET)
    public String create(@ModelAttribute CategoriesEntity category, Model model) {
        model.addAttribute("categories",categoryService.getCategoriesNames());
        // Should init the AutoPopulatingList
       // category.setSubcategoriesByCatId(new AutoPopulatingList<SubcategoriesEntity>(SubcategoriesEntity.class));

        //return create(category, model, true);
        return "category_submit";
    }

    private String create(CategoriesEntity category, Model model, boolean init) {
        if (init) {
            // Init the AutoPopulatingList

        }
        model.addAttribute("type", "create");
        return "redirect:/category_submit";
    }

    @RequestMapping(value = "category_submit", method = RequestMethod.POST)
    public ModelAndView create(@Valid @ModelAttribute("categories") CategoriesEntity category, @Valid @ModelAttribute("subcategories") SubcategoriesEntity sub, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();

        /* ---------------Dokimastikes ektiposeis ---------------*/
        System.out.println("Prospathisa na kano eisagogi");
        System.out.println("Pira thn katigoria : " + category.getName());
        System.out.println("Pira to subname: " + sub.getSubName());
        if (sub.getPk().getCategoryName()!=null)
            System.out.println("H katigoria einai: " + sub.getPk().getCategoryName().getName());
        else
            System.out.println("Einai null");
        /*-----------------------------------------------------------------*/

        if (categoryService.getCategoryByName(category.getName()) == null){
            categoryService.saveCategory(category);
            model.addObject("error", "Δεν υπήρχε κατηγορια με αυτό το όνομα");
            //return model;
        }
        categoryService.saveSubCategory(category,sub);

        return model;
    }

    // -- Updating an existing employer ----------

    @RequestMapping(value = "update/{pk}", method = RequestMethod.GET)
    public String update(@PathVariable Integer pk, @ModelAttribute CategoriesEntity category, Model model) {
        // Add your own getEmployerById(pk)
        model.addAttribute("type", "update");
        return "category_submit";
    }

    @RequestMapping(value = "update/{pk}", method = RequestMethod.POST)
    public String update(@PathVariable Integer pk, @Valid @ModelAttribute CategoriesEntity category, BindingResult bindingResult, Model model) {
        // Add your own getEmployerById(pk)
        if (bindingResult.hasErrors()) {
            return update(pk, category, model);
        }
        List<SubcategoriesEntity> subcategories2remove = manageSubCategories(category);
        // First, save the employer
        categoryService.update(category);
        // Then, delete the previously linked employees which should be now removed
//        for (SubcategoriesEntity subcat : subcategories2remove) {
//            if (subcat.getSubName() != null) {
//                userService.delete(subcat);
//            }
//        }
        return "redirect:category_submit" + category.getName();
    }

    // -- Show an existing employer ----------

    @RequestMapping(value = "show/{pk}", method = RequestMethod.GET)
    public String show(@PathVariable Integer pk, @ModelAttribute CategoriesEntity category) {
        // Add your own getEmployerById(pk)
        return "category_submit";
    }

    /*	@RequestMapping(value="/category_submit", method = RequestMethod.GET)
	public ModelAndView category_submit(@ModelAttribute("user") @Valid UserEntity user){
		ModelAndView modelAndView = new ModelAndView();
		//UserEntity user = new UserEntity();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("category_submit");
		return modelAndView;
	}*/

	/*@RequestMapping(value="/category_submit", method = RequestMethod.POST)
	public ModelAndView category_create(@ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("category") @Valid CategoriesEntity category, @ModelAttribute @Valid List<SubcategoriesEntity> subcategories, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		//UserEntity user = new UserEntity();
		modelAndView.addObject("user", user);
		CategoriesEntity cat=userService.findByName(category.getName());
		if(cat!=null){
            bindingResult
                    .rejectValue("category", "error.user",
                            "There is already a category registered with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user",user);
            modelAndView.setViewName("redirect:/error_page");
        } else {
            userService.saveCategory(category);
            userService.saveSubCategory(category, subcategories);
            //userRepository.saveUser(user);
            modelAndView.addObject("successMessage", "Cta has been registered successfully");
            modelAndView.addObject("user", user);
            //modelAndView.addObject("parent",parent);
            modelAndView.setViewName("redirect:/index");
        }


         //   modelAndView.setViewName("category_creat");
		return modelAndView;
	}*/
}
