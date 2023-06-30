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
 *    Arm3.java
 *    Copyright (C) 2006 Francesco De Comité
 *
 */
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JFrame; 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 

import javax.swing.BoxLayout;
import environnement.AbstractContraintesSolitaire; 
import environnement.State; 
import environnement.Action; 
import environnement.ComposedAction; 
import environnement.ListeActions;


/** A three segments Arm */

public class Arm3  extends AbstractMember{

 

  /** Arm angles */
    protected double alpha; 
    protected double beta; 
    protected double delta; 


     public Arm3(){
	this.taille=6;
	this.xTarget=Math.sqrt(2.0); 
	this.yTarget=Math.sqrt(2.0); 
	this.defaultInitialState=new MaabacState(this,taille); 
	for(int i=0;i<taille;i++)
	    this.defaultInitialState.init(i,false); 
	int c[]=new int[6]; 
	int m[]=new int[6]; 
	for(int i=0;i<6;i++){
	    c[i]=this.defaultInitialState.getValue(i); 
	    m[i]=this.defaultInitialState.getMaxValue(i);
	}
	
	this.alpha=Math.PI*(1+((c[0]/(m[0]+0.0))-(c[1]/(m[1]+0.0))))/2; 
	this.beta=Math.PI*(1+((c[2]/(m[2]+0.0))-(c[3]/(m[3]+0.0))))/2; 
	this.delta=Math.PI*(1+((c[4]/(m[4]+0.0))-(c[5]/(m[5]+0.0))))/2; 
	this.x=Math.cos(alpha)+Math.cos(beta)+Math.cos(delta); 
	this.y=Math.sin(alpha)+Math.sin(beta)+Math.cos(delta); 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 


    }

    public Arm3(double x,double y,double r){
	this(); 
	this.xTarget=x; 
	this.yTarget=y; 
	this.rTarget=r; 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 	
    }

    public void setState(int c[],int m[]){
	super.setState(c,m); 
	// Only valid for Arm !!!
	this.alpha=Math.PI*(1+((c[0]/(m[0]+0.0))-(c[1]/(m[1]+0.0))))/2; 
	this.beta=Math.PI*(1+((c[2]/(m[2]+0.0))-(c[3]/(m[3]+0.0))))/2; 
	this.delta=Math.PI*(1+((c[4]/(m[4]+0.0))-(c[5]/(m[5]+0.0))))/2; 
	this.x=Math.cos(alpha)+Math.cos(beta)+Math.cos(delta); 
	this.y=Math.sin(alpha)+Math.sin(beta)+Math.sin(delta); 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 
	
    }

    public State etatSuivant(State s,Action a){
	MaabacState ms=(MaabacState)s; 
	MaabacState nouveau=(MaabacState)ms.copy(); 
	ComposedAction ca=(ComposedAction)a; 
	for(int i=0;i<this.taille;i++){
	    nouveau.changeValue(i,(MuscleAction)(ca.get(i))); 
	}
	int c0=ms.getValue(0); int m0=ms.getMaxValue(0); 
	int c1=ms.getValue(1); int m1=ms.getMaxValue(1); 
	int c2=ms.getValue(2); int m2=ms.getMaxValue(2); 
	int c3=ms.getValue(3); int m3=ms.getMaxValue(3); 
	int c4=ms.getValue(4); int m4=ms.getMaxValue(4); 
	int c5=ms.getValue(5); int m5=ms.getMaxValue(5); 
	this.alpha=Math.PI*(1+((c0/(m0+0.0))-(c1/(m1+0.0))))/2; 
	this.beta=Math.PI*(1+((c2/(m2+0.0))-(c3/(m3+0.0))))/2; 
	this.delta=Math.PI*(1+((c4/(m4+0.0))-(c5/(m5+0.0))))/2; 

	this.x=Math.cos(alpha)+Math.cos(beta)+Math.cos(delta); 
	this.y=Math.sin(alpha)+Math.sin(beta)+Math.sin(delta); 
	nouveau.setDist(this.computeDist()); 
	nouveau.setDir(this.computeDir()); 
	return nouveau; 
    }// etatSuivant

   
   
    // Graphical Interface

    protected Arm3Design designArea; 
    protected boolean graphicsEnabled=false; 
    protected boolean isVisible=false; 
    
    public void setGraphics(){
	if(this.graphicsEnabled){
	    if(!this.isVisible) graf.setVisible(true);
	    return;
	}
	this.graphicsEnabled=true; 
	this.isVisible=true; 
	graf=new JFrame(); 
	graf.setSize(new Dimension(400,400)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new Arm3Design(400,400); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 
    }  
    public void sendState(State e){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.designArea.plot(alpha,beta,delta,xTarget,yTarget,rTarget);
	
    }
    public void clearGraphics(){this.designArea.reinit(); }
    
    public void closeGraphics(){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.isVisible=false; 
	graf.setVisible(false); 
    }

}

  