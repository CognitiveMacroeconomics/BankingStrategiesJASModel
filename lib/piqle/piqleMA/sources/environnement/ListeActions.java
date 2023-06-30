package environnement; 
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
 *    ListeActions.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */




import java.util.Vector; 
import java.util.Iterator; 

/** A place to put actions : used when looking for the liste of possible moves. 

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 


*/

public class ListeActions{

    /** The true list of Actions.*/
    private Vector laListe=new Vector(); 
    /** The starting state.*/
    private State etat;
    

    public ListeActions(State e){
	this.etat=e;}

    /** Adds an action.*/
    public void add(Action a){
	if(!laListe.contains(a))
	    this.laListe.add(a); 
    }

   
    public int size(){return this.laListe.size();}

    /** The starting state.*/
    public State getState(){return etat;}

    /** Get Action at rank i */
    public Action getAction(int i){ 
	if(laListe.size()!=0)
	    return (Action)(this.laListe.get(i));
	else return null; 
    }

    public String toString(){
	String s=""; 
	for(int i=0;i<this.laListe.size();i++)
	    s+=getAction(i)+"\n"; 
	return s; 
    }
}
