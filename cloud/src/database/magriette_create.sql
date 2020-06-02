-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-01 10:58:30.084

-- tables
-- Table: AccessoryData
CREATE TABLE AccessoryData (
    id serial  NOT NULL,
    idAccessory int  NOT NULL,
    idData int  NOT NULL,
    CONSTRAINT mide_unique_idAccessory_idData UNIQUE (idAccessory, idData) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT AccessoryData_pk PRIMARY KEY (id)
);

-- Table: PatientRoutineDataSet
CREATE TABLE PatientRoutineDataSet (
    id serial  NOT NULL,
    idPatient int  NOT NULL,
    idRoutine int  NOT NULL,
    idDataSet int  NOT NULL,
    CONSTRAINT PatientRoutineDataSet_unique_idPatient_idRoutine_idDataSet UNIQUE (idPatient, idRoutine, idDataSet) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT PatientRoutineDataSet_pk PRIMARY KEY (id)
);

-- Table: RoutineAccessory
CREATE TABLE RoutineAccessory (
    id serial  NOT NULL,
    idRoutine int  NOT NULL,
    idAccessory int  NOT NULL,
    CONSTRAINT utiliza_unique_idRoutine_idAccessory UNIQUE (idRoutine, idAccessory) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT RoutineAccessory_pk PRIMARY KEY (id)
);

-- Table: accessory
CREATE TABLE accessory (
    idAccessory serial  NOT NULL,
    name varchar(15)  NOT NULL,
    CONSTRAINT accessory_pk PRIMARY KEY (idAccessory)
);

-- Table: data
CREATE TABLE data (
    idData int  NOT NULL,
    dataType varchar(20)  NOT NULL,
    CONSTRAINT data_pk PRIMARY KEY (idData)
);

-- Table: dataSet
CREATE TABLE dataSet (
    idDataSet serial  NOT NULL,
    dataType varchar(20)  NOT NULL,
    measurement int  NOT NULL,
    unit varchar(3)  NOT NULL,
    CONSTRAINT dataSet_pk PRIMARY KEY (idDataSet)
);

-- Table: medic
CREATE TABLE medic (
    idUser int  NOT NULL,
    specialization varchar(30)  NOT NULL,
    license int  NOT NULL,
    CONSTRAINT unique_medico_matricula UNIQUE (license) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT medic_pk PRIMARY KEY (idUser)
);

-- Table: patient
CREATE TABLE patient (
    idUser int  NOT NULL,
    birthdate date  NOT NULL,
    gender char(1)  NOT NULL,
    height int  NOT NULL,
    weight decimal(6,3)  NOT NULL,
    idMedic int  NOT NULL,
    CONSTRAINT patient_pk PRIMARY KEY (idUser)
);

-- Table: routine
CREATE TABLE routine (
    idRoutine serial  NOT NULL,
    creatorName varchar(15)  NOT NULL,
    routineName varchar(15)  NOT NULL,
    totalTime time  NOT NULL,
    difficulty int  NOT NULL,
    CONSTRAINT routine_pk PRIMARY KEY (idRoutine)
);

-- Table: session
CREATE TABLE session (
    id serial  NOT NULL,
    idRoutine int  NOT NULL,
    name varchar(15)  NOT NULL,
    numberOfSeries int  NOT NULL,
    numberOfRepetitions int  NOT NULL,
    exerciseTime time  NOT NULL,
    breakTime time  NOT NULL,
    CONSTRAINT sesion_unique_sesion_rutina UNIQUE (idRoutine) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT session_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
    idUser serial  NOT NULL,
    dni varchar(9)  NOT NULL,
    firstname varchar(15)  NOT NULL,
    lastname varchar(15)  NOT NULL,
    password varchar(13)  NOT NULL,
    email varchar(50)  NOT NULL,
    CONSTRAINT unique_user_dni UNIQUE (dni) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT user_pk PRIMARY KEY (idUser)
);

-- foreign keys
-- Reference: Medico_User (table: medic)
ALTER TABLE medic ADD CONSTRAINT Medico_User
    FOREIGN KEY (idUser)
    REFERENCES "user" (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Paciente_User (table: patient)
ALTER TABLE patient ADD CONSTRAINT Paciente_User
    FOREIGN KEY (idUser)
    REFERENCES "user" (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: makes_dataSet (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT makes_dataSet
    FOREIGN KEY (idDataSet)
    REFERENCES dataSet (idDataSet)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: makes_patient (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT makes_patient
    FOREIGN KEY (idPatient)
    REFERENCES patient (idUser)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_accesorio (table: AccessoryData)
ALTER TABLE AccessoryData ADD CONSTRAINT mide_accesorio
    FOREIGN KEY (idAccessory)
    REFERENCES accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_dato (table: AccessoryData)
ALTER TABLE AccessoryData ADD CONSTRAINT mide_dato
    FOREIGN KEY (idData)
    REFERENCES data (idData)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: patient_medic (table: patient)
ALTER TABLE patient ADD CONSTRAINT patient_medic
    FOREIGN KEY (idMedic)
    REFERENCES medic (idUser)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: realiza_rutina (table: PatientRoutineDataSet)
ALTER TABLE PatientRoutineDataSet ADD CONSTRAINT realiza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: sesion_rutina (table: session)
ALTER TABLE session ADD CONSTRAINT sesion_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_accesorio (table: RoutineAccessory)
ALTER TABLE RoutineAccessory ADD CONSTRAINT utiliza_accesorio
    FOREIGN KEY (idAccessory)
    REFERENCES accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_rutina (table: RoutineAccessory)
ALTER TABLE RoutineAccessory ADD CONSTRAINT utiliza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

