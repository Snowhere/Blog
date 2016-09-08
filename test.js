var matrix = ['1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1'];
var target = ['1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1', '0', '1', '1', '0', '0', '0', '1', '1', '0', '0', '0', '0', '0', '0', '1'];
var sort = [];
var matrix=[
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
];
var target = [
    [1, 0],
    [1, 0],
    [1, 1],
    [0, 1]
];

//一维化
var matrixStr = '';
for(var i=0;i<matrix.length;i++){
    matrixStr+=matrix[i].join('');
}
console.log(matrixStr);

var targetStr = '';
for(var j=0;j<target.length-1;j++){
    targetStr+=target[j].join('');
    for(var k=target[j].length;k<matrix[1].length;k++){
        targetStr+='0';
    }
}
targetStr+=target[j].join('');
console.log(targetStr);

//get reg
var count = 1;
for (var i = 0; i < targetStr.length - 1; i++) {
    if (targetStr[i] == targetStr[i + 1]) {
        count++;
    } else {
        sort.push(count);
        count = 1;
    }
}
sort.push(count);
console.log(sort);

var pattern = '';
var isZero = false;
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


function canPut(matrixStr, reg, tableWidth, brickWidth) {
    var index = matrixStr.search(reg);
    if (index == -1) {
        return false;
    } else {
        if (parseInt(index / tableWidth) == parseInt((index + brickWidth-1) / tableWidth)) {
            return true;
        } else {
            return canPut(matrixStr.slice(index + 1), reg, tableWidth, brickWidth);
        }
    }
}


var result = canPut(matrixStr,reg,matrix[0].length,target[0].length);
console.log(result);
