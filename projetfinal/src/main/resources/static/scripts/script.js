//setup jquery start
$("#progress_bar").progress({
  total: 8,
});
$("#btn_precedent").addClass("disabled");

//---------------------on_click-----------------------

$("#btn_suivant").on("click", function () {
  checkProjetInput();
  if (checkProjetInput()) {
    $("#progress_bar").progress("increment");

    plusSlides(1);
    if ($("#progress_bar").progress("get percent") != 0) {
      $("#btn_precedent").removeClass("disabled");
    }
    if ($("#progress_bar").progress("get percent") === 88) {
      $("#btn_suivant").addClass("disabled");
    }
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
  $(".template .carte-projet")
    .first()
    .clone(true)
    .appendTo($(this).next(".cards-container").find(" .ui.one.cards"));
  $(".ui.dropdown").dropdown();
});

$(".btn_enlever_projet").on("click", function () {
  $(this).closest(".carte-projet").remove();
});

$("#btn_soumettre").on("click", function () {
  getJsonString();
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

//--------------JSON_METHODS-------------

// Cette fonction retourne un string Json de la liste d'objets ProjetEmploye
function getJsonString() {
  const dates = getDatesArray();
  const projets = [];

  $(".slide-content .carte-projet").each(function () {
    const projet_obj = {};
    projet_obj.numero_employe = parseInt($("#employe-id").html());
    projet_obj.numero_projet = parseInt(
      $(this).find(".ui.dropdown").dropdown("get value")
    );
    projet_obj.temps_travaille = parseInt(
      $(this).find("input[name='temps_travail']").val()
    );

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

  $.ajax({
    type: "POST",
    url: "submitjson", //do not put the full url,you need use an absolute url
    dataType: "json",
    data: JSON.stringify(projets), //put search js object directly here
    contentType: "application/json",
    success: function (data) {
      console.log("ok");
      // do what ever you want with data
    },
  });
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

//-----ERROR_CHECK_METHODS------

function checkProjetInput() {
  let ok = true;
  $(".mySlides[style='display: block;'] .carte-projet").each(function () {
    if ($(this).find(".ui.dropdown").dropdown("get text") === "Projet") {
      $(this).find(".ui.dropdown").addClass("error");
      if (ok) {
        ok = false;
      }
    }
  });
  return ok;
}

$(".ui.dropdown").click(function (e) {
  e.preventDefault();
  $(this).removeClass("error");
});
