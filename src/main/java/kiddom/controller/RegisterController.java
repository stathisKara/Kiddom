package kiddom.controller;

import kiddom.authentication.IAuthenticationFacade;
import kiddom.model.ParentEntity;
import kiddom.model.ParentPK;
import kiddom.model.ProviderEntity;
import kiddom.model.UserEntity;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by Arianna on 6/6/2017.
 */
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";


    /*---------------------------------Parent Register------------------------------*/
    /*-- Get --*/


    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView register(RedirectAttributes redirectAttributes,Model model){
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = new UserEntity("");
        ParentEntity parent = new ParentEntity();
        redirectAttributes.addFlashAttribute("success",false);
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser"))
            modelAndView.addObject("uname", authentication.getName());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("files", file.listFiles());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    /*-- Post --*/
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("parent") @Valid ParentEntity parent, BindingResult bindingResult, RedirectAttributes redirectAttributes,@RequestParam("uploadingFile") MultipartFile uploadingFile) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userExists = userService.findByUsername(user.getUsername());
        if (userExists != null) {
           /* bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {*/
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register");
        } else {

            if (!uploadingFile.isEmpty()) {
                File file = new File(uploadingdir + uploadingFile.getOriginalFilename());

                userService.saveUser(user, parent, "/image/" + uploadingFile.getOriginalFilename());
                uploadingFile.transferTo(file);
            }
            else
                userService.saveUser(user, parent,"");


            Authentication authentication = authenticationFacade.getAuthentication();
            System.out.println("Authentication name is"+authentication.getName());
            System.out.println("edv eimai re me onoma");
            if(!authentication.getName().equals("anonymousUser")) {
                modelAndView.addObject("uname", authentication.getName());
                UserEntity userS = userService.findByUsername(authentication.getName());
                modelAndView.addObject("type", String.valueOf(userS.getType()));
            }

            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }




    /*------------------------------Provider Register-----------------------------*/
    /*-- Get --*/
    @RequestMapping(value="/register_prov", method = RequestMethod.GET)
    public ModelAndView register_prov(RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        UserEntity user = new UserEntity("");
        ProviderEntity provider = new ProviderEntity();
        redirectAttributes.addFlashAttribute("success",false);
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is" + authentication.getName());
        if(!authentication.getName().equals("anonymousUser"))
            modelAndView.addObject("uname",authentication.getName());
        modelAndView.setViewName("register_prov");
        return modelAndView;
    }

    /*-- Post --*/
    @RequestMapping(value = "/register_prov", method = RequestMethod.POST)
    public ModelAndView createNewUserProvider(@ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("provider") @Valid ProviderEntity provider, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userExists = userService.findByUsername(user.getUsername());
        if (userExists != null)
        {
         /*   bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user",user);*/
            redirectAttributes.addFlashAttribute("success","false");
            modelAndView.setViewName("redirect:/register_prov");
        }
        else
        {
            userService.saveUserProvider(user,provider);
            //userRepository.saveUser(user);
            Authentication authentication = authenticationFacade.getAuthentication();
            System.out.println("Authentication name is"+authentication.getName());
            if(!authentication.getName().equals("anonymousUser"))
            {
                modelAndView.addObject("uname", authentication.getName());
                UserEntity userS = userService.findByUsername(authentication.getName());
                modelAndView.addObject("type", String.valueOf(userS.getType()));
            }
            redirectAttributes.addFlashAttribute("success","true");
            modelAndView.setViewName("redirect:/register_prov");
        }
        return modelAndView;
    }
}
