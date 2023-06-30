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
 *    Arm3Design.java
 *    Copyright (C) 2005 Francesco De Comité
 *
 */



import javax.swing.JPanel; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

/** A graphical representation of a 3-segments arm*/
public class Arm3Design extends JPanel{
    /** Size of the inner window */
    private int xcord,ycord; 
    private int origineX=200; 
    private int origineY=200; 
    private int barSize=50; 
    
    public Arm3Design(int lo,int la){
	super(); 
	this.xcord=lo; 
	this.ycord=la;
	this.setSize(new Dimension(lo,la)); 
    }
    
    /**
     * Renders this component
     * @param gx the graphics context
     */
    
    public void paintComponent(Graphics gx) {
	super.paintComponent(gx);   
    }
    
    // l1 and l2=50 pixels
    public void plot(double t1,double t2,double t3,double xt,double yt,double rt){

	Graphics gx=this.getGraphics(); 
	this.reinit(); 
	//System.err.println(xt+" "+yt+" "+rt); 
	int jointX1=origineX+(int)(barSize*Math.sin(t1)); 
	int jointY1=origineY+(int)(barSize*Math.cos(t1)); 
	gx.drawLine(origineX,origineY,jointX1,jointY1); 
	gx.fillArc(jointX1-5,jointY1-5,10,10,0,360); 
	gx.fillArc(origineX-5,origineY-5,10,10,0,360); 
	

	double lx=barSize*Math.sin(t2); 
	double ly=barSize*Math.cos(t2); 
	gx.drawLine(jointX1,jointY1,jointX1+(int)lx,jointY1+(int)ly); 
	gx.fillArc(jointX1+(int)lx-5,jointY1+(int)ly-5,10,10,0,360); 
	
	double lxx=barSize*Math.sin(t3); 
	double lyy=barSize*Math.cos(t3); 
	gx.drawLine(jointX1+(int)lx,jointY1+(int)ly,
		    jointX1+(int)lx+(int)lxx,
		    jointY1+(int)ly+(int)lyy);
	gx.fillArc(jointX1+(int)lx+(int)lxx-5,jointY1+(int)ly+(int)lyy-5,10,10,0,360); 
	gx.drawArc(origineX+(int)(50*yt),origineY+(int)(50*xt),
		   (int)(50*rt),(int)(50*rt),0,360); 

	// temporization
	for(int i=0;i<450;i++)
	    for(int j=0;j<500;j++)
		{int zozo=(int)Math.sqrt((double)i); }
	//this.repaint(); 
    }
	 protected void reinit(){
	     this.getGraphics().clearRect(0,0,2*xcord,2*ycord); 
	 }

}