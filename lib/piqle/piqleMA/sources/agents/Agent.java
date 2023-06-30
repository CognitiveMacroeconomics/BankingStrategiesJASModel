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
 *    alo0ng with this program; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA.
 */

/*
 *    Agent.java
 *    Copyright (C) 2004 Francesco De Comité
 *
 */

import environnement.State; 
import environnement.Action; 
import environnement.ListeActions; 
import environnement.Contraintes; 
import outils.ExtensionFileFilter; 
import dataset.Dataset; 

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





import algorithmes.Selectionneur; 

/** The basic behavior of an Agent is : 
 <ul>
<li> According to the current state of the environment, choose the action</li>
<li> Apply this action, get the reward</li>
</ul>

Every Agent can call its underlying <i>algorithm</i>, and ask it to choose the action. 

 @author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 
*/


public class Agent implements Serializable{

    /** The current state of the agent. */
    protected State etatCourant=null; 
    /** The algorithm which chooses the action among the liste of possible ones.*/
    protected Selectionneur algorithme; 

   

    /** Access the algorithm, for exemple to change its settings. */
    public Selectionneur getAlgorithm(){
	return this.algorithme; 
    }

    /** The universe in which the agent lives (to allow the arbitre to communicate with graphical interface) */
    protected Contraintes universe; 
    public Contraintes getContraintes(){return this.universe;}

    /** Controlling whether the agent is in a learning phase or not. */
    protected boolean learningEnabled=true; 

    /** Put the agent in learning phase (default). */
    public void enableLearning(){learningEnabled=true;}
    
    /** Stop learning. */
    public void freezeLearning(){learningEnabled=false;}


    /** Last reward. */
    protected double reward=0; 

    
    /** Place the agent  in the environment.*/
    public Agent(Contraintes s, Selectionneur al){ 
	this.universe=s; 
	this.etatCourant=s.defaultInitialState(); 
	this.algorithme=al; 
    }

  
    /** Get the state where the agent is.*/
    public State getEtatCourant(){
	return this.etatCourant; 
    }

    /** Place the agent.*/
    public void setInitialState(State s){
	this.etatCourant=s; 
    }
    
    /** Ask the algorithm to choose the next action. */
    protected  Action choix(){
	ListeActions l=etatCourant.getListeActions();
	return algorithme.getChoix(l); 
    }

    
    /** <ul>
	<li>Apply the action, get the reward.</li>
	<li>If learning is enabled, learn from states, action, and reward. </li>
	<ul>*/
    protected Action applyAction(Action a){ 
	
	 State old = etatCourant;
	 
	etatCourant=etatCourant.modify(a); 
	double r=etatCourant.getReward(old,a);
	reward=r; 

	if(this.learningEnabled)
	    algorithme.apprend(old,etatCourant,r,a);
	return a; 
    }
    /** Read the last reward. */
    public double getLastReward(){return reward;}


    /** Acts */
    public Action  agir(){
	return this.applyAction(this.choix()); 
    }

    /** Try to understand the states and/or actions values found by the algorithm. */
    public void explainValues(){
	System.out.println(algorithme); 
    }

    /** From the states and actions encountered during the exploration, extracts a dataset in a
	format suitable for the Neural Network treatment. */
    public Dataset extractDataset(){
	return algorithme.extractDataset(); 
    }

    /** Save an Agent into a file : by reading it again, you can continue the learning.*/
    public void saveAgent(){
	JFileChooser chooser=new JFileChooser();
	chooser.setCurrentDirectory(new File(".")); 
	String fileName="raymond"; 
	String extension="agt";
	File sauvegarde; 
	ObjectOutputStream sortie;
	String ext[]={"agt"};
	ExtensionFileFilter filter = new ExtensionFileFilter(ext); 
	filter.setDescription("Agent file"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showSaveDialog(null); 
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	    { System.out.println("You chose to open this file: " 
				 + chooser.getSelectedFile().getName()); 
	    
	    sauvegarde=chooser.getSelectedFile(); 
	    }
	else 
	    {
		sauvegarde=new File("raymond.agt"); 
	   
	    }
	try{
	sortie=new ObjectOutputStream(new FileOutputStream(sauvegarde)); 
	sortie.writeObject(this); 
	sortie.close(); 
	}
	catch(Exception e){ System.err.println("Problem when trying to save agent "+e.getMessage()); 
	}
	
    }

    /** Same as above, but the file name is given. */
    public void saveAgent(String fichier){
	File sauvegarde; 
	ObjectOutputStream sortie;
	sauvegarde=new File(fichier+".agt"); 
	try{
	    sortie=new ObjectOutputStream(new FileOutputStream(sauvegarde)); 
	    sortie.writeObject(this); 
	    sortie.close(); 
	}
	catch(Exception e){ System.err.println("Problem when trying to save agent "+e.getMessage()); 
	}
	
    }

    /** Read an agent's description from a file. */
    static public Agent readAgent(String fichier,Contraintes s){
	File fichierALire=new File(fichier+".agt"); 
	ObjectInputStream entree;
	Agent resultat=null; 
	try{
	    entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	    resultat=(Agent)entree.readObject(); 
	    entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	resultat.etatCourant=s.defaultInitialState(); 
	return resultat; 
    }// readAgent

    /** Read an agent's description from a file, but find itself the universe the agent was into */
    static public Agent readAgent(String fichier){
	File fichierALire=new File(fichier+".agt"); 
	ObjectInputStream entree;
	Agent resultat=null; 
	try{
	    entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	    resultat=(Agent)entree.readObject(); 
	    entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	Contraintes s=resultat.getContraintes(); 
	resultat.etatCourant=s.defaultInitialState(); 
	return resultat; 
    }// readAgent




    /** Same as above, but the file name is not given.*/
	
    static public Agent readAgent(Contraintes s){
	JFileChooser chooser=new JFileChooser();
	chooser.setCurrentDirectory(new File(".")); 
	String fileName="raymond"; 
	String extension="agt";
	File fichierALire; 
	ObjectInputStream entree;
	ExtensionFileFilter filter = new ExtensionFileFilter(); 
	Agent resultat=null; 
	filter.addExtension(extension);  
	filter.setDescription("Agent file"); 
	chooser.setFileFilter(filter); 
	int returnVal = chooser.showOpenDialog(null); 
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	    { System.err.println("You chose to open this file: " 
				 + chooser.getSelectedFile().getName()); 
	    
	    fichierALire=chooser.getSelectedFile(); 
	    }
	else 
	    {
		return null; 
	   
	    }
	try{
	entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	resultat=(Agent)entree.readObject(); 
	entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading agent file. "+e.getMessage()); }
	resultat.etatCourant=s.defaultInitialState(); 
	return resultat; 
    }
	
  
    /** Start a new episode, ask the algorithm to do the same. */
    public void nouvelEpisode(){
	reward=0.0;
	if(this.algorithme!=null)
	    this.algorithme.nouvelEpisode(); 
    }

   
	
	
}
