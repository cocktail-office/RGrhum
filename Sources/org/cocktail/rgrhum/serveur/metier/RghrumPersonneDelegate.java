package org.cocktail.rgrhum.serveur.metier;

import org.cocktail.fwkcktlpersonne.common.metier.IPersonne;
import org.cocktail.fwkcktlpersonne.common.metier.PersonneDelegate;

import com.webobjects.foundation.NSValidation;
import com.webobjects.foundation.NSValidation.ValidationException;

public class RghrumPersonneDelegate extends PersonneDelegate {

	private final IPersonne personne;
	
	public RghrumPersonneDelegate(IPersonne personne) {
		super(personne);
		this.personne = personne;
	}
	
	@Override
	public void checkUsers() throws ValidationException {
		
		if (personne.persIdModification() == null) {
			throw new NSValidation.ValidationException("La référence au modificateur (persIdModification) est obligatoire pour la personne " + personne.persLibelle() + " (" + personne.persId() + ")");
		}
		if (personne.persIdCreation() == null) {
			throw new NSValidation.ValidationException("La référence au créateur (persIdCreation) est obligatoire pour la personne " + personne.persLibelle() + " (" + personne.persId() + ")");
		}
	}

}
