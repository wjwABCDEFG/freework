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