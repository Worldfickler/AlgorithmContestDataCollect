from autoPack import autoPack
from autoUpload import upload


if __name__ == "__main__":
    autoPack("E:\\AlgorithmContestDataCollect", "E:\\AlgorithmContestDataCollect\\pack")
    upload("E:\\AlgorithmContestDataCollect\\pack", "acmdata-uploader",
           "~\\.ssh\\id_rsa", "buctoj.com", "~")


