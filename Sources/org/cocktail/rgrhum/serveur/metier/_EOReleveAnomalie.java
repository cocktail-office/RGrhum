// _EOReleveAnomalie.java
/*
 * Copyright COCKTAIL (www.cocktail.org), 2001, 2010 
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software. You can use, 
 * modify and/or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty and the software's author, the holder of the
 * economic rights, and the successive licensors have only limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading, using, modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean that it is complicated to manipulate, and that also
 * therefore means that it is reserved for developers and experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and, more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

// DO NOT EDIT.  Make changes to EOReleveAnomalie.java instead.
package org.cocktail.rgrhum.serveur.metier;

import org.cocktail.fwkcktlwebapp.server.database.CktlServerRecord;

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.NoSuchElementException;

@SuppressWarnings("all")
public abstract class _EOReleveAnomalie extends  CktlServerRecord {
	public static final String ENTITY_NAME = "RGrhum_ReleveAnomalie";
	public static final String ENTITY_TABLE_NAME = "GRHUM.RG_RELEVE_ANOMALIE";

	// Attributes
	public static final String ENTITY_PRIMARY_KEY = "ranId";

	public static final String C_STRUCTURE_KEY = "cStructure";
	public static final String MESSAGE_ERREUR_KEY = "messageErreur";
	public static final String PERSID_KEY = "persid";

	// Non visible attributes
	public static final String RAN_RAPPORT_ID_KEY = "ranRapportId";
	public static final String RAN_ID_KEY = "ranId";

	// Colkeys
	public static final String C_STRUCTURE_COLKEY = "RAN_C_STRUCTURE";
	public static final String MESSAGE_ERREUR_COLKEY = "RAN_MESSAGE_ERREUR";
	public static final String PERSID_COLKEY = "RAN_PERSID";

	// Non visible colkeys
	public static final String RAN_RAPPORT_ID_COLKEY = "RAN_RAPPORT_ID";
	public static final String RAN_ID_COLKEY = "RAN_ID";

	// Relationships
	public static final String TO_INDIVIDUS_KEY = "toIndividus";
	public static final String TO_RAPPORT_KEY = "toRapport";
	public static final String TO_STRUCTURES_KEY = "toStructures";

	// Create / Init methods

	/**
	 * Creates and inserts a new EOReleveAnomalie with non null attributes and mandatory relationships.
	 *
	 * @param editingContext
	 * @param toRapport
	 * @return EOReleveAnomalie
	 */
	public static EOReleveAnomalie create(EOEditingContext editingContext, org.cocktail.rgrhum.serveur.metier.EORapport toRapport) {
		EOReleveAnomalie eo = (EOReleveAnomalie) createAndInsertInstance(editingContext);
		eo.setToRapportRelationship(toRapport);
		return eo;
	}

	/**
	 * Creates and inserts a new empty EOReleveAnomalie.
	 *
	 * @param editingContext
	 * @return EOReleveAnomalie
	 */
	public static EOReleveAnomalie create(EOEditingContext editingContext) {
		EOReleveAnomalie eo = (EOReleveAnomalie) createAndInsertInstance(editingContext);
		return eo;
	}

	// Utilities methods

	public EOReleveAnomalie localInstanceIn(EOEditingContext editingContext) {
		EOReleveAnomalie localInstance = (EOReleveAnomalie) localInstanceOfObject(editingContext, (EOReleveAnomalie) this);
		if (localInstance == null) {
			throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
		}
		return localInstance;
	}

	public static EOReleveAnomalie localInstanceIn(EOEditingContext editingContext, EOReleveAnomalie eo) {
		EOReleveAnomalie localInstance = (eo == null) ? null : (EOReleveAnomalie) localInstanceOfObject(editingContext, eo);
		if (localInstance == null && eo != null) {
			throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
		}
		return localInstance;
	}

	// Accessors methods

	public String cStructure() {
		return (String) storedValueForKey("cStructure");
	}

	public void setCStructure(String value) {
		takeStoredValueForKey(value, "cStructure");
	}
	public String messageErreur() {
		return (String) storedValueForKey("messageErreur");
	}

	public void setMessageErreur(String value) {
		takeStoredValueForKey(value, "messageErreur");
	}
	public Integer persid() {
		return (Integer) storedValueForKey("persid");
	}

	public void setPersid(Integer value) {
		takeStoredValueForKey(value, "persid");
	}

	public org.cocktail.rgrhum.serveur.metier.EORapport toRapport() {
		return (org.cocktail.rgrhum.serveur.metier.EORapport)storedValueForKey("toRapport");
	}

	public void setToRapportRelationship(org.cocktail.rgrhum.serveur.metier.EORapport value) {
		if (value == null) {
			org.cocktail.rgrhum.serveur.metier.EORapport oldValue = toRapport();
			if (oldValue != null) {
				removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toRapport");
			}
		} else {
			addObjectToBothSidesOfRelationshipWithKey(value, "toRapport");
		}
	}
  
	public NSArray toIndividus() {
		return (NSArray)storedValueForKey("toIndividus");
	}

	public NSArray toIndividus(EOQualifier qualifier) {
		return toIndividus(qualifier, null);
	}

	public NSArray toIndividus(EOQualifier qualifier, NSArray sortOrderings) {
		NSArray results;
				results = toIndividus();
		if (qualifier != null) {
			results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
		}
		if (sortOrderings != null) {
			results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
		}
				return results;
	}
  
	public void addToToIndividusRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOIndividu object) {
		addObjectToBothSidesOfRelationshipWithKey(object, "toIndividus");
	}

	public void removeFromToIndividusRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOIndividu object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toIndividus");
	}

	public org.cocktail.fwkcktlpersonne.common.metier.EOIndividu createToIndividusRelationship() {
		EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Fwkpers_Individu");
		EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
		editingContext().insertObject(eo);
		addObjectToBothSidesOfRelationshipWithKey(eo, "toIndividus");
		return (org.cocktail.fwkcktlpersonne.common.metier.EOIndividu) eo;
	}

	public void deleteToIndividusRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOIndividu object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toIndividus");
				editingContext().deleteObject(object);
			}

	public void deleteAllToIndividusRelationships() {
		Enumeration objects = toIndividus().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deleteToIndividusRelationship((org.cocktail.fwkcktlpersonne.common.metier.EOIndividu)objects.nextElement());
		}
	}
	public NSArray toStructures() {
		return (NSArray)storedValueForKey("toStructures");
	}

	public NSArray toStructures(EOQualifier qualifier) {
		return toStructures(qualifier, null);
	}

	public NSArray toStructures(EOQualifier qualifier, NSArray sortOrderings) {
		NSArray results;
				results = toStructures();
		if (qualifier != null) {
			results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
		}
		if (sortOrderings != null) {
			results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
		}
				return results;
	}
  
	public void addToToStructuresRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOStructure object) {
		addObjectToBothSidesOfRelationshipWithKey(object, "toStructures");
	}

	public void removeFromToStructuresRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOStructure object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructures");
	}

	public org.cocktail.fwkcktlpersonne.common.metier.EOStructure createToStructuresRelationship() {
		EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Fwkpers_Structure");
		EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
		editingContext().insertObject(eo);
		addObjectToBothSidesOfRelationshipWithKey(eo, "toStructures");
		return (org.cocktail.fwkcktlpersonne.common.metier.EOStructure) eo;
	}

	public void deleteToStructuresRelationship(org.cocktail.fwkcktlpersonne.common.metier.EOStructure object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toStructures");
				editingContext().deleteObject(object);
			}

	public void deleteAllToStructuresRelationships() {
		Enumeration objects = toStructures().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deleteToStructuresRelationship((org.cocktail.fwkcktlpersonne.common.metier.EOStructure)objects.nextElement());
		}
	}

	// Finders

	// Fetching many (returns NSArray)
	
	public static NSArray fetchAll(EOEditingContext editingContext) {
		return _EOReleveAnomalie.fetchAll(editingContext, (NSArray) null);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, NSArray sortOrderings) {
		return _EOReleveAnomalie.fetchAll(editingContext, null, sortOrderings);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, EOQualifier qualifier) {
		return fetchAll(editingContext, qualifier, null, false);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
		return fetchAll(editingContext, qualifier, sortOrderings, false);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, String keyName, Object value, NSArray sortOrderings) {
		return fetchAll(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), sortOrderings, false);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings, boolean distinct) {
		EOFetchSpecification fetchSpec = new EOFetchSpecification(ENTITY_NAME, qualifier, sortOrderings);
		fetchSpec.setIsDeep(true);
		fetchSpec.setUsesDistinct(distinct);
		return (NSArray) editingContext.objectsWithFetchSpecification(fetchSpec);
	}

	// Fetching one (returns EOReleveAnomalie)
	
	/**
	 * Renvoie un objet simple.
	 * Pour recuperer un tableau, utiliser fetchAll(EOEditingContext, String, Object, NSArray).
	 * Si plusieurs objets sont susceptibles d'etre trouves, utiliser fetchFirstByKeyValue(EOEditingContext, String, Object).
	 * Une exception est declenchee si plusieurs objets sont trouves.
	 * 
	 * @param editingContext
	 * @param keyName
	 * @param value
	 * @return Renvoie l'objet correspondant a la paire cle/valeur
	 * @throws IllegalStateException  
	 */
	public static EOReleveAnomalie fetchByKeyValue(EOEditingContext editingContext, String keyName, Object value) throws IllegalStateException {
		return fetchByQualifier(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
	}

	/**
	 * Renvoie l'objet correspondant au qualifier.
	 * Si plusieurs objets sont susceptibles d'etre trouves, utiliser fetchFirstByQualifier(EOEditingContext, EOQualifier).
	 * Une exception est declenchee si plusieurs objets sont trouves.
	 * 
	 * @param editingContext
	 * @param qualifier
	 * @return L'objet qui correspond au qualifier passé en parametre. Si plusieurs objets sont trouves, une exception est declenchee.
	 * 		   Si aucun objet n'est trouve, null est renvoye.
	 * @throws IllegalStateException
	 */
	public static EOReleveAnomalie fetchByQualifier(EOEditingContext editingContext, EOQualifier qualifier) throws IllegalStateException {
		NSArray eoObjects = fetchAll(editingContext, qualifier, null);
		EOReleveAnomalie eoObject;
		int count = eoObjects.count();
		if (count == 0) {
			eoObject = null;
		}
		else if (count == 1) {
			eoObject = (EOReleveAnomalie)eoObjects.objectAtIndex(0);
		}
		else {
			throw new IllegalStateException("Il y a plus d'un objet qui correspond au qualifier '" + qualifier + "'.");
		}
		return eoObject;
	}

	/**
	 * Renvoie le premier objet simple trouve.
	 * Pour recuperer un tableau, utiliser fetchAll(EOEditingContext, String, Object, NSArray).
	 * 
	 * @param editingContext
	 * @param keyName
	 * @param value
	 * @return Renvoie le premier objet trouve correspondant a la paire cle/valeur, null si aucun trouve
	 */
	public static EOReleveAnomalie fetchFirstByKeyValue(EOEditingContext editingContext, String keyName, Object value) {
		return fetchFirstByQualifier(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value), null);
	}

	/**
	 * Renvoie le premier objet simple trouve.
	 * Pour recuperer un tableau, utiliser fetchAll(EOEditingContext, EOQualifier).
	 * 
	 * @param editingContext
	 * @param qualifier
	 * @return Renvoie le premier objet trouve correspondant au qualifier, null si aucun trouve
	 */
	public static EOReleveAnomalie fetchFirstByQualifier(EOEditingContext editingContext, EOQualifier qualifier) {
		return fetchFirstByQualifier(editingContext, qualifier, null);
	}

	/**
	 * Renvoie le premier objet simple trouve dans la liste triee.
	 * Pour recuperer un tableau, utiliser fetchAll(EOEditingContext, EOQualifier, NSArray).
	 * 
	 * @param editingContext
	 * @param qualifier
	 * @param sortOrderings
	 * @return Renvoie le premier objet trouve correspondant au qualifier, null si aucun trouve
	 */
	public static EOReleveAnomalie fetchFirstByQualifier(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
		NSArray eoObjects = fetchAll(editingContext, qualifier, sortOrderings);
		EOReleveAnomalie eoObject;
		int count = eoObjects.count();
		if (count == 0) {
			eoObject = null;
		}
		else {
			eoObject = (EOReleveAnomalie)eoObjects.objectAtIndex(0);
		}
		return eoObject;
	}  

	/**
	 * Renvoie le premier objet simple obligatoirement trouve.
	 * 
	 * @param editingContext
	 * @param keyName
	 * @param value
	 * @return Renvoie le premier objet trouve correspondant a la paire cle/valeur, une exception si aucun trouve.
	 * Pour ne pas avoir d'exception, utiliser fetchFirstByKeyValue(EOEditingContext, String, Object)
	 * @throws NoSuchElementException Si aucun objet trouve
	 */
	public static EOReleveAnomalie fetchFirstRequiredByKeyValue(EOEditingContext editingContext, String keyName, Object value) throws NoSuchElementException {
		return fetchFirstRequiredByQualifier(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
	}

	/**
	 * Renvoie le premier objet simple obligatoirement trouve.
	 * 
	 * @param editingContext
	 * @param qualifier
	 * @return Renvoie le premier objet trouve correspondant au qualifier, une exception si aucun trouve.
	 * Pour ne pas avoir d'exception, utiliser fetchFirstByQualifier(EOEditingContext, EOQualifier)
	 * @throws NoSuchElementException Si aucun objet trouve
	 */
	public static EOReleveAnomalie fetchFirstRequiredByQualifier(EOEditingContext editingContext, EOQualifier qualifier) throws NoSuchElementException {
		EOReleveAnomalie eoObject = fetchFirstByQualifier(editingContext, qualifier);
		if (eoObject == null) {
			throw new NoSuchElementException("Aucun objet EOReleveAnomalie ne correspond au qualifier '" + qualifier + "'.");
		}
		return eoObject;
	}	

	// FetchSpecs...
	


	// Internal utilities methods for common use (server AND client)...

	private static EOReleveAnomalie createAndInsertInstance(EOEditingContext ec) {
		EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_EOReleveAnomalie.ENTITY_NAME);
		if(classDescription == null) {
			throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _EOReleveAnomalie.ENTITY_NAME + "' !");
		}
		else {
			EOReleveAnomalie object = (EOReleveAnomalie) classDescription.createInstanceWithEditingContext(ec, null);
			ec.insertObject(object);
			return object;
		}
	}

	private static EOReleveAnomalie localInstanceOfObject(EOEditingContext ec, EOReleveAnomalie object) {
		if(object != null && ec != null) {
			EOEditingContext otherEditingContext = object.editingContext();
			if(otherEditingContext == null) {
				throw new IllegalArgumentException("The EOReleveAnomalie " + object + " is not in an EOEditingContext.");
			}
			else {
				com.webobjects.eocontrol.EOGlobalID globalID = otherEditingContext.globalIDForObject(object);
				return (EOReleveAnomalie) ec.faultForGlobalID(globalID, ec);
			}
		}
		else {
			return null;
		}
	}

}
