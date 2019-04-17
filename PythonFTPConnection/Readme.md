This project is devloped to automate the process of file uploading to FTP.

#1. Check the file availability in FTP Directory and return the flag
#2. Based on the return flag
    #2.1 If file is available then skip the process
    #2.2 If file is not available then check the source directory for file availability
        #2.2.1 If file is available then upload thr File on FTP