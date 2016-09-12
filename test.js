var matrix = [
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1],
    [1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1],
    [1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0],
    [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0]
];
var target = [
    [1, 0],
    [1, 0],
    [1, 1],
    [0, 1]
];

for (var i = 0; i <= 100; i++) {
    matrix[i] = []
    for (var j = 0; j <= 100; j++) {
        matrix[i][j] = Math.round(Math.random());
    }
}
//console.log(matrix)
    /**
     *  一维化正则式匹配算法
     */
    //一维化
var matrixStr = '';
for (var i = 0; i < matrix.length; i++) {
    matrixStr += matrix[i].join('');
}
//console.log(matrixStr);

function makeReg(target, targetRow, targetCol, tableRow, tableCol) {
    var targetStr = '';
    for (var j = 0; j < targetRow - 1; j++) {
        targetStr += target[j].join('');
        for (var k = targetCol; k < tableCol; k++) {
            targetStr += '0';
        }
    }
    targetStr += target[j].join('');
    //console.log('target' + targetStr);

    //get reg
    var sort = [];
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
    //console.log(sort);

    var pattern = '';
    var isZero = !target[0][0];
    for (var i = 0; i < sort.length; i++) {
        if (isZero) {
            pattern += '[0-1]{' + sort[i] + '}';
        } else {
            pattern += '0{' + sort[i] + '}';
        }
        isZero = !isZero;
    }
    //console.log(pattern);

    return new RegExp(pattern);
}

var reg = makeReg(target, target.length, target[0].length, matrix.length, matrix[0].length);
//console.log(reg);



function canPut(matrixStr, reg, tableCol, targetCol, lastIndex) {
    var index = matrixStr.search(reg);
    if (index == -1) {
        return false;
    } else {
        if (parseInt(index / tableCol) == parseInt((index + targetCol - 1) / tableCol)) {
            return index;
        } else {
            return canPut(matrixStr.slice(index + 1), reg, tableCol, targetCol, lastIndex + index);
        }
    }
}
var timeOne;
console.time(timeOne);
var result = canPut(matrixStr, reg, matrix[0].length, target[0].length, 0);
console.log(result);
if (result) { console.log([parseInt(result / matrix[0].length), result % matrix[0].length]); }

console.timeEnd(timeOne);




/**
 * target在棋盘table上是否可放置
 * return false 不可放置
 * return [ [ row, col ], .. ],可放置,table上的这些点需要变色
 */
function check(table, target) {
    for (var i = 0; i <= table.length - target.length; i++) {
        for (var j = 0; j <= table[0].length - target[0].length; j++) {
            //定点i,j
            var result = checkCover(table, target, i, j);
            if (result) {
                return result;
            }
        }
    }
    return false;
}
/*
 * target在棋盘table上是否重叠
 * return false 重叠
 * return [ [ row, col ], .. ],不重叠,table上的这些点需要变色
 */
function checkCover(table, target, i, j) {
    var result=[];
    if (i + target.length > table.length || j + target[0].length > table[0].length) return false;
    for (var n = 0; n < target.length; n++) {
        for (var m = 0; m < target[0].length; m++) {
            if (matrix[i + n][j + m] && target[n][m]) {
                return false;
            }
            if(target[n][m]){
                result.push([i + n,j + m]);
            }
        }
    }
    return result;
}


var timeTwo;
console.time(timeTwo);
var result2 = check(matrix, target);
console.log(result2);
console.timeEnd(timeTwo);
