
var modal = document.getElementById('myModal');
//    var btn = document.getElementById("myBtn");
//    var btn2 = document.getElementById("myBtn2");
//    var finalpoints = document.getElementById("finalpoints");
var closebutton = document.getElementById("closebutton");
//    function myFunction() {
//        document.getElementById("finishbutton").click();
//    }
//    document.getElementById('totalPoints').value = 200;



// When the user clicks on the button, open the modal
//    btn.onclick = function() {
////        document.getElementById('change_form').submit();
//        var quantity = parseInt(document.getElementById("quantity").value);
//        var quantity2 = parseInt(document.getElementById("quantity2").value);
//        var quantity3 = parseInt(document.getElementById("quantity3").value);
//        var quantity4 = parseInt(document.getElementById("quantity4").value);
//        var points = (quantity * 260) + (quantity2 * 530) + (quantity3 * 1070) + (quantity4);
////        document.getElementById("finalpoints").innerText = points;
//        modal.style.display = "block";
//        myFunction();
//    }
//    btn2.onclick = function() {
//        var quantity = parseInt(document.getElementById("quantity").value);
//        var quantity2 = parseInt(document.getElementById("quantity2").value);
//        var quantity3 = parseInt(document.getElementById("quantity3").value);
//        var quantity4 = parseInt(document.getElementById("quantity4").value);
//        var points = (quantity * 260) + (quantity2 * 530) + (quantity3 * 1070) + (quantity4);
////        document.getElementById("finalpoints").innerText = points;
//        modal.style.display = "block";
//        myFunction();
//    }
//    reset();
// When the user clicks on <span> (x), close the modal
closebutton.onclick = function() {
//        reset();
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
//            reset();
        modal.style.display = "none";
    }
}





function hidediv(id){$('#'+id).css('display','none');}
function showdiv(id){$('#'+id).css('display','block');}

$('#user_input, #pass_input, #v_pass_input, #email').bind('keyup', function() {
    if(allFilled()) $('#register').removeAttr('disabled');
});

function allFilled() {
    var filled = true;
    $('body input').each(function() {
        if($(this).val() == '') filled = false;
    });
    return filled;
}

function pr() {
    if (document.getElementById("payment").value == "1") {

        showdiv('credit_payment');
        document.getElementById("cardNumber").required=true;
        document.getElementById("monthselect").required=true;
        document.getElementById("expityYear").required=true;
        document.getElementById("cvCode").required=true;
        document.getElementById("username").required=false;
        document.getElementById("Email").required=false;



    }
    else {

        hidediv('credit_payment');

    }
    if (document.getElementById("payment").value == "2") {
        showdiv('paypal_payment');
        document.getElementById("cardNumber").required=false;
        document.getElementById("monthselect").required=false;
        document.getElementById("expityYear").required=false;
        document.getElementById("cvCode").required=false;
        document.getElementById("username").required=true;
        document.getElementById("Email").required=true;

    }
    else {
        hidediv('paypal_payment');
    }
}

function calculate() {
    var cost = 10;
    var package1 = document.getElementById("quantity");
    var package2 = document.getElementById("quantity2");
    var package3 = document.getElementById("quantity3");
    var quantity4 = parseInt(document.getElementById("quantity4").value);
    if (quantity4 < 0) {
        alert("Πληκτρολογειστε μια τιμη μεγαλύτερη ή ιση με το 0");
        return;
    }
    var quantity = 0;
    var quantity2 = 0;
    var quantity3 = 0;
    if (package1.checked) {
        quantity = 1
    }
    if (package2.checked) {
        quantity2 = 1
    }
    if (package3.checked) {
        quantity3 = 1
    }
    var quantity5 = (quantity * 50) + (quantity2 * 100) + (quantity3 * 200) + (quantity4 / 5);
    var points = (quantity * 260) + (quantity2 * 530) + (quantity3 * 1070) + (quantity4);
    document.getElementById("cost").innerText = quantity5 + "€";
    document.getElementById("points").innerText = points;
    document.getElementById('totalPoints').value = points;
    document.getElementById("totalcost").setAttribute("value", quantity5.toString());
    document.getElementById("points2").innerText =quantity5;
    document.getElementById("points3").innerText =quantity5;
}


function reset() {
//        alert("Πληκτρολογειστε μια τιμη μεγαλύτερη ή ιση με το 0");

    document.getElementById("quantity").value = 0;
    document.getElementById("quantity2").value = 0;
    document.getElementById("quantity3").value = 0;
    document.getElementById("quantity4").value = 0;
    calculate();
}

