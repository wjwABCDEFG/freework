export function slideToStr(src) {
    let arr = Array(7).fill(0);

    for (let i = src[0] - 1; i < src[1]; i++){
        arr[i] = 1
    }

    return arr.join("")
}

export function strToSlide(src) {
    let l = src.indexOf('1')
    let r = src.lastIndexOf('1')
    return [l + 1, r + 1]
}

export function strToHuman(src) {
    let humanText = src

    // if(src == '1111100') humanText = '周末双休'
    // else if(src == '1111110') humanText = '单休'
    // else{
    //     let sli = strToSlide(src)
    //     humanText = `周${sli[0]} - 周${sli[1]}`
    // }
    let sli = strToSlide(src)
    humanText = `周${sli[0]} - 周${sli[1]}`

    return humanText
}