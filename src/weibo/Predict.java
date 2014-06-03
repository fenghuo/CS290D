package weibo;

import java.util.ArrayList;

import weibo.utils.Data;

public class Predict {

	public static void run(utils.Data data) {

		double err=0;
		
		int n=data.data.length/10;
		for (int i = 0; i < n; i++) {
			err+=run(data, i);
		}
		err/=n;
		System.out.println(err);
	}

	private static double run(Data data, int n) {

		ArrayList<Integer[]> res = split(data.serial[n]);

		System.out.println("Data : " + data.count[n]);
		
		int min = Integer.MAX_VALUE;
		int count = 0;
		for (int i = 0; i < data.data.length; i++) {
			if (i == n)
				continue;
			int dis = Distance.edit(data.serial[i], data.serial[n]);
			if (dis < min) {
				min = dis;
				count = data.count[i];
			}
		}
		//System.out.println(min + "--\t" + count);

		double sum = 0;
		double avgd = 0;
		double d1 = min;
		double c1=count;

		for (Integer[] d : res) {
			min = Integer.MAX_VALUE;
			if (d.length < 3) {
				sum += d.length;
				continue;
			}

			for (int i = 0; i < data.data.length; i++) {
				if (i == n)
					continue;

				int dis = Distance.edit(data.serial[i], d);

				if (dis < min) {
					min = dis;
					count = data.count[i];
				}
			}

			sum += count;
			avgd += min;
			// System.out.println(min + "\t" + count);

		}
		// avgd/=res.size();
		//System.out.println(avgd + "\t" + sum);
		//System.out.println("P:" + ((sum * d1 + c1 * avgd) / (avgd + d1)));
		System.out.println("P:" + ((sum  + c1 ) / 2));

		return Math.pow(Math.log10((sum+c1)/2)-Math.log10(data.count[n]),2);
	}

	private static ArrayList<Integer[]> split(Integer[] data) {
		ArrayList<Integer[]> res = new ArrayList<Integer[]>();
		ArrayList<Integer> temp = new ArrayList<Integer>();

		for (int i = 1; i < data.length + 1; i++) {
			if (i == data.length || data[i] == 2) {
				temp.add(0, 1);
				res.add(temp.toArray(new Integer[temp.size()]));
				temp.clear();
			} else
				temp.add(data[i] - 1);
		}

		return res;
	}

}
