/**
 * Created by Stathis on 7/6/2017.
 */

function changeSelect( selectNo ) {
    var towns = document.getElementById("type6");
    var selectMe = 0;
    for (var i = 0; i < towns.length; i++) {
        if (towns[i].value == selectNo) {
            selectMe = i;
        }
        towns[i].value = i + 1;


    }
    var sels5 = document.getElementById("type7").getElementsByTagName('SELECT');
    for (var j = 0; j < sels5.length; j++) {
        sels5[j].style.display = "none";

        if (j == (selectMe - 1)) {
            sels5[j].style.display = '';
        }
    }
    var sels6 = document.getElementById("type7").getElementsByClassName("input-group-addon");
    for (var j = 0; j < sels6.length; j++) {
        sels6[j].style.display = "none";

        if (j == (selectMe - 1)) {
            sels6[j].style.display = '';
        }
    }
}
//
//     var sels7 = document.getElementById("type8").getElementsByTagName('SELECT');
//     for( var j=0; j<sels.length; j++ )
//     {
//         sels7[j].style.display = "none";
//
//         if ( j==(selectMe-1) )
//         { sels7[j].style.display = ''; }
//     }
//     var sels2 = document.getElementById("type8").getElementsByClassName("input-group-addon");
//     for( var j=0; j<sels2.length; j++ )
//     {
//         sels2[j].style.display = "none";
//
//         if ( j==(selectMe-1) )
//         { sels2[j].style.display = ''; }
//     }
//
//     var sels = document.getElementById("type9").getElementsByTagName('SELECT');
//     for( var j=0; j<sels.length; j++ )
//     {
//         sels[j].style.display = "none";
//
//         if ( j==(selectMe-1) )
//         { sels[j].style.display = ''; }
//     }
//     var sels2 = document.getElementById("type9").getElementsByClassName("input-group-addon");
//     for( var j=0; j<sels2.length; j++ )
//     {
//         sels2[j].style.display = "none";
//
//         if ( j==(selectMe-1) )
//         { sels2[j].style.display = ''; }
//     }
// }
//
