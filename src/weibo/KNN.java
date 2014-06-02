package weibo;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KNN {

	public static void run(utils.Data data, int k, int nFold) {
		
		Comparator<Data> com=new Comparator<Data>(){
			@Override
			public int compare(Data arg0, Data arg1) {
				// TODO Auto-generated method stub
				return -arg0.dis+arg1.dis;
			}
			
		};
		
		double avg=0;
		
		for (int i = 0; i < nFold; i++) {
			System.out.println(i + " Fold");
			
			double err=0;

			for (int test = i; test < data.data.length; test += nFold) {

				PriorityQueue<Data> kdata = new PriorityQueue<Data>(1,com);
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
					predict+=Math.log10(kdata.poll().count);
				}
				predict/=k;
				err+=Math.pow((Math.log10(data.count[test])-predict),2);
				//System.out.println(predict+"\t"+data.count[test]);
			}
			
			err/=(data.data.length/nFold);
			avg+=err;
			
			System.out.println("Err: "+err);
		}
		avg/=nFold;

		System.out.println("Avg Err: "+avg);
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
