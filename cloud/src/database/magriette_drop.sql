-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-10 13:55:21.964

-- foreign keys
ALTER TABLE Medic
    DROP CONSTRAINT Medico_User;

ALTER TABLE Patient
    DROP CONSTRAINT Paciente_User;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_dataSet;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_patient;

ALTER TABLE accessory_data
    DROP CONSTRAINT mide_accesorio;

ALTER TABLE accessory_data
    DROP CONSTRAINT mide_dato;

ALTER TABLE Patient
    DROP CONSTRAINT patient_medic;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT realiza_rutina;

ALTER TABLE Session
    DROP CONSTRAINT sesion_rutina;

ALTER TABLE routine_accessory
    DROP CONSTRAINT utiliza_accesorio;

ALTER TABLE routine_accessory
    DROP CONSTRAINT utiliza_rutina;

-- tables
DROP TABLE Accessory;

DROP TABLE Data;

DROP TABLE DataSet;

DROP TABLE Medic;

DROP TABLE Patient;

DROP TABLE PatientRoutineDataSet;

DROP TABLE Routine;

DROP TABLE Session;

DROP TABLE Usuario;

DROP TABLE accessory_data;

DROP TABLE routine_accessory;

-- End of file.

