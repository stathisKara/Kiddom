package kiddom.controller;

/**
 * Created by Stathis on 6/30/2017.
 */

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import kiddom.controller.Views;

public class AjaxResponseBody {

    @JsonView(Views.Public.class)
    String msg;

    @JsonView(Views.Public.class)
    String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//getters and setters
}