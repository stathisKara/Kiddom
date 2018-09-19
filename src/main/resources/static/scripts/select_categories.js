
function changeSelect( selectNo )
{
    var cats=document.getElementById("type1");
    var selectMe=0;
    for( var i=0; i<cats.length; i++ )
    {
        if ( cats[i].value==selectNo )
        { selectMe=i; }
        cats[i].value = i+1;


    }
    var sels = document.getElementById("type2").getElementsByTagName('SELECT');
    for( var j=0; j<sels.length; j++ )
    {
        sels[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels[j].style.display = ''; }
    }
    var sels2 = document.getElementById("type2").getElementsByClassName("input-group-addon");
    for( var j=0; j<sels2.length; j++ )
    {
        sels2[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels2[j].style.display = ''; }
    }

    var sels = document.getElementById("type3").getElementsByTagName('SELECT');
    for( var j=0; j<sels.length; j++ )
    {
        sels[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels[j].style.display = ''; }
    }
    var sels2 = document.getElementById("type3").getElementsByClassName("input-group-addon");
    for( var j=0; j<sels2.length; j++ )
    {
        sels2[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels2[j].style.display = ''; }
    }

    var sels = document.getElementById("type4").getElementsByTagName('SELECT');
    for( var j=0; j<sels.length; j++ )
    {
        sels[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels[j].style.display = ''; }
    }
    var sels2 = document.getElementById("type4").getElementsByClassName("input-group-addon");
    for( var j=0; j<sels2.length; j++ )
    {
        sels2[j].style.display = "none";

        if ( j==(selectMe-1) )
        { sels2[j].style.display = ''; }
    }
}

