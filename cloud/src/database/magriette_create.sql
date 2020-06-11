-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-10 13:55:21.964

-- tables
-- Table: Accessory
CREATE TABLE Accessory (
    idAccessory serial  NOT NULL,
    name varchar(15)  NOT NULL,
    CONSTRAINT unique_name_Accessory UNIQUE (name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Accessory_pk PRIMARY KEY (idAccessory)
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
    id int  NOT NULL,
    specialization varchar(30)  NOT NULL,
    license int  NOT NULL,
    CONSTRAINT unique_license UNIQUE (license) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Medic_pk PRIMARY KEY (id)
);

-- Table: Patient
CREATE TABLE Patient (
    id int  NOT NULL,
    birthdate date  NOT NULL,
    gender char(1)  NOT NULL,
    height int  NOT NULL,
    weight decimal(6,3)  NOT NULL,
    medic_id int  NULL,
    CONSTRAINT Patient_pk PRIMARY KEY (id)
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
    totalTime int  NOT NULL,
    difficulty int  NOT NULL,
    CONSTRAINT unique_creator_name UNIQUE (creator, name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Routine_pk PRIMARY KEY (idRoutine)
);

-- Table: Session
CREATE TABLE Session (
    id serial  NOT NULL,
    idRoutine int  NOT NULL,
    name varchar(15)  NOT NULL,
    numberOfSeries int  NOT NULL,
    numberOfRepetitions int  NOT NULL,
    exerciseTime int  NOT NULL,
    breakTime int  NOT NULL,
    CONSTRAINT unique_idRoutine_name UNIQUE (idRoutine, name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Session_pk PRIMARY KEY (id)
);

-- Table: Usuario
CREATE TABLE Usuario (
    id serial  NOT NULL,
    dni varchar(9)  NOT NULL,
    firstname varchar(15)  NOT NULL,
    lastname varchar(15)  NOT NULL,
    password varchar(13)  NOT NULL,
    email varchar(50)  NOT NULL,
    user_type char(1)  NOT NULL,
    CONSTRAINT unique_DNI UNIQUE (dni) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT unique_email UNIQUE (email) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Usuario_pk PRIMARY KEY (id)
);

-- Table: accessory_data
CREATE TABLE accessory_data (
    id serial  NOT NULL,
    accessories_idaccessory int  NOT NULL,
    data_iddata int  NOT NULL,
    CONSTRAINT unique_idAccessory_idData UNIQUE (accessories_idaccessory, data_iddata) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT accessory_data_pk PRIMARY KEY (id)
);

-- Table: routine_accessory
CREATE TABLE routine_accessory (
    id serial  NOT NULL,
    routine_idroutine int  NOT NULL,
    accessories_idaccessory int  NOT NULL,
    CONSTRAINT unique_idRoutine_idAccessory UNIQUE (routine_idroutine, accessories_idaccessory) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT routine_accessory_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Medico_User (table: Medic)
ALTER TABLE Medic ADD CONSTRAINT Medico_User
    FOREIGN KEY (id)
    REFERENCES Usuario (id)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Paciente_User (table: Patient)
ALTER TABLE Patient ADD CONSTRAINT Paciente_User
    FOREIGN KEY (id)
    REFERENCES Usuario (id)
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
    REFERENCES Patient (id)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_accesorio (table: accessory_data)
ALTER TABLE accessory_data ADD CONSTRAINT mide_accesorio
    FOREIGN KEY (accessories_idaccessory)
    REFERENCES Accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_dato (table: accessory_data)
ALTER TABLE accessory_data ADD CONSTRAINT mide_dato
    FOREIGN KEY (data_iddata)
    REFERENCES Data (idData)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: patient_medic (table: Patient)
ALTER TABLE Patient ADD CONSTRAINT patient_medic
    FOREIGN KEY (medic_id)
    REFERENCES Medic (id)
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

-- Reference: utiliza_accesorio (table: routine_accessory)
ALTER TABLE routine_accessory ADD CONSTRAINT utiliza_accesorio
    FOREIGN KEY (accessories_idaccessory)
    REFERENCES Accessory (idAccessory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_rutina (table: routine_accessory)
ALTER TABLE routine_accessory ADD CONSTRAINT utiliza_rutina
    FOREIGN KEY (routine_idroutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

