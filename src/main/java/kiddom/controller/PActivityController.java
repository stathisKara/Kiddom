package kiddom.controller;

/**
 * Created by eleni on 04-Jul-17.
 */

import kiddom.authentication.IAuthenticationFacade;
import kiddom.model.*;
import kiddom.service.ActivityService;
import kiddom.service.EventService;
import kiddom.service.ProgramService;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class PActivityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value="/activityProvider/{eventID}", method = RequestMethod.GET)
    public ModelAndView activity_show(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @PathVariable("eventID") int eventID){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is"+authentication.getName());
        if(!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            UserEntity userS = userService.findByUsername(authentication.getName());
            modelAndView.addObject("type", String.valueOf(userS.getType()));
        }
        System.out.println("Phra " + eventID);
        //Integer eventID_ = Integer.parseInt(eventID);
        System.out.println("Diavasa " + eventID);
        SingleEventEntity event = eventService.findSingleEventById(eventID);

        List<ReservationsEntity> eventReservations = activityService.getReservationsByEventId(event);
        for (ReservationsEntity res : eventReservations) {
            System.out.println("ID " + res.getReservation_id());
        }
        if (eventReservations.size() == 0) {
            System.out.println("DEN EXEI KRATISEIS");
            modelAndView.addObject("hasReservations", 0);
        }
        else {
            System.out.println("EXEI KRATISEIS");
            System.out.println(eventReservations.size());
            modelAndView.addObject("hasReservations", 1);
            modelAndView.addObject("reservations", eventReservations);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.now();
        System.out.println("-----------" + dtf.format(localDate));
        String currDate1 = dtf.format(localDate).toString();
        String eventDate1 = event.getDate();
        System.out.println("-----------event " + eventDate1);

        int cancelationApproved = 0;
        if (currDate1.substring(6,10).equals(eventDate1.substring(6,10)) && currDate1.substring(3,5).equals(eventDate1.substring(3,5))) {
            Integer dayNow = Integer.parseInt(currDate1.substring(0,2));
            Integer dayEvent = Integer.parseInt(eventDate1.substring(0,2));
            System.out.println("--- NOW " + dayNow + " EVENT " + dayEvent);
            if ((dayEvent - dayNow) > 2) {
                cancelationApproved = 1;
            }
        }

        modelAndView.addObject("cancelation", cancelationApproved);

        List<String> photos1 = new ArrayList<>();
        List<String> photos2 = new ArrayList<>();
        List<String> photos3 = new ArrayList<>();
        String[] photos = null;
        if (event.getPhotos() != null) {
            photos = event.getPhotos().split(";");
            int i = 0;
            for (String photo : photos) {
                if (i < 2) {
                    photos1.add(photos[i]);
                }
                else if (i < 5) {
                    photos2.add(photos[i]);
                }
                else {
                    photos3.add(photos[i]);
                }
                i +=1;
                System.out.println("exw pragmata" + photo);
            }


            modelAndView.addObject("photos", photos);
            modelAndView.addObject("photos1", photos1);
            modelAndView.addObject("photos2", photos2);
            modelAndView.addObject("photos3", photos3);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date currDate = sdf.parse(currDate1);
            Date eventDate = sdf.parse(event.getDate());
            if (eventDate.after(currDate)) {
                //    currEvent
                modelAndView.addObject("eventStatus", 1);
            }
            else {
                //    pastEvent
                modelAndView.addObject("eventStatus", 2);
            }
        }
        catch (ParseException e) {
        }

        //if (event.getProgram() != null) {
        //    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //    for (ProgramEntity program : event.getProgram()) {
        //        try {
        //            Date currDate = sdf.parse(currDate1);
        //            Date eventDate = sdf.parse(program.getDate());
        //            if (eventDate.after(currDate)) {
                    //    currEvent
        //                modelAndView.addObject("eventStatus", 1);
        //            }
        //            else {
                    //    pastEvent
        //                modelAndView.addObject("eventStatus", 2);
        //            }
        //        }
        //        catch (ParseException e) {
        //        }
        //        break;
        //    }
        //}
        //else {
        //    modelAndView.addObject("eventStatus", 0);
        //}

        if (event.getProgram().isEmpty()) {
            System.out.println("TA PIAME");
            modelAndView.addObject("hasProgram", 0);
            modelAndView.addObject("eventStatus", 0);
        }
        else {
            System.out.println("DEN TA PIAME");
            modelAndView.addObject("hasProgram", 1);
            modelAndView.addObject("program", event.getProgram());
        }

        if (!event.getComment_parent().isEmpty()) {
            modelAndView.addObject("hasComments", 1);
            modelAndView.addObject("comments", event.getComment_parent());
        }
        else {
            modelAndView.addObject("hasComments", 0);
        }

        modelAndView.addObject("event", event);

        //APPEND OLES TIS KRATISEIS TOY EVENT POY EIMASTE
        //


        modelAndView.setViewName("activityProvider");
        return modelAndView;
    }

    @RequestMapping(value="/edit_event/{eventID}", method = RequestMethod.POST)
    public ModelAndView edit_event(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, @PathVariable("eventID") String eventID)
    {
        System.out.println("Provider name: " + provider.getName());
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            Integer eventID_ = Integer.parseInt(eventID);
            SingleEventEntity eventEdit = eventService.findSingleEventById(eventID_);
            System.out.println("Kanw update sto " + eventID_);
            eventService.updateSingleEvent(provider, event, eventEdit);
            modelAndView.addObject("event", eventEdit);
            modelAndView.addObject("provider", provider);
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("activityProvider");
        return modelAndView;
    }

    @RequestMapping(value="/event_cancelation/{eventID}", method = RequestMethod.POST)
    public  ModelAndView event_cancelation(@ModelAttribute("provider") @Valid ProviderEntity provider, @ModelAttribute("user") @Valid UserEntity user, @ModelAttribute("single_event") @Valid SingleEventEntity event, @PathVariable("eventID") int eventID) throws ParseException {
        System.out.println("Provider name: " + provider.getName());
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = authenticationFacade.getAuthentication();
        System.out.println("Authentication name is " + authentication.getName());
        if (!authentication.getName().equals("anonymousUser")) {
            modelAndView.addObject("uname", authentication.getName());
            //Integer eventID_ = Integer.parseInt(eventID);
            SingleEventEntity eventEdit = eventService.findSingleEventById(eventID);
            System.out.println("Kanw update sto " + eventID);

            ProviderPK providerPK = new ProviderPK(authentication.getName());
            ProviderEntity prov = userService.findProvider(providerPK);

            SingleEventEntity event_ = eventService.findSingleEventById(eventID);
            List<ReservationsEntity> eventReservations = activityService.getReservationsByEventId(event_);
            if (eventReservations.size() != 0) {
                eventService.cancelSingleEvent(prov, eventEdit, eventReservations);
            }
            else {
                eventService.cancelSingleEvent(prov, eventEdit);
            }
            modelAndView.addObject("event", eventEdit);
            modelAndView.addObject("provider", prov);
            modelAndView.addObject("user", user);
        }
        modelAndView.setViewName("activityProvider");
        return modelAndView;
    }
}
