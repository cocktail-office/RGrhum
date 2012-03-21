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
package org.cocktail.rgrhum.serveur.metier.job;

import org.cocktail.fwkcktldroitsutils.common.util.MyDateCtrl;
import org.cocktail.fwkcktlevenement.FwkCktlEvenementPrincipal;
import org.cocktail.fwkcktlevenement.common.exception.ExceptionOperationImpossible;
import org.cocktail.fwkcktlevenement.common.util.FwkCktlEvenementUtil;
import org.cocktail.fwkcktlevenement.serveur.quartz.job.impl.JobEvenementImpl;
import org.cocktail.fwkcktlpersonne.common.metier.EOIndividu;
import org.cocktail.fwkcktlpersonne.common.metier.EOStructure;
import org.cocktail.fwkcktlpersonne.common.metier.IPersonne;
import org.cocktail.rgrhum.serveur.metier.EORapport;
import org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie;
import org.cocktail.rgrhum.serveur.metier.RghrumPersonneDelegate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSValidation.ValidationException;

import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.eof.ERXEOGlobalIDUtilities;

public class JobValidationReport extends JobEvenementImpl {
	private EOEditingContext editingContext;
	private EOEditingContext ecForValidations;
	
	private NSArray<EOGlobalID> individusAValiderGlobalIds;
	private NSArray<EOGlobalID> structuresAValiderGlobalIds;
	
	private EORapport nouvRapport;

	public NSData executeJob(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		getEcForValidations().lock();
		getEditingContext().lock();
		try {
			// Validation des individus
			
			// On compte les individus, pour pouvoir purger l'ec tous les 200 individus
			int nbIndividus = 0;
			for (EOGlobalID globalID : individusAValiderGlobalIds) {
//				EOEnterpriseObject eoIndividu = ERXEOGlobalIDUtilities.fetchObjectWithGlobalID(getEcForValidations(), globalID);
				EOEnterpriseObject eoIndividu = getEcForValidations().faultForGlobalID(globalID, getEcForValidations());
				RghrumPersonneDelegate personneDelegate = new RghrumPersonneDelegate((EOIndividu)eoIndividu);
				((EOIndividu)eoIndividu).setPersonneDelegate(personneDelegate);
				try {
					eoIndividu.validateForUpdate();
				} catch (ValidationException e) {
					reportAnomalie(eoIndividu, e);
				} catch (Exception e) {
					reportAnomalie(eoIndividu, e);
					FwkCktlEvenementUtil.LOG.error("Une erreur s'est produite lors de la tentative de validation de la personne :"+eoIndividu, e);
				} finally {
					getEditingContext().saveChanges();
					nbIndividus++;
					// On recycle les EC tous les 100 eos
					if (nbIndividus % 100 == 0) {
						switchEcForValidationAndPurge();
					}
				}
			}

			switchEcForValidationAndPurge();

			// Validation des structures
			int nbStructures = 0;
			for (EOGlobalID globalID : structuresAValiderGlobalIds) {
//				EOEnterpriseObject eoStructure = ERXEOGlobalIDUtilities.fetchObjectWithGlobalID(getEcForValidations(), globalID);
				EOEnterpriseObject eoStructure = getEcForValidations().faultForGlobalID(globalID, getEcForValidations());
				try {
					eoStructure.validateForUpdate();
				} catch (ValidationException e) {
					reportAnomalie(eoStructure, e);
				} catch (Exception e) {
					reportAnomalie(eoStructure, e);
					FwkCktlEvenementUtil.LOG.error("Une erreur s'est produite lors de la tentative de validation de la personne :"+eoStructure, e);
				} finally {
					getEditingContext().saveChanges();
					nbStructures++;
					// On recycle les EC tous les 100 eos
					if (nbStructures % 100 == 0) {
						switchEcForValidationAndPurge();
					}
				}
			}

			// Fin du rapport
			nouvRapport.setRapportDFin(MyDateCtrl.now());
			// Sauvegarde des relevés d'anomalies et du nouveau rapport
			try {
				getEditingContext().saveChanges();
			} catch (Exception e) {
				FwkCktlEvenementUtil.LOG.error("Une erreur s'est produite lors de l'enregistrement en base des relevés d'anomalies.", e);
				throw new JobExecutionException(e);
			}
		} finally {
			getEditingContext().unlock();
			getEcForValidations().unlock();
		}
		getEcForValidations().invalidateAllObjects();
		getEditingContext().invalidateAllObjects();

		// GC de l'EC dés que possible
		getEcForValidations().dispose();
		setEcForValidations(null);
		getEditingContext().dispose();
		setEditingContext(null);

		return null;
	}

