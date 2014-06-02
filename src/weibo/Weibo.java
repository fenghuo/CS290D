package weibo;

import java.util.Scanner;

public class Weibo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Engine.getDistanceMultiple(100,100,"3705342694877442.msgpack", "3705342694877442.msgpack");
			
		//Engine.getDistance(100, "3705342694877442.msgpack", "3705342694877442.msgpack");
		
		Engine.Max=1000;
		int treeSize=100;
		int timeLimit=60*60;
		boolean ignore=false;
		boolean isSample=false;
		
		utils.Data data=null;
		
		//int[][]dis=Engine.getPairDistance(utils.path, 100);
		data=Engine.getPairDistance(utils.path, treeSize,timeLimit,ignore,isSample);
		
		//utils.dump(data, "2.2000");
		
		//data=utils.readDump("1.100");
		
		//utils.save(data,"test");
		
		//utils.computeMDS(args[0]);
		
		KNN.run(data, 5, 10);
		
		//utils.print(data.data);
	
		//Clustering.Hac(data);
		
		return;
	}
}
