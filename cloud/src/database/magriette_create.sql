-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-27 18:42:40.95

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
    unit varchar(3)  NOT NULL,
    CONSTRAINT unique_dataType_data UNIQUE (dataType) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT Data_pk PRIMARY KEY (idData)
);

-- Table: DataSet
CREATE TABLE DataSet (
    id serial  NOT NULL,
    idPatient int  NOT NULL,
    idRoutine int  NOT NULL,
    dateOfRealization timestamp  NOT NULL,
    measurement int  NOT NULL,
    dataOfData timestamp  NOT NULL,
    idData int  NOT NULL,
    CONSTRAINT DataSet_pk PRIMARY KEY (id)
);

-- Table: Medic
CREATE TABLE Medic (
    id int  NOT NULL,
    specialization varchar(30)  NOT NULL,
    license int  NOT NULL,
    availability boolean  NOT NULL,
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
    CONSTRAINT Patient_pk PRIMARY KEY (id)
);

-- Table: Routine
CREATE TABLE Routine (
    idRoutine serial  NOT NULL,
    idUser int  NOT NULL,
    name varchar(15)  NOT NULL,
    totalTime int  NOT NULL,
    difficulty int  NOT NULL,
    CONSTRAINT unique_creator_name UNIQUE (idUser, name) NOT DEFERRABLE  INITIALLY IMMEDIATE,
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
    nickname varchar(15)  NOT NULL,
    dni varchar(9)  NOT NULL,
    firstname varchar(15)  NOT NULL,
    lastname varchar(15)  NOT NULL,
    password varchar(13)  NOT NULL,
    email varchar(50)  NOT NULL,
    user_type char(1)  NOT NULL,
    nativeLanguage varchar(15)  NOT NULL,
    city varchar(15)  NOT NULL,
    CONSTRAINT unique_DNI UNIQUE (dni) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT unique_email UNIQUE (email) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT unique_nickname UNIQUE (nickname) NOT DEFERRABLE  INITIALLY IMMEDIATE,
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

-- Table: medic_patient
CREATE TABLE medic_patient (
    id serial  NOT NULL,
    Medic_id int  NOT NULL,
    Patient_id int  NOT NULL,
    CONSTRAINT unique_medic_patient UNIQUE (Medic_id, Patient_id) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT medic_patient_pk PRIMARY KEY (id)
);

-- Table: routine_accessory
CREATE TABLE routine_accessory (
    id serial  NOT NULL,
    routines_idroutine int  NOT NULL,
    accessories_idaccessory int  NOT NULL,
    CONSTRAINT unique_idRoutine_idAccessory UNIQUE (routines_idroutine, accessories_idaccessory) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT routine_accessory_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: DataSet_Data (table: DataSet)
ALTER TABLE DataSet ADD CONSTRAINT DataSet_Data
    FOREIGN KEY (idData)
    REFERENCES Data (idData)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: DataSet_Patient (table: DataSet)
ALTER TABLE DataSet ADD CONSTRAINT DataSet_Patient
    FOREIGN KEY (idPatient)
    REFERENCES Patient (id)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: DataSet_Routine (table: DataSet)
ALTER TABLE DataSet ADD CONSTRAINT DataSet_Routine
    FOREIGN KEY (idRoutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

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

-- Reference: Routine_Usuario (table: Routine)
ALTER TABLE Routine ADD CONSTRAINT Routine_Usuario
    FOREIGN KEY (idUser)
    REFERENCES Usuario (id)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: SeAsocia_Medic (table: medic_patient)
ALTER TABLE medic_patient ADD CONSTRAINT SeAsocia_Medic
    FOREIGN KEY (Medic_id)
    REFERENCES Medic (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: SeAsocia_Patient (table: medic_patient)
ALTER TABLE medic_patient ADD CONSTRAINT SeAsocia_Patient
    FOREIGN KEY (Patient_id)
    REFERENCES Patient (id)  
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
    FOREIGN KEY (routines_idroutine)
    REFERENCES Routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

