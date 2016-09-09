var matrix=[
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

/**
 *  一维化正则式匹配算法
 */
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

var timeOne;
console.time(timeOne);
function canPut(matrixStr, reg, tableWidth, brickWidth,lastIndex) {
    var index = matrixStr.search(reg);
    if (index == -1) {
        return false;
    } else {
        if (parseInt(index / tableWidth) == parseInt((index + brickWidth-1) / tableWidth)) {
            return index;
        } else {
            return canPut(matrixStr.slice(index + 1), reg, tableWidth, brickWidth,lastIndex+index);
        }
    }
}
var result = canPut(matrixStr,reg,matrix[0].length,target[0].length,0);
console.log([parseInt(result/11)+1,result%11]);
console.timeEnd(timeOne);



var timeTwo;
console.time(timeTwo);
/**
 *  遍历算法时间
 */
function traverse(matrix, target) {
    for (var i = 0; i <= matrix.length - target.length; i++) {
        for (var j = 0; j <= matrix[0].length - target[0].length; j++) {
            //定点i,j
            var rt=check(i,j);
            if(rt){
                return rt;
            }
        }
    }
    return false;
}
function check(i, j) {
    for (var n = 0; n < target.length; n++) {
        for (var m = 0; m < target[0].length; m++) {
            if (matrix[i + n][j + m] && target[n][m]) {
                return false;
            }
        }
    }
    return [i,j];
}



var result2  = traverse(matrix,target);
console.log(result2);
console.timeEnd(timeTwo);