package weibo;

import java.io.File;
import java.util.ArrayList;

import mdsj.MDSJ;

public class Engine {

	public static Tree tree1 = null;
	public static Tree tree2 = null;
	public static Tree stree1 = null;
	public static Tree stree2 = null;
	public static int Max = 1000;

	public static int getDistance(int n, String name1, String name2) {

		tree1 = new Tree(name1);
		stree1 = tree1.sample(n);
		stree1.sort();

		tree2 = new Tree(name2);
		stree2 = tree2.sample(n);
		stree2.sort();

		Integer[] s1 = stree1.serial();
		Integer[] s2 = stree2.serial();

		return (Distance.edit(s1, s2));
	}

	public static int getDistance(Tree tree1, Tree tree2) {

		Integer[] s1 = tree1.serials;
		Integer[] s2 = tree2.serials;;

		return (Distance.edit(s1, s2));
		// return Distance.LCS(s1, s2);
	}
	
	public static int getDistance(int n, Tree tree1, Tree tree2,
			boolean isSample) {

		if(isSample){
			tree1=tree1.sample(n);
			tree2=tree2.sample(n);
		}
		tree1.sort();
		tree2.sort();
		
		Integer[] s1 = tree1.serials;
		Integer[] s2 = tree2.serials;;

		return (Distance.edit(s1, s2));
		// return Distance.LCS(s1, s2);
	}

	public static void getDistanceMultiple(int times, int n, String name1,
			String name2) {
		int result[] = new int[times];
		for (int i = 0; i < times; i++)
			result[i] = getDistance(n, name1, name2);
		utils.Statistics s = new utils.Statistics(result);
		System.out.println("Mean: " + s.getMean());
		System.out.println("Variance: " + s.getVariance());
		System.out.println("Stdev: " + s.getStdDev());

	}

	public static utils.Data getPairDistance(String dir, int n, int time,
			boolean ignore,boolean isSample) {
		File folder = new File(dir);
		ArrayList<Tree> trees = new ArrayList<Tree>();
		String[] files = new String[Max + 10];
		int[] count = new int[Max + 10];
		Integer[][] serial = new Integer[Max + 10][];

		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println(file);
				Tree tree = new Tree(file, time);
				tree.sort();
				if (ignore && (tree.root == null || tree.root.size < n))
					continue;

				if (tree.lineCount < n)
					continue;

				files[trees.size()] = file.getName();
				count[trees.size()]=tree.lineCount;
				if(isSample)
					tree=tree.sample(n);
				tree.sort();
				tree.serial();
				serial[trees.size()]=tree.serials;
				
				trees.add(tree);

				if (trees.size() > Max)
					break;
			}
		}

		int[][] dis = new int[trees.size()][trees.size()];

		for (int i = 0; i < trees.size(); i++) {
			for (int j = i; j < trees.size(); j++)
				dis[i][j] = getDistance(trees.get(i), trees.get(j));
			System.out.println(i + "/" + trees.size());
		}
		for (int i = 0; i < trees.size(); i++)
			for (int j = 0; j < trees.size(); j++) {
				if (i > j)
					dis[i][j] = dis[j][i];
				else if (i == j)
					dis[i][j] = 0;
			}

		MDS.run(dis, files);

		return new utils.Data(dis, files,count,serial);
	}
	//
	// public static void showAll()
	// {
	// if(tree1==null)
	// return;
	//
	// tree1.show2();
	// tree2.show2();
	//
	// stree1.show2();
	// stree2.show2();
	//
	// }

}
