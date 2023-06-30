package PiqleAgent; 

import rlglue.agent.Agent;
import rlglue.types.Action;
import rlglue.types.Observation;


import rlVizLib.utilities.TaskSpecObject;




import java.util.Random;
import java.util.Scanner;
import java.io.*;

import environment.ActionList;  
import environment.ActionRL; 
import environment.StateRL;

import algorithms.*; 
import qlearning.*;



public class PiqleAgent implements Agent 
{

    /** Storing information */
    TaskSpecObject TSO; 


    /** Informations from the taskSpec string */
    private int version;
    /** True if task is episodic */
    private boolean episodic; 
    
    private int obsDim; 
    private int actDim; 

    private int num_discrete_obsDim; 
    private int num_cont_obsDim; 

    private int num_discrete_actDim; 
    private int num_cont_actDim; 


    

    /** True if type is integer, false otherwise */
    private boolean obsTypes[]; 
    private boolean actTypes[]; 

    private double obs_minDouble[]; 
    private double obs_maxDouble[];

    private int obs_minInt[]; 
    private int obs_maxInt[]; 

    private double act_minDouble[]; 
    private double act_maxDouble[];

    private int act_minInt[]; 
    private int act_maxInt[]; 


    private double maxReward,minReward; 

    private boolean freeze = false;
    
    private WatkinsNNSelectorSinglePass guru;
    private double epsilon; 


    // Experiment monitoring
    private int totalSteps=0; 
    private int episodeSteps; 
    private boolean throughFinalState; 
    private int episodeNumber; 
    private double pastMem[]; 
    private int indexPastMem; 
    private double moyenne; 
    private double globalReward=0; 
    
    private StateRL currentState,oldState; 
    private ActionRL lastAction; 




    



    protected Random rand;
    protected Action previousAction;
    protected Observation previousObservation;



    /** List of possible actions : can be build as soon as we know the description of action space */
    private ActionList possibleActions; 
  
 

    public void saveAlgo(String fileName) {
	File sauvegarde; 
	ObjectOutputStream sortie;
	sauvegarde=new File(fileName+".agt"); 
	try{
	    sortie=new ObjectOutputStream(new FileOutputStream(sauvegarde)); 
	    sortie.writeObject(this.guru); 
	    sortie.close(); 
	}
	catch(Exception e){ System.err.println("Problem when trying to save algorithm "+e.getMessage()+"***"+e); 
	}
	
	}

    public static AbstractMemorySelector readAlgo(String fichier) {
	return null;
	/*	File fichierALire=new File(fichier+".agt"); 
	ObjectInputStream entree;
	AbstractMemorySelector resultat=null; 
	try{
	    entree=new ObjectInputStream(new FileInputStream(fichierALire)); 
	    resultat=(AbstractMemorySelector)entree.readObject(); 
	    entree.close(); 
	}
	catch(Exception e){ System.err.println("Problem when reading algo file. "+e.getMessage()); }
	return resultat; */
	}

   

    public PiqleAgent() 
    {
	rand = new Random();
    }

   

