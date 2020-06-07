-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-07 00:52:00.925

-- tables
-- Table: Accessory
CREATE TABLE Accessory (
    idAccessory serial  NOT NULL,
    name varchar(15)  NOT NULL,
    CONSTRAINT unique_name_Accessory UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Accessory_pk PRIMARY KEY (idAccessory)
);

-- Table: AccessoryData
CREATE TABLE AccessoryData (
    id serial  NOT NULL,
    idAccessory int  NOT NULL,
    idData int  NOT NULL,
    CONSTRAINT unique_idAccessory_idData UNIQUE (idAccessory, idData) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT AccessoryData_pk PRIMARY KEY (id)
);

-- Table: Data
CREATE TABLE Data (
    idData serial  NOT NULL,
    dataType varchar(20)  NOT NULL,
    CONSTRAINT Data_pk PRIMARY KEY (idData)
);

-- Table: DataSet
CREATE TABLE DataSet (
    idDataSet serial  NOT NULL,
    dataType varchar(20)  NOT NULL,
    measurement int  NOT NULL,
    unit varchar(3)  NOT NULL,
    CONSTRAINT DataSet_pk PRIMARY KEY (idDataSet)
);

-- Table: Medic
CREATE TABLE Medic (
    idUser int  NOT NULL,
    specialization varchar(30)  NOT NULL,
    license int  NOT NULL,
    CONSTRAINT unique_license UNIQUE (license) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Medic_pk PRIMARY KEY (idUser)
);

-- Table: Patient
CREATE TABLE Patient (
    idUser int  NOT NULL,
    birthdate date  NOT NULL,
    gender char(1)  NOT NULL,
    height int  NOT NULL,
    weight decimal(6,3)  NOT NULL,
    idMedic int  NULL,
    CONSTRAINT Patient_pk PRIMARY KEY (idUser)
);

-- Table: PatientRoutineDataSet
CREATE TABLE PatientRoutineDataSet (
    id serial  NOT NULL,
    idPatient int  NOT NULL,
    idRoutine int  NOT NULL,
    idDataSet int  NOT NULL,
    CONSTRAINT unique_idPatient_idRoutine_idDataSet UNIQUE (idPatient, idRoutine, idDataSet) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT PatientRoutineDataSet_pk PRIMARY KEY (id)
);

-- Table: Routine
CREATE TABLE Routine (
    idRoutine serial  NOT NULL,
    creator varchar(15)  NOT NULL,
    name varchar(15)  NOT NULL,
    totalTime time  NOT NULL,
    difficulty int  NOT NULL,
    CONSTRAINT unique_creator_name UNIQUE (creator, name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Routine_pk PRIMARY KEY (idRoutine)
);

-- Table: RoutineAccessory
CREATE TABLE RoutineAccessory (
    id serial  NOT NULL,
    idRoutine int  NOT NULL,
    idAccessory int  NOT NULL,
    CONSTRAINT unique_idRoutine_idAccessory UNIQUE (idRoutine, idAccessory) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT RoutineAccessory_pk PRIMARY KEY (id)
);

-- Table: Session
CREATE TABLE Session (
    id serial  NOT NULL,
    idRoutine int  NOT NULL,
    name varchar(15)  NOT NULL,
    numberOfSeries int  NOT NULL,
    numberOfRepetitions int  NOT NULL,
    exerciseTime time  NOT NULL,
    breakTime time  NOT NULL,
    CONSTRAINT unique_idRoutine_name UNIQUE (idRoutine, name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Session_pk PRIMARY KEY (id)
);

-- Table: User
CREATE TABLE "User" (
    idUser serial  NOT NULL,
    dni varchar(9)  NOT NULL,
    firstname varchar(15)  NOT NULL,
    lastname varchar(15)  NOT NULL,
    password varchar(13)  NOT NULL,
    email varchar(50)  NOT NULL,
    userType char(1)  NOT NULL,
    CONSTRAINT unique_DNI UNIQUE (dni) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT unique_email UNIQUE (email) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT User_pk PRIMARY KEY (idUser)
);

-- foreign keys
-- Reference: Medico_User (table: Medic)
ALTER TABLE Medic ADD CONSTRAINT Medico_User
    FOREIGN KEY (idUser)
    REFERENCES "User" (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Paciente_User (table: Patient)
ALTER TABLE Patient ADD CONSTRAINT Paciente_User
    FOREIGN KEY (idUser)
    REFERENCES "User" (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: makes_dataSet (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT makes_dataSet
    FOREIGN KEY (idDataSet)
    REFERENCES DataSet (idDataSet)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: makes_patient (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT makes_patient
    FOREIGN KEY (idPatient)
    REFERENCES Patient (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_accesorio (table: AccessoryData)
ALTER TABLE AccessoryData ADD CONSTRAINT mide_accesorio
    FOREIGN KEY (idAccessory)
    REFERENCES Accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_dato (table: AccessoryData)
ALTER TABLE AccessoryData ADD CONSTRAINT mide_dato
    FOREIGN KEY (idData)
    REFERENCES Data (idData)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: patient_medic (table: Patient)
ALTER TABLE Patient ADD CONSTRAINT patient_medic
    FOREIGN KEY (idMedic)
    REFERENCES Medic (idUser)
    ON DELETE  SET NULL 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: realiza_rutina (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT realiza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: sesion_rutina (table: Session)
ALTER TABLE Session ADD CONSTRAINT sesion_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_accesorio (table: RoutineAccessory)
ALTER TABLE RoutineAccessory ADD CONSTRAINT utiliza_accesorio
    FOREIGN KEY (idAccessory)
    REFERENCES Accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_rutina (table: RoutineAccessory)
ALTER TABLE RoutineAccessory ADD CONSTRAINT utiliza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

