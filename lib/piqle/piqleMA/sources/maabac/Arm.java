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
 *    Arm.java
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


/** A Arm is a environment which controls 4 Muscles. It keeps track of all Muscles contractions values.*/

public class Arm  extends AbstractContraintesSolitaire{

    /** Number of Muscles */
    protected int taille=4; /* The basic arm example */

    /** Arm positions */
    protected double x; 
    protected double y; 

    /** Arm angles */
    protected double alpha; 
    protected double beta; 

    protected double beta1=1.1,
	beta2=1.5,
	beta3=2.0; 

    /** Target coordinates */
    protected double xTarget; 
    protected double yTarget; 
    /** Target Radius */
    protected double rTarget=0.2; 

    public void setTarget(double x,double y,double r){
	this.xTarget=x;
	this.yTarget=y;
	this.rTarget=r; 
    }

    public double getXtarget(){return this.xTarget;}
    public double getYtarget(){return this.yTarget;}
    public double getRtarget(){return this.rTarget;}

    
    protected MaabacState defaultInitialState; 

    public int getTaille(){return this.taille;}

    public ListeActions getListeActions(State s){
	System.err.println("Wrong fonction call, exit"); 
	System.exit(0); 
	return null;
    }

    public Arm(){
	this.defaultInitialState=new MaabacState(this,taille); 
	for(int i=0;i<taille;i++)
	    this.defaultInitialState.init(i,false); 
	this.xTarget=Math.sqrt(2.0); 
	this.yTarget=Math.sqrt(2.0); 
	int c0=this.defaultInitialState.getValue(0); 
	int m0=this.defaultInitialState.getMaxValue(0); 
	int c1=this.defaultInitialState.getValue(1); 
	int m1=this.defaultInitialState.getMaxValue(1); 
	int c2=this.defaultInitialState.getValue(2); 
	int m2=this.defaultInitialState.getMaxValue(2); 
	int c3=this.defaultInitialState.getValue(3); 
	int m3=this.defaultInitialState.getMaxValue(3); 
	this.alpha=Math.PI*(1+((c0/(m0+0.0))-(c1/(m1+0.0))))/2; 
	this.beta=Math.PI*(1+((c2/(m2+0.0))-(c3/(m3+0.0))))/2; 
	this.x=Math.cos(alpha)+Math.cos(beta); 
	this.y=Math.sin(alpha)+Math.sin(beta); 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 


    }

    public Arm(double x,double y,double r){
	this(); 
	this.xTarget=x; 
	this.yTarget=y; 
	this.rTarget=r; 
	this.defaultInitialState.setDist(this.computeDist()); 
	this.defaultInitialState.setDir(this.computeDir()); 

    }

    public void setState(int c[],int m[]){
	this.defaultInitialState=new MaabacState(this,taille); 
	for(int i=0;i<taille;i++)
	    this.defaultInitialState.init(i,c[i],m[i]); 
	// Only valid for Arm !!!
	this.alpha=Math.PI*(1+((c[0]/(m[0]+0.0))-(c[1]/(m[1]+0.0))))/2; 
	this.beta=Math.PI*(1+((c[2]/(m[2]+0.0))-(c[3]/(m[3]+0.0))))/2; 
	this.x=Math.cos(alpha)+Math.cos(beta); 
	this.y=Math.sin(alpha)+Math.sin(beta); 
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
	this.alpha=Math.PI*(1+((c0/(m0+0.0))-(c1/(m1+0.0))))/2; 
	//this.alpha=Math.PI*(1+(c0-c1)/(((m0+0.0)+(m1+0.0))/2))/2;
	this.beta=Math.PI*(1+((c2/(m2+0.0))-(c3/(m3+0.0))))/2; 
	//this.beta=Math.PI*(1+(c2-c3)/(((m2+0.0)+(m3+0.0))/2))/2;
	this.x=Math.cos(alpha)+Math.cos(beta); 
	this.y=Math.sin(alpha)+Math.sin(beta); 
	nouveau.setDist(this.computeDist()); 
	nouveau.setDir(this.computeDir()); 
	return nouveau; 
    }// etatSuivant

    protected int computeDist(){
	double dist=((this.xTarget-this.x)*(this.xTarget-this.x))
	    +((this.yTarget-this.y)*(this.yTarget-this.y)); 
	dist=Math.sqrt(dist); 
	if(dist<beta1*rTarget) return 0; 
	if(dist<beta2*rTarget) return 1; 
	if(dist<beta3*rTarget) return 2; 
	return 3; 
    }// compute Dist
    
    protected int computeDir(){
	if(this.x<this.xTarget){
	    if(this.y<this.yTarget) return 2; 
	    else return 1;
	}
	else{
	    if(this.y<this.yTarget) return 3; 
	    else return 0;
	}


    }// computeDir

     public State defaultInitialState(){
	return this.defaultInitialState;
    }

    public int whoWins(State s){if(isFinal(s)) return -1; else return 0;}

    public boolean isFinal(State s){
	double dist=((this.xTarget-this.x)*(this.xTarget-this.x))
	    +((this.yTarget-this.y)*(this.yTarget-this.y)); 
	return (Math.sqrt(dist)<this.rTarget); 
    }// isFinal

       public double getReward(State s1,State s2,Action a){
	MaabacState bls=(MaabacState)s2; 
	//	if(isFinal(s2)) System.out.println(s2); 
	if(isFinal(s2)) return 1; 
	return -0.0025;
       }

    // Graphical Interface

    protected ArmDesign designArea; 
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
	graf.setSize(new Dimension(300,300)); 
	// TODO revoir le comportement du programme en cas de quittage
	graf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
		    System.out.println("On quitte");
                    System.exit(0);
                }
            });             
	graf.getContentPane().setLayout(new BoxLayout(graf.getContentPane(),BoxLayout.Y_AXIS)); 
	designArea=new ArmDesign(300,300); 
	graf.getContentPane().add(designArea); 
	graf.setVisible(true); 
    }  
    public void sendState(State e){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.designArea.plot(alpha,beta,xTarget,yTarget,rTarget);
	
    }
    public void clearGraphics(){this.designArea.reinit(); }
    
    public void closeGraphics(){
	if(!this.graphicsEnabled) return; 
	if(!this.isVisible) return; 
	this.isVisible=false; 
	graf.setVisible(false); 
    }
    
}
    