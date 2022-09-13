//setup jquery start
$("#progress_bar").progress({
  total: 8,
});
$("#btn_precedent").addClass("disabled");

//---------------------on_click-----------------------

$("#btn_suivant").on("click", function () {
  $("#progress_bar").progress("increment");

  plusSlides(1);
  if ($("#progress_bar").progress("get percent") != 0) {
    $("#btn_precedent").removeClass("disabled");
  }
  if ($("#progress_bar").progress("get percent") === 88) {
    $("#btn_suivant").addClass("disabled");
  }
});

$("#btn_precedent").on("click", function () {
  $("#progress_bar").progress("decrement");
  plusSlides(-1);
  if ($("#progress_bar").progress("get percent") === 0) {
    $("#btn_precedent").addClass("disabled");
  }
  $("#btn_suivant").removeClass("disabled");
});

$(".btn_ajouter_projet").on("click", function () {
  $(".carte-projet")
    .first()
    .clone(true)
    .appendTo($(this).next(".ui.one.cards"));
  $(".ui.dropdown").dropdown();
});

$(".btn_enlever_projet").on("click", function () {
  $(this).parents(".carte-projet").remove();
});

//---------------------------SLIDESHOW-------------------------------
let slideIndex = 1;
showSlides(slideIndex);

// Next/previous controls
function plusSlides(n) {
  showSlides((slideIndex += n));
}

// Thumbnail image controls
function currentSlide(n) {
  showSlides((slideIndex = n));
}

function showSlides(n) {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  if (n > slides.length) {
    slideIndex = 1;
  }
  if (n < 1) {
    slideIndex = slides.length;
  }
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }

  slides[slideIndex - 1].style.display = "block";
}
