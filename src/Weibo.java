public class Weibo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Engine.getDistanceMultiple(100,100,"3705342694877442.msgpack", "3705342694877442.msgpack");
			
		//Engine.getDistance(100, "3705342694877442.msgpack", "3705342694877442.msgpack");
		
		int[][]dis=null;
		
//		int[][]dis=Engine.getPairDistance(utils.path, 100);
//		dis=Engine.getPairDistance(utils.path, 100,0,true);
		
//		utils.dump(dis, "1.1000");
		
		dis=utils.readDump("1.100");
		
//		utils.print(dis);
	
		Clustering.Hac(dis);
		
		return;
	}
}
