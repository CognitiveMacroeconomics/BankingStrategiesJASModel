This directory contains all the Piqle's files you need to connect Piqle with RL_glue and to run the
2008 competition. 



- PiqleAgent.java  is an example of learning agent (actually using a NN and Watkin's TD(lambda, but you
can easily change the learner).
- PiqleAgentTiling.java is a learning agent using tiling, and regularily saving the learner.
- PiqleAgentTilingRead.java is also an agent using tiling, but begins with reading a formerly saved learner. 


All three files have their own makefile, together with their own launcher (same name as the agent)


To install : 

- create a directory PiqleAgent under rl_compettion's agent directory.
- Uncompress the file
- goto PiqleAgent directory


To start the agent : 
- make clean
- make -f Make_PIQLEXXX (wher XXX depends of the chosen agent)
- bash runPiqleXXX.bash

For the meaning of parameters, and the different possibilities of tuning, please refer to Piqle's documentation.

I will be glad to answer any of your inquiries. 

You need to start a trainer separately.
