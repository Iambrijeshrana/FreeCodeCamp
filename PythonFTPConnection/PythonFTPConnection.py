# -*- coding: utf-8 -*-
"""
Created on Thu Apr 18 02:02:39 2019

@author: Brijesh Rana

############################################################################################################
#1. Check the file availability in FTP Directory and return the flag
#2. Based on the return flag
    #2.1 If file is available then skip the process
    #2.2 If file is not available then check the source directory for file availability
        #2.2.1 If file is available then upload thr File on FTP
############################################################################################################
"""
# Importing Packages
from ftplib import FTP
from datetime import date
from os import path
import time
import datetime

# Credentials to connect to FTP
server = 'FTP Address'
user ='FTP Username'
password='FTP Password'
BINARY_STORE = True

# File Name
today = date.today()
filename = 'FileName'+ today.strftime("%Y%m%d")
print(filename)

def connect_ftp():
    try:
        ftp=FTP(server)
        ftp.login(user, password)
        return ftp
    except Exception as e:
        print(e)

def FTP_file_avail_check():
    try:
        ftp_conn = connect_ftp()
        fileList = []
        ftp_conn.retrlines('LIST',fileList.append)
        file_status=0
        for fname in fileList:
            if filename in fname:
                print('File Available in ftp server')
                file_status=1
        if file_status == 1:
            print('File available in ftp server')
            File_avail_check_in_source()
            #ftp_conn.quit()
            #time.sleep(6)
        else:
            print('File Not available in ftp server')
            File_avail_check_in_source(ftp_conn)
        ftp_conn.quit()
        time.sleep(6)
    except Exception as err:
        print(err)

def upload_file(ftp_connecion, upload_file_path):
    try:
        upload_file = open(upload_file_path, 'rb')
        path_split = upload_file_path.split('/')
        final_file_name = path_split[len(path_split)-1]
        print('Uploading ' + final_file_name +'   ....')
        if BINARY_STORE:
            print(str(datetime.datetime.now()).split('.')[0])
            ftp_connecion.storbinary('STOR ' + final_file_name, upload_file)
            time.sleep(5)
            print(str(datetime.datetime.now()).split('.')[0])
        else:
            ftp_connecion.storlines('STOR ' + final_file_name, upload_file)

        print('Upload finished.')
        #upload_file.close()
    except Exception as err:
        print(err)

def File_avail_check_in_source(ftp_conn):
    try:
        if path.exists('FTP File Path/'+filename+'.zip'):
            print ('File available in source directory')
            print('Call upload_file()')
            #ftp_conn = connect_ftp()
            upload_file(ftp_conn, 'FTP File Path/FileName20190417.zip')
            ftp_conn.quit()
            time.sleep(6)
        else:
            print('File Not available in source directory')
    except Exception as e:
        print(e)
    ftp_conn.quit()   
    time.sleep(6)
FTP_file_avail_check()
