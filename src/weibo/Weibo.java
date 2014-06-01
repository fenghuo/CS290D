package weibo;

public class Weibo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Engine.getDistanceMultiple(100,100,"3705342694877442.msgpack", "3705342694877442.msgpack");
			
		//Engine.getDistance(100, "3705342694877442.msgpack", "3705342694877442.msgpack");
		
		Engine.Max=2000;
		
		utils.Data data=null;
		
		int[][]dis=Engine.getPairDistance(utils.path, 100);
		data=Engine.getPairDistance(utils.path, 100,60*60,false);
		
		utils.dump(data, "2.2000");
		
		data=utils.readDump("1.100");
		
		utils.print(data.data);
	
		Clustering.Hac(data);
		
		return;
	}
}
