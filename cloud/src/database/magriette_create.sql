-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-05-26 22:23:58.356

-- tables
-- Table: accesory
CREATE TABLE accesory (
    idAccesory serial  NOT NULL,
    name varchar(15)  NOT NULL,
    CONSTRAINT accesory_pk PRIMARY KEY (idAccesory)
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
    idMakes int  NOT NULL,
    dataType varchar(20)  NOT NULL,
    measurement int  NOT NULL,
    unit varchar(3)  NOT NULL,
    CONSTRAINT dataSet_pk PRIMARY KEY (idDataSet)
);

-- Table: isAssociated
CREATE TABLE isAssociated (
    idAssociated serial  NOT NULL,
    medic_idUser int  NOT NULL,
    patient_idUser int  NOT NULL,
    CONSTRAINT isAssociated_pk PRIMARY KEY (idAssociated)
);

-- Table: makes
CREATE TABLE makes (
    idMakes serial  NOT NULL,
    idUser int  NOT NULL,
    idRoutine int  NOT NULL,
    CONSTRAINT makes_pk PRIMARY KEY (idMakes)
);

-- Table: measure
CREATE TABLE measure (
    idMeasure serial  NOT NULL,
    idAccesory int  NOT NULL,
    idData int  NOT NULL,
    CONSTRAINT mide_unique_accesorio_dato UNIQUE (idAccesory, idData) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT measure_pk PRIMARY KEY (idMeasure)
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
    CONSTRAINT patient_pk PRIMARY KEY (idUser)
);

-- Table: routine
CREATE TABLE routine (
    idRoutine serial  NOT NULL,
    creatorName varchar(15)  NOT NULL,
    routineName varchar(15)  NOT NULL,
    totalTime time  NOT NULL,
    dificulty int  NOT NULL,
    CONSTRAINT routine_pk PRIMARY KEY (idRoutine)
);

-- Table: session
CREATE TABLE session (
    idSesion serial  NOT NULL,
    idS serial  NOT NULL,
    idRutina int  NOT NULL,
    name varchar(15)  NOT NULL,
    numberOfSeries int  NOT NULL,
    numberOfRepetitions int  NOT NULL,
    exerciseTime time  NOT NULL,
    breakTime time  NOT NULL,
    CONSTRAINT sesion_unique_sesion_rutina UNIQUE (idS, idRutina) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT session_pk PRIMARY KEY (idSesion)
);

-- Table: user
CREATE TABLE "user" (
    idUser serial  NOT NULL,
    dni varchar(9)  NOT NULL,
    name varchar(15)  NOT NULL,
    lastname varchar(15)  NOT NULL,
    password varchar(13)  NOT NULL,
    email varchar(50)  NOT NULL,
    userType char(1)  NOT NULL,
    CONSTRAINT unique_user_dni UNIQUE (dni) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT user_pk PRIMARY KEY (idUser)
);

-- Table: uses
CREATE TABLE uses (
    idUses serial  NOT NULL,
    idRoutine int  NOT NULL,
    idAccesory int  NOT NULL,
    CONSTRAINT utiliza_unique_rutina_accesorio UNIQUE (idRoutine, idAccesory) NOT DEFERRABLE  INITIALLY IMMEDIATE,
    CONSTRAINT uses_pk PRIMARY KEY (idUses)
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

-- Reference: dataSet_realiza (table: dataSet)
ALTER TABLE dataSet ADD CONSTRAINT dataSet_realiza
    FOREIGN KEY (idMakes)
    REFERENCES makes (idMakes)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_accesorio (table: measure)
ALTER TABLE measure ADD CONSTRAINT mide_accesorio
    FOREIGN KEY (idAccesory)
    REFERENCES accesory (idAccesory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: mide_dato (table: measure)
ALTER TABLE measure ADD CONSTRAINT mide_dato
    FOREIGN KEY (idData)
    REFERENCES data (idData)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: realiza_rutina (table: makes)
ALTER TABLE makes ADD CONSTRAINT realiza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: realiza_user (table: makes)
ALTER TABLE makes ADD CONSTRAINT realiza_user
    FOREIGN KEY (idUser)
    REFERENCES "user" (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: seAsocia_Medico (table: isAssociated)
ALTER TABLE isAssociated ADD CONSTRAINT seAsocia_Medico
    FOREIGN KEY (medic_idUser)
    REFERENCES medic (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: seAsocia_Paciente (table: isAssociated)
ALTER TABLE isAssociated ADD CONSTRAINT seAsocia_Paciente
    FOREIGN KEY (patient_idUser)
    REFERENCES patient (idUser)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: sesion_rutina (table: session)
ALTER TABLE session ADD CONSTRAINT sesion_rutina
    FOREIGN KEY (idRutina)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_accesorio (table: uses)
ALTER TABLE uses ADD CONSTRAINT utiliza_accesorio
    FOREIGN KEY (idAccesory)
    REFERENCES accesory (idAccesory)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: utiliza_rutina (table: uses)
ALTER TABLE uses ADD CONSTRAINT utiliza_rutina
    FOREIGN KEY (idRoutine)
    REFERENCES routine (idRoutine)
    ON DELETE  CASCADE 
    ON UPDATE  CASCADE 
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

