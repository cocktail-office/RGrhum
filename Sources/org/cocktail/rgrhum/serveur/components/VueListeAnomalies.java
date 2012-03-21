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

import org.cocktail.fwkcktlajaxwebext.serveur.component.tableview.column.CktlAjaxTableViewColumn;
import org.cocktail.fwkcktlajaxwebext.serveur.component.tableview.column.CktlAjaxTableViewColumnAssociation;
import org.cocktail.fwkcktlpersonneguiajax.serveur.components.ASelectComponent;
import org.cocktail.rgrhum.serveur.metier.EOReleveAnomalie;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WODisplayGroup;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.appserver.ERXDisplayGroup;
import er.extensions.eof.ERXQ;

public class VueListeAnomalies extends MyWOComponent {
	private static final long serialVersionUID = -5269291654944117785L;

	private static final String BDG_LISTEANOMALIES = "listeAnomalies";
	private static final String ANOMALIE = "itemAnomalie";
	
	private EOReleveAnomalie itemAnomalie;
	private NSMutableArray<CktlAjaxTableViewColumn> colonnes;
	private ERXDisplayGroup<EOReleveAnomalie> anomaliesDG;
	
    public VueListeAnomalies(WOContext context) {
        super(context);
    }

	public NSArray<EOReleveAnomalie> getAnomalies() {
		return (NSArray<EOReleveAnomalie>)valueForBinding(BDG_LISTEANOMALIES);
	}

	/**
	 * @return the itemAnomalie
	 */
	public EOReleveAnomalie getItemAnomalie() {
		return itemAnomalie;
	}

	/**
	 * @param itemAnomalie the itemAnomalie to set
	 */
	public void setItemAnomalie(EOReleveAnomalie itemAnomalie) {
		this.itemAnomalie = itemAnomalie;
	}

	/**
	 * @return the colonnes
	 */
	public NSMutableArray<CktlAjaxTableViewColumn> getColonnes() {
		if (colonnes==null) {
			NSMutableArray<CktlAjaxTableViewColumn> newColonnes = new NSMutableArray<CktlAjaxTableViewColumn>();
			
			CktlAjaxTableViewColumn col = new CktlAjaxTableViewColumn();
			col.setLibelle("PersId");
			col.setOrderKeyPath(EOReleveAnomalie.PERSID_KEY);
			String keyPath = ERXQ.keyPath(ANOMALIE, EOReleveAnomalie.PERSID_KEY);
			CktlAjaxTableViewColumnAssociation ass = new CktlAjaxTableViewColumnAssociation(
					keyPath, "");
			col.setAssociations(ass);
			newColonnes.add(col);

			col = new CktlAjaxTableViewColumn();
			col.setLibelle("CStructure");
			col.setOrderKeyPath(EOReleveAnomalie.C_STRUCTURE_KEY);
			keyPath = ERXQ.keyPath(ANOMALIE, EOReleveAnomalie.C_STRUCTURE_KEY);
			ass = new CktlAjaxTableViewColumnAssociation(
					keyPath, "");
			col.setAssociations(ass);
			newColonnes.add(col);
			
			col = new CktlAjaxTableViewColumn();
			col.setLibelle("Nom");
			col.setOrderKeyPath(EOReleveAnomalie.PERSONNE_NOM_AFFICHAGE_KEY);
			keyPath = ERXQ.keyPath(ANOMALIE, EOReleveAnomalie.PERSONNE_NOM_AFFICHAGE_KEY);
			ass = new CktlAjaxTableViewColumnAssociation(
					keyPath, "");
			col.setAssociations(ass);
			newColonnes.add(col);
			
			col = new CktlAjaxTableViewColumn();
			col.setLibelle("Anomalie");
			col.setOrderKeyPath(EOReleveAnomalie.MESSAGE_ERREUR_KEY);
			keyPath = ERXQ.keyPath(ANOMALIE, EOReleveAnomalie.MESSAGE_ERREUR_KEY);
			ass = new CktlAjaxTableViewColumnAssociation(
					keyPath, "");
			col.setAssociations(ass);
			newColonnes.add(col);
			
			setColonnes(newColonnes);
		}
		return colonnes;
	}

	/**
	 * @param colonnes the colonnes to set
	 */
	public void setColonnes(NSMutableArray<CktlAjaxTableViewColumn> colonnes) {
		this.colonnes = colonnes;
	}

	/**
	 * @return the anomaliesDG
	 */
	public WODisplayGroup getAnomaliesDG() {
		if (anomaliesDG==null) {
			ERXDisplayGroup<EOReleveAnomalie> dg = new ERXDisplayGroup<EOReleveAnomalie>();
			dg.setObjectArray(getAnomalies());
			dg.setNumberOfObjectsPerBatch(50);
			setAnomaliesDG(dg);
		}
		return anomaliesDG;
	}

	/**
	 * @param anomaliesDG the anomaliesDG to set
	 */
	public void setAnomaliesDG(ERXDisplayGroup<EOReleveAnomalie> anomaliesDG) {
		this.anomaliesDG = anomaliesDG;
	}
	
}