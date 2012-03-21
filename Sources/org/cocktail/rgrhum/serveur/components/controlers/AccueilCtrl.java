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

/*******************************************************************************
 *  Copyright CONSORTIUM COCKTAIL (www.cocktail.org), 1995, 2008
 *  
 *  This software is governed by the CeCILL license under French law and abiding
 *  by the rules of distribution of free software. You can use, modify and/or
 *  redistribute the software under the terms of the CeCILL license as circulated
 *  by CEA, CNRS and INRIA at the following URL "http://www.cecill.info".
 *  
 *  As a counterpart to the access to the source code and rights to copy, modify
 *  and redistribute granted by the license, users are provided only with a
 *  limited warranty and the software's author, the holder of the economic
 *  rights, and the successive licensors have only limited liability.
 *  
 *  In this respect, the user's attention is drawn to the risks associated with
 *  loading, using, modifying and/or developing or reproducing the software by
 *  the user in light of its specific status of free software, that may mean that
 *  it is complicated to manipulate, and that also therefore means that it is
 *  reserved for developers and experienced professionals having in-depth
 *  computer knowledge. Users are therefore encouraged to load and test the
 *  software's suitability as regards their requirements in conditions enabling
 *  the security of their systems and/or data to be ensured and, more generally,
 *  to use and operate it in the same conditions as regards security.
 *  
 *  The fact that you are presently reading this means that you have had
 *  knowledge of the CeCILL license and that you accept its terms.
 * 
 *******************************************************************************/

package org.cocktail.rgrhum.serveur.components.controlers;

import org.apache.log4j.Logger;
import org.cocktail.fwkcktldroitsutils.common.metier.EOPersonne;
import org.cocktail.fwkcktlevenement.FwkCktlEvenementPrincipal;
import org.cocktail.fwkcktlevenement.common.exception.ExceptionTacheCreation;
import org.cocktail.fwkcktlevenement.common.util.DateUtilities;
import org.cocktail.fwkcktlevenement.common.util.FwkCktlEvenementUtil;
import org.cocktail.fwkcktlevenement.common.util.quartz.FwkCktlEvenementSchedulerUtil;
import org.cocktail.fwkcktlevenement.serveur.modele.EOEvenement;
import org.cocktail.fwkcktlevenement.serveur.modele.EOEvenementType;
import org.cocktail.fwkcktlevenement.serveur.modele.EOTache;
import org.cocktail.fwkcktlevenementguiajax.serveur.components.EvenementForm.TypeOffset;
import org.cocktail.rgrhum.serveur.RGrhumHelpers;
import org.cocktail.rgrhum.serveur.Session;
import org.cocktail.rgrhum.serveur.components.Accueil;
import org.cocktail.rgrhum.serveur.components.ListeAnomaliesPage;
import org.cocktail.rgrhum.serveur.metier.job.JobValidationReport;
import org.quartz.Trigger;

import com.ibm.icu.util.Calendar;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSForwardException;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation.ValidationException;

import er.extensions.appserver.ERXRedirect;
import er.extensions.eof.ERXEC;

public class AccueilCtrl extends Object {
    
	public Accueil wocomponent;
	public EOEditingContext edc;
	private Trigger itemNextTrigger;
	
	public AccueilCtrl(Accueil aWOComponent) {
		wocomponent = aWOComponent;
		edc = wocomponent.session().defaultEditingContext();
    }

	public WOActionResults creer() {
		// TODO Ajouter le code de creation
		return null;
	}
	
	public WOActionResults goProgrammationPeriode() {
//		return_type page = (return_type) this.pageWithName(return_type.class.getName());
//		
//		return page;
		return null;
	}
	
