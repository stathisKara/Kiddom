package kiddom.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import kiddom.authentication.IAuthenticationFacade;
import kiddom.model.*;
import kiddom.service.*;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Arianna on 6/6/2017.
 */
@Controller
public class   ActivityController {

    public class ShowComment
    {
        private String username;
        private String comment;
        private Float rate;
        public String getUsername() {return username;}
        public String getComment() {return comment;}
        public Float getRate() {return rate;}
        public ShowComment(String username,String comment,Float rate) {this.username = username;
        this.rate=rate;this.comment=comment;}
    }

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ProgramService programService;

    @Qualifier("townService")
    @Autowired
    private TownsService townsService;

    @Autowired
    private ReservationService reservationService;

    @Qualifier("categoryService")
    @Autowired
    private CategoryService categoryService;
    public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

    @RequestMapping(value="/activity/{eventID}", method = RequestMethod.GET)
    public ModelAndView activityshow(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @PathVariable int eventID){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is"+authentication.getName());
        if(!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UserEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }
        //Integer eventID_ = Integer.parseInt(eventID);
        SingleEventEntity event = eventService.findSingleEventById(eventID);
        Set<ProgramEntity> program= eventService.findProgram(eventID);
        Set<CommentsEntity> comment =event.getComment_parent();
        modelAndView.addObject("event", event);
        List<String> photos1 = new ArrayList<>();
        List<String> photos2 = new ArrayList<>();
        List<String> photos3 = new ArrayList<>();
        String[] photos = null;
        if (event.getPhotos() != null) {
            photos = event.getPhotos().split(";");
            int i = 0;
            for (String photo : photos) {
                if (i < 2)
                    photos1.add(photos[i]);
                else if (i < 5)
                    photos2.add(photos[i]);
                else
                    photos3.add(photos[i]);
                i+=1;
                System.out.println("exw pragmata" + photo);

            }
            modelAndView.addObject("photos", photos);
            modelAndView.addObject("photos1", photos1);
            modelAndView.addObject("photos2", photos2);
            modelAndView.addObject("photos3", photos3);
        }
        Set<ShowComment> comments=new HashSet<>();
        for (CommentsEntity com:comment){
            ShowComment showComment=new ShowComment(com.getParent_username().getParentUsername(),com.getComment(),com.getRating());
            comments.add(showComment);
        }
        float finalRating = event.getRatings_sum()/event.getRatings_num();
        if (event.getRatings_num() == 0) {
            modelAndView.addObject("numOfRatings", 0);
        }
        else {
            modelAndView.addObject("numOfRatings", 1);
        }
        modelAndView.addObject("rating", finalRating);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("program", program);
        modelAndView.setViewName("/activity");
        return modelAndView;
    }

