$(function(){
  $('a.trigger').hover(function(e){
    $(this).prev().stop().css('top',e.pageY-115).css('left',e.pageX+5).show();
    //$(this).prev().stop().show();
    },
    function () {
      $(this).prev().stop().hide();
    }
  )
});