	public WOActionResults doNouveauRapport() {
		
		// clean des evt passés ??
		//creation nouvel evenement + tache specifique
		EOEditingContext editingContextForEvent = ERXEC.newEditingContext();
		
		NSTimestamp datePrevue = DateUtilities.decalerTimestamp(new NSTimestamp(), 1, Calendar.MINUTE);
		
		EOEvenement event = FwkCktlEvenementUtil.creerNouvelEvenementPonctuel(
				EOEvenementType.ID_INTERNE_AUTRE, 
				datePrevue, 
				"Validation du referentiel GRhum", "Validation du referentiel GRhum",
				utilisateur().localInstanceIn(editingContextForEvent), 
				editingContextForEvent);
		event.setUtilisateurModifRelationship(utilisateur().localInstanceIn(editingContextForEvent));
		
//        int unite = DateUtilities.Minutes.getCode();
//        TypeOffset typeOffset = TypeOffset.Apres;
//        int triggerOffsetInMinutes = DateUtilities.toMinutes(1, unite);
//        if (TypeOffset.Avant.equals(typeOffset))
//        	triggerOffsetInMinutes = -triggerOffsetInMinutes;

        try {
			EOTache newTask = EOTache.creerTacheExecutionTacheApplication(event, "Validation du referentiel GRhum", 
			    						JobValidationReport.class.getCanonicalName(), false, 
			    						EOTache.ETAT_NON_PROGRAMMEE, utilisateur().localInstanceIn(editingContextForEvent), editingContextForEvent);
//			newTask.setTriggerOffsetInMinutes(triggerOffsetInMinutes);
			newTask.setUtilisateurModifRelationship(utilisateur().localInstanceIn(editingContextForEvent));
		} catch (ExceptionTacheCreation e) {
			logger().error("Une erreur est survenue lors de la création de la tache associé à l'evenement "+event, e);
            throw new NSForwardException(e, "Une erreur est survenue lors de la création de la tache associé à l'evenement "+event);

		}
		
		//Sauvegarde de l'evt, programmation des taches associées et affichage de la page d'attente
		try {
            if (editingContextForEvent.hasChanges()){
                editingContextForEvent.saveChanges();
                logger().info("Programmation des tâches éventuelles corespondants à l'évènement " + event);
                FwkCktlEvenementSchedulerUtil.programmerTachesEvenement(event, new NSTimestamp(), FwkCktlEvenementPrincipal.instance().getScheduler(), editingContextForEvent);
                wocomponent.session().addSimpleInfoMessage("Nouvelle validation programmée avec succès.", null);
            }
//            ERXRedirect nextPage = (ERXRedirect)wocomponent.pageWithName(ERXRedirect.class.getName());
//            return nextPage;
            return null;
        }  catch (ValidationException e2) {
            logger().info(e2.getMessage(), e2);
            wocomponent.session().addSimpleErrorMessage(e2.getLocalizedMessage(), e2);
        } catch (Exception e) {
            logger().error("Une erreur est survenue lors de l'enregistrement en base ou du scheduling de l'évènement " + event+"\n"+e.getMessage(), e);
            throw new NSForwardException(e, "Une erreur est survenue lors de l'enregistrement en base ou du scheduling de l'évènement " + event +"\n"+e.getMessage());
        }
		
		
		return null;
	}

	private EOPersonne utilisateur() {
		return ((Session)wocomponent.session()).applicationUser().getPersonne();
	}

	public WOActionResults goLastResult() {
//		ListeAnomaliesPage nextPage = (ListeAnomaliesPage) wocomponent.pageWithName(ListeAnomaliesPage.class.getName());
		return  wocomponent.pageWithName(ListeAnomaliesPage.class.getName());
	}
	
	public WOActionResults goLastResulRedirect() {
		ListeAnomaliesPage listeAnomaliePage = (ListeAnomaliesPage) wocomponent.pageWithName(ListeAnomaliesPage.class.getName());
		ERXRedirect redirect = (ERXRedirect)wocomponent.pageWithName(ERXRedirect.class.getName());
        redirect.setComponent(listeAnomaliePage);
		return redirect;
	}
	
	public Logger logger() {
		return wocomponent.logger();
	}
	
	public boolean lastResultMenuDisabled() {
		return wocomponent.getRapport()==null;
	}
	
	public boolean doNewValidationEnabled() {
		return !isValidationInProgress();
	}
	
	public boolean doNewValidationDisabled() {
		return !doNewValidationEnabled();
	}
	
	public boolean isValidationInProgress() {
		return RGrhumHelpers.isValidationInProgress();
	}
	
	public boolean hasNextScheduledValidation() {
		return RGrhumHelpers.hasNextScheduledValidation();
	}
	
	public NSArray<Trigger> getNextTriggers() {
		return RGrhumHelpers.getAllNextScheduledValidations();
	}

	/**
	 * @return the itemNextTrigger
	 */
	public Trigger getItemNextTrigger() {
		return itemNextTrigger;
	}

	/**
	 * @param itemNextTrigger the itemNextTrigger to set
	 */
	public void setItemNextTrigger(Trigger itemNextTrigger) {
		this.itemNextTrigger = itemNextTrigger;
	}
	
	public boolean hasScheduler() {
		return FwkCktlEvenementPrincipal.instance().getScheduler() !=null;
	}
	
}