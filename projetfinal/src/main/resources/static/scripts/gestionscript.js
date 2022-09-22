$(".ui.dropdown").dropdown();

$(".ui.menu > .item").click(function (e) {
  e.preventDefault();
  $(".ui.menu > .item").removeClass("active");
  $(this).addClass("active");
  showCards($(this).attr("id"));
});

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
      break;
    case "menu-unsent":
      $(".ui.card").hide();
      $(".ui.card.card-unsent").show();
      break;
    default:
      break;
  }
}
