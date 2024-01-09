import os
import platform
import pathlib


def autoPack(pathStr,outputPath):
    funList = []
    path = pathlib.Path(pathStr)
    if not path.is_dir():
        print("The path you entered is not a directory")
        return
    for modulename, fun in subModuleFunMap.items():
        if checkChildFolder(path, modulename) and checkPackIt(modulename):
            funList.append(packCloser(fun, path.joinpath(
                modulename), modulename, outputPath))
    if platform.system() == "Windows":
        os.system("powershell -Command  \"& rm -r "+outputPath+" \"")
        os.system("powershell -Command  \"& mkdir "+outputPath+" \"")
    else:
        os.system("rm -r "+outputPath)
        os.system("mkdir "+outputPath)
    for fun in funList:
        fun()
    
def main():
    pathStr = input("Enter the path to the folder the poject is in: ")
    outputPath = input("Enter the path to the folder you want to put the packages in: ")
    autoPack(pathStr,outputPath)


def packJAVA(path,moduleName,outputPath):
    posix_path = path.as_posix()
    os.system("cd " + posix_path + " && mvn package -Dmaven.test.skip=true ")
    os.system("zip -r {}/{}.zip {}/target".format(posix_path,moduleName, posix_path))
    
    if platform.system() == "Windows":
        os.system("powershell -Command  \"& mv "+posix_path +
                  "/"+moduleName+".zip "+outputPath+" \"")
    else:
        os.system("mv "+ posix_path +
                  "/"+moduleName+".zip "+outputPath)

def packJAVAAndCopy(moduleName,copyPath):
    path = pathlib.Path("./" + moduleName)
    posix_path = path.absolute().as_posix()
    os.system("cd " + posix_path + " && mvn package -Dmaven.test.skip=true ")
    os.system("cp {}/target/*.jar {}/{}-latest.jar".format(posix_path, copyPath,moduleName))




def packJAVAAssemible(path, moduleName, outputPath):
    posix_path = path.as_posix()
    os.system("cd " + posix_path + " && mvn package")
    os.system("zip -r {}/{}.zip {}/target".format(posix_path,
                                                  moduleName, posix_path))

    if platform.system() == "Windows":
        os.system("powershell -Command  \"& mv "+posix_path +
                  "/"+moduleName+".zip "+outputPath+" \"")
    else:
        os.system("mv " + posix_path +
                  "/"+moduleName+".zip "+outputPath)

def packVue(path,moduleName,outputPath):
    posix_path = path.as_posix()
    os.system("cd " + posix_path + " &&npm install && npm run build ")
    os.system("zip -r {}/{}.zip {}/dist".format(posix_path,
                                                  moduleName, posix_path))

    if platform.system() == "Windows":
        os.system("powershell -Command  \"& mv "+posix_path +
                  "/"+moduleName+".zip "+outputPath+" \"")
    else:
        os.system("mv " + posix_path +
                  "/"+moduleName+".zip "+outputPath)

def packVueAndCopy(path,moduleName,copyPath):
    path = pathlib.Path(path)
    posix_path = path.absolute().as_posix()
    os.system("cd " + posix_path + " &&npm install && npm run build ")
    os.system("rm -rf {1}/{2} && mkdir {1}/{2} && cp -r {0}/dist/* {1}/{2}/".format(posix_path,copyPath,moduleName))

subModuleFunMap = {
    "CrawlerEndPoint": packJAVA,
    "CrawlerDispatcher":packJAVA,
    "ManageBackend":packJAVA,
    "DisplayBackend":packJAVA,
    "ManageFrontend":packVue,
    "DisplayFrontend":packVue
}


def packCloser(fun,path,moduleName,outputPath):
    def closer():
        fun(path,moduleName,outputPath)
    return closer
    
    


def checkChildFolder(path, folderName):
    childPath = path.joinpath(folderName)
    if not childPath.is_dir():
        print("The path you entered does not contain the " + folderName + " folder")
        return False
    return True


def checkPackIt(name):
    isPack = input("Do you want to pack the"+name+"? (y/n): ")
    if isPack == "y":
        return True
    return False
    
if __name__ == "__main__":
    main()
    