    public void agent_init(final String taskSpec) 
    {
	
	System.out.println(taskSpec); 
	this.TSO=new TaskSpecObject(taskSpec); 

	this.version=(int)this.TSO.version; 
	this.episodic=(this.TSO.episodic=='i'); 

	this.obsDim=this.TSO.obs_dim; 
	this.actDim=this.TSO.action_dim; 

	this.num_discrete_obsDim=this.TSO.num_discrete_obs_dims; 
	this.num_cont_obsDim=this.TSO.num_continuous_obs_dims; 

	this.num_discrete_actDim=this.TSO.num_discrete_action_dims; 
	this.num_cont_actDim=this.TSO.num_continuous_action_dims; 
	
	this.obsTypes=new boolean[this.obsDim]; 
	this.actTypes=new boolean[this.actDim]; 

	for(int i=0;i<this.obsDim;i++) this.obsTypes[i]=(this.TSO.obs_types[i]=='i'); 
	for(int i=0;i<this.actDim;i++) this.actTypes[i]=(this.TSO.action_types[i]=='i'); 

	this.obs_minDouble=new double[this.obsDim]; 
	this.obs_maxDouble=new double[this.obsDim]; 
	this.obs_minInt=new int[this.obsDim]; 
	this.obs_maxInt=new int[this.obsDim]; 

	this.act_minDouble=new double[this.actDim]; 
	this.act_maxDouble=new double[this.actDim]; 
	this.act_minInt=new int[this.actDim]; 
	this.act_maxInt=new int[this.actDim]; 

	//	for(int i=0;i<this.obsDim;i++) System.out.println(i+"---> "+this.TSO.obs_mins[i]); 


	
	    
	for(int i=0;i<this.obsDim;i++){
	    // Lower bounds for obs
	    if(this.obsTypes[i]){
		System.out.println("integer"); 
		if((this.TSO.obs_mins[i]!=Double.NaN)&&(this.TSO.obs_mins[i]!=Double.NEGATIVE_INFINITY))
		    this.obs_minInt[i]=(int)this.TSO.obs_mins[i]; 
		else 
		    this.obs_minInt[i]=Integer.MIN_VALUE; 
	    }
	    else
		{
		    System.out.println("double"); 
		    if((this.TSO.obs_mins[i]!=Double.NaN)&&(this.TSO.obs_mins[i]!=Double.NEGATIVE_INFINITY))
		    this.obs_minDouble[i]=this.TSO.obs_mins[i]; 
		else 
		    this.obs_minDouble[i]=Double.NEGATIVE_INFINITY; 
		}
	    // Upper bounds for obs

	    if(this.obsTypes[i]){
		if((this.TSO.obs_maxs[i]!=Double.NaN)&&(this.TSO.obs_maxs[i]!=Double.POSITIVE_INFINITY))
		    this.obs_maxInt[i]=(int)this.TSO.obs_maxs[i]; 
		else 
		    this.obs_maxInt[i]=Integer.MAX_VALUE; 
	    }
	    else
		{
		    if((this.TSO.obs_maxs[i]!=Double.NaN)&&(this.TSO.obs_maxs[i]!=Double.POSITIVE_INFINITY))
		    this.obs_maxDouble[i]=this.TSO.obs_maxs[i]; 
		else 
		    this.obs_maxDouble[i]=Double.POSITIVE_INFINITY; 
		}
	    
	

	}

    

	for(int i=0;i<this.actDim;i++){
	    // Lower bounds for actions
	    if(this.actTypes[i]){
		if((this.TSO.action_mins[i]!=Double.NaN)&&(this.TSO.action_mins[i]!=Double.NEGATIVE_INFINITY))
		    this.act_minInt[i]=(int)this.TSO.action_mins[i]; 
		else 
		    this.act_minInt[i]=Integer.MIN_VALUE; 
	    }
	    else
		{
		    if((this.TSO.action_mins[i]!=Double.NaN)&&(this.TSO.action_mins[i]!=Double.NEGATIVE_INFINITY))
		    this.act_minDouble[i]=this.TSO.action_mins[i]; 
		else 
		    this.act_minDouble[i]=Double.NEGATIVE_INFINITY; 
		}
	    // Upper bounds for actions

	   
	    if(this.actTypes[i]){
		if((this.TSO.action_maxs[i]!=Double.NaN)&&(this.TSO.action_maxs[i]!=Double.POSITIVE_INFINITY))
		    this.act_maxInt[i]=(int)this.TSO.action_maxs[i]; 
		else 
		    this.act_maxInt[i]=Integer.MAX_VALUE; 
	    }
	    else
		{
		    if((this.TSO.action_maxs[i]!=Double.NaN)&&(this.TSO.action_maxs[i]!=Double.POSITIVE_INFINITY))
		    this.act_maxDouble[i]=this.TSO.action_maxs[i]; 
		else 
		    this.act_maxDouble[i]=Double.POSITIVE_INFINITY; 
		}
	    
	

	}


	this.minReward=this.TSO.reward_min; 
	this.maxReward=this.TSO.reward_max; 
 

	// Survey
	System.out.println("Observation space dimension : "+this.obsDim); 
	System.out.println("Action space dimension : "+this.actDim); 

	// verifying obs
	for(int i=0;i<this.obsDim;i++){
	    System.out.print("Obs. space dimension "+i+" : "); 
	    if(this.obsTypes[i]) System.out.println("Integer"); else System.out.println("Double"); 
	    System.out.print("Lower bound : "); 
	    if(this.obsTypes[i]) System.out.println(this.obs_minInt[i]); else  System.out.println(this.obs_minDouble[i]);
	     System.out.print("Upper bound : "); 
	    if(this.obsTypes[i]) System.out.println(this.obs_maxInt[i]); else  System.out.println(this.obs_maxDouble[i]);

	}

	for(int i=0;i<this.actDim;i++){
	    System.out.print("Act. space dimension "+i+" : "); 
	    if(this.actTypes[i]) System.out.println("Integer"); else System.out.println("Double"); 
	    System.out.print("Lower bound : "); 
	    if(this.actTypes[i]) System.out.println(this.act_minInt[i]); else  System.out.println(this.act_minDouble[i]);
	    System.out.print("Upper bound : "); 
	    if(this.actTypes[i]) System.out.println(this.act_maxInt[i]); else  System.out.println(this.act_maxDouble[i]);
	     
	}
	


       
	System.out.println("Reward range :\n"+this.minReward+","+this.maxReward); 

	this.possibleActions=buildPossibleActionList();     
	System.out.println(this.possibleActions);
	StateRL.init(this.obsDim,
		     this.num_discrete_obsDim,
		     this.num_cont_obsDim,
		     this.obsTypes,
		     this.obs_minDouble,
		     this.obs_maxDouble,
		     this.obs_minInt,
		     this.obs_maxInt,
		     this.possibleActions); 

	this.guru=new WatkinsNNSelectorSinglePass(0.9,new ConstantValueChooser(3));
	this.epsilon=0.9; 

	guru.setBoltzmann(); 
	guru.setEpsilon(this.epsilon); 
	guru.setGeometricAlphaDecay();
	guru.setExponentialAlphaDecay();
	guru.setGamma(0.9);
	guru.setAlpha(0.1); 
	guru.setDecay(0.9999999); 

	StateRL.setEqualCoef(0.001); 
	System.out.println(StateRL.printInfo()); 
	
       	this.freeze=false; 
	guru.setFreeze(this.freeze); 
	
	System.out.println(guru.toString()); 

	this.throughFinalState=true;
	this.episodeNumber=-1;
	this.pastMem=new double[100]; 
	this.indexPastMem=0; 
	this.moyenne=0; 
    }

