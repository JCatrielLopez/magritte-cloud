-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-06-01 10:58:30.084

-- foreign keys
ALTER TABLE medic
    DROP CONSTRAINT Medico_User;

ALTER TABLE patient
    DROP CONSTRAINT Paciente_User;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_dataSet;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT makes_patient;

ALTER TABLE AccessoryData
    DROP CONSTRAINT mide_accesorio;

ALTER TABLE AccessoryData
    DROP CONSTRAINT mide_dato;

ALTER TABLE patient
    DROP CONSTRAINT patient_medic;

ALTER TABLE PatientRoutineDataSet
    DROP CONSTRAINT realiza_rutina;

ALTER TABLE session
    DROP CONSTRAINT sesion_rutina;

ALTER TABLE RoutineAccessory
    DROP CONSTRAINT utiliza_accesorio;

ALTER TABLE RoutineAccessory
    DROP CONSTRAINT utiliza_rutina;

-- tables
DROP TABLE AccessoryData;

DROP TABLE PatientRoutineDataSet;

DROP TABLE RoutineAccessory;

DROP TABLE accessory;

DROP TABLE data;

DROP TABLE dataSet;

DROP TABLE medic;

DROP TABLE patient;

DROP TABLE routine;

DROP TABLE session;

DROP TABLE "user";

-- End of file.

