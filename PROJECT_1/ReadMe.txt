-----------------------------------
Welcome
-----------------------------------
Author: Mark Bedoya
-----------------------------------

-----------------------
INTRO:
-----------------------
This is my Project_1 (Employee Reimbursement System)

This project provides a web aplication for employees to submit reimbursement request called "tickets" 
on purcahses the company may or may not reimburse.
A manager can then accept or deny these requests.

The basic requirements for this projects were all met and can be seen in the 
ERS requirements.txt provided.

-----------------------
FEATURES:
-----------------------
Employees can submit images with their requests and a sample image is provided
in the "receipt images" folder. 
No images were used in the .sql document and so to see images in the tables you must create a new ticket with an image. 
I highly suggest that you do to see how awesome it is to view the images. (click them in the tables after new ticket is made)

-----------------------
HOW TO USE:
-----------------------
1.
The database used is a localhost Oracle11g.
The database interface used is the Oracle SQL Developer. 
The database files are in the database folder.
There is only one .sql document that can simply be run to setup a user 
and create all the tables and procedures this aplication uses.

2. 
The connection user and password to this database is hard coded into the connections.util pacakage. 
Simply set up the database and run the projecT and navagate to the URL. 
example:     http://localhost:8084/PROJECT_1

3. 
There are 4 employees setup in the database. 
Two managers and Two employees. 
This is there login info to start and play around.
ENJOY!


user: mark (regular employee)
password: password

user: kyle (regular employee)
password: password

user: john (manager)
password: password

user: derrick (manager)
password: password
