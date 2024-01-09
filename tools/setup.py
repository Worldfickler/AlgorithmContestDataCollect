import os
import git_tools as git_tools
import autoPack as autoPack
import sys
from autoGenEnvRunScript import *
import argparse
import json
parser = argparse.ArgumentParser(description="setup tools for ACDC")
workPath = ""
targetPath = ""
configPath = ""

parser.add_argument("-w", "--workPath", help="the path to the folder the poject is in",required=True)
parser.add_argument("-t", "--targetPath", help="the path to the folder you want to put the packages in",required=True)
parser.add_argument("-c", "--configPath", help="the path to the folder you want to put the config files in",required=True)
javaProject = ["CrawlerEndpoint2", "CrawlerDispatcher", "ManageBackend", "DisplayBackend", "CrawlerReceiver"]


def Main():
    for project in javaProject:
        JavaPack(project)
        genJavaEnv(targetPath,project,targetPath + "/bash")
    VuePack("ManageFrontend")
    VuePack("DisplayFrontend")
    genVueEnv(targetPath)

def JavaPack(moduleName):
    autoPack.packJAVAAndCopy(moduleName, targetPath)

def VuePack(moduleName):
    autoPack.packVueAndCopy("./"+ moduleName,moduleName, targetPath)

def ChangeWorkPath(path):
    os.chdir(path)

if __name__ == '__main__':
    args = parser.parse_args()
    if args.configPath:
        env = json.load(open(args.configPath,"r"))
        SetUpEnv(env)
    if args.workPath:
        workPath = args.workPath
    if args.targetPath:
        targetPath = args.targetPath
    ChangeWorkPath(workPath)
    setRootAccount()
    Main()