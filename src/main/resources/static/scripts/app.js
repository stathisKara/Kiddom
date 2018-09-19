/**
 * Created by Arianna on 2/7/2017.
 */
$(document).ready(function() {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function(evt) {
        window.location.replace("/?pageSize=" + this.value + "&page=1");
    });
}