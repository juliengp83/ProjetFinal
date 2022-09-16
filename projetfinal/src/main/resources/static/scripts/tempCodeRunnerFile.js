function getJsonString() {
  const date_now = new Date();
  const dates = [];
  for (let i = 0; i < 7; i++) {
    let day = new Date();
    if (i <= date_now.getDay()) {
      day.setDate(day.getDate() - date_now.getDay() + i);
    } else {
      day.setDate(day.getDate() - date_now.getDay() + i - 7);
    }
    day.toISOString().split("T")[0];
    dates.push(day);
  }
  // dates.push(dates.shift());

  let obj = { datenow: dates };
  let json_string = JSON.stringify(obj);
  return json_string;
}