public class Weibo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		//Engine.getDistanceMultiple(100,100,"3705342694877442.msgpack", "3705342694877442.msgpack");
			
		//Engine.getDistance(100, "3705342694877442.msgpack", "3705342694877442.msgpack");
		
		
//		int[][]dis=Engine.getPairDistance(utils.path, 100);
		int[][]dis=Engine.getPairDistance(utils.path, 100,60*60,false);
		
		for(int i=0;i<dis.length;i++)
		{
			for(int j=0;j<dis.length;j++)
				System.out.print(dis[i][j]+"\t");
			System.out.println();
		}
		
		return;
	}
}
