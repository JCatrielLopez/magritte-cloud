-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2020-05-26 22:23:58.356

-- foreign keys
ALTER TABLE medic
    DROP CONSTRAINT Medico_User;

ALTER TABLE patient
    DROP CONSTRAINT Paciente_User;

ALTER TABLE dataSet
    DROP CONSTRAINT dataSet_realiza;

ALTER TABLE measure
    DROP CONSTRAINT mide_accesorio;

ALTER TABLE measure
    DROP CONSTRAINT mide_dato;

ALTER TABLE makes
    DROP CONSTRAINT realiza_rutina;

ALTER TABLE makes
    DROP CONSTRAINT realiza_user;

ALTER TABLE isAssociated
    DROP CONSTRAINT seAsocia_Medico;

ALTER TABLE isAssociated
    DROP CONSTRAINT seAsocia_Paciente;

ALTER TABLE session
    DROP CONSTRAINT sesion_rutina;

ALTER TABLE uses
    DROP CONSTRAINT utiliza_accesorio;

ALTER TABLE uses
    DROP CONSTRAINT utiliza_rutina;

-- tables
DROP TABLE accesory;

DROP TABLE data;

DROP TABLE dataSet;

DROP TABLE isAssociated;

DROP TABLE makes;

DROP TABLE measure;

DROP TABLE medic;

DROP TABLE patient;

DROP TABLE routine;

DROP TABLE session;

DROP TABLE "user";

DROP TABLE uses;

-- End of file.

