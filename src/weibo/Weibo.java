package weibo;

import java.util.Scanner;

public class Weibo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Engine.getDistanceMultiple(100,100,"3705342694877442.msgpack", "3705342694877442.msgpack");
			
		//Engine.getDistance(100, "3705342694877442.msgpack", "3705342694877442.msgpack");
		
<<<<<<< HEAD
		Engine.Max=1000;
=======
<<<<<<< HEAD
		Engine.Max=3000;
=======
		Engine.Max=100;
>>>>>>> e3b44df8d5970870019c2ba08cbb8ba78b0276a0
>>>>>>> 800291913ce93963bbfd33d5087de9fe709b4ac5
		int treeSize=100;
		int timeLimit=60*60;
		boolean ignore=false;
		boolean isSample=false;
		
		utils.Data data=null;
		
		//int[][]dis=Engine.getPairDistance(utils.path, 100);
		data=Engine.getPairDistance(utils.path, treeSize,timeLimit,ignore,isSample);
		
		//utils.dump(data, "2.2000");
		
		//data=utils.readDump("1.100");
		
<<<<<<< HEAD
		utils.save(data,"1000-100-false-false");
		//data=utils.readSave("all-100-false-false");
		
		MDS.run(data.data,data.name);

=======
		//utils.save(data,"test");
		
		//utils.computeMDS(args[0]);
		
		KNN.run(data, 5, 10);
		
>>>>>>> e3b44df8d5970870019c2ba08cbb8ba78b0276a0
		//utils.print(data.data);
	
		//Clustering.Hac(data);
		
		return;
	}
}
