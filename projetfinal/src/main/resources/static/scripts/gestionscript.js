$(document).ready(function () {
  // global arrays!!!
  const employes = JSON.parse($("#employes").text());
  const formulaires = [];

  $(".ui.dropdown").dropdown({
    action: "activate",
    onChange: function (value, text, $selectedItem) {
      $.ajax({
        type: "GET",
        url: "getfeuilles?selectedWeek=" + value,
        success: function (f) {
          const feuilles_de_temps = JSON.parse(f);
          formulaires.length = 0;
          for (const i of feuilles_de_temps) {
            formulaires.push(i);
          }
          appendCards(formulaires);
        },
      });
    },
  });

  $("#table-container").hide();

  // -----------------------------ON_CLICK----------------------------------
  $(".ui.menu > .item").click(function (e) {
    e.preventDefault();
    $(".ui.menu > .item").removeClass("active");
    $(this).addClass("active");
    showCards($(this).attr("id"));
  });

  $("#table-container")
    .find(".close.icon")
    .click(function (e) {
      e.preventDefault();
      $(this).closest("#table-container").hide();
    });

  function refreshCardClick() {
    $(".ui.segment")
      .find(".ui.card")
      .click(function (e) {
        e.preventDefault();
        let emp = getEmployeFromCard($(this).find(".meta").first().text());
        showTable($(this).attr("class"), emp);
      });
  }
  // -----------------------------CUSTOM_METHODS----------------------------------

  function showCards(menu_id) {
    switch (menu_id) {
      case "menu-all":
        $(".ui.card").show();
        break;
      case "menu-approved":
        $(".ui.card").hide();
        $(".ui.card.card-approved").show();
        break;
      case "menu-unapproved":
        $(".ui.card").hide();
        $(".ui.card.card-unapproved").show();
        $(".ui.card.card-errors-unapproved").show();
        break;
      case "menu-unsent":
        $(".ui.card").hide();
        $(".ui.card.card-unsent").show();
        break;
      default:
        break;
    }
  }

  function showTable(str_classes, employe) {
    $("#table-container > div").hide();
    if (str_classes.match("card-approved") != null) {
      createTableProjets(employe, "#table-approved");
      $("#table-container").show();
      $("#table-approved").show();
    } else if (str_classes.match("card-unapproved") != null) {
      createTableProjets(employe, "#table-unapproved");
      $("#table-container").show();
      $("#table-unapproved").show();
    } else if (str_classes.match("card-errors-unapproved") != null) {
      createTableProjets(employe, "#table-unapproved-errors");
      $("#table-container").show();
      $("#table-unapproved-errors").show();
    } else if (str_classes.match("card-unsent") != null) {
      createTableUnsent(employe);
      $("#table-container").show();
      $("#table-unsent").show();
    }
  }

  // cards creation
  function appendCards(feuilles_de_temps) {
    $(".ui.cards").empty();
    for (const employe of employes) {
      let isAvailable = false;
      let obj_feuille = null;
      for (const feuille of feuilles_de_temps) {
        if (employe.employeId == feuille.employe.employeId) {
          isAvailable = true;
          obj_feuille = feuille;
          break;
        }
      }
      if (isAvailable) {
        if (obj_feuille.estApprouvee) {
          createCardApproved(employe);
        } else {
          createCardUnapproved(employe, obj_feuille.estValidee);
        }
      } else {
        createCardUnsent(employe);
      }
    }
    refreshCardClick();
  }

  function createCardUnsent(employe) {
    const card_string_start =
      '<a class="ui grey card card-unsent"><div class="content">';
    const header =
      '<div class="header">' + employe.prenom + " " + employe.nom + "</div>";
    const id = '<div class="meta">ID:' + employe.employeId + "</div>";
    const card_string_end =
      '</div><div class="extra content"><i class="ellipsis horizontal icon"></i>En attente du formulaire</div></a>';
    const card_string_final = card_string_start + header + id + card_string_end;
    $(".ui.cards").append(card_string_final);
  }

  function createCardUnapproved(employe, estValidee) {
    let card_string_start = "";
    if (estValidee) {
      card_string_start =
        '<a class="ui yellow card card-unapproved"><div class="content">';
    } else {
      card_string_start =
        '<a class="ui red card card-errors-unapproved"><div class="content">';
    }
    const header =
      '<div class="header">' + employe.prenom + " " + employe.nom + "</div>";
    const id = '<div class="meta">ID:' + employe.employeId + "</div>";
    const card_string_end =
      '</div><div class="extra content"><i class="exclamation icon"></i>Formulaire à approuver</div></a>';
    const card_string_final = card_string_start + header + id + card_string_end;
    $(".ui.cards").append(card_string_final);
  }

  function createCardApproved(employe) {
    const card_string_start =
      '<a class="ui green card card-approved"><div class="content">';
    const header =
      '<div class="header">' + employe.prenom + " " + employe.nom + "</div>";
    const id = '<div class="meta">ID:' + employe.employeId + "</div>";
    const card_string_end =
      '</div><div class="extra content"><i class="check icon"></i>Formulaire approuvé</div></a>';
    const card_string_final = card_string_start + header + id + card_string_end;
    $(".ui.cards").append(card_string_final);
  }

  // table creation
  function createTableUnsent(employe) {
    $("#table-unsent").find(".message").empty();
    const start_str =
      "<h4>" +
      employe.prenom +
      " n'a pas encore rempli son formulaire pour cette semaine.</h4>";
    const end_str =
      "<h4>Contactez " +
      employe.prenom +
      ' via <a href="mailto:">' +
      employe.courriel +
      "</a> pour lui demander de remplir son formulaire.</h4>";
    const final_str = start_str + end_str;
    $("#table-unsent").find(".message").append(final_str);
  }

  function getEmployeFromCard(meta_txt) {
    let id = parseInt(meta_txt.substr(3));
    let employe = null;
    for (const emp of employes) {
      if (emp.employeId == id) {
        employe = emp;
        break;
      }
    }
    return employe;
  }

  function createTableProjets(employe, table_id) {
    let formulaire = null;
    for (const f of formulaires) {
      if (f.employe.employeId == employe.employeId) {
        formulaire = f;
        break;
      }
    }
    $(table_id).find("tbody").empty();
    for (const entree of formulaire.entreesProjets) {
      const nom_projet =
        '<td data-label="nom_projet">' + entree.projet.nom_projet + "</td>";
      const temps =
        '<td data-label="tempsTravaille">' + entree.tempsTravaille + "</td>";
      const date =
        '<td data-label="dateTravaille">' + entree.dateTravaille + "</td>";
      const final = "<tr>" + nom_projet + temps + date + "</tr>";
      $(table_id).find("tbody").append(final);
    }
    if (table_id === "#table-unapproved-errors") {
      $(table_id).find(".message").empty();
      const start_str =
        "<h4>Une ou plusieurs règles ne sont pas respectées par ce formulaire.</h4>";
      const end_str =
        "<h4>Contactez " +
        employe.prenom +
        ' via <a href="mailto:">' +
        employe.courriel +
        "</a> avant d'approuver le formulaire.</h4>";
      const final_str = start_str + end_str;
      $(table_id).find(".message").append(final_str);
    }
    approuver(formulaire);
  }
  // bouton approuver
  function approuver(formulaire) {
    $(".btn-approuver").click(function (e) {
      e.preventDefault();
      $.ajax({
        type: "POST",
        url: "approuvefeuille",
        data: formulaire,
        success: function (response) {
          console.log(response);
        },
      });
    });
  }
});
