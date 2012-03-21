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
package org.cocktail.rgrhum.serveur.components;

import java.util.GregorianCalendar;

import org.cocktail.rgrhum.serveur.Application;
import org.cocktail.rgrhum.serveur.metier.EORapport;

import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSTimeZone;
import com.webobjects.foundation.NSTimestamp;

public class VueRapport extends MyWOComponent {
	private static final long serialVersionUID = -339094772037190190L;
	private static final String BDG_RAPPORT = "rapport";

	public VueRapport(WOContext context) {
        super(context);
    }
	
	public EORapport getRapport(){
		return (EORapport)valueForBinding(BDG_RAPPORT);
	}
	
	public String getDureeRapport(){
		NSTimestamp dateDeb = getRapport().rapportDDebut();
		NSTimestamp dateFin = getRapport().rapportDFin();
		String dureeStr = "n/a";
		
		long duree = (dateDeb!=null && dateFin!=null) ? Math.abs(dateFin.getTime() - dateDeb.getTime()) : 0L;
		
		long dureeS = duree / 1000L;
		
		return dureeS+"s";
	}

}