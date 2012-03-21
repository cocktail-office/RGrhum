-- Script de correction d'annomalie de validation des personnes dans le référentiel GRhum de Cocktail (cocktest etc...).
-- à executer avec le user GRHUM.
-- auteur : pym, alexis

-- ************
-- PersIdModification
update GRHUM.STRUCTURE_ULR set pers_id_modification = 966 where pers_id_modification is null;
-- attention desactiver les contraintes sur indivivu_ulr (prenom etc...)  :  
alter trigger "GRHUM"."TRG_BR_INDIVIDU_ULR" disable;
update GRHUM.individu_ulr set pers_id_modification = 966 where pers_id_modification is null;
-- attention ré-activer les contraintes sur indivivu_ulr (prenom etc...) : 
alter trigger "GRHUM"."TRG_BR_INDIVIDU_ULR" enable;

-- TODO : faire de même avec les persid_creation ????

-- ***********
-- INSEE
-- La clé INSEE est obligatoire si vous renseignez le numero INSEE
-- Tous les individus concernés ont un num insee déclaré comme provisoire.

-- num insee déjà provisoire
update GRHUM.individu_ulr set IND_NO_INSEE_PROV = IND_NO_INSEE, ind_cle_insee_prov = ind_cle_insee where IND_NO_INSEE_PROV is null and IND_NO_INSEE is not null and PRISE_CPT_INSEE = 'P';
update GRHUM.individu_ulr set IND_NO_INSEE=null, ind_cle_insee=null where IND_NO_INSEE_PROV is not null and PRISE_CPT_INSEE = 'P';

-- numero insee non-provisoires et non valides => on les passes en provisoire
update GRHUM.individu_ulr set IND_NO_INSEE_PROV = IND_NO_INSEE, ind_cle_insee_prov = ind_cle_insee, PRISE_CPT_INSEE = 'P' 
where pers_id in (978,1314,1688,1766,1775,1803,2481,2813,3119,28873,29059,42108,43485,44169,54887,57267,68758,71208,81040,83072,84150,84230,87357,91540,94501,102615,104249,104275,105038,105559,106061,117546,118461,123232);
update GRHUM.individu_ulr set IND_NO_INSEE=null, ind_cle_insee=null
where pers_id in (978,1314,1688,1766,1775,1803,2481,2813,3119,28873,29059,42108,43485,44169,54887,57267,68758,71208,81040,83072,84150,84230,87357,91540,94501,102615,104249,104275,105038,105559,106061,117546,118461,123232);


-- *********
-- UNIVERSITE PARIS 8 (persid=93751) : La structure pere ETABLISSEMENTS (persid=93741) doit etre un groupe.
insert into grhum.REPART_TYPE_GROUPE(c_structure, tgrp_code, d_creation, d_modification, pers_id_creation, pers_id_modification) values (30228, 'G', SYSDATE, SYSDATE, 966, 966);

-- *********
-- Le responsable du service est obligatoire. 
UPDATE GRHUM.STRUCTURE_ULR SET d_Modification = SYSDATE, GRP_RESPONSABLE = 333, PERS_ID_MODIFICATION = 128639, PERS_ID_CREATION = 128639 
WHERE (C_STRUCTURE = 19 AND PERS_ID = 323 AND LL_STRUCTURE = 'FORMATIONS' AND LC_STRUCTURE = 'FORMATIONS' AND C_STRUCTURE_PERE = 28512);
update GRHUM.structure_ulr set d_Modification = SYSDATE, grp_responsable = 333, PERS_ID_MODIFICATION = 128639, PERS_ID_CREATION = 128639
where c_structure = 378670;

-- **********
-- SUPPORT INFORMATIQUE DU CONSORTIUM COCKTAIL (persid=33) : Une adresse email support@cocktail.org existe déjà pour la personne COCKTAIL (persid=39)
delete from GRHUM.compte_email where cpt_ordre = 96018

-- **********
-- La personne de type fournisseur doit être affectée a un groupe de fournisseur (tgrp_code=FO). 

-- La structure "ENTREPRISES FOURNISSEURS" doit être de type 'FO'
insert into GRHUM.repart_type_groupe (c_structure, d_creation, d_modification, pers_id_creation, pers_id_modification, tgrp_code)
values (378421, sysdate, sysdate, 966, 966, 'FO');

-- Les individus ayant un fournisseur de créé sont mis dans les groupes "FOURNISSEURS VALIDES (INDIVIDUS)" et "FOURNISSEURS VALIDES (EXTERNES)"
insert into repart_structure (PERS_ID,C_STRUCTURE,D_CREATION,D_MODIFICATION,PERS_ID_CREATION,PERS_ID_MODIFICATION) 
select pers_id,(select c_structure from structure_ulr where ll_structure = 'FOURNISSEURS VALIDES (INDIVIDUS)' ),sysdate,sysdate,128639,128639 
from individu_ulr 
where pers_id in (90700,90921,91003,88279,85849,285,128350,51,123232,128510,128512,128632,941,128504);

insert into repart_structure (PERS_ID,C_STRUCTURE,D_CREATION,D_MODIFICATION,PERS_ID_CREATION,PERS_ID_MODIFICATION) 
select pers_id,(select c_structure from structure_ulr where ll_structure = 'FOURNISSEURS VALIDES (EXTERNES)' ),sysdate,sysdate,128639,128639 
from individu_ulr 
where pers_id in (90700,90921,91003,88279,85849,285,128350,51,123232,128510,128512,128632,941,128504);

-- Les individus ayant un fournisseur de créé sont mis dans les groupes "FOURNISSEURS VALIDES (STRUCTURES)"
insert into repart_structure (PERS_ID,C_STRUCTURE,D_CREATION,D_MODIFICATION,PERS_ID_CREATION,PERS_ID_MODIFICATION) 
select pers_id,(select c_structure from structure_ulr where ll_structure = 'FOURNISSEURS VALIDES (STRUCTURES)' ),sysdate,sysdate,128639,128639 
from structure_ulr where pers_id in (128484, 86423);

-- *****************
-- Un individu qui n'est pas fournisseur ne peut etre affectee a un groupe de type FO.  
-- Les structures suivantes ne doivent pas être de type FO :
delete from repart_type_groupe where c_structure = (select c_structure from structure_ulr where ll_structure = 'DIRECTION GENERALE COCKTAIL') and tgrp_code = 'FO';

-- *****************
--Une adresse de type facturation est obligatoire pour une personne fournisseur. 
-- -- = (90700,91003,123232,128632)

-- *****************
-- La saisie du SIRET est obligatoire pour un fournisseur non étranger => on passe à fournisseur etranger en attendant même pour des structures internes. 
UPDATE GRHUM.FOURNIS_ULR SET D_MODIFICATION = sysdate, FOU_ETRANGER = 'O' WHERE PERS_ID in (4648,9375,10793,11330,12178,12441,12678,13481,16784,18979,24025,25919,36352,128484);

-- *****************
-- Une autre structure a déjà ce numéro de siret => on mets à null les siren et siret correspondants.
update GRHUM.STRUCTURE_ULR set siren=null, siret=null, D_MODIFICATION=sysdate where pers_id in (9082,16074,30985,32868,37182);




