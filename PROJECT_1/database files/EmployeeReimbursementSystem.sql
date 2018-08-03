/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP USER ERS CASCADE;

/*******************************************************************************
   Create database
********************************************************************************/
CREATE USER ERS
IDENTIFIED BY p4ssw0rd;

GRANT connect to ERS;
GRANT resource to ERS;
GRANT create view TO ERS;

conn ERS/p4ssw0rd

/*******************************************************************************
   Create Tables
********************************************************************************/

CREATE TABLE Employee
(
    EmployeeId NUMBER NOT NULL,
    FirstName VARCHAR2(40) NOT NULL,
    LastName VARCHAR2(40) NOT NULL,
    Title VARCHAR2(30),
    Address VARCHAR2(70),
    City VARCHAR2(40),
    State VARCHAR2(40),
    Country VARCHAR2(40),
    PostalCode VARCHAR2(10),
    Phone VARCHAR2(24),
    Email VARCHAR2(60),
    EmployeeUsername VARCHAR2(40) UNIQUE NOT NULL,
    EmployeePassword VARCHAR2(40) NOT NULL,
    CONSTRAINT PK_Employee PRIMARY KEY  (EmployeeId)
);

CREATE TABLE Ticket
(
    TicketId NUMBER NOT NULL,
    TicketType VARCHAR2(40) NOT NULL,
    TicketDescription VARCHAR2(500) NOT NULL,
    TicketStatus VARCHAR2(100) NOT NULL,
    TotalAmount NUMBER NOT NULL,
    EmployeeId NUMBER NOT NULL,
    ManagerId NUMBER,
    Image clob,
    CONSTRAINT PK_Ticket PRIMARY KEY  (TicketId),
    CONSTRAINT FK_EmployeeId FOREIGN KEY (EmployeeId) REFERENCES Employee (EmployeeId) ON DELETE CASCADE
);

/*******************************************************************************
   Create Primary Key Unique Indexes
********************************************************************************/
-- already in tables
/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
--already in tables 

/*******************************************************************************
   Populate Tables
********************************************************************************/
INSERT INTO Employee (EmployeeId, FirstName, LastName, EmployeeUsername, EmployeePassword) VALUES (1, 'Mark', 'Bedoya', 'mark', 'password');
INSERT INTO Employee (EmployeeId, FirstName, LastName, Title, EmployeeUsername, EmployeePassword) VALUES (2, 'John', 'Oberholtzer', 'Manager', 'john', 'password');
INSERT INTO Employee (EmployeeId, FirstName, LastName, EmployeeUsername, EmployeePassword) VALUES (3, 'Kyle', 'Butterfield', 'kyle', 'password');
INSERT INTO Employee (EmployeeId, FirstName, LastName, Title, EmployeeUsername, EmployeePassword) VALUES (4, 'Derrick', 'Cheung', 'Manager', 'derrick', 'password');

INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (1, 'Business Expense', 'some bussiness description', 'Accepted',345.45, 1, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (2, 'Auto Mileage and Travel', 'some auto description', 'Pending',134.12, 1);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (3, 'Medical Expense', 'some medical description', 'Denied',999.62, 1, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (4, 'Business Expense', 'some bussiness description', 'Accepted',224.13, 1, 4);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (5, 'Auto Mileage and Travel', 'some auto description', 'Pending',67.92, 1);

INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (6, 'Business Expense', 'some bussiness description', 'Accepted',734.45, 3, 4);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (7, 'Auto Mileage and Travel', 'some auto description', 'Pending',23.27, 3);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (8, 'Medical Expense', 'some medical description', 'Denied',513.13, 3, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (9, 'Auto Mileage and Travel', 'some auto description', 'Pending',952.12, 3);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId, ManagerId) VALUES (10, 'Medical Expense', 'some medical description', 'Denied',4.62, 3, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (11, 'Auto Mileage and Travel', 'some auto description', 'Pending',784.35, 3);

INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (12, 'Auto Mileage and Travel', 'some auto description', 'Pending',173.99, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (13, 'Medical Expense', 'some medical description', 'Pending',56.87, 2);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (14, 'Auto Mileage and Travel', 'some auto description', 'Pending',134.42, 2);

INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (15, 'Auto Mileage and Travel', 'some auto description', 'Pending',78.99, 4);
INSERT INTO Ticket (TicketId, TicketType, TicketDescription, TicketStatus, TotalAmount, EmployeeId) VALUES (16, 'Business Expense', 'some bussiness description', 'Pending',135.99, 4);


/*******************************************************************************
   Sequence and Trigger
********************************************************************************/
-- Employee
CREATE SEQUENCE SQ_Employee_PK START WITH 5 INCREMENT BY 1;
-- Trigger (before insert, use sequence)
CREATE OR REPLACE TRIGGER TR_INSERT_Employee
BEFORE INSERT ON Employee
FOR EACH ROW
BEGIN
    SELECT SQ_Employee_PK.NEXTVAL INTO :NEW.EmployeeId FROM DUAL;
END;
/
-- BankAccount
CREATE SEQUENCE SQ_Ticket_PK START WITH 17 INCREMENT BY 1;
-- Trigger (before insert, use sequence)
CREATE OR REPLACE TRIGGER TR_INSERT_Ticket
BEFORE INSERT ON Ticket
FOR EACH ROW
BEGIN
    SELECT SQ_Ticket_PK.NEXTVAL INTO :NEW.TicketId FROM DUAL;
END;
/


/*******************************************************************************
   Procedure
********************************************************************************/


-- Stored Procedure SP_OPEN_BANK_ACCOUNT
-- INPUTS: AccountName, Balance, CustomerId
-- create new bank account
-- Add new bankAccount in BankAccount table
CREATE OR REPLACE PROCEDURE SP_ADD_TICKET
(T_Type IN VARCHAR2, T_Description IN VARCHAR2, T_Amount IN NUMBER, E_ID IN NUMBER, I in CLOB) AS
BEGIN
    --SAVEPOINT;
    
    INSERT INTO Ticket (TICKETId, TICKETTYPE, TICKETDESCRIPTION, TicketStatus, TOTALAMOUNT, EMPLOYEEId, IMAGE) VALUES (1, T_Type, T_Description, 'Pending', T_Amount, E_ID, I);
    
    --ROLLBACK    
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE SP_DELETE_TICKET
(T_ID IN Number) AS
BEGIN
    --SAVEPOINT;
    
    DELETE FROM TICKET WHERE TICKETID = T_ID; 
    
    --ROLLBACK    
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE SP_Update_Employee
(e_id IN NUMBER, e_phone IN Varchar2, e_email IN varchar2, e_address IN varchar2, e_city IN varchar2, e_state IN varchar2, e_country IN varchar2, e_postalcode IN varchar2) AS
BEGIN
    --SAVEPOINT;
    
    UPDATE Employee SET phone = e_phone, email = e_email, address = e_address, city = e_city, state = e_state, country = e_country, postalcode = e_postalcode
        WHERE EmployeeId = e_id;
    --ROLLBACK    
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE SP_Update_Accepted_Ticket
(T_id IN NUMBER, M_id IN NUMBER) AS
BEGIN
    --SAVEPOINT;
    
    UPDATE Ticket SET ticketStatus = 'Accepted', ManagerId = M_id
        WHERE TicketId = T_id;
    --ROLLBACK    
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE SP_Update_Pending_Ticket
(T_id IN NUMBER) AS
BEGIN
    --SAVEPOINT;
    
    UPDATE Ticket SET ticketStatus = 'Pending', ManagerId = ''
        WHERE TicketId = T_id;
    --ROLLBACK    
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE SP_Update_Denied_Ticket
(T_id IN NUMBER, M_id IN NUMBER) AS
BEGIN
    --SAVEPOINT;
    
    UPDATE Ticket SET ticketStatus = 'Denied', ManagerId = M_id
        WHERE TicketId = T_id;
    --ROLLBACK    
    COMMIT;
END;
/









-- FOR debug --
--SELECT * FROM Ticket WHERE TicketStatus = 'Accepted' OR TicketStatus = 'Denied';
--SELECT * FROM Ticket WHERE TicketStatus = 'Denied';



commit;
exit;