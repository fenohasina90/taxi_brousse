function parseDate(dateString) {
    if (!dateString) return null;
    return new Date(dateString);
}

function parseDateTwo(dateString) {
    if (!dateString) return null;
    var parts = dateString.split('/');
    var date = parts[2]+'-'+parts[1]+'-'+parts[0];
    console.log(date);
    
    return parseDate(date);
}

let date = '08/11/2003'
console.log(parseDateTwo(date));
