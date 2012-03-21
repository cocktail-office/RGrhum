// _EORapport.java
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

// DO NOT EDIT.  Make changes to EORapport.java instead.
package org.cocktail.rgrhum.serveur.metier;

import org.cocktail.fwkcktlwebapp.server.database.CktlServerRecord;

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.NoSuchElementException;

@SuppressWarnings("all")
public abstract class _EORapport extends  CktlServerRecord {
	public static final String ENTITY_NAME = "RGrhum_Rapport";
	public static final String ENTITY_TABLE_NAME = "GRHUM.RG_RAPPORT";

	// Attributes
	public static final String ENTITY_PRIMARY_KEY = "rapportId";

	public static final String RAPPORT_D_DEBUT_KEY = "rapportDDebut";
	public static final String RAPPORT_DESCRIPTION_KEY = "rapportDescription";
	public static final String RAPPORT_D_FIN_KEY = "rapportDFin";

	// Non visible attributes
	public static final String RAPPORT_ID_KEY = "rapportId";

	// Colkeys
	public static final String RAPPORT_D_DEBUT_COLKEY = "RAPPORT_D_DEBUT";
	public static final String RAPPORT_DESCRIPTION_COLKEY = "RAPPORT_DESCRIPTION";
	public static final String RAPPORT_D_FIN_COLKEY = "RAPPORT_D_FIN";

	// Non visible colkeys
	public static final String RAPPORT_ID_COLKEY = "RAPPORT_ID";

	// Relationships
	public static final String TO_RELEVE_ANNOMALIES_KEY = "toReleveAnnomalies";

	// Create / Init methods

	/**
	 * Creates and inserts a new EORapport with non null attributes and mandatory relationships.
	 *
	 * @param editingContext
	 * @return EORapport
	 */
	public static EORapport create(EOEditingContext editingContext) {
		EORapport eo = (EORapport) createAndInsertInstance(editingContext);
		return eo;
	}


	// Utilities methods

	public EORapport localInstanceIn(EOEditingContext editingContext) {
		EORapport localInstance = (EORapport) localInstanceOfObject(editingContext, (EORapport) this);
		if (localInstance == null) {
			throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
		}
		return localInstance;
	}

	public static EORapport localInstanceIn(EOEditingContext editingContext, EORapport eo) {
		EORapport localInstance = (eo == null) ? null : (EORapport) localInstanceOfObject(editingContext, eo);
		if (localInstance == null && eo != null) {
			throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
		}
		return localInstance;
	}

	// Accessors methods

	public NSTimestamp rapportDDebut() {
		return (NSTimestamp) storedValueForKey("rapportDDebut");
	}

	public void setRapportDDebut(NSTimestamp value) {
		takeStoredValueForKey(value, "rapportDDebut");
	}
	public String rapportDescription() {
		return (String) storedValueForKey("rapportDescription");
	}

	public void setRapportDescription(String value) {
		takeStoredValueForKey(value, "rapportDescription");
	}
	public NSTimestamp rapportDFin() {
		return (NSTimestamp) storedValueForKey("rapportDFin");
	}

	public void setRapportDFin(NSTimestamp value) {
		takeStoredValueForKey(value, "rapportDFin");
	}

	public NSArray toReleveAnnomalies() {
		return (NSArray)storedValueForKey("toReleveAnnomalies");
	}

	public NSArray toReleveAnnomalies(EOQualifier qualifier) {
		return toReleveAnnomalies(qualifier, null, false);
	}

	public NSArray toReleveAnnomalies(EOQualifier qualifier, boolean fetch) {
		return toReleveAnnomalies(qualifier, null, fetch);
	}

