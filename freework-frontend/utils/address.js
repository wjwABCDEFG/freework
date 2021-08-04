// import { CodeToText, TextToCode } from 'element-china-area-data'

if (process.client) {
    var regionData = require("element-china-area-data").regionData;
    var CodeToText = require("element-china-area-data").CodeToText;
    var TextToCode = require("element-china-area-data").TextToCode;
}

export function addToStr(src) {
    let addressText = src[0] == '' ? '' : CodeToText[src[0]]
    for (let i = 1; i < src.length; i++) {
        if (src[i] != '') addressText = addressText + '-' + CodeToText[src[i]]
    }
    return addressText
}

export function strToAdd(src, len = 3) {
    let addTextArr = src.split('-')
    let addCodeArr = []
    let curNode = TextToCode[addTextArr[0]]
    let i = 0

    while (i < addTextArr.length) {
        if (!curNode) break
        addCodeArr.push(curNode.code)
        i++
        curNode = curNode[addTextArr[i]]
    }
    if (addCodeArr.length < len) addCodeArr.push("")

    return addCodeArr
}

// 截断到市
export function addCity(src) {
    let addr = "";
    let count = 0;
    for (let i = 0; i < src.length; i++) {
        if (src[i] == "-") count += 1;
        if (count == 2) break;
        addr += src[i];
    }
    return addr;
}