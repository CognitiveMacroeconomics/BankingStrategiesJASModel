package environment; 

import java.util.Random; 
import java.util.Iterator;
import tiling.*; 


public class TileAbleEnvironmentRL extends AbstractEnvironmentSingle implements TileAbleEnvironment{
    
    /** Number of tiling for each action */
    protected int nbt=10; // 10
    protected int nnbdiv=40; // 40
 /**  Gives the list of possible actions from a given state. */
    public ActionList getActionList(IState s){
	System.out.println("getActionList : should not be used !"); 
	System.exit(-1); 
	return null; 
    }
	    
    /**  Computes the next state, given a start state and an action. */
    public IState successorState(IState s,IAction a){
	System.out.println("successorState : should not be used !"); 
	System.exit(-1); 
	return null;
    }

    public IState defaultInitialState(){
	System.out.println("defaultInitialState : should not be used !"); 
	System.exit(-1); 
	return null;
    }
	    
    public double getReward(IState s1,IState s2,IAction a){
	System.out.println("getReward : should not be used !"); 
	System.exit(-1);
	return 0.0; 
    }
	    
    public boolean isFinal(IState s){
	System.out.println("isFinal : should not be used !"); 
	System.exit(-1);
	return false;
    }

    public int whoWins(IState s){
	System.out.println("isFinal : should not be used !"); 
	System.exit(-1);
	return 0;
    }

    private static Random generateur=new Random(); 
    private SetOfTilings arrayTilings[]; 
    private int nbDimStates=StateRL.size; 
    private int nbAct; 

    protected void addTiling(HyperRectangularSparseTiling t,int level){
	arrayTilings[level].addTiling(t); 
    }
    

    /** Constructor : need the number of actions, and the state's definition 
	Ugly : collecting information about state by mean of reading protected static fields of StateRL
    */

    public TileAbleEnvironmentRL(int nbOfActions,boolean[] obsTypes){
	this.nbAct=nbOfActions; 
	System.out.println("Debug "+this.nbDimStates); 
	this.arrayTilings=new SetOfTilings[nbOfActions];
	double bi[]=new double[this.nbDimStates]; 
	double bs[]=new double[this.nbDimStates]; 
	double sh[]=new double[this.nbDimStates];
	boolean pvalid[]=new boolean[this.nbDimStates]; 
	int nbdiv[]=new int[this.nbDimStates];
	// Initialising arrays
	for(int i=0;i<this.nbDimStates;i++){
	    pvalid[i]=true; 
	    nbdiv[i]=nnbdiv; 
	    if(obsTypes[i]){ // Integer case
		bi[i]=(double)StateRL.minInt[i]; 
		bs[i]=(double)StateRL.maxInt[i]; 
	    }
	    else{
		bi[i]=StateRL.minDouble[i]; 
		bs[i]=StateRL.maxDouble[i];
	    }
	}// for
	// For each possible action
	for(int i=0;i<nbOfActions;i++){
	    arrayTilings[i]=new SetOfTilings(); 
	    for(int j=0;j<nbt;j++){ // nbt tilings for a single action
		for(int k=0;k<nbDimStates;k++) sh[k]=0.2*generateur.nextDouble()*(bs[k]-bi[k]); 
		this.addTiling(new HyperRectangularSparseTiling(bi,bs,sh,nbdiv,pvalid),i); 
	    }// j
	}//i
    }// constructor
	

    public ListOfTiles getTiles(IState s,IAction a){
	ActionRL aa=(ActionRL)a; 
	StateRL ea=(StateRL)s; 
	double x[]=new double[nbDimStates]; 
	int indexInt=0;
	int indexDouble=0;
	for(int i=0;i<nbDimStates;i++){
	    if(StateRL.stateTypes[i])
		x[i]=(double)ea.stateValuesInt[indexInt++]; 
	    else
		x[i]=ea.stateValuesDouble[indexDouble++]; 
	}//for

	int index=aa.getNumber(); 
	Iterator c=arrayTilings[index].iterator(); 
	ListOfTiles resu=new ListOfTiles(); 
	while(c.hasNext()){
	    HyperRectangularSparseTiling t=(HyperRectangularSparseTiling)c.next(); 
	    resu.add(t.getTile(x)); 
	}
	return resu;   
    }// getTile

    public String toString(){// Elementary display : must be generalized 
	String s="Tiling characteritics :\n"; 
	s+="Number of tilings for one action : "+nbt+"\n"; 
	s+="Size of each tiling : "+nnbdiv+"\n"; 
	/*	for(int i=0;i<nbAct;i++) 
	    if(arrayTilings[i]!=null) 
	    s+="**"+arrayTilings[i].toString()+"\n"; */
	return s; 
    }

}// class