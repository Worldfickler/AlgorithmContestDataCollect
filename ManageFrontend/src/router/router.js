import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
const routes = [
    {
        path:"/",
        component:()=>import("../page/base.vue"),
        redirect:"/index",
        children:[
            {
                path:"/index",
                component:()=>import('../page/index.vue')
            },
            {
                path:"/admin/school",
                component:()=>import('../page/school.vue'),
            },
            {
                path:"/admin/manager",
                component:()=>import('../page/manager.vue'),
            },
            {
                path:"/user",
                component:()=>import('../page/user.vue'),
            },
            {
                path: "/application",
                component:()=>import("../page/application.vue"),
            },
            {
                path: "/addUserBatch",
                component:()=>import("../page/addUserBatch.vue")
            },
            {
                path:"/tags",
                component:()=>import("../page/tags.vue")
            },
            {
                path:"/codeforcesAccountManage",
                component:()=>import("../page/CodeforcesAccountManage.vue")
            },
            {
                path:"/atcoderAccountManage",
                component:()=>import("../page/AtcoderAccountManage.vue")
            },
            {
                path:"/editTagMember/:id",
                component:()=>import("../page/EditTagMember.vue")
            },
            {
                path: "/intelligentTraining/trainListManage",
                component:()=>import("../page/trainListManage.vue")
            },
            {
                path: "/intelligentTraining/strategyManage",
                component:()=>import("../page/strategyList.vue")
            },
            {
                path: "/intelligentTraining/generateFunc",
                component:()=>import("../page/GenerateFuncList.vue")
            }
        ]
    },
    {
        path: "/login",
        component:()=>import("../page/login.vue"),
    }

]

const router = createRouter({
    history: createWebHistory('/ACDC/manage/'),
    routes, // `routes: routes` 的缩写
})

export default router