import os

def existPath(path):
    return os.path.exists(path)

def genEndPoint(ProjectPath,properties):
    EndPointPath = ""
    if existPath(ProjectPath + "/CrawlerEndPoint"):
        EndPointPath = ProjectPath + "/CrawlerEndPoint"
    else:
        print("The path you entered does not contain the CrawlerEndPoint folder")
        return
    run_script = "java -jar "
    for root, dirs, files in os.walk(EndPointPath):
        for file in files:
            if file.endswith(".jar") :
                run_script += root + "/" + file
    run_script += " --redis.host=" + properties['redisHost']
    run_script += " --redis.port=" + properties['redisPort']
    run_script += " --redis.password=" + properties['redisPassword']
    run_script += " --CrawlerTask.stream=" + properties['CrawlerTaskStream']
    run_script += " --CrawlerTask.group=" + properties['CrawlerTaskGroup']
    run_script += " --result.stream=" + properties['resultStream']  
    run_script += " --result.group=" + properties['resultGroup']
    
    return run_script


def genDispatcher(ProjectPath, properties):
    DispatchPath = ""
    if existPath(ProjectPath + "/CrawlerDispatcher"):
        DispatchPath = ProjectPath + "/CrawlerDispatcher"
    else:
        print("The path you entered does not contain the CrawlerDispatcher folder")
        return
    run_script = "java -jar "
    for root, dirs, files in os.walk(DispatchPath):
        for file in files:
            if file.endswith(".jar") :
                run_script += root + "/" + file
    run_script += " --spring.datasource.username=" + properties['mysqlUser']
    run_script += " --spring.datasource.password=" + properties['mysqlPassword']
    run_script += " --spring.datasource.url=jdbc:mysql://" + properties['mysqlHost'] + ":" + properties['mysqlPort'] + "/" + properties['mysqlDatabase']
    run_script += " --spring.redis.host=" + properties['redisHost']
    run_script += " --spring.redis.port=" + properties['redisPort']
    run_script += " --spring.redis.password=" + properties['redisPassword']
    run_script += " --CrawlerTask.stream=" + properties['CrawlerTaskStream']
    run_script += " --CrawlerTask.group=" + properties['CrawlerTaskGroup']
    run_script += " --result.stream=" + properties['resultStream']
    run_script += " --result.group=" + properties['resultGroup']
    return run_script
    
def genManageBackend(ProjectPath, properties):
    ManagerBackendPath = ""
    if existPath(ProjectPath + "/ManageBackend"):
        ManagerBackendPath = ProjectPath + "/ManageBackend"
    else:
        print("The path you entered does not contain the ManageBackend folder")
        return
    run_script = "java -jar "
    for root, dirs, files in os.walk(ManagerBackendPath):
        for file in files:
            if file.endswith(".jar"):
                run_script += root + "/" + file
    run_script += " --spring.datasource.username=" + properties['mysqlUser']
    run_script += " --spring.datasource.password=" + properties['mysqlPassword']
    run_script += " --spring.datasource.url=jdbc:mysql://" + properties['mysqlHost'] + ":" + properties['mysqlPort'] + "/" + properties['mysqlDatabase']
    
    return run_script

def genDisplayBackend(ProjectPath, properties):
    DisplayBackendPath = ""
    if existPath(ProjectPath + "/DisplayBackend"):
        DisplayBackendPath = ProjectPath + "/DisplayBackend"
    else:
        print("The path you entered does not contain the DisplayBackend folder")
        return
    run_script = "java -jar "
    for root, dirs, files in os.walk(DisplayBackendPath):
        for file in files:
            if file.endswith(".jar"):
                run_script += root + "/" + file
    run_script += " --spring.datasource.username=" + properties['mysqlUser']
    run_script += " --spring.datasource.password=" + properties['mysqlPassword']
    run_script += " --spring.datasource.url=jdbc:mysql://" + properties['mysqlHost'] + ":" + properties['mysqlPort'] + "/" + properties['mysqlDatabase']
    
    return run_script

def genDisplayFrontend(ProjectPath, properties):
    DisplayFrontendPath = ""
    if existPath(ProjectPath + "/DisplayFrontend"):
        DisplayFrontendPath = ProjectPath + "/DisplayFrontend/dist"
    else:
        print("The path you entered does not contain the DisplayFrontend folder")
        return
    
    nginx_config = """server {{
        listen {};
        listen [::]:{} default_server;
        charset utf-8;
        root {};
        index index.html;
        location /api/ {{
                proxy_pass http://127.0.0.1:{}/;
        }}
        location / {{
                try_files $uri $uri/ /index.html;
        }}
    }}
    """.format(properties['DisplayFrontend'], properties['DisplayFrontend'], DisplayFrontendPath, properties['DisplayBackend'])
    
    return nginx_config

