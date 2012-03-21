-- Saisir ici vos instructions ddl (executables a partir de grhum ou d'un user dba)

CREATE TABLE GRHUM.RG_RAPPORT (
  RAPPORT_ID          number(22) NOT NULL, 
  RAPPORT_D_DEBUT     date, 
  RAPPORT_D_FIN       date, 
  RAPPORT_DESCRIPTION varchar2(1024), 
  CONSTRAINT PK_RG_RAPPORT 
    PRIMARY KEY (RAPPORT_ID));
COMMENT ON TABLE GRHUM.RG_RAPPORT IS 'Table listant les données du rapport d''anomalies du reférentiel.';
COMMENT ON COLUMN GRHUM.RG_RAPPORT.RAPPORT_ID IS 'Clé primaire des rapports d''anomalies du referentiel';
COMMENT ON COLUMN GRHUM.RG_RAPPORT.RAPPORT_D_DEBUT IS 'Date de début du rapport.';
COMMENT ON COLUMN GRHUM.RG_RAPPORT.RAPPORT_D_FIN IS 'Date de fin du rapport';
COMMENT ON COLUMN GRHUM.RG_RAPPORT.RAPPORT_DESCRIPTION IS 'Informations sur le rapport.';

CREATE TABLE GRHUM.RG_RELEVE_ANNOMALIE (
  RAN_ID             number(22) NOT NULL, 
  RAN_RAPPORT_ID     number(22) NOT NULL, 
  RAN_PERSID         number(10), 
  RAN_C_STRUCTURE    varchar2(10), 
  RAN_MESSAGE_ERREUR varchar2(1024), 
  CONSTRAINT PK_RG_RELEVE_ANNOMALIE 
    PRIMARY KEY (RAN_ID));
COMMENT ON TABLE GRHUM.RG_RELEVE_ANNOMALIE IS 'Ensemble des anomalies (erreurs de validation) relevées lors de la dernière exécution de l''audit sur le Référentiel.';
COMMENT ON COLUMN GRHUM.RG_RELEVE_ANNOMALIE.RAN_ID IS 'Clé primaire des relevés d''anomalies.';
COMMENT ON COLUMN GRHUM.RG_RELEVE_ANNOMALIE.RAN_RAPPORT_ID IS 'Clé extérieur vers la table GRHUM.RG_RAPPORT';
COMMENT ON COLUMN GRHUM.RG_RELEVE_ANNOMALIE.RAN_PERSID IS 'PersID qui provoque une anomalie dans le réferentiel. (éventuellement redondant avec le C_structure).
Peut être nul, dans ce cas voir la colonne "C_structure".';
COMMENT ON COLUMN GRHUM.RG_RELEVE_ANNOMALIE.RAN_C_STRUCTURE IS 'C_STRUCTURE concerné par l''anomalie. Peut être nul, dans ce cas voir la colonne "PersId".';
COMMENT ON COLUMN GRHUM.RG_RELEVE_ANNOMALIE.RAN_MESSAGE_ERREUR IS 'Message de l''anomalie.';

ALTER TABLE GRHUM.RG_RELEVE_ANNOMALIE ADD CONSTRAINT FK_RG_RAPPORT FOREIGN KEY (RAN_RAPPORT_ID) REFERENCES GRHUM.RG_RAPPORT (RAPPORT_ID);
CREATE SEQUENCE GRHUM.RG_RAPPORT_SEQ;
CREATE SEQUENCE GRHUM.RG_RELEVE_ANNOMALIE_SEQ;


