package org.cocktail.rgrhum.serveur;

import org.cocktail.fwkcktldroitsutils.common.metier.EOCompte;
import org.cocktail.fwkcktlpersonne.common.PersonneApplicationUser;
import org.cocktail.fwkcktlpersonne.common.metier.EOGrhumParametres;
import org.cocktail.fwkcktlpersonne.common.metier.EOGrhumParametresType;
import org.cocktail.fwkcktlwebapp.server.CktlParamManager;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEOAccessUtilities;
import er.extensions.foundation.ERXThreadStorage;

public class RgrhumParamManager extends CktlParamManager {
	
	public static final String RGHRUM_OBJECTHASCHANGED_DESACTIVE = "org.cocktail.rgrhum.objecthaschanged.disabled";
	
	private EOEditingContext ec = ERXEC.newEditingContext();
	
	public RgrhumParamManager(){
		getParamList().add(RGHRUM_OBJECTHASCHANGED_DESACTIVE);
		getParamComments().put(RGHRUM_OBJECTHASCHANGED_DESACTIVE, "Forcer les validations des objets métier");
		getParamDefault().put(RGHRUM_OBJECTHASCHANGED_DESACTIVE, "O");
		getParamTypes().put(RGHRUM_OBJECTHASCHANGED_DESACTIVE, EOGrhumParametresType.codeActivation);
	}
	
	@Override
	public void checkAndInitParamsWithDefault() {
		//Recuperer un grhum_createur
		String cptLogin = EOGrhumParametres.parametrePourCle(ec, EOGrhumParametres.PARAM_GRHUM_CREATEUR);
		if (cptLogin != null) {
			EOCompte cpt = EOCompte.compteForLogin(ec, cptLogin);
			if (cpt != null) {
				ERXThreadStorage.takeValueForKey(cpt.persId(), PersonneApplicationUser.PERS_ID_CURRENT_USER_STORAGE_KEY);
			}
		}
		super.checkAndInitParamsWithDefault();
	}
	
	@Override
	public void createNewParam(String key, String value, String comment) {
		createNewParam(key, value, comment, EOGrhumParametresType.codeActivation);
	}

	@Override
	public void createNewParam(String key, String value, String comment,
			String type) {
		EOGrhumParametres newParametre = EOGrhumParametres.creerInstance(ec);
		newParametre.setParamKey(key);
		newParametre.setParamValue(value);
		newParametre.setParamCommentaires(comment);
		newParametre.setToParametresTypeRelationship(EOGrhumParametresType.fetchByKeyValue(ec, EOGrhumParametresType.TYPE_ID_INTERNE_KEY, type));
		if (ec.hasChanges()) {
			EOEntity entityParameter = ERXEOAccessUtilities.entityForEo(newParametre);
			try {

				// Avant de sauvegarder les données, nous modifions le modèle
				// pour que l'on puisse avoir accès aussi en écriture sur les données
				entityParameter.setReadOnly(false);
				ec.saveChanges();

			} catch (Exception e) {
				log.warn("Erreur lors de l'enregistrement des parametres.");
				e.printStackTrace();
			} finally {
				entityParameter.setReadOnly(true);
			}
		}
	}

	@Override
	public String getParam(String key) {
		String res = getApplication().config().stringForKey(key);
		return res;
	}

}