    @RequestMapping(value = "/reservation_cancel/{slotID}", method = RequestMethod.POST)
    public ModelAndView reservation_cancelation(@ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("parent") @Valid ParentEntity parent, @PathVariable("slotID") int slotID) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.addObject("uname", authentication.getName());
        UserEntity userS = userService.findByUsername(authentication.getName());
        ParentPK parentPK = new ParentPK(authentication.getName());
        ParentEntity parentLocal = userService.findParent(parentPK);
        ReservationsEntity reservation = reservationService.getReservationsEntityById(slotID);
        SingleEventEntity event = eventService.findSingleEventById(reservation.getTimeslot_id().getEvent().getId());
        System.out.println("Event is " + event.getId());
        ProviderPK providerPK = new ProviderPK(event.getProviders().getPk().getUser().getUsername());
        System.out.println("Provider pk " + providerPK.getUser().getUsername());
        ProviderEntity provider = userService.findProvider(providerPK);
        ProgramEntity slot = reservation.getTimeslot_id();
        System.out.println("Reservation " + reservation.getReservation_id());
        programService.cancelReservation(provider, parentLocal, reservation, slot);
        modelAndView.addObject("parent", parentLocal);
        modelAndView.addObject("user", userS);
        modelAndView.setViewName("/profile");
        return modelAndView;
    }

    @RequestMapping(value="/comment/{eventID}", method = RequestMethod.POST)
    public ModelAndView comment(@ModelAttribute("user") @Valid UserEntity user, @PathVariable String eventID, @RequestParam("comment") String Comment, @RequestParam("rate") float rate){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        modelAndView.addObject("uname", authentication.getName());
        UserEntity userS = userService.findByUsername(authentication.getName());
        modelAndView.addObject("type", String.valueOf(userS.getType()));
        System.out.println("Eimai o gonios "+ userS.getUsername()+"thelw n prosthesw ena sxolio ,to"+Comment);
        Integer eventID_ = Integer.parseInt(eventID);
        SingleEventEntity event = eventService.findSingleEventById(eventID_);
        ParentEntity parenton = userService.findParent(new ParentPK(authentication.getName()));
        CommentsEntity commentsEntity=new CommentsEntity();
        commentsEntity.setComment(Comment);
        float rating = event.getRatings_sum() + rate;
        event.setRatings_sum(rating);
        event.setRatings_num(event.getRatings_num() + (float)1);
        eventService.addComment(parenton,commentsEntity,event);
        System.out.println("to prosthesa");
        float finalRating = event.getRatings_sum()/event.getRatings_num();
        modelAndView.addObject("rating", finalRating);
        modelAndView.setViewName("redirect:/activity/"+eventID);
        return modelAndView;
    }

    @RequestMapping(value="/reserve/{programID}", method = RequestMethod.POST)
    public ModelAndView reservation(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @PathVariable("programID") int programID, @RequestParam("num") int slots){
        ModelAndView modelAndView = new ModelAndView();
        //System.out.println("bika st reservation");
        Authentication authentication = authenticationFacade.getAuthentication();
       //modelAndView.addObject("uname", authentication.getName());
        ProgramEntity program = programService.getProgramById(programID);
        if(authentication.getName().equals("anonymousUser"))
        {
            modelAndView.addObject("availability", 0);
            modelAndView.setViewName("redirect:/activity/"+program.getEvent().getId());
            return modelAndView;
        }
        UserEntity userS = userService.findByUsername(authentication.getName());
        modelAndView.addObject("type", String.valueOf(userS.getType()));
        if(user.getType()==2)
        {
            modelAndView.addObject("availability", 1);
            modelAndView.setViewName("redirect:/activity/"+program.getEvent().getId());
            return modelAndView;
        }
        System.out.println(provider.getName()+ provider.getUsername());
        System.out.println("\n\n\nAvailability is " + program.getAvailability() + " for program " + program.getId() + "\nfor " + slots);

        if (program.getAvailability() < slots){
            modelAndView.addObject("availability", 2);
            modelAndView.setViewName("redirect:/activity/"+program.getEvent().getId());
            return modelAndView;
        }
        ParentEntity parent = userService.findParent(new ParentPK(userS.getUsername()));
        SingleEventEntity event = eventService.findSingleEvent(program.getEvent());
        if(parent.getAvailPoints()<event.getPrice()*slots)
        {
            modelAndView.addObject("availability", 3);
            modelAndView.setViewName("redirect:/activity/"+program.getEvent().getId());
            return modelAndView;
        }

//        System.out.println(user.getUsername());

        ReservationsEntity reservation = new ReservationsEntity();
        reservation.setParent(parent);
        reservation.setTimeslot_id(program);
        reservation.setSlots(slots);



//        ParentEntity parenton = userService.findParent(new ParentPK(authentication.getName()));
//        ProgramEntity programEntity=programService.getProgramById(Integer.parseInt(programID)); //slot of event
//        ProviderEntity providerEntity=  programEntity.getEvent().getProviders(); //provider of event

        programService.makeReservation(reservation, program.getEvent().getProviders(), parent, program);
        modelAndView.setViewName("redirect:/activity/"+program.getEvent().getId());
        System.out.println("vgainw apo to st reservation");
        return modelAndView;
    }


    @RequestMapping(value="/activity_reg", method = RequestMethod.GET)
    public ModelAndView activity_register(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();

        UserEntity userExists = userService.findByUsername(user.getUsername());
        ProviderPK providerPk = new ProviderPK(authentication.getName());
        ProviderEntity prov = userService.findProvider(providerPk);
        SingleEventEntity event = new SingleEventEntity();
        ProgramEntity daily_program = new ProgramEntity();
        HashSet<ProgramEntity> program = new HashSet<>();

        System.out.println("Authentication name is" + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UserEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
            redirectAttributes.addFlashAttribute("success","true");
        }
        else {
            modelAndView.setViewName("redirect:/error?error_code=anon");
            return modelAndView;
        }
        System.out.println("Provider status " + prov.getApproved() + " prov: " + prov.getUsername());
        modelAndView.addObject("is_approved", prov.getApproved());
        modelAndView.addObject("categories",categoryService.getCategoriesNames());
        modelAndView.addObject("subcategories",categoryService.getALLSubCategoryNamesByCategory());
        System.out.println("\n\n\n\n"+townsService.GetAllTowns());
        modelAndView.addObject("towns", townsService.GetAllTowns());
        modelAndView.addObject("areas", townsService.GetAllAreasByCategory());

        modelAndView.setViewName("/activity_reg");
        return modelAndView;
    }

    @RequestMapping(value="/activity_reg", method = RequestMethod.POST)
    public ModelAndView activity_creation(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, @ModelAttribute("program") @Valid ProgramEntity daily_program, HashSet<ProgramEntity> program, BindingResult bindingResult, RedirectAttributes redirectAttributes,@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name (provider) is " + authentication.getName());
        event.setCategory("Temp Category");
        if (!authentication.getName().equals("anonymousUser")) {
            if (event.getNumOfSlots() == 0) {
                System.out.println("No slots");
                event.setNumOfSlots(1);
                program.add(daily_program);
            }
            else if (event.getNumOfSlots() > 1) {
                String startTime = daily_program.getStartTime();
                int numOfSlots = event.getNumOfSlots();
                int slotDuration = event.getSlotDuration();
                int price = daily_program.getPrice();
                int availability = daily_program.getAvailability();
                daily_program.setCapacity(daily_program.getAvailability());
                int capacity = daily_program.getCapacity();

                String timeValue = startTime;
                System.out.println("Time value is " + timeValue);
                DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter();
                LocalTime startTimeL = LocalTime.parse(timeValue, parseFormat);
                System.out.println(startTimeL);

                int hours = slotDuration/60;
                int minutes = slotDuration%60;

                LocalTime lastEndTime = startTimeL;

                for (int i = 0; i < numOfSlots; ++i) {
                    ProgramEntity newProgram = new ProgramEntity();
                    newProgram.setCanceled(0);
                    newProgram.setAvailability(availability);
                    newProgram.setCapacity(capacity);
                    newProgram.setPrice(price);
                    newProgram.setDate(daily_program.getDate());
                    System.out.println("Last end time is " + lastEndTime);
                    String finalStartTime = lastEndTime.toString();
                    String finalST = finalStartTime.substring(0, 5);
                    newProgram.setStartTime(finalST);
                    LocalTime endTime = lastEndTime.plusMinutes(minutes);
                    endTime = endTime.plusHours(hours);
                    String finalEndTime = endTime.toString();
                    String finalET = finalEndTime.substring(0, 5);
                    newProgram.setEndTime(finalET);
                    program.add(newProgram);
                    lastEndTime = endTime;
                }
                for (ProgramEntity p : program) {
                    System.out.println("Program with start " + p.getStartTime() + " end " + p.getEndTime());
                }
            }
            else {
                event.setNumOfSlots(1);
                program.add(daily_program);
            }

            modelAndView.addObject("uname", authentication.getName());
            ProviderEntity provideron = userService.findProvider(new ProviderPK(authentication.getName()));
            UserEntity useron = userService.findByUsername(authentication.getName());
            ProviderPK provideronPK = new ProviderPK(authentication.getName());
            provideron = userService.findProvider(provideronPK);
            modelAndView.addObject("user", provideronPK.getUser());
            modelAndView.addObject("provider", provideron);
            String[] files = new String[uploadingFiles.length];
            int count=0;
            if (!uploadingFiles[0].isEmpty()) {
                for (MultipartFile uploadedFile : uploadingFiles) {


                    File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
//                    userService.uploadPhoto(parenton, parent, useron, user, "/image/" + uploadedFile.getOriginalFilename());
                    files[count] = "/image/" + uploadedFile.getOriginalFilename();
                    uploadedFile.transferTo(file);
                    ++count;
                }
            }
            System.out.println(files);
            if (useron.getType() == 2) {
                System.out.println("I'm a provider");
                eventService.saveActivity(useron, provideron, event, program,files);
            }
            else {
                modelAndView.setViewName("redirect:/error?error_code=not_prov");
                return modelAndView;
            }
        }
        else {
            modelAndView.setViewName("redirect:/error?error_code=anon");
            return modelAndView;
        }
        redirectAttributes.addFlashAttribute("success","true");
        modelAndView.setViewName("redirect:/activity_reg");
        return modelAndView;
    }

}
