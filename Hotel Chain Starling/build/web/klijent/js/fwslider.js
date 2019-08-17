$(document).ready(function(){
  
  $(".Modern-Slider").slick({
    autoplay:true,
    autoplaySpeed:10000,
    speed:600,
    slidesToShow:1,
    slidesToScroll:1,
    pauseOnHover:false,
    dots:true,
    pauseOnDotsHover:true,
    cssEase:'linear',
   // fade:true,
    draggable:false,
    prevArrow:'<button class="PrevArrow"></button>',
    nextArrow:'<button class="NextArrow"></button>', 
  });
  
})


//hotels pic
$('#next').hover(function() {
  $('.entypo-right-open-big').addClass('animnext');
}, function() {
  $('.entypo-right-open-big').removeClass('animnext');
});

$('#previous').hover(function() {
  $('.entypo-left-open-big').addClass('animprev');
}, function() {
  $('.entypo-left-open-big').removeClass('animprev');
});

$('#heart').hover(function() {
  $('.entypo-heart').addClass('animheart');
}, function() {
  $('.entypo-heart').removeClass('animheart');
});

$(function() {
  $('#next').hover(function() {
    $('#slides').css('left', '-490px');
  }, function() {
    // on mouseout, reset the background colour
    $('#slides').css('left', '0px');
  });
});
/*
jQuery(document).ready(function($) {
  $('.counter').counterUp({
    delay: 10,
    time: 2000
  });
});
*/