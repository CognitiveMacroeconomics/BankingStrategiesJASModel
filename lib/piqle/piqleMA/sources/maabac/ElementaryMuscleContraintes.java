package maabac; 
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation; either version 2.1 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    ElementaryMuscleContraintes.java
 *    Copyright (C) 2006 Francesco De Comit�
 *
 */

import environnement.ElementaryMultiAgentContraintes; 
import environnement.State; 
import environnement.ListeActions; 

/** Actions are contract, decontract, let the muscle still. */

public class ElementaryMuscleContraintes  extends ElementaryMultiAgentContraintes{
    
    public ListeActions getListeActions(State s){
	ListeActions loa=new ListeActions(s); 
	loa.add(MuscleAction.STILL); 
	loa.add(MuscleAction.CONTRACT);
	loa.add(MuscleAction.DECONTRACT);
	return loa;
    }
}
