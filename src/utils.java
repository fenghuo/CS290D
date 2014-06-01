import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class utils {

	public final static int ID = 0;
	public final static int UID = 2;
	public final static int PARENT = 3;
	public final static int TIME = 5;
	public final static int N = 4;
	public static HashMap<String, Integer> keys=new HashMap<String, Integer>();
	

//	public final static String path = "E:\\Tech\\Java\\WorkSpace\\CS290D\\weiboevents\\weiboevents\\";
	public final static String path = "E:\\Download\\weiboevents-trailer-json\\";

	public static void loadJson(Tree t,File file,int time) {
		Tree tree = t;

		JSONParser parser = new JSONParser();

		Vector<HashMap<String, String>> data = new Vector<HashMap<String, String>>();

		Object obj = null;
		try {
			obj = parser.parse(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		JSONObject jsonObject = (JSONObject) obj;

		// loop array
		JSONArray fields = (JSONArray) jsonObject.get("fields");

		loadKey(fields);
		
		JSONArray src = (JSONArray) jsonObject.get("data");

		tree.createTree(addToTree(src,time));
	}
	
	private static void loadKey(JSONArray fields) {
		// TODO Auto-generated method stub
		for(int i=0;i<fields.size();i++)
			keys.put(fields.get(i).toString().trim(),i);
		return;
	}

	public static void loadJson(Tree t,String name) {
		Tree tree = t;

		JSONParser parser = new JSONParser();

		Vector<HashMap<String, String>> data = new Vector<HashMap<String, String>>();

		Object obj = null;
		try {
			obj = parser.parse(new FileReader(path
					+ name+".json"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		JSONObject jsonObject = (JSONObject) obj;

		// loop array
		JSONArray fields = (JSONArray) jsonObject.get("fields");
		loadKey(fields);

		JSONArray src = (JSONArray) jsonObject.get("data");

		tree.createTree(addToTree(src,0));
		
	}

	public static ArrayList<long[]> addToTree(JSONArray src,int time) {
		ArrayList<long[]> data = new ArrayList<long[]>();

		Comparator<long[]> com = new Comparator<long[]>() {

			@Override
			public int compare(long[] o1, long[] o2) {
				return (int) (o1[2] - o2[2]);
			}
		};

		for (int i = 0; i < src.size(); i++) {
			JSONArray line = (JSONArray) src.get(i);
			long[] d=getData(line);
			if(time>0 && data.size()>0 && data.get(0)[2]-d[2]>time)
				continue;
			data.add(d);
		}

		Collections.sort(data,com);
//		for(int i=0;i<data.size();i++)
//		{
//			for(int j=0;j<data.get(i).length;j++)
//				System.out.print(data.get(i)[j]+",");
//			System.out.println();
//		}
		return data;
	}

	public static long[] getData(JSONArray line) {
		long[] data = new long[N];
		data[0] = Long.parseLong(line.get(keys.get("uid")).toString());
		data[1] = Long.parseLong(line.get(keys.get("parent"))==null?"0":line.get(keys.get("parent")).toString());
		data[2] = Long.parseLong(line.get(keys.get("t")).toString());
		data[3] = Long.parseLong(line.get(keys.get("id")).toString());
		return data;
	}
	
	public static class Statistics 
	{
	    double[] data;
	    double size;    

	    public Statistics(double[] data) 
	    {
	        this.data = data;
	        size = data.length;
	    } 
	    
	    public Statistics(int[] data) 
	    {
	    	double[] doubles = new double[data.length];
	    	for(int i=0; i<data.length; i++) {
	    	    doubles[i] = data[i];
	    	}
	    	this.data=doubles;
	        size = data.length;
	    }

	    double getMean()
	    {
	        double sum = 0.0;
	        for(double a : data)
	            sum += a;
	            return sum/size;
	    }

	    double getVariance()
	    {
	        double mean = getMean();
	        double temp = 0;
	        for(double a :data)
	            temp += (mean-a)*(mean-a);
	            return temp/size;
	    }

	    double getStdDev()
	    {
	        return Math.sqrt(getVariance());
	    }

	    public double median() 
	    {
	       double[] b = new double[data.length];
	       System.arraycopy(data, 0, b, 0, b.length);
	       Arrays.sort(b);

	       if (data.length % 2 == 0) 
	       {
	          return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
	       } 
	       else 
	       {
	          return b[b.length / 2];
	       }
	    }
	}

}
