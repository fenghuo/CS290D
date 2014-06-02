package weibo;

import java.util.Scanner;

import weibo.Engine;
import weibo.KNN;
import weibo.MDS;

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
		boolean isSample=true;
		
		utils.Data data=null;
		
		String name="cluster"+Engine.Max+"-"+treeSize+"-"+ignore+"-"+isSample;
		
		//int[][]dis=Engine.getPairDistance(utils.path, 100);
		//data=Engine.getPairDistance(utils.path, treeSize,timeLimit,ignore,isSample);
		
		//utils.dump(data, "2.2000");
		
		//data=utils.readDump("1.100");
		
		//utils.save(data,name);
		data=utils.readSave(name,Engine.Max+1);
		
		//MDS.run(data.data,data.name);

		//utils.save(data,"test");
		
		//utils.computeMDS(args[0]);
		
		//KNN.run(data,50, 10);
		
		//utils.print(data.data);
	
		Clustering.Hac(data);
		
		//Plot.Bar(MDS.run(data.data, data.name));
		
		return;
	}
}
