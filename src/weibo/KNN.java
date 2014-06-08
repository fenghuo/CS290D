package weibo;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KNN {

	public static void run(utils.Data data, int k, int nFold) {

		Comparator<Data> com = new Comparator<Data>() {
			@Override
			public int compare(Data arg0, Data arg1) {
				// TODO Auto-generated method stub
				return -arg0.dis + arg1.dis;
			}

		};

		double avg = 0;

		for (int i = 0; i < nFold; i++) {
			System.out.println(i + " Fold");

			double err = 0;

			for (int test = i; test < data.data.length; test += nFold) {

				PriorityQueue<Data> kdata = new PriorityQueue<Data>(1, com);
				double predict = 0;

				for (int train = 0; train < data.data.length; train++) {
					if (train % nFold == i)
						continue;
					kdata.add(new Data(data.data[train][test],
							data.count[train]));
					if (kdata.size() > k)
						kdata.poll();
				}
				while (!kdata.isEmpty()) {
					predict += Math.log10(kdata.poll().count);
				}
				predict /= k;
				err += Math.pow((Math.log10(data.count[test]) - predict), 2);
				// System.out.println(predict+"\t"+data.count[test]);
			}

			err /= (data.data.length / nFold);
			avg += err;

			System.out.println("Err: " + err);
		}
		avg /= nFold;

		System.out.println("Avg Err: " + avg);
	}

	public static double predict(int k, utils.Data data, int n, Integer[] d) {
		return predict2(k,data,n,d).predict;
	}
	
	public static Result predict2(int k, utils.Data data, int n, Integer[] d) {

		if(k<1)k=1;
		
		Comparator<Data> com = new Comparator<Data>() {
			@Override
			public int compare(Data arg0, Data arg1) {
				// TODO Auto-generated method stub
				return -arg0.dis + arg1.dis;
			}

		};

		PriorityQueue<Data> kdata = new PriorityQueue<Data>(1, com);
		double predict = 0;

		double distance=0;
		
		for (int train = 0; train < data.data.length; train++) {
			if (train == n)
				continue;
			kdata.add(new Data(Distance.edit(data.serial[train], d),
					data.count[train]));
			if (kdata.size() > k)
				kdata.poll();
		}
		while (!kdata.isEmpty()) {
			Data re=kdata.poll();
			predict += Math.log10(re.count);
			distance+=re.dis;
		}
		predict /= k;
		distance/=k;
		
		return new Result(predict,distance);
	}

	public static class Result{
		public double predict;
		public double distance;
		
		public Result(double a,double b){
			predict=a;
			distance=b;
		}
	}
	
	private static class Data {

		public int dis;
		public int count;

		public Data(int a, int b) {
			dis = a;
			count = b;
		}

	}

}
