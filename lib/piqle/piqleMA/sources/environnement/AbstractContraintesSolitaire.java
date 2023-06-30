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
 *    AbstractContraintesSolitaire.java
 *    Copyright (C) 2005,USTL  Francesco De Comité
 *
 */


/** For the moment (september 2005,USTL ), graphical interface is only provided for one player game/puzzles : this class defines the minimal behaviour of a universe in which graphical display is possible.

@author Francesco De Comite (decomite at lifl.fr)
 @version $Revision: 1.0 $ 

*/
import javax.swing.JFrame; 
import javax.swing.BoxLayout; 
import javax.swing.JTextArea; 
import java.awt.*; 
import java.awt.event.*; 


abstract public class AbstractContraintesSolitaire implements Contraintes{

    /**  Gives the list of possible actions from a given state. */
    abstract public ListeActions getListeActions(State s); 
    
    /**  Computes the next state, given a start state and an action. */
    abstract public State etatSuivant(State s,Action a); 

    abstract public State defaultInitialState(); 
    
    abstract public double getReward(State s1,State s2,Action a); 
    
    abstract public boolean isFinal(State s); 

    abstract public int whoWins(State s);
    
    /* Graphical methods */
    
    
    protected JFrame graf; 
    protected JTextArea defaultText=new JTextArea("Default Text"); 
    public void setGraphics(){
	graf=new JFrame(); 
	graf.setSize(new Dimension(300,300)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	
	graf.getContentPane().add(defaultText); 
	graf.setVisible(true); 

} 
    public void closeGraphics(){}
    public void sendState(State e){defaultText.setText(e+""); }
    public void clearGraphics(){}



}
    
