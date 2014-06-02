package weibo;

import java.util.PriorityQueue;

public class KNN {

	public static void run(utils.Data data, int k, int nFold) {
		
	
		for (int i = 0; i < nFold; i++) {
			System.out.println(i + " Fold");
			
			double err=0;

			for (int test = i; test < data.data.length; test += nFold) {

				PriorityQueue<Data> kdata = new PriorityQueue<Data>();
				double predict=0;
				
				for (int train = 0; train < data.data.length; train++) {
					if (train % nFold == i)
						continue;
					kdata.add(new Data(data.data[train][test],
							data.count[train]));
					if (kdata.size() > k)
						kdata.poll();
				}
				while(!kdata.isEmpty())
				{
					predict+=kdata.poll().count;
					System.out.print(predict+" - ");
				}
				System.out.println();
				predict/=k;
				err+=Math.pow((Math.log10(data.count[test])-Math.log10(predict)),2);
				System.out.println(predict+"\t"+data.count[test]);
			}
			
			err/=(data.data.length/nFold);
			
			System.out.println("Err: "+err);
		}
	}

	private static class Data implements Comparable<Data> {

		int dis;
		int count;

		public Data(int a, int b) {
			dis = a;
			count = b;
		}

		@Override
		public int compareTo(Data arg0) {
			// TODO Auto-generated method stub
			return dis - arg0.dis;
		}

	}

}