    private ActionList buildPossibleActionList(){
	ActionRL.init(this.actDim,this.actTypes,this.act_minDouble,this.act_maxDouble,this.act_minInt,this.act_maxInt,this.num_discrete_actDim,this.num_cont_actDim); 
	return ActionRL.getActionList();
    }
    
   

    public Action agent_start(Observation o)
    {
	if(this.episodeNumber==3) {this.saveAlgo("/tmp/save"); System.out.println(this.guru); }
	//if(this.episodeNumber==5) {System.out.println(guru);this.guru=(NNSelector)this.readAlgo("/tmp/save"); System.out.println(guru);}

	if(this.episodeNumber>=0){
	    System.out.print("Episode "+this.episodeNumber+" steps : "+(this.episodeSteps+1)); 
	    this.moyenne-=this.pastMem[this.episodeNumber%100]; 
	    this.moyenne+=this.episodeSteps+1; 
	   
	    this.pastMem[this.episodeNumber%100]=this.episodeSteps+1; 
	    double facteur; 
	    if(this.episodeNumber<100) facteur=this.episodeNumber+1; else facteur=100; 
	    System.out.println("\t\t "+this.moyenne/facteur+" "+this.globalReward/(this.episodeNumber+1)); 
	}
   
	this.episodeSteps=0; 
	this.episodeNumber++;
	this.currentState=new StateRL(o); 
	this.possibleActions.setState(currentState); 
	ActionRL action = (ActionRL) this.guru.getChoice(this.possibleActions); 
	this.lastAction = action;
	this.oldState=this.currentState;
	
	return action.actionRLToAction();
    }

    public Action agent_step(double r, Observation o)
    {
	this.episodeSteps++; 
	this.totalSteps++; 
	this.globalReward+=r; 
	if(this.totalSteps%100==0) {
	    this.epsilon*=0.99995; 
	    //  if(this.totalSteps%10000==0) System.out.println("#***** "+this.epsilon+" *****"); 
	    //  this.guru.setEpsilon(this.epsilon); 
	}
	
	this.currentState=new StateRL(o); 
	this.possibleActions.setState(currentState); 
	ActionRL action = (ActionRL)this.guru.getChoice(this.possibleActions); 

	/*if we haven't frozen the agent, we should improve our value function with each step*/
	if(!freeze) {
	    this.guru.learn(oldState,currentState,lastAction,r); 
	}
	this.lastAction = action;
	this.oldState=this.currentState;
	return action.actionRLToAction();
    }
    
    public void agent_end(double r)
    {
	this.globalReward+=r; 
	currentState=StateRL.FINAL; 
	//	System.out.println("Episode "+this.episodeNumber+" steps : "+(this.episodeSteps+1));  
	/*if the agent isn't frozen, do the last update to the value function, in sarsa this
	 *means the currentQ is zero for this calculation*/
	if(!freeze) {
	     this.guru.learn(oldState,currentState,lastAction,r); 
	}
    }
    
    public void agent_cleanup() 
    {
    }
    
    public String agent_message(final String message)
    {
	return "This agent does not respond to any messages.";
    }
    
    public void agent_freeze()
    {
	freeze = true;
	this.guru.setFreeze(true); 
    }

}
