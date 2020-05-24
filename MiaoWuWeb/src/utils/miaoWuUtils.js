export function parseStrToDate(str) {
  var array =  str.split("-");
  return new Date(array[0], array[1], array[2]).getTime();
}

/**
 * @return {boolean}
 */
export function objectsContains(objects, property, value) {
  let flag = false;
  objects.forEach(object =>{
    if(object[property] === value){
      flag = true;
    }
  })
  return flag;
}
