/*
 * Copyright COCKTAIL (www.cocktail.org), 1995, 2011 This software
 * is governed by the CeCILL license under French law and abiding by the
 * rules of distribution of free software. You can use, modify and/or
 * redistribute the software under the terms of the CeCILL license as
 * circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * As a counterpart to the access to the source code and rights to copy, modify
 * and redistribute granted by the license, users are provided only with a
 * limited warranty and the software's author, the holder of the economic
 * rights, and the successive licensors have only limited liability. In this
 * respect, the user's attention is drawn to the risks associated with loading,
 * using, modifying and/or developing or reproducing the software by the user
 * in light of its specific status of free software, that may mean that it
 * is complicated to manipulate, and that also therefore means that it is
 * reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the
 * software's suitability as regards their requirements in conditions enabling
 * the security of their systems and/or data to be ensured and, more generally,
 * to use and operate it in the same conditions as regards security. The
 * fact that you are presently reading this means that you have had knowledge
 * of the CeCILL license and that you accept its terms.
 */
package org.cocktail.rgrhum.serveur.metier;

import org.cocktail.fwkcktlpersonne.common.metier.EOIndividu;
import org.cocktail.fwkcktlpersonne.common.metier.EOStructure;
import org.cocktail.fwkcktlpersonne.common.metier.IPersonne;

import com.webobjects.foundation.NSValidation;

public class EOReleveAnomalie extends _EOReleveAnomalie {
	private static final long serialVersionUID = 4155109980077220837L;
	
	public static final String PERSONNE_NOM_AFFICHAGE_KEY = "personneNomAffichage";

	public EOReleveAnomalie() {
        super();
    }

    /**
     * Vous pouvez définir un delegate qui sera appelé lors de l'execution de cette methode. AfwkPersRecord#registerValidationDelegate(IValidationDelegate).
     * @throws NSValidation.ValidationException
     */
    public void validateForInsert() throws NSValidation.ValidationException {
        this.validateObjectMetier();
        validateBeforeTransactionSave();
        super.validateForInsert();
    }

    /**
     * Vous pouvez définir un delegate qui sera appelé lors de l'execution de cette methode. AfwkPersRecord#registerValidationDelegate(IValidationDelegate).
     * @throws NSValidation.ValidationException
     */
    public void validateForUpdate() throws NSValidation.ValidationException {
        this.validateObjectMetier();
        validateBeforeTransactionSave();
        super.validateForUpdate();
    }

    /**
     * Vous pouvez définir un delegate qui sera appelé lors de l'execution de cette methode. AfwkPersRecord#registerValidationDelegate(IValidationDelegate).
     * @throws NSValidation.ValidationException
     */
    public void validateForDelete() throws NSValidation.ValidationException {
        super.validateForDelete();
    }

    /**
     * Peut etre appele à partir des factories.
     * Vous pouvez définir un delegate qui sera appelé lors de l'execution de cette methode. AfwkPersRecord#registerValidationDelegate(IValidationDelegate).
     * @throws NSValidation.ValidationException
     */
    public void validateObjectMetier() throws NSValidation.ValidationException {
    	
    }
    
    /**
     * A appeler par les validateforsave, forinsert, forupdate.
     * Vous pouvez définir un delegate qui sera appelé lors de l'execution de cette methode. AfwkPersRecord#registerValidationDelegate(IValidationDelegate).
     *
     */
    public void validateBeforeTransactionSave() throws NSValidation.ValidationException {

    }
    
    /**
	 * @return toIndividu() si pas null sinon toStructure().
	 */
	public IPersonne toPersonne() {
		return (toIndividu() != null ? toIndividu() : toStructure());
	}
    
    /**
	 * @return L'individu associe ou null.
	 */
	public EOIndividu toIndividu() {
		if (toIndividus().count() > 0) {
			return (EOIndividu) toIndividus().objectAtIndex(0);
		}
		return null;
	}

	/**
	 * @return La structure associee ou null.
	 */
	public EOStructure toStructure() {
		if (toStructures().count() > 0) {
			return (EOStructure) toStructures().objectAtIndex(0);
		}
		return null;
	}

	public String personneNomAffichage(){
		return toPersonne().getNomCompletAffichage();
	}
	
	
	
	/**
	 * Permet de renseigner les champs du {@link EOReleveAnomalie} à partir de la personne {@link IPersonne} (pour les
	 * données sur le persId et le CStructure) et de l'exception {@link ValidationException} (pour le message d'erreur)
	 * passés en paramètres.
	 * @param personne : {@link IPersonne} concerné par l'anomalie de validation
	 * @param e : {@link ValidationException} stipulant l'anomalie de validation
	 */
	public void populate(IPersonne personne, Exception e) {
		if (personne!=null) {
			setPersid(personne.persId());
			if (personne.isStructure()) {
				setCStructure(((EOStructure)personne).cStructure());
			}
		}
		if (e!=null) {
			String message = (e.getLocalizedMessage()!=null) ? e.getLocalizedMessage() : "Exception : "+e.toString();
			if (message.length()>1000) {
				message = e.getLocalizedMessage().substring(0, 998) + "...";
			}
			setMessageErreur(message);
		}
	}
}