	private void switchEcForValidationAndPurge() {
		// On crée un nouvel EC
		EOEditingContext freshEC = FwkCktlEvenementPrincipal.instance().newEditingContextForJobs();
		// On délock l'actuel et on le dispose
		getEcForValidations().unlock();
		getEcForValidations().dispose();
		// On utilisera le nouveau
		setEcForValidations(freshEC);
		// On le lock
		freshEC.lock();
		// On balance un coup de gc
		Runtime.getRuntime().gc();
	}
	
	private void reportAnomalie(EOEnterpriseObject eoIndividu, Exception e) {
		EOReleveAnomalie eoReleveAnomalie = EOReleveAnomalie.create(getEditingContext(), nouvRapport);
		eoReleveAnomalie.populate((IPersonne)eoIndividu,e);
	}
	
	@Override
	protected void initialize() throws ExceptionOperationImpossible {
		super.initialize();

		// new editing context avec une stack EOF à part pour bien séparer avec l'application.
		setEditingContext(FwkCktlEvenementPrincipal.instance().newEditingContextForJobs());
		// new EC pour effectuer les validations en dehors de l'EC principal pour ne pas sauvegarder les traitements fait lors des validations
		setEcForValidations(FwkCktlEvenementPrincipal.instance().newEditingContextForJobs());

		// suppression du dernier rapport
		getEditingContext().lock();
		try {
			NSArray<EORapport> oldRapports = EORapport.fetchAll(getEditingContext());
			for (EORapport eoRapport : oldRapports) {
				getEditingContext().deleteObject(eoRapport);
			}

			// Création du nouveau rapport
			nouvRapport = EORapport.create(getEditingContext());
			nouvRapport.setRapportDDebut(MyDateCtrl.now());

			// Sauvegarde du rapport en cours
			try {
				getEditingContext().saveChanges();
			} catch (Exception e) {
				FwkCktlEvenementUtil.LOG.error("Une erreur s'est produite lors de l'initialisation et de l'enregistrement en base du rapport de validation.", e);
				throw new ExceptionOperationImpossible(e);
			}
		} finally {
			getEditingContext().unlock();
		}
		// chargement des glovalIds des personnes du referentiel à valider
		EOEditingContext ecTempForInit = FwkCktlEvenementPrincipal.instance().newEditingContextForJobs();
		ecTempForInit.lock();
		try {
			//TODO : mettre en place par configuration la prise en compte des personnes non valides aussi.
			NSArray<EOIndividu> individusAValider = EOIndividu.fetchAllValides(ecTempForInit, null, NSArray.EmptyArray);
			//fetchAll(ecTempForInit);
			NSArray<EOStructure> structuresAValider = EOStructure.fetchAll(ecTempForInit, EOStructure.QUAL_STRUCTURES_VALIDE);
			//fetchAll(ecTempForInit);

			individusAValiderGlobalIds = ERXEOControlUtilities.globalIDsForObjects(individusAValider);
			structuresAValiderGlobalIds = ERXEOControlUtilities.globalIDsForObjects(structuresAValider);
		} finally {
			ecTempForInit.unlock();
			// Libération de la mémoire
			ecTempForInit.dispose();
			ecTempForInit=null;
			Runtime.getRuntime().gc();
		}

	}

	/**
	 * @return the editingContext
	 */
	public EOEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * @param editingContext the editingContext to set
	 */
	public void setEditingContext(EOEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * @return the ecForValidations
	 */
	public EOEditingContext getEcForValidations() {
		return ecForValidations;
	}

	/**
	 * @param ecForValidations the ecForValidations to set
	 */
	public void setEcForValidations(EOEditingContext ecForValidations) {
		this.ecForValidations = ecForValidations;
	}

}
