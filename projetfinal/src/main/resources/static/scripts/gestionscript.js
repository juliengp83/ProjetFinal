// $('#search-select')
//   .dropdown();

$(".ui.dropdown").dropdown({
  action: "activate",
  onChange: function (value, text, $selectedItem) {
    $.ajax({
      type: "GET",
      url: "getfeuilles?selectedWeek=" + value,
      //data: value,
      success: function (x) {
        console.log(x);
      },
    });
  },
});
$("#table-container").hide();
// $("#table-approved").hide();
// $("#table-unapproved").hide();
// $("#table-unapproved-errors").hide();
// $("#table-unsent").hide();

$(".ui.menu > .item").click(function (e) {
  e.preventDefault();
  $(".ui.menu > .item").removeClass("active");
  $(this).addClass("active");
  showCards($(this).attr("id"));
});

// -----------------------------ON_CLICK----------------------------------
$("#table-container")
  .find(".close.icon")
  .click(function (e) {
    e.preventDefault();
    $(this).closest("#table-container").hide();
  });

$(".ui.segment")
  .find(".ui.card")
  .click(function (e) {
    e.preventDefault();
    showTable($(this).attr("class"));
  });

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

function showTable(str_classes) {
  // debugger;
  $("#table-container > div").hide();
  if (str_classes.match("card-approved") != null) {
    $("#table-container").show();
    $("#table-approved").show();
  } else if (str_classes.match("card-unapproved") != null) {
    $("#table-container").show();
    $("#table-unapproved").show();
  } else if (str_classes.match("card-errors-unapproved") != null) {
    $("#table-container").show();
    $("#table-unapproved-errors").show();
  } else if (str_classes.match("card-unsent") != null) {
    $("#table-container").show();
    $("#table-unsent").show();
  }
}
