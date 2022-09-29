$(document).ready(function () {
  // global variables
  const employes = JSON.parse($("#employes").text());
  const formulaires = [];
  // setup debut
  $("#table-container").hide();

  // -----------------------------ON_CLICK_EVENTS----------------------------------

  // cliquer sur une des options du menu va filtrer les cartes a afficher selon l'option selectionnee
  $(".ui.menu > .item").click(function (e) {
    e.preventDefault();
    $(".ui.menu > .item").removeClass("active");
    $(this).addClass("active");
    showCards($(this).attr("id"));
  });

  // changer sa selection de date dans le dropdown va remplacer les cartes par
  // celles qui correspondent a la nouvelle date selectionnee
  $(".ui.dropdown").dropdown({
    action: "activate",
    onChange: function (value, text, $selectedItem) {
      $.ajax({
        type: "GET",
        url: "getfeuilles?selectedWeek=" + value,
        success: function (f) {
          refreshCards(f);
        },
      });
    },
  });

  // cliquer sur l'icon X d'une fenetre infos va fermer la fenetre
  $("#table-container")
    .find(".close.icon")
    .click(function (e) {
      e.preventDefault();
      $(this).closest("#table-container").hide();
    });

  // cliquer sur une carte va afficher sa fenetre d'infos
  function refreshCardClick() {
    $(".ui.segment")
      .find(".ui.card")
      .click(function (e) {
        e.preventDefault();
        let emp = getEmployeFromCard($(this).find(".meta").first().text());
        showTable($(this).attr("class"), emp);
      });
  }

  // cliquer sur un bouton class=".btn-approuver" va approuver le formulaire en argument
  function approuver(formulaire) {
    $(".btn-approuver").click(function (e) {
      e.preventDefault(); 
      $(this).closest("#table-container").hide();
      $.ajax({
        type: "POST",
        url: "approuvefeuille",
        data: formulaire,
        success: function (response) {
          refreshCards(response);
        },
      });
    });
  }
  // -----------------------------DISPLAY_METHODS----------------------------------
  /**
   *Affiche les cartes selon l'id du menu en argument
   *
   * @param {string} menu_id l'ID du menu selectionne
   * @return {void}
   */
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
  /**
   *Affiche la fenetre d'informations sur le formulaire selon la classe de la carte et l'employe en argument
   *
   * @param {string} str_classes les classes d'une carte
   * @param {employe} employe objet employe
   * @return {void}
   */
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

  // -----------------------------CARD_CREATION_METHODS----------------------------------

  /**
   *Vide la liste globale formulaires et la repopule avec les donnees de feuilles_de_temps.
   * Affiche les nouvelles cartes.
   * @param {formulaires} feuilles_de_temps liste d'objets formulaire
   * @return {void}
   */
  function refreshCards(feuilles_de_temps) {
    const fs = JSON.parse(feuilles_de_temps);
    formulaires.length = 0;
    for (const i of fs) {
      formulaires.push(i);
    }
    appendCards(formulaires);
  }
  /**
   *Cree une carte pour chaque employe de l'array "employes" a partir des infos de feuilles_de_temps
   *
   * @param {formulaires} feuilles_de_temps liste d'objets formulaire
   * @return {void}
   */
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

  /**
   *Cree une carte class="card-unsent" pour l'employe en argument
   *
   * @param {employe} employe l'employe pour lequel la carte sera creee
   * @return {void}
   */
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

  /**
   *Cree une carte class="card-unapproved" ou class="card-errors-unapproved" pour l'employe en argument
   *selon le boolean estValidee en argument
   * @param {employe} employe l'employe pour lequel la carte sera creee
   * @param {boolean} estValidee Doit provenir du formulaire de l'employe
   * @return {void}
   */
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

  /**
   *Cree une carte class="card-approved" pour l'employe en argument
   *
   * @param {employe} employe l'employe pour lequel la carte sera creee
   * @return {void}
   */
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

  // -----------------------------TABLE_CREATION_METHODS----------------------------------

  /**
   *Modifie le contenu du div id="table-unsent" selon l'employe en argument
   *
   * @param {employe} employe l'employe pour lequel le contenu s'adressera
   * @return {void}
   */
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
  /**
   *Retourne l'employe dont l'ID est egal a celui dans l'argument meta_txt
   *
   * @param {string} meta_txt texte provenant d'un div class="meta" (ex:"ID:101")
   * @return {employe}
   */
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
  /**
   *Modifie le contenu du div dont l'id = table_id selon les infos de employe. Utilise la variable globale formulaires
   *
   * @param {employe} employe employe pour lequel le contenu s'adressera
   * @param {string} table_id id du div a modifier
   * @return {void}
   */
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
});
