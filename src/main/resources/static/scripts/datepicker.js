jQuery(function(){
    jQuery($(":input[id^='datepicker']")).datepicker({
        inline: true,
        showOtherMonths: true
    });
});

jQuery( function() {
    var availableTags = [
        "ActionScript",
        "AppleScript",
        "Asp",
        "BASIC",
        "C",
        "C++",
        "Clojure",
        "COBOL",
        "ColdFusion",
        "Erlang",
        "Fortran",
        "Groovy",
        "Haskell",
        "Java",
        "JavaScript",
        "Lisp",
        "Perl",
        "PHP",
        "Python",
        "Ruby",
        "Scala",
        "Scheme"
    ];
    $(":input[id^='location']").autocomplete({
        source: availableTags
    });
} );