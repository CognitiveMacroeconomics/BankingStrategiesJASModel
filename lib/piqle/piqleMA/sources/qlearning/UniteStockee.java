package qlearning;

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
 *    UniteStockee.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import environnement.Action; 
import environnement.State; 

import java.io.Serializable;  


/** Acts like a key to store Q(s,a) values. The key is the union of state and action.

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/

public class UniteStockee implements Serializable{

    /** Action part of the key. */
    private Action myAction; 
    /** State part of the key.*/
    private State myState; 

    public UniteStockee(Action a,State s){
	this.myAction=a; 
	this.myState=s; 
    }

    public int hashCode(){
	return myAction.hashCode()+myState.hashCode(); 
    }

    public boolean equals(Object o){
	if(!(o instanceof UniteStockee)) return false; 
	UniteStockee us=(UniteStockee)o; 
	// System.out.println(us.myState+" /  "+this.myState); 
// 	System.out.println(us.myAction+" /  "+this.myAction); 
	//	if(us.myState.equals(this.myState)&&us.myAction.equals(this.myAction)) System.out.println("Meme unité"); 
 	//else System.out.println("Unités différentes"); 
	return (us.myState.equals(this.myState)&&us.myAction.equals(this.myAction)); 
    }

    public State getState(){return myState; }
    public Action getAction(){return myAction; }

    public String toString(){
	    return myState+" "+myAction; 
    }
}
