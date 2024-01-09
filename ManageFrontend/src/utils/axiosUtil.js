import axios from 'axios'


const Axios = axios.create({
    baseURL: "/ACDC/manage/api",
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
    },
}
)
Axios.interceptors.request.use(async config => {
    console.log("request config:", config)
    if (config.url === "/login" || config.url === "/rewrite") {
        return config;
    }
    if (window.localStorage.getItem("token") == null || window.localStorage.getItem("expire") - Date.now() <= 0) {
        return
    }
    if (window.localStorage.getItem("expire") - Date.now() <= 1000 * 1500) {
        await requestUtil.flushToken()
    }
    config.headers = { ...config.headers, "token": window.localStorage.getItem("token") }
    return config
}, err => {

})

let requestUtil = {
    login(username,password){
        return Axios.post("/login",{username,password})
    },
    async flushToken(){
        let token = window.localStorage.getItem("token")
        if (token === null || token === ""){
            return false
        }
        return await Axios.post("/rewrite",{},{headers:{token}}).then(res=>{
            if (res.data.code === 200) {
                let result = res.data.result
                window.localStorage.setItem("token",result.token)
                window.localStorage.setItem("expire", (Date.now() + 1000*1800).toString())
                return true
            }else{
                return false
            }
        })
    },
    getSchoolList(){
        return Axios.get("/school")
    },
    addSchool(school){
        return Axios.post("/admin/school",{school})
    },
    deleteSchool(schoolId){
        return Axios.delete("/admin/school/",{data:{id:schoolId}})
    },
    getManagerList() {
        console.log("getManagerList")
        return Axios.get("/admin/manager")
    },
    addManager( username, password, schoolId, isSuper) {
        return Axios.post("/admin/manager", { username, password, school:schoolId, is_super:isSuper })
    },
    deleteManager(managerId) {
        return Axios.delete("/admin/manager/", { data: { id: managerId } })
    },
    modifyManagerPassword(managerId, password) {
        return Axios.put("/admin/manager/", { id: managerId, password })
    },
    getStudentList() {
        return Axios.get("/user")
    },
    getApplicationList(){
        return Axios.get("/application")
    },
    acceptApplication(id) {
        return Axios.post("/application", { id,status:1 })
    },
    rejectApplication(id) {
        return Axios.post("/application", { id,status:2 })
    },
    addUserBatch(data) {
        return Axios.post("/addUserBatch",data)
    },
    addUser(data) {
        return Axios.post("/addUser",data)
    },
    checkUserExist(data) {
        return Axios.post("/checkUserExist",data)
    },
    deleteUser(uid) {
        return Axios.delete(`/deleteUser/${uid}`)
    },
    changeUserPassword(uid, newPassword) {
        return Axios.post("/changeUserPassword",{uid,newPassword})
    },
    getTagList() {
        return Axios.get("/tagList")
    },
    addTag(name) {
        return Axios.post("/newtag", { name })
    },
    editTagName(id,newName){
        return Axios.post("/changeTagName", { id,newName })
    },
    deleteTag(id) {
        return Axios.delete("/deletetag", { data: { id } })
    },
    getTagStuList(id) {
        return Axios.get(`/tag/${id}/userList`)
    },
    setTagStuList(id, userList) {
        return Axios.post(`/tag/${id}/setUserList`, { userList })
    },
    getAllCodeforcesAccount() {
        return Axios.get("/codeforces/account")
    },
    deleteCodeforcesAccount(codeforcesAccountId) {
        return Axios.delete("/codeforces/account", { data: { id: codeforcesAccountId } })
    },
    updateNormalUserInfo(id, classname,year,stuNo,realname) {
        return Axios.post("/modifyNormalUserInfo", { id, classname,year,stuNo,realname })
    },
    updateUserStatus(id,status) {
        return Axios.post("/modifyNormalUserInfo", { id,status})
    },
    updateUsersStatus(ids,status) {
        return Axios.post("/modifyNormalUserStatusBatch", { ids,status})
    },
    getAllAtcoderAccount() {
        return Axios.get("/atcoder/account")
    },
    deleteAtcoderAccount(AtcoderAccountId) {
        return Axios.delete("/atcoder/account", { data: { id: AtcoderAccountId } })
    },
    getTagAcTotalInfo(id) {
        return Axios.get(`/tag/${id}/totalInfo/ac`)
    },
    getTagCfTotalInfo(id) {
        return Axios.get(`/tag/${id}/totalInfo/cf`)
    },
    deleteTagByTidAndUid(tid, uid) {
        return Axios.delete("/tag/deleteByTidAndId", { data: { tid, uid } })
    },
    getTrainList() {
        return Axios.get("/intelligentTrain")
    },
    getFuncs() {
        return Axios.get("/intelligentTrain/funcs")
    },
    getStrategyList() {
        return Axios.get("/intelligentTrain/strategy")
    },
    addTrain(name, strategyId,startTimestamp,endTimestamp) {
        return Axios.post("/intelligentTrain", { name, strategyId,startTimestamp,endTimestamp })
    },
    delTrain(id) {
        return Axios.delete(`/intelligentTrain/${id}`)
    },
    addStrategy(name, useFunc) {
        return Axios.post("/intelligentTrain/strategy",{name,useFunc})
    },
    delStrategy(id) {
        return Axios.delete(`/intelligentTrain/strategy/${id}`)
    },
    addGenerateFunc(name, description){
        return Axios.post("/intelligentTrain/funcs",{name,desc: description})
    },
    delGenerateFunc(id) {
        return Axios.delete(`/intelligentTrain/funcs/${id}`)
    }
}

export default requestUtil