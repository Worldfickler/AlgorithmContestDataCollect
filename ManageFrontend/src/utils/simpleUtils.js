import jsSHA from "jssha";

let util = {
    timeStampToLocalTime(timestamp) {
        let date = new Date(timestamp)
        return date.toLocaleString()
    },
    getDuration(time) {
        return Math.floor(time/3600) + ":" + Math.floor((time%3600) / 60) +":" +(time%60)
    },
    checkAllChinese(str) {
        let reg = /^[\u4E00-\u9FA5]*$/g;
        return reg.test(str);
    },
    checkAllChineseAndEnglishAndNumber(str) {
        let reg = /^[\u4E00-\u9FA5A-Za-z0-9]*$/g;
        return reg.test(str);
    },
    checkCharAndNumber(str) {
        let reg = /^[a-zA-Z0-9]*$/g;
        return reg.test(str);
    },
    checkCharAndNumberAndUnderscore(str) {
        let reg = /^[a-zA-Z0-9_]*$/g;
        return reg.test(str);
    },
    SHA512(str) {
        const shaObj = new jsSHA("SHA-512","TEXT",{encoding:"UTF8"})
        shaObj.update(str)
        return shaObj.getHash('HEX')
    }
}
export default util