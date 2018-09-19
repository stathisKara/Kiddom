package kiddom.controller;

import kiddom.model.UserEntity;
import kiddom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.mkyong.web.jsonview.Views;
//import kiddom.controller.AjaxResponseBody;
//import com.mkyong.web.model.SearchCriteria;
//import com.mkyong.web.model.User;

@RestController
//@RequestMapping("/register")
public class AjaxController {


    @Autowired
    private UserService userService;
//
    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, filters json data to display.
    @RequestMapping(value = "/usernameCheck", method = RequestMethod.POST)
    public @ResponseBody AjaxResponseBody getSearchResultViaAjax(@RequestBody UserEntity userInfo) {
//
        System.out.println("Inside AjaxController with |"+userInfo.getUsername()+ "|");
        AjaxResponseBody result = new AjaxResponseBody();
        if (userService.findByUsername(userInfo.getUsername()) != null){
            result.setCode("400");
            result.setMsg("Το όνομα χρήστη υπάρχει");
        }

//        if (isValidSearchCriteria(search)) {
//            List<User> users = findByUserNameOrEmail(search.getUsername(), search.getEmail());
//
//            if (users.size() > 0) {
//                result.setCode("200");
//                result.setMsg("");
//                result.setResult(users);
//            } else {
//                result.setCode("204");
//                result.setMsg("No user!");
//            }
//
//        } else {

//        }
//
//        //AjaxResponseBody will be converted into json format and send back to the request.
        return result;
//
    }
//
//    private boolean isValidSearchCriteria(SearchCriteria search) {
//
//        boolean valid = true;
//
//        if (search == null) {
//            valid = false;
//        }
//
//        if ((StringUtils.isEmpty(search.getUsername())) && (StringUtils.isEmpty(search.getEmail()))) {
//            valid = false;
//        }
//
//        return valid;
//    }
//
//    // Init some users for testing
//    @PostConstruct
//    private void iniDataForTesting() {
//        users = new ArrayList<User>();
//
//        User user1 = new User("mkyong", "pass123", "mkyong@yahoo.com", "012-1234567", "address 123");
//        User user2 = new User("yflow", "pass456", "yflow@yahoo.com", "016-7654321", "address 456");
//        User user3 = new User("laplap", "pass789", "mkyong@yahoo.com", "012-111111", "address 789");
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//
//    }
//
//    // Simulate the search function
//    private List<User> findByUserNameOrEmail(String username, String email) {
//
//        List<User> result = new ArrayList<User>();
//
//        for (User user : users) {
//
//            if ((!StringUtils.isEmpty(username)) && (!StringUtils.isEmpty(email))) {
//
//                if (username.equals(user.getUsername()) && email.equals(user.getEmail())) {
//                    result.add(user);
//                    continue;
//                } else {
//                    continue;
//                }
//
//            }
//            if (!StringUtils.isEmpty(username)) {
//                if (username.equals(user.getUsername())) {
//                    result.add(user);
//                    continue;
//                }
//            }
//
//            if (!StringUtils.isEmpty(email)) {
//                if (email.equals(user.getEmail())) {
//                    result.add(user);
//                    continue;
//                }
//            }
//
//        }
//
//        return result;
//
//    }
}
