/**
 * Created by Stathis on 7/2/2017.
 */



//    jQuery(document).ready(function() {
//
//        var usertimer, pass1timer, pass2timer, emailtimer;
//        jQuery(".input-group #username").keyup(function (e){
//            clearTimeout(usertimer);
//            var user_name = $(this).val();
//            usertimer = setTimeout(function(){
//                searchAjax();
//            }, 1000);
//        });
//
//        jQuery(".input-group #email").keyup(function (e){
//            clearTimeout(emailtimer);
//            var user_name = $(this).val();
//            emailtimer = setTimeout(function(){
//                searchAjax();
//            }, 1000);
//        });
//        jQuery("form").on('submit', function(e){
//            e.preventDefault();
//            alert("lala");
//            var email=jQuery(".input-group #email").val;
//            alert(email);
//            if ( email=== "aa@aa.gr")
//                e.preventDefault();
//            else
//                alert("to ena");
//            return true;
//
////            return false
//
//        });
//
//        jQuery("#loginForm").submit(function(e){
//            e.preventDefault();
//            alert("to allo");
//        });
//
////        jQuery(".input-group #password").keyup(function (e){
////            clearTimeout(pass1timer);
////            var user_name = $(this).val();
////            pass1timer = setTimeout(function(){
////                searchAjax();
////            }, 1000);
////        });
////
////        jQuery(".input-group #passwordsignup_confirm").keyup(function (e){
////            clearTimeout(pass2timer);
////            var passConfirm = $(this).val();
////            pass2timer = setTimeout(function(){
////                alert(jQuery('.input-group #password').val());
////                if (!(jQuery('.input-group #password').val().isEqual(passConfirm))){
////                    alert(123);
////                }
////            }, 1000);
////        });
    jQuery(".input-group #passwordsignup_confirm").keyup(checkPasswordMatch);
function checkPasswordMatch() {
    var password = $(".input-group #password").val();
    var confirmPassword = $(".input-group #passwordsignup_confirm").val();

    if (password != confirmPassword) {
        jQuery('.form-group #pass2Error').removeClass("hidden");
        jQuery('.form-group #pass2Error .isa_error span').html("Οι κωδικοι δεν είναι ίδιοι");
        return 1;
    }
    else {
        jQuery('.form-group #pass2Error').addClass("hidden");

    }
    return 0;
}
//
//
////        jQuery('#to_koumpi').click(function() {
////            alert("123");
////        });
//
////        jQuery('#loginForm').submit(function () {
////    alert("lala")
////            // Get the Login Name value and trim it
////            var name = $.trim($('#log').val());
////
////            // Check if empty of not
////            if (name  === '') {
////                alert('Text-field is empty.');
////                return false;
////            }
////        });
//
//    });

//    function validateForm() {
//        alert("tsekaro mia");
//        var x = document.forms["loginForm"]["username"].value;
//        alert(x);
//        if (x == "") {
//            alert("Name must be filled out");
//            return true;
//        }
//    }

var error_free=0;
jQuery(document).ready(function () {
    jQuery(".col-md-10 #to_koumpi").click(function(event){
        var form_data=jQuery("#step-2 input").serializeArray();
        console.log(form_data);
        error_free=0;
        searchAjax();
        if (!jQuery('.form-group #usernameError').hasClass("hidden")){
            error_free++;
        }
        error_free += checkPasswordMatch();
        if (error_free>0){
            event.preventDefault();
        }

    });

    jQuery('.input-group #username').on('input', function() {
        searchAjax();
    });

    jQuery('.input-group #email').on('input', function() {
        //alert(jQuery('.input-group #email').val())
        //searchAjax();
    });

});

function handleData(data){
    var mySpan= jQuery('.form-group #usernameError');
    if (data["code"] === "400") {
        mySpan.removeClass("hidden");
        mySpan.html(data["msg"]);
    }
    else{
        mySpan.addClass("hidden");
        mySpan.html("");
    }
}

function searchAjax() {
    var username = jQuery('.input-group #username').val();
    jQuery.ajax({
        type : "POST",
        contentType : "application/json",
        url : "usernameCheck",
        data : JSON.stringify({"username" : username}),
        dataType : 'json',
        timeout : 100000,
        success : function (data){
            var mySpan= jQuery('.form-group #usernameError');
            if (data["code"] === "400") {
                mySpan.removeClass("hidden");
                mySpan.html(data["msg"]);
            }
            else{
                mySpan.addClass("hidden");
                mySpan.html("");
            }
        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done : function(e) {
            alert("hmmm");
            console.log("DONE");

        }
    });
}


