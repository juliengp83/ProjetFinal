//global values
let slideIndex = 1;
//setup start
$("#progress_bar").progress({
  total: 8,
});
$("#btn_precedent").addClass("disabled");
showSlides(slideIndex);

//---------------------on_click-----------------------

$("#btn_suivant").on("click", function () {
  checkProjetInput();
  if (checkProjetInput()) {
    $("#progress_bar").progress("increment");
    plusSlides(1);
    toggleActifInactif();
  }
});

$("#btn_precedent").on("click", function () {
  $("#progress_bar").progress("decrement");
  plusSlides(-1);
  toggleActifInactif();
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
  const projets = getListeProjetEmploye();
  if (projets.length <= 0) {
    alert("Erreur, votre formulaire est vide!");
  } else {
    // console.log(projets);
    getResultat(projets);
  }
});

$("#btn_non_attention").on("click", function () {
  $("#container-resultats").css("display", "none");
  plusSlides(-7);
  toggleActifInactif();
});

$("#btn_oui_attention").on("click", function () {
  const projets = getListeProjetEmploye();
  submitJson(projets);
  $("#container-resultats").css("display", "none");
  plusSlides(1);
  toggleActifInactif();
});

$(".ui.dropdown").click(function (e) {
  e.preventDefault();
  $(this).removeClass("error");
});

//---------------------------SLIDESHOW-------------------------------

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

//--------------METHODS-------------

// Cette fonction active ou desactive les boutons SUIVANT et PRECEDENT selon la valeur du slideIndex
function toggleActifInactif() {
  switch (slideIndex) {
    case 1:
      $("#progress_bar").progress("reset");
      $("#btn_precedent").addClass("disabled");
      $("#btn_suivant").removeClass("disabled");
      break;
    case 8:
      $("#progress_bar").progress("set percent", 88);
      $("#btn_suivant").addClass("disabled");
      break;
    case 9:
      $("#progress_bar").progress("complete");
      $("#btn_suivant").addClass("disabled");
      $("#btn_precedent").addClass("disabled");
      break;
    default:
      if ($("#btn_precedent").hasClass("disabled")) {
        $("#btn_precedent").removeClass("disabled");
      }
      if ($("#btn_suivant").hasClass("disabled")) {
        $("#btn_suivant").removeClass("disabled");
      }
      break;
  }
}
// Cette fonction cree un tableau contenant la valeur de "date_travail" pour creer la liste EmployeProjet
function getDates() {
  const dates = [];
  for (let i = 0; i < 7; i++) {
    const date = new Date();
    date.setDate(date.getDate() - date.getDay() - 7 + i);
    dates.push(date);
  }
  return dates;
}
// Cette fonction retourne la liste d'objets ProjetEmploye
function getListeProjetEmploye() {
  const dates = getDates();
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
      case "#slide-dimanche":
        projet_obj.date_travail = dates[0];
        break;
      case "#slide-lundi":
        projet_obj.date_travail = dates[1];
        break;
      case "#slide-mardi":
        projet_obj.date_travail = dates[2];
        break;
      case "#slide-mercredi":
        projet_obj.date_travail = dates[3];
        break;
      case "#slide-jeudi":
        projet_obj.date_travail = dates[4];
        break;
      case "#slide-vendredi":
        projet_obj.date_travail = dates[5];
        break;
      case "#slide-samedi":
        projet_obj.date_travail = dates[6];
        break;

      default:
        break;
    }
    projets.push(projet_obj);
  });

  return projets;
}
// Cette fonction envoie la liste de ProjetEmploye au JsonController pour recevoir une confirmation Resultat
function getResultat(projets) {
  $.ajax({
    type: "POST",
    url: "getresultat", //do not put the full url,you need use an absolute url
    dataType: "json",
    data: JSON.stringify(projets), //put search js object directly here
    contentType: "application/json",
    success: function (regles) {
      let ok = confirmeRegles(regles);
      if (ok) {
        submitJson(projets, true);
        $("#progress_bar").progress("complete");
        $("#btn_precedent").addClass("disabled");
        plusSlides(1);
      } else if (!ok) {
        $("#container-resultats").css("display", "flex");
      }
    },
  });
}
/**
 * @todo creer la methode reliee dans JsonController
 * Cette fonction envoie une liste ProjetEmploye au JsonController pour l'inserer dans la database
 */
function submitJson(projets) {
  $.ajax({
    type: "POST",
    url: "submitjson", //do not put the full url,you need use an absolute url
    dataType: "json",
    data: JSON.stringify(projets), //put search js object directly here
    contentType: "application/json",
  });
}
// Cette retourne true si toutes les regles sont respectees, sinon, elle retourne false et popule le .container-resultats des regles non-respectees
function confirmeRegles(regles) {
  $("#container-resultats").find(".container-regles").empty();
  let all_ok = true;

  for (const regle of regles) {
    if (!regle.est_respectee) {
      const str =
        '<p style="color: red;">- ' + JSON.stringify(regle.message) + "</p>";
      $("#container-resultats").find(".container-regles").append(str);
      all_ok = false;
    }
  }
  return all_ok;
}
// Cette fonction empeche l'user de passer a la Slide suivante si il n'a pas choisi de projets dans le dropdown
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