	public NSArray toReleveAnnomalies(EOQualifier qualifier, NSArray sortOrderings, boolean fetch) {
		NSArray results;
				if (fetch) {
			EOQualifier fullQualifier;
						EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie.TO_RAPPORT_KEY, EOQualifier.QualifierOperatorEqual, this);
			
			if (qualifier == null) {
				fullQualifier = inverseQualifier;
			}
			else {
				NSMutableArray qualifiers = new NSMutableArray();
				qualifiers.addObject(qualifier);
				qualifiers.addObject(inverseQualifier);
				fullQualifier = new EOAndQualifier(qualifiers);
			}

						results = org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie.fetchAll(editingContext(), fullQualifier, sortOrderings);
					}
		else {
				results = toReleveAnnomalies();
		if (qualifier != null) {
			results = (NSArray)EOQualifier.filteredArrayWithQualifier(results, qualifier);
		}
		if (sortOrderings != null) {
			results = (NSArray)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
		}
				}
				return results;
	}
  
	public void addToToReleveAnnomaliesRelationship(org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie object) {
		addObjectToBothSidesOfRelationshipWithKey(object, "toReleveAnnomalies");
	}

	public void removeFromToReleveAnnomaliesRelationship(org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toReleveAnnomalies");
	}

	public org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie createToReleveAnnomaliesRelationship() {
		EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("RGrhum_ReleveAnomalie");
		EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
		editingContext().insertObject(eo);
		addObjectToBothSidesOfRelationshipWithKey(eo, "toReleveAnnomalies");
		return (org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie) eo;
	}

	public void deleteToReleveAnnomaliesRelationship(org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie object) {
		removeObjectFromBothSidesOfRelationshipWithKey(object, "toReleveAnnomalies");
				editingContext().deleteObject(object);
			}

	public void deleteAllToReleveAnnomaliesRelationships() {
		Enumeration objects = toReleveAnnomalies().immutableClone().objectEnumerator();
		while (objects.hasMoreElements()) {
			deleteToReleveAnnomaliesRelationship((org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie)objects.nextElement());
		}
	}

	// Finders

	// Fetching many (returns NSArray)
	
	public static NSArray fetchAll(EOEditingContext editingContext) {
		return _EORapport.fetchAll(editingContext, (NSArray) null);
	}

	public static NSArray fetchAll(EOEditingContext editingContext, NSArray sortOrderings) {
		return _EORapport.fetchAll(editingContext, null, sortOrderings);
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

	// Fetching one (returns EORapport)
	
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
	public static EORapport fetchByKeyValue(EOEditingContext editingContext, String keyName, Object value) throws IllegalStateException {
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
	public static EORapport fetchByQualifier(EOEditingContext editingContext, EOQualifier qualifier) throws IllegalStateException {
		NSArray eoObjects = fetchAll(editingContext, qualifier, null);
		EORapport eoObject;
		int count = eoObjects.count();
		if (count == 0) {
			eoObject = null;
		}
		else if (count == 1) {
			eoObject = (EORapport)eoObjects.objectAtIndex(0);
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
	public static EORapport fetchFirstByKeyValue(EOEditingContext editingContext, String keyName, Object value) {
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
	public static EORapport fetchFirstByQualifier(EOEditingContext editingContext, EOQualifier qualifier) {
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
	public static EORapport fetchFirstByQualifier(EOEditingContext editingContext, EOQualifier qualifier, NSArray sortOrderings) {
		NSArray eoObjects = fetchAll(editingContext, qualifier, sortOrderings);
		EORapport eoObject;
		int count = eoObjects.count();
		if (count == 0) {
			eoObject = null;
		}
		else {
			eoObject = (EORapport)eoObjects.objectAtIndex(0);
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
	public static EORapport fetchFirstRequiredByKeyValue(EOEditingContext editingContext, String keyName, Object value) throws NoSuchElementException {
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
	public static EORapport fetchFirstRequiredByQualifier(EOEditingContext editingContext, EOQualifier qualifier) throws NoSuchElementException {
		EORapport eoObject = fetchFirstByQualifier(editingContext, qualifier);
		if (eoObject == null) {
			throw new NoSuchElementException("Aucun objet EORapport ne correspond au qualifier '" + qualifier + "'.");
		}
		return eoObject;
	}	

	// FetchSpecs...
	


	// Internal utilities methods for common use (server AND client)...

	private static EORapport createAndInsertInstance(EOEditingContext ec) {
		EOClassDescription classDescription = EOClassDescription.classDescriptionForEntityName(_EORapport.ENTITY_NAME);
		if(classDescription == null) {
			throw new IllegalArgumentException("Could not find EOClassDescription for entity name '" + _EORapport.ENTITY_NAME + "' !");
		}
		else {
			EORapport object = (EORapport) classDescription.createInstanceWithEditingContext(ec, null);
			ec.insertObject(object);
			return object;
		}
	}

	private static EORapport localInstanceOfObject(EOEditingContext ec, EORapport object) {
		if(object != null && ec != null) {
			EOEditingContext otherEditingContext = object.editingContext();
			if(otherEditingContext == null) {
				throw new IllegalArgumentException("The EORapport " + object + " is not in an EOEditingContext.");
			}
			else {
				com.webobjects.eocontrol.EOGlobalID globalID = otherEditingContext.globalIDForObject(object);
				return (EORapport) ec.faultForGlobalID(globalID, ec);
			}
		}
		else {
			return null;
		}
	}

}
