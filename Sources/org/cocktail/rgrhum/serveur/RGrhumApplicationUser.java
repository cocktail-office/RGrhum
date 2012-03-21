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

package org.cocktail.rgrhum.serveur;

import org.cocktail.fwkcktldroitsutils.common.metier.EOUtilisateur;
import org.cocktail.fwkcktldroitsutils.common.ApplicationUser;

import com.webobjects.eocontrol.EOEditingContext;

/**
 * Représente un utilisateur de l'application RGrhum.
 * Seules les specificites exclusives à l'application sont decrites dans cette classe
 */

public class RGrhumApplicationUser extends ApplicationUser {

	public RGrhumApplicationUser(EOEditingContext ec, Integer persId) {
		super(ec, persId);
	}

	public RGrhumApplicationUser(EOEditingContext ec, EOUtilisateur utilisateur) {
		super(ec, utilisateur);
	}

	/** 
	 * Les apis suivantes necessitent l'identification de l'application via les tables de Jefy_admin
	 * @link https://sites.google.com/a/cocktail.org/developpeurs/wo#TOC-D-l-guer-la-gestion-des-droits-d-un
	 * 
	 */
	public RGrhumApplicationUser(EOEditingContext ec, String tyapStrId, Integer persId) {
		super(ec, tyapStrId, persId);
	}

	public RGrhumApplicationUser(EOEditingContext ec, String tyapStrId, EOUtilisateur utilisateur) {
		super(ec, tyapStrId, utilisateur);
	}


}
