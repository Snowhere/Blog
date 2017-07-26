var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;

//每厘米像素
var arrDPI = new Array();
var tmpNode = document.createElement("DIV");
tmpNode.style.cssText = "width:1cm;height:1cm;position:absolute;left:0px;top:0px;z-index:99;visibility:hidden";
document.body.appendChild(tmpNode);
arrDPI[0] = parseInt(tmpNode.offsetWidth);
arrDPI[1] = parseInt(tmpNode.offsetHeight);
tmpNode.parentNode.removeChild(tmpNode);


//浏览器尺寸(cm)
var lengthWidth = clientWidth / arrDPI[0];
var lengthHeight = clientHeight / arrDPI[1];

console.log(clientWidth, clientHeight);
console.log(lengthWidth, lengthHeight);

var minWidth = 9; //键盘区域
var minHeight = 4; //键盘区域
var step = 1.9; //间距

var keys = document.getElementsByClassName('key'); //6 keys
var positionLeft;
var positionTop;

if (lengthWidth > lengthHeight) {
    //横屏
    if (lengthWidth < minWidth || lengthHeight < minHeight) {
        console.log('too small');
    }
    positionLeft = (lengthWidth - minWidth) / 2;
    positionTop = (lengthHeight - minHeight) / 2;
    for (var i = 0; i < 6; i++) {
        keys[i].style.left = positionLeft + 'cm';
        keys[i].style.top = positionTop + 'cm';
        if (i == 3) {
            positionLeft -= step * 1.7;
            positionTop += step;
        }
        positionLeft += step;
        //onclick
        keys[i].onclick = function() {
            console.log(this.getAttribute('id'));
        }
    }
} else {
    //竖屏
    if (lengthHeight < minWidth || lengthWidth < minHeight) {
        action('too small');
    }
    positionLeft = lengthWidth / 2;
    positionTop = (lengthHeight - minWidth) / 2;
    for (var i = 0; i < 6; i++) {
        keys[i].style.left = positionLeft + 'cm';
        keys[i].style.top = positionTop + 'cm';
        if (i == 3) {
            positionTop -= step * 1.7;
            positionLeft -= step;
        }
        positionTop += step;
        //onclick
        keys[i].onclick = function() {
            action(this.getAttribute('id'));
        }
    }
}

var ballList = [];
var skillList = [];
var skillTable = {};

function Skill(id, key, name) {
    this.id = id;
    this.key = key;
    this.name = name;
}

skillTable['qqq'] = new Skill('', 'qqq', '急速冷却');
skillTable['qqw'] = new Skill('', 'qqw', '幽灵漫步');
skillTable['qqe'] = new Skill('', 'qqe', '寒冰之墙');
skillTable['www'] = new Skill('', 'www', '电磁脉冲');
skillTable['wwe'] = new Skill('', 'wwe', '灵动迅捷');
skillTable['wwq'] = new Skill('', 'wwq', '强袭飓风');
skillTable['eee'] = new Skill('', 'eee', '阳炎冲击');
skillTable['eew'] = new Skill('', 'eew', '混沌陨石');
skillTable['eeq'] = new Skill('', 'eeq', '熔炉精灵');
skillTable['qwe'] = new Skill('', 'qwe', '超震声波');

function action(key) {
    if (key == 'q' || key == 'w' || key == 'e') {
        ballList.unshift(key);
         console.log(key);
    }
    if (key == 'r') {
        var skillKey = ballList[2] + ballList[1] + ballList[0];
        if (skillList[0] && skillKey == skillList[0].key) {
            console.log('重复');
            return;
        }
        if (skillTable[skillKey]) {
            skillList.unshift(skillTable[skillKey]);
            console.log('合成"'+skillTable[skillKey].name+'"');
        } else {
            console.log('没有这个技能');
        }
    }
    if (key == 'd') {
        if (skillList[0]) {
            console.log(skillList[0].name);
        } else {
            console.log('不能释放这个技能');
        }
    }
    if (key == 'f') {
        if (skillList[1]) {
            console.log(skillList[1].name);
        } else {
            console.log('不能释放这个技能');
        }
    }
}