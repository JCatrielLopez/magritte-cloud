-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-04 21:29:48.876

-- foreign keys
ALTER TABLE Medic
    DROP CONSTRAINT Medico_User;

ALTER TABLE Patient
    DROP CONSTRAINT Paciente_User;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_dataSet;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_patient;

ALTER TABLE AccessoryData
    DROP CONSTRAINT mide_accesorio;

ALTER TABLE AccessoryData
    DROP CONSTRAINT mide_dato;

ALTER TABLE Patient
    DROP CONSTRAINT patient_medic;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT realiza_rutina;

ALTER TABLE Session
    DROP CONSTRAINT sesion_rutina;

ALTER TABLE RoutineAccessory
    DROP CONSTRAINT utiliza_accesorio;

ALTER TABLE RoutineAccessory
    DROP CONSTRAINT utiliza_rutina;

-- tables
DROP TABLE Accessory;

DROP TABLE AccessoryData;

DROP TABLE Data;

DROP TABLE DataSet;

DROP TABLE Medic;

DROP TABLE Patient;

DROP TABLE PatientRoutineDataSet;

DROP TABLE Routine;

DROP TABLE RoutineAccessory;

DROP TABLE Session;

DROP TABLE "User";

-- End of file.

