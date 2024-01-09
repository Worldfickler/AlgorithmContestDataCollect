import os

def unzipAll(archive, destination):
    li = os.listdir(archive)
    for l in li :
        if l.endswith(".zip"):
            os.system("unzip -o " + archive + "/" + l + " -d " + destination)
            
def main():
    archive = input("Enter the path to the folder you want to unzip: ")
    destination = input("Enter the path to the folder you want to put the unzipped files in: ")
    unzipAll(archive, destination)
    
    
    
if __name__ == "__main__":
    main()

