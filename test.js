var matrix = ['1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1'];
var target = ['1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1'];
var sort = [];


var targetStr = target.join('');
console.log(targetStr);

var count = 1;
for (var i = 0; i < matrix.length - 1; i++) {
    if (matrix[i] == matrix[i + 1]) {
        count++;
    } else {
        sort.push(count);
        count = 1;
    }
}
sort.push(count);
console.log(sort);

var pattern = '';
var isZero = true;
for (var i = 0; i < sort.length; i++) {
    if (isZero) {
        pattern += '[0-1]{' + sort[i] + '}';
    } else {
        pattern += '0{' + sort[i] + '}';
    }
    isZero = !isZero;
}
console.log(pattern);

var reg = new RegExp(pattern);

function canPut(target, reg, tableWidth, brickWidth) {
    var index = targetStr.search(reg);
    if (index == -1) {
        return false;
    } else {
        if (index / tableWidth == (index + brickWidth) / tableWidth) {
            return true;
        } else {
            return canPut(target.slice(index + 1), reg, tableWidth, brickWidth);
        }
    }
}

console.log(result);
