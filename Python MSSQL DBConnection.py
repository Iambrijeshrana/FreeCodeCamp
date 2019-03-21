# -*- coding: utf-8 -*-
"""
Created on Thu Mar 21 15:08:46 2019

@author: Brijesh Rana
"""

#How to Connect Python and SQL Server

import pymssql
import pandas as pd

## instance a python db connection object- same form as psycopg2/python-mysql drivers also
conn = pymssql.connect(server="Server Name", user="User Name",password="Password", port=1433)  # You can lookup the port number inside SQL server. 

## Hey Look, college data
stmt = "SELECT *  FROM [Database Name].[Schema Name].[Table Name]"
# Excute Query here
df = pd.read_sql(stmt,conn)

df.head(5)
