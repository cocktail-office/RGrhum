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
package org.cocktail.rgrhum.serveur;

import org.cocktail.fwkcktldroitsutils.common.util.MyDateCtrl;
import org.cocktail.fwkcktlevenement.FwkCktlEvenementPrincipal;
import org.cocktail.fwkcktlevenement.common.exception.ExceptionOperationImpossible;
import org.cocktail.fwkcktlevenement.common.util.FwkCktlEvenementUtil;
import org.cocktail.fwkcktlevenement.common.util.quartz.FwkCktlEvenementSchedulerUtil;
import org.cocktail.fwkcktlwebapp.common.util.NSArrayCtrl;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import er.ajax.CktlAjaxUtils;
import er.extensions.foundation.ERXArrayUtilities;

public class RGrhumHelpers {
	
	/**
     * Rajoute les styles css aux réponses.
     * @param context le contexte
     * @param response la réponse
     */
    public static void insertStylesSheet(WOContext context, WOResponse response) {
    	CktlAjaxUtils.addStylesheetResourceInHead(context, response, "FwkCktlThemes.framework", "themes/default.css");
		CktlAjaxUtils.addStylesheetResourceInHead(context, response, "FwkCktlThemes.framework", "themes/alert.css");
		CktlAjaxUtils.addStylesheetResourceInHead(context, response, "FwkCktlThemes.framework", "themes/lighting.css");

		CktlAjaxUtils.addStylesheetResourceInHead(context, response, "FwkCktlThemes.framework", "css/CktlCommon.css");
		CktlAjaxUtils.addStylesheetResourceInHead(context, response, "FwkCktlThemes.framework", "css/CktlCommonBleu.css");
		CktlAjaxUtils.addStylesheetResourceInHead(context, response, "app", "styles/rgrhum.css");
    }

    public static boolean isValidationInProgress() {
    	Scheduler scheduler = FwkCktlEvenementPrincipal.instance().getScheduler();
    	boolean isValidationInProgress = false;
    	try {
			isValidationInProgress = !scheduler.getCurrentlyExecutingJobs().isEmpty();
		} catch (SchedulerException e) {
			e.printStackTrace();
			Application.log.error(e);
		}
    	return isValidationInProgress;
	}
    
    public static boolean hasNextScheduledValidation() {
    	boolean hasNextScheduledValidation = false;
    	try {
    		NSArray<Trigger> triggers = FwkCktlEvenementUtil.getCurrentTriggers();
    		if (triggers!=null) {
    			for (Trigger trigger : triggers) {
    				if (trigger.getNextFireTime().after(MyDateCtrl.now())) {
    					hasNextScheduledValidation = true;
    					break;
    				}
    			}
			}
		} catch (ExceptionOperationImpossible e) {
			e.printStackTrace();
			Application.log.error(e);
		}
    	return hasNextScheduledValidation;
	}
    
    public static Trigger getNextScheduledValidation() {
    	Trigger nextTrigger = null;
    	if (hasNextScheduledValidation()) {
				NSArray<Trigger> triggersOrdered = getAllNextScheduledValidations();
				nextTrigger = ERXArrayUtilities.firstObject(triggersOrdered);
		}
    	return nextTrigger;
	}
    
    public static NSArray<Trigger> getAllNextScheduledValidations() {
    	NSArray<Trigger> nextTriggers = new NSArray<Trigger>();
    	if (hasNextScheduledValidation()) {
    		try {
				NSArray<Trigger> triggersOrdered = ERXArrayUtilities.sortedArraySortedWithKey(FwkCktlEvenementUtil.getCurrentTriggers(), "getNextFireTime");
				nextTriggers = triggersOrdered;
			} catch (ExceptionOperationImpossible e) {
				e.printStackTrace();
				Application.log.error(e);
			}
		}
    	return nextTriggers;
	}
}
