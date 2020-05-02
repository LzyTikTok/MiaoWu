export function parseStrToDate(str) {
  var array =  str.split("-");
  return new Date(array[0], array[1], array[2]).getTime();
}
