import { CodeToText, TextToCode } from 'element-china-area-data'

export function addToStr(src) {
    let addressText = src[0] == '' ? '' : CodeToText[src[0]]
    for (let i = 1; i < src.length; i++) {
        if(src[i] != '') addressText = addressText + '-' + CodeToText[src[i]]
    }
    return addressText
}

export function strToAdd(src) {
    let addTextArr = src.split('-')
    let addCodeArr = []
    let curNode = TextToCode[addTextArr[0]]
    let i = 0
    
    while (i < addTextArr.length) {
        if(!curNode) break
        addCodeArr.push(curNode.code)
        i++
        curNode = curNode[addTextArr[i]]
    }
    if(addCodeArr.length < 3) addCodeArr.push("")

    return addCodeArr
}