def genManageFrontend(ProjectPath, properties):
    ManageFrontendPath = ""
    if existPath(ProjectPath + "/ManageFrontend"):
        ManageFrontendPath = ProjectPath + "/ManageFrontend/dist"
    else:
        print("The path you entered does not contain the ManageFrontend folder")
        return
    
    nginx_config = """server {{
        listen {};
        listen [::]:{} default_server;
        charset utf-8;
        root {};
        index index.html;
        location /api/ {{
                proxy_pass http://127.0.0.1:{}/;
        }}
        location / {{
                try_files $uri $uri/ /index.html;
        }}
    }}
    """.format(properties['ManageFrontend'], properties['ManageFrontend'], ManageFrontendPath, properties['ManageBackend'])
    
    return nginx_config

                
    
def getProperty(portMap):
    properties = {}
    if "ManageBackend" in portMap or "DisplayBackend" in portMap or "CrawlerDispatcher" in portMap:
        properties['mysqlHost'] = input("Enter the mysql host: ")
        properties['mysqlPort'] = input("Enter the mysql port: ")
        properties['mysqlUser'] = input("Enter the mysql user: ")
        properties['mysqlPassword'] = input("Enter the mysql password: ")
        properties['mysqlDatabase'] = input("Enter the mysql database: ")
    if "CrawlerDispatcher" in portMap or "CrawlerEndPoint" in portMap:
        properties['redisHost'] = input("Enter the redis host: ")
        properties['redisPort'] = input("Enter the redis port: ")
        properties['redisPassword'] = input("Enter the redis password: ")
        properties['CrawlerTaskStream'] = input("Enter the CrawlerTaskStreamName: ")
        properties['CrawlerTaskGroup'] = input("Enter the CrawlerTaskStreamGroup: ")
        properties['resultStream'] = input("Enter the result stream name: ")
        properties['resultGroup'] = input("Enter the result stream group: ")
        
    return properties

def whichToGen():
    portMap = {}
    
    shouldGenEndPoint = input("Do you want to generate the EndPoint? (y/n): ")
    if shouldGenEndPoint == "y":
        portMap['CrawlerEndPoint'] = ""
    
    shouldGenDispatcher = input("Do you want to generate the Dispatcher? (y/n): ")
    if shouldGenDispatcher == "y":
        portMap['CrawlerDispatcher'] = input("Enter the port of the Dispatcher: ")
    
    shouldGenManageModule = input("Do you want to generate the ManageModule? (y/n): ")
    if shouldGenManageModule == "y":
        portMap['ManageBackend'] = input("Enter the port of the ManageBackend: ")
        portMap['ManageFrontend'] = input("Enter the port of the ManageFrontend: ")
    
    shouldGenDisplayModule = input("Do you want to generate the DisplayModule? (y/n): ")
    if shouldGenDisplayModule == "y":
        portMap['DisplayBackend'] = input("Enter the port of the DisplayBackend: ")
        portMap['DisplayFrontend'] = input("Enter the port of the DisplayFrontend: ")
    return portMap
    
def genRunScript(ProjectPath, properties,portMap):
    properties.update(portMap)
    run_script = ""
    if "CrawlerEndPoint" in portMap:
        run_script += "nohup " + genEndPoint(ProjectPath, properties) + " &\n"
    if "CrawlerDispatcher" in portMap:
        run_script += "nohup " + genDispatcher(ProjectPath, properties) + " &\n"
    if "ManageBackend" in portMap:
        run_script += "nohup " + genManageBackend(ProjectPath, properties) + " &\n"
    if "DisplayBackend" in portMap:
        run_script += "nohup " + genDisplayBackend(ProjectPath, properties) + " &\n"
    with open(ProjectPath + "/run.sh", "w") as f:
        f.write(run_script)
    
    nginxConfig = ""
    if "DisplayFrontend" in portMap:
        nginxConfig += genDisplayFrontend(ProjectPath, properties)
        nginxConfig += "\n"
    if "ManageFrontend" in portMap:
        nginxConfig += genManageFrontend(ProjectPath, properties)
        nginxConfig += "\n"
    with open(ProjectPath + "/part.conf", "w") as f:
        f.write(nginxConfig)


def displayRunScript(ProjectPath, properties, portMap):
    properties.update(portMap)
    displayStr = ""
    if "CrawlerEndPoint" in portMap:
        displayStr += genEndPoint(ProjectPath, properties)
        displayStr += "\n"
    if "CrawlerDispatcher" in portMap:
        displayStr += genDispatcher(ProjectPath, properties)
        displayStr += "\n"
    if "ManageBackend" in portMap:
        displayStr += genManageBackend(ProjectPath, properties)
        displayStr += "\n"
    if "DisplayBackend" in portMap:
        displayStr += genDisplayBackend(ProjectPath, properties)
        displayStr += "\n"
    print(displayStr)
        
def main():
    ProjectPath = input("Enter the path to the folder you want to pack: ")
    portMap = whichToGen()
    properties = getProperty(portMap)
    displayRunScript(ProjectPath, properties, portMap)
    genRunScript(ProjectPath, properties, portMap)
    


if __name__ == "__main__":
    main()
