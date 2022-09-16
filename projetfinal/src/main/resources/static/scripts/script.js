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

$("#btn_soumettre").on("click", function () {
  alert(getJsonString());
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

function getJsonString() {
  const dates = getDatesArray();
  const projets = [];

  $(".slide-content .carte-projet").each(function () {
    const projet_obj = {};
    // projet_obj.id_employe_projet = 1;
    projet_obj.numero_employe = $("#employe-id").html();

    projet_obj.numero_projet = parseInt(
      $(this).find(".ui.dropdown").dropdown("get value")
    );

    // projet_obj.id_feuille_temps = 2;

    if ($(this).find("input[name='temps_travail']").val() === "") {
      projet_obj.temps_travaille = 0;
    } else {
      projet_obj.temps_travaille = $(this)
        .find("input[name='temps_travail']")
        .val();
    }

    switch ($(this).closest(".slide-content").attr("id")) {
      case "#slide-lundi":
        projet_obj.date_travail = dates[0];
        break;
      case "#slide-mardi":
        projet_obj.date_travail = dates[1];
        break;
      case "#slide-mercredi":
        projet_obj.date_travail = dates[2];
        break;
      case "#slide-jeudi":
        projet_obj.date_travail = dates[3];
        break;
      case "#slide-vendredi":
        projet_obj.date_travail = dates[4];
        break;
      case "#slide-samedi":
        projet_obj.date_travail = dates[5];
        break;
      case "#slide-dimanche":
        projet_obj.date_travail = dates[6];
        break;
      default:
        break;
    }
    projets.push(projet_obj);
  });

  let json_string = JSON.stringify(projets);
  return json_string;
}

// Cette fonction cree un tableau contenant la valeur (en commencant par lundi) de "date_travail" pour creer le fichier json
function getDatesArray() {
  const date_now = new Date();
  const dates = [];
  for (let i = 0; i < 7; i++) {
    let day = new Date();
    if (i <= date_now.getDay()) {
      day.setDate(day.getDate() - date_now.getDay() + i - 1);
    } else {
      day.setDate(day.getDate() - date_now.getDay() + i - 7 - 1);
    }
    dates.push(day);
  }
  //cette ligne de code met le dimanche en dernier au lieu de premier dans la liste
  dates.push(dates.shift());
  return dates;
}
