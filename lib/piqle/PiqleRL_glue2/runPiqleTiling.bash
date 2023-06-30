#/bin/bash

###Change these if necessary ###
basePath=../..   #Path to the main rl-competition directory
packageName=PiqleAgent  #Name of the package the Agent is in.
className=PiqleAgentTiling    #Name of the agent class
maxMemory=128M			 #Max amount of memory to give the agent (Java default is often too low)


###Shouldn't need to change things below ###
libraryPath=$basePath/system/libraries
compLib=../../system/libraries/RLVizLib.jar

java -Xmx$maxMemory -cp $compLib:./bin rlglue.agent.AgentLoader $packageName.$className | tee /tmp/resuP

echo "-- Agent is complete"
