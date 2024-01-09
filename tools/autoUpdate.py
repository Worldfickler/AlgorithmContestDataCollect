import os
import git_tools as git_tools
import autoPack as autoPack
import sys


def Main(targetPath):
    JavaPack("CrawlerEndpoint2",targetPath)
    JavaPack("CrawlerDispatcher",targetPath)
    JavaPack("ManageBackend",targetPath)
    JavaPack("DisplayBackend",targetPath)
    JavaPack("CrawlerReceiver",targetPath)
    VuePack("ManageFrontend",targetPath)
    VuePack("DisplayFrontend",targetPath)

def JavaPack(moduleName,targetPath):
    repo = git_tools.git_getrepo("./"+moduleName)
    if not git_tools.judge_remote_local(repo):
        git_tools.git_pull(repo)
    autoPack.packJAVAAndCopy(moduleName, targetPath)

def VuePack(moduleName,targetPath):
    repo = git_tools.git_getrepo("./"+moduleName)
    if not git_tools.judge_remote_local(repo):
        git_tools.git_pull(repo)
        autoPack.packVueAndCopy("./"+ moduleName,moduleName, targetPath)

def ChangeWorkPath(path):
    os.chdir(path)

if __name__ == '__main__':
    ChangeWorkPath("./")
    targetPath = "./build"
    Main(targetPath)
