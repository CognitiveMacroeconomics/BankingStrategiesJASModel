/* Try to reach the target at (1.0,5)
   Graphical interface appears when 100 episodes ina row reach the target.

*/
import environnement.AbstractContraintesSolitaire; 
import environnement.State; 
import agents.Swarm; 
import algorithmes.*;
import arbitres.Solitaire; 

import maabac.ArmN;
import maabac.Muscle; 
import maabac.ElementaryMuscleContraintes;
import maabac.MuscleFilter; 

import java.util.Random; 

public class TestMaabac{

    public static void main(String argv[]){
	Random gene=new Random(); 
	// Number of arm segments */
	int nbseg=4; 
	/* Target's position  and radius */
	ArmN bras=new ArmN(nbseg,0.5,3.0,0.2); 
	Swarm essaim=new Swarm(bras); 
	SelectionneurMemoire guru[]=new SelectionneurMemoire[2*nbseg]; 
	int contr[]=new int[2*nbseg];
	int maxi[]=new int[2*nbseg];
	for(int i=0;i<2*nbseg;i++) maxi[i]=50; 
	bras.setState(contr,maxi); 
	State depart=bras.defaultInitialState(); 
	for(int i=0;i<2*nbseg;i++){
	    guru[i]=new SelectionneurQL(); 
	    guru[i].setBoltzmann(); 
 	    guru[i].setGamma(1.0);  
	    guru[i].setAlpha(0.1);  
	    guru[i].setGeometricAlphaDecay(); 
	    guru[i].setDecay(1); 
	    guru[i].setTau(0.5); 
	    
	    essaim.add(new Muscle(new ElementaryMuscleContraintes(),
				  guru[i],
				  new MuscleFilter(i),
				  depart)); 

	}
        

	Solitaire arbitre=new Solitaire(essaim); 
	arbitre.setMaxIter(1000); 
	int repet=1; 
	int maxrepet=0;
	boolean serie=false; 
	double total=0.0; 
	int birep=0;
	for(int i=1;i<100000;i++){
	    int u=arbitre.episode(depart); 
	    if(u<500) {
		if(serie) repet++; 
		else repet=1; 
		serie=true; 
		maxrepet++; 
		if(repet==100){
		    arbitre.setGraphical();
		}
		
	
	
	    }
	    else serie=false;
	
	     
	  
	    total+=u; 
	    if(i%100==0) 
		System.out.println(i+" "+u+" "+(total/(i+0.0))+" "+repet+" "+maxrepet+" "+(maxrepet+0.0)/(i+0.0)); 
	    
	
	}

    }
}