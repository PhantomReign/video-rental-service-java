/**
 * Created by Rave on 30.04.2017.
 */

$(function(){
    $("input[name='fileCover']").change(function() {
        $("input[name='imageUrl']").val($(this).val().split('\\').pop());
    });
    $("input[name='fileBG']").change(function() {
        $("input[name='imageBGUrl']").val($(this).val().split('\\').pop());
    });
});