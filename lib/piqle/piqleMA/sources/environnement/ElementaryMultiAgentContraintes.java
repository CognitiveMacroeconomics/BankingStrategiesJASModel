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
 *    ElementaryMultiAgentContraintes.java
 *    Copyright (C) 2006 Francesco De Comité
 *
 */

/** This kind of Contraintes is only able to produce a list of possible actions : all other computations are made inside a µAgentContraintes */

abstract public class ElementaryMultiAgentContraintes implements Contraintes{

    /**  Gives the list of possible actions from a given state. The only
     useful method in this class.*/
    abstract public ListeActions getListeActions(State s); 

    /**  No meaning here */
    public State etatSuivant(State s,Action a){
	return null;
    }

    /** No meaning here */ 
    public State defaultInitialState(){return null;}

    /** No meaning here */
    public double getReward(State s1,State s2,Action a){return 0.0; }

    /** No meaning here */
    public boolean isFinal(State s){return false;}

    /** No meaning here */
    public int whoWins(State s){return -1;};
}