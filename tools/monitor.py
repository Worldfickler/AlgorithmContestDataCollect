import os,sys
import time
import git_tools
import autoPack
import autoUpdate
javaModule = ["CrawlerEndpoint2","CrawlerDispatcher","ManageBackend","DisplayBackend","CrawlerReceiver"]
def module_is_running(moduleName):
    output = os.popen("ps -ef|grep {}".format(moduleName))
    lines = output.read()
    for line in lines.splitlines():
        if line.find("grep") != -1:
            continue
        if line.find("jar") != -1:
            return int(line.split()[1])
    return -1

workPath = ""
def main():
    lastTime = 0
    while True :
        thisTime = time.time()
        if thisTime - lastTime < 30:
            lastTime = thisTime
            time.sleep(30)
        for module in javaModule:
            repo = git_tools.git_getrepo(workPath+"/"+module)
            if not git_tools.judge_remote_local(repo):
                print(module+"版本发生变化")
                git_tools.git_pull(repo)
                print("拉取成功")
                flag=module_is_running(module)
                if flag!=-1:
                    os.system("kill -9 {}".format(flag))
                print("杀死旧线程成功")
                autoPack.packJAVAAndCopy(module, workPath+"/build")
                print("打包jar包成功")
            pid = module_is_running(module)
            if pid == -1:
                os.system("bash {}/build/bash/{}.sh".format(workPath,module))
                print("{} is not running, restart it".format(module))
        autoUpdate.VuePack("ManageFrontend",workPath+"/build")
        autoUpdate.VuePack("DisplayFrontend",workPath+"/build")
if __name__ == "__main__":
    workPath = sys.argv[1]
    os.chdir(workPath)
    os.system("")
    main()
