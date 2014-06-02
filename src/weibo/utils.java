package weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import org.jfree.util.StringUtils;
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
	public static HashMap<String, Integer> keys = new HashMap<String, Integer>();

//	public final static String path = "E:\\Tech\\Java\\WorkSpace\\CS290D\\weiboevents\\weiboevents\\";
	public final static String path = "E:\\Download\\weiboevents-trailer-json\\";
//	public final static String path = "/home/tianjiu/cs290dweiboevents/crawl-20140531/";


	public static void loadJson(Tree t, File file, int time) {
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

		tree.createTree(addToTree(src, time));

		tree.lineCount = src.size();
	}

	private static void loadKey(JSONArray fields) {
		// TODO Auto-generated method stub
		for (int i = 0; i < fields.size(); i++)
			keys.put(fields.get(i).toString().trim(), i);
		return;
	}

	public static void loadJson(Tree t, String name) {
		Tree tree = t;

		JSONParser parser = new JSONParser();

		Vector<HashMap<String, String>> data = new Vector<HashMap<String, String>>();

		Object obj = null;
		try {
			obj = parser.parse(new FileReader(path + name + ".json"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		JSONObject jsonObject = (JSONObject) obj;

		// loop array
		JSONArray fields = (JSONArray) jsonObject.get("fields");
		loadKey(fields);

		JSONArray src = (JSONArray) jsonObject.get("data");

		tree.createTree(addToTree(src, 0));

	}

	public static ArrayList<long[]> addToTree(JSONArray src, int time) {
		ArrayList<long[]> data = new ArrayList<long[]>();

		Comparator<long[]> com = new Comparator<long[]>() {

			@Override
			public int compare(long[] o1, long[] o2) {
				return (int) (o1[2] - o2[2]);
			}
		};

		for (int i = 0; i < src.size(); i++) {
			JSONArray line = (JSONArray) src.get(i);
			long[] d = getData(line);
			if (time > 0 && data.size() > 0 && d[2] - data.get(0)[2] > time)
				continue;
			data.add(d);
		}

		Collections.sort(data, com);
		// for(int i=0;i<data.size();i++)
		// {
		// for(int j=0;j<data.get(i).length;j++)
		// System.out.print(data.get(i)[j]+",");
		// System.out.println();
		// }
		return data;
	}

	public static long[] getData(JSONArray line) {
		long[] data = new long[N];
		data[0] = Long.parseLong(line.get(keys.get("uid")).toString());
		data[1] = Long.parseLong(line.get(keys.get("parent")) == null ? "0"
				: line.get(keys.get("parent")).toString());
		data[2] = Long.parseLong(line.get(keys.get("t")).toString());
		data[3] = Long.parseLong(line.get(keys.get("id")).toString());
		return data;
	}

	public static void dump(Data output, String name) {
		int[][] data = output.data;
		String[] fname = output.name;

		try {
			PrintWriter write = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(name + ".dump"))));
			write.println(data.length);
			for (int[] d1 : data) {
				for (int d : d1)
					write.print(d + "\t");
				write.println();
			}
			for (int i = 0; i < data.length; i++)
				write.println(fname[i].substring(0, fname[i].indexOf(".")));
			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void save(Data output, String name) {
		int[][] data = output.data;
		String[] fname = output.name;
		int[] count = output.count;

		try {
			PrintWriter write = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(name + ".name"))));

			for (int i = 0; i < data.length; i++)
				write.println(fname[i].substring(0, fname[i].indexOf(".")));
			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrintWriter write = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(name + ".count"))));

			for (int i = 0; i < data.length; i++)
				write.println(count[i]);
			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PrintWriter write = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(name + ".distance"))));

			for (int[] d1 : data) {
				for (int d : d1)
					write.print(d + "\t");
				write.println();
			}

			write.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (output.serial != null) {
			try {
				PrintWriter write = new PrintWriter(new BufferedWriter(
						new FileWriter(new File(name + ".serial"))));

				for (int i=0;i<data.length;i++)
				{
					for(Integer d:output.serial[i])
						write.print(d+",");
					write.println();
				}

				write.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void computeMDS(String name, int n) {
		Data data = readSave(name, n);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashSet<Integer> trains = new HashSet<Integer>();
		HashSet<Integer> tests = new HashSet<Integer>();
		for (int i = 0; i < data.name.length; i++)
			map.put(data.name[i], i);

		Scanner in = new Scanner(System.in);
		int train = in.nextInt();
		int test = in.nextInt();

		for (int i = 0; i < train; i++)
			trains.add(map.get(in.nextLine().trim()));
		for (int i = 0; i < test; i++)
			tests.add(map.get(in.nextLine().trim()));

		int[][] newd = new int[train][train];
		for (int i = 0; i < data.data.length; i++)
			for (int j = 0; j < data.data.length; j++) {

			}
	}

	public static Data readSave(String name, int n) {
		int[][] data = null;
		String[] names = null;
		int[] count = null;
		Integer[][] serial = null;
		try {
			Scanner inName = new Scanner(new File(name + ".name"));
			Scanner inDis = new Scanner(new File(name + ".distance"));
			Scanner inCount = new Scanner(new File(name + ".count"));
			Scanner inSerial = new Scanner(new File(name + ".serial"));

			data = new int[n][n];
			names = new String[n];
			count = new int[n];
			serial = new Integer[n][];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					data[i][j] = inDis.nextInt();
			for (int i = 0; i < n; i++)
				names[i] = inName.nextLine().trim();
			for (int i = 0; i < n; i++)
				count[i] = inCount.nextInt();
			for (int i = 0; i < n; i++){
				String[] s=inSerial.nextLine().trim().split(",");
				serial[i]=new Integer[s.length];
				for(int j=0;j<s.length;j++)
					serial[i][j]=Integer.parseInt(s[j]);
			}
			
			inName.close();
			inDis.close();
			inCount.close();
			inSerial.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Data(data, names, count,serial);

	}

	public static Data readDump(String name) {
		int[][] data = null;
		String[] names = null;
		try {
			Scanner in = new Scanner(new File(name + ".dump"));
			int n = in.nextInt();
			data = new int[n][n];
			names = new String[n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					data[i][j] = in.nextInt();
			for (int i = 0; i < n; i++)
				names[i] = in.nextLine().trim();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Data(data, names);
	}

	public static void print(int[][] dis) {
		for (int i = 0; i < dis.length; i++) {
			for (int j = 0; j < dis.length; j++)
				System.out.print(dis[i][j] + "\t");
			System.out.println();
		}
	}

	public static class Data {
		public int[][] data;
		public String[] name;
		public int[] count;
		public Integer[][] serial = null;

		public Data(int[][] d, String[] n) {
			this.data = d;
			this.name = n;
		}

		public Data(int[][] d, String[] n, int[] c) {
			this.data = d;
			this.name = n;
			count = c;
		}

		public Data(int[][] d, String[] n, int[] c, Integer[][] s) {
			this.data = d;
			this.name = n;
			count = c;
			serial = s;
		}
	}

	public static class Statistics {
		double[] data;
		double size;

		public Statistics(double[] data) {
			this.data = data;
			size = data.length;
		}

		public Statistics(int[] data) {
			double[] doubles = new double[data.length];
			for (int i = 0; i < data.length; i++) {
				doubles[i] = data[i];
			}
			this.data = doubles;
			size = data.length;
		}

		double getMean() {
			double sum = 0.0;
			for (double a : data)
				sum += a;
			return sum / size;
		}

		double getVariance() {
			double mean = getMean();
			double temp = 0;
			for (double a : data)
				temp += (mean - a) * (mean - a);
			return temp / size;
		}

		double getStdDev() {
			return Math.sqrt(getVariance());
		}

		public double median() {
			double[] b = new double[data.length];
			System.arraycopy(data, 0, b, 0, b.length);
			Arrays.sort(b);

			if (data.length % 2 == 0) {
				return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
			} else {
				return b[b.length / 2];
			}
		}
	}

}
