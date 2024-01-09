from .autoGenRunScript import displayRunScript,genRunScript
from .autoUnzip import unzipAll


def quickScript(ProjectPath):
    portMap = {
        "CrawlerEndPoint": "",
        "CrawlerDispatcher": "8040",
        "ManageBackend": "8041",
        "ManageFrontend": "8042",
        "DisplayBackend": "8043",
        "DisplayFrontend": "8044"
    }
    properties = {}
    properties['mysqlHost'] = "127.0.0.1"
    properties['mysqlPort'] = "3306"
    properties['mysqlUser'] = "ACDC_Manager"
    properties['mysqlPassword'] = ""
    properties['mysqlDatabase'] = "graduate"
    properties['redisHost'] = "42.193.131.202"
    properties['redisPort'] = "6379"
    properties['redisPassword'] = ""
    properties['CrawlerTaskStream'] = "CrawlerTasks"
    properties['CrawlerTaskGroup'] = "consumer"
    properties['resultStream'] = "ResultQueue"
    properties['resultGroup'] = "consumer"
    displayRunScript(ProjectPath, properties, portMap)
    genRunScript(ProjectPath, properties, portMap)

if __name__ == "__main__":
    ProjectPath = ""
    OutPutPath = ""
    unzipAll(ProjectPath, OutPutPath)
    quickScript(OutPutPath)
