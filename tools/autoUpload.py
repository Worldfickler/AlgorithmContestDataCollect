import os

def upload(uploadPath, username, publicKeyPath, serverIP, serverPath):
    print("Uploading...")
    os.system("scp -r -i " + publicKeyPath + " " + uploadPath +
              " " + username + "@" + serverIP + ":" + serverPath)
    print("Upload complete")
    

def main():
    uploadPath = input("Enter the path to the folder you want to upload: ")
    username = input("Enter the username: ")
    publicKeyPath = input("Enter the path to the public key: ")
    serverIP = input("Enter the IP you want to serve on: ")
    serverPath = input("Enter the path you want to serve on: ")
    upload(uploadPath, username, publicKeyPath, serverIP, serverPath)
    
if __name__ == "__main__":
    main()
