package agents; 

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
 *    ElementaryAgent.java
 *    Copyright (C) 2006 Francesco De Comité
 *
 */

/** An agent part of a Swarm, i.e. a multi agent community. The only difference with a normal agent is the method filterState */

import environnement.Contraintes;
import environnement.Filter; 
import environnement.State; 
import environnement.ListeActions;
import environnement.Action;
import algorithmes.Selectionneur; 
import outils.ExtensionFileFilter; 

import java.io.Serializable; 
import java.io.ObjectOutputStream; 
import java.io.ObjectInputStream; 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.FileInputStream; 
import javax.swing.JFileChooser; 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.WindowConstants; 


public class ElementaryAgent extends Agent{

    protected Filter fil; 
 /** Place the agent  in the environment.*/
    public ElementaryAgent(Contraintes s, Selectionneur al,Filter f,State st){ 
	super(s,al); 
	this.fil=f; 
	this.etatCourant=this.fil.filterState(st,this.universe); 
    }

    public void setEtatCourant(State s){
	this.etatCourant=this.fil.filterState(s,this.universe); 
    }
    
    protected Action applyAction(Action a){
	System.err.println("No need to call this function. Exit"); 
	System.exit(0);
	return null;
    }

    public Filter getFilter(){return this.fil;}

 /** Place the agent.*/
    public void setInitialState(State s){
	this.etatCourant=this.fil.filterState(s,this.universe); 
    }

     /** Ask the algorithm to choose the next action. */
    protected  Action choix(){
	ListeActions l=etatCourant.getListeActions();
	return algorithme.getChoix(l); 
    }

    /** Each elementary agent adds some part of reward to the global reward (MAABAC)*/
    public double internalReward(){
	return 0.0; 
    }

    /** Meaningless */
    static public Agent readAgent(String fichier,Contraintes s){
	return null;
    }
    
    /** Meaningless*/
    static public Agent readAgent(String fichier){
	return null;
    }
    
    /** Meaningless*/
    static public Agent readAgent(Contraintes s){
	return null;
    }
}