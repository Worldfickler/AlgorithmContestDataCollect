import os
import pathlib
RootInfo = {
    "rootUserName" : "",
    "rootUserPassword" : ""
}

EnvMap = {
    "DB_USERNAME":"",
    "DB_PASSWORD":"",
    "DB_URL":"",
    "REDIS_HOST":"",
    "REDIS_PORT":"",
    "REDIS_PASSWORD":"",
    "CRAWLERTASK_STREAM":"",
    "CRAWLERTASK_GROUP":"",
    "RESULTQUEUE_STREAM":"",
    "RESULTQUEUE_GROUP":"",
    "ERRORQUEUE_STREAM":"",
    "ERRORQUEUE_GROUP":"",
    "MANAGER_BACKEND_PORT":"",
    "DISPLAY_BACKEND_PORT":"",
    "CRAWLER_DISPATCHER_PORT":"",
    "MANAGER_FRONTEND_PORT":"",
    "DISPLAY_FRONTEND_PORT":"",
    "WX_BOT_URL":"",
    "LOG_FILE_PATH":""
}
ModuleEnv = {
    "ManageBackend":["DB_USERNAME","DB_PASSWORD","DB_URL",
                    "REDIS_HOST","REDIS_PORT","REDIS_PASSWORD",
                    "MANAGER_BACKEND_PORT","WX_BOT_URL","LOG_FILE_PATH"],
    "DisplayBackend":["DB_USERNAME","DB_PASSWORD","DB_URL","DISPLAY_BACKEND_PORT","LOG_FILE_PATH"],
    "CrawlerEndpoint2" : ["REDIS_HOST","REDIS_PORT","REDIS_PASSWORD",
                       "CRAWLERTASK_STREAM","CRAWLERTASK_GROUP",
                       "RESULTQUEUE_STREAM","RESULTQUEUE_GROUP",
                       "ERRORQUEUE_STREAM","ERRORQUEUE_GROUP",
                       "LOG_FILE_PATH"],
    "CrawlerReceiver" : ["REDIS_HOST","REDIS_PORT","REDIS_PASSWORD","CRAWLERTASK_STREAM",
                        "DB_USERNAME","DB_PASSWORD","DB_URL",
                        "RESULTQUEUE_STREAM","RESULTQUEUE_GROUP",
                        "ERRORQUEUE_STREAM","ERRORQUEUE_GROUP",
                        "LOG_FILE_PATH"],
    "CrawlerDispatcher" :["REDIS_HOST","REDIS_PORT","REDIS_PASSWORD",
                        "CRAWLERTASK_STREAM","CRAWLERTASK_GROUP",
                        "DB_USERNAME","DB_PASSWORD","DB_URL",
                        "RESULTQUEUE_STREAM","RESULTQUEUE_GROUP",
                        "ERRORQUEUE_STREAM","ERRORQUEUE_GROUP",
                        "CRAWLER_DISPATCHER_PORT",
                        "LOG_FILE_PATH"
                        ]

}

def genJavaEnv(jarPath,moduleName,bashPath):
    thisMoudleEnv = ModuleEnv[moduleName]
    if not os.path.exists(bashPath):
        os.mkdir(bashPath)
    f = open(bashPath + "/" + moduleName+".sh","w")
    f.write("#!/bin/bash\n")
    for envName in thisMoudleEnv:
        f.write("export "+envName+"="+EnvMap[envName]+"\n")
    if moduleName == "ManageBackend":
        f.write("export ROOT_USER_NAME={}".format(RootInfo["rootUserName"])+"\n")
        f.write("export ROOT_USER_PASSWORD={}".format(RootInfo["rootUserPassword"])+"\n")
    jarFilePath = pathlib.PosixPath(jarPath + "/" + moduleName + "-latest.jar").absolute()
    f.write("nohup java -jar {} >/dev/null 2>&1 &\n".format(jarFilePath))


def setRootAccount():
    RootInfo["rootUserName"] = input("Please input the root user name:")
    RootInfo["rootUserPassword"] = input("Please input the root user password:")
def genVueEnv(buildPath):
    standardCfg = \
"""
    server {{
        listen {0};
        listen [::]:{0} default_server;
        charset utf-8;
        root {1};
        index index.html;
        location /api/ {{
                proxy_pass http://127.0.0.1:{2}/api/;
        }}
        location / {{
                try_files $uri $uri/ /index.html;
        }}
    }}\n
"""
    MainServiceCfg = \
"""
server {{
    listen 80;
    listen [::]:80 default_server;
    charset utf-8;
    location /ACDC/manage/assets {{
                proxy_pass http://127.0.0.1:{0};
        }}
        location /ACDC/assets {{
                proxy_pass http://127.0.0.1:{1};
        }}
        location /ACDC/manage/ {{
                proxy_pass http://127.0.0.1:{0}/;
        }}
        location /ACDC/ {{
                proxy_pass http://127.0.0.1:{1}/;
        }}
}}\n
""".format(EnvMap["MANAGER_FRONTEND_PORT"],EnvMap["DISPLAY_FRONTEND_PORT"])
    ManagerFrontendPath = pathlib.PosixPath(buildPath + "/ManagerFrontend").absolute()
    DisplayFrontendPath = pathlib.PosixPath(buildPath + "/DisplayFrontend").absolute()
    ManagerFrontendCfg = standardCfg.format(EnvMap["MANAGER_FRONTEND_PORT"],ManagerFrontendPath,EnvMap["MANAGER_BACKEND_PORT"])
    DisplayFrontendCfg = standardCfg.format(EnvMap["DISPLAY_FRONTEND_PORT"],DisplayFrontendPath,EnvMap["DISPLAY_BACKEND_PORT"])
    f = open(buildPath + "/nginx.snippet","w")
    f.write(MainServiceCfg)
    f.write(ManagerFrontendCfg)
    f.write(DisplayFrontendCfg)
    f.flush()
    f.close()

def SetUpEnv(Env):
    EnvMap.update(Env)
