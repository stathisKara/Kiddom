package kiddom.controller;

/**
 * Created by fotis on 7/3/2017.
 */

import kiddom.authentication.IAuthenticationFacade;
import kiddom.model.ParentEntity;
import kiddom.model.ParentPK;
import kiddom.model.UserEntity;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class FileUploadController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";


    private static final File updir = new File(uploadingdir );
    private static final String updirAbsolutePath = updir.getAbsolutePath() + File.separator;

    private static final String FAILED_UPLOAD_MESSAGE = "You failed to upload [%s] because the file because %s";
    private static final String SUCCESS_UPLOAD_MESSAGE = "You successfully uploaded file = [%s]";


    @RequestMapping("/upload")
    public ModelAndView uploading(Model model) {
        File file = new File(uploadingdir);
        model.addAttribute("files", file.listFiles());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/buypoints");
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("parent") @Valid ParentEntity parent) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            ParentEntity parenton = userService.findParent(new ParentPK(authentication.getName()));
            UserEntity useron = userService.findByUsername(authentication.getName());
            String direct = "C:/Users/fotis/IdeaProjects/war/src/main/resources/static/images/";
            for (MultipartFile uploadedFile : uploadingFiles) {

                File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
                userService.uploadPhoto(parenton,parent,useron,user,"/image/" + uploadedFile.getOriginalFilename());
                uploadedFile.transferTo(file);
            }
        }
        modelAndView.setViewName("redirect:/buypoints");
        return modelAndView;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(@RequestParam("name") String name,
                                          @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();

        if (file.isEmpty()) {
            modelAndView.addObject("message", String.format(FAILED_UPLOAD_MESSAGE, name, "file is empty"));
        } else {
            createImagesDirIfNeeded();
            modelAndView.addObject("message", createImage(name, file));
        }

        modelAndView.setViewName("redirect:/buypoints");
        return modelAndView;
    }

    private void createImagesDirIfNeeded() {
        if (!updir.exists()) {
            updir.mkdirs();
        }
    }

    private String createImage(String name, MultipartFile file) {
        try {
            File image = new File(updirAbsolutePath + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image));
            stream.write(file.getBytes());
            stream.close();

            return String.format(SUCCESS_UPLOAD_MESSAGE, name);
        } catch (Exception e) {
            return String.format(FAILED_UPLOAD_MESSAGE, name, e.getMessage());
        }
    }

    @RequestMapping(value = "/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
        createImagesDirIfNeeded();

        File serverFile = new File(updirAbsolutePath + imageName + ".jpg");

        return Files.readAllBytes(serverFile.toPath());
    }

}