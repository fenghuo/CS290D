package weibo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import weibo.utils.Data;

public class Predict {

	static PrintWriter write=null;
	
	public static void init(){
		try {
			write=new PrintWriter(new FileWriter(new File("knn-test-"+new Date().getMinutes()),true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void run(utils.Data data) {

		double err=0;
		
		init();
		
		int n=data.data.length/10;
		for (int i = 0; i < n; i++) {
			err+=run(data, i);
		}
		err/=n;
		
		write.close();
	}
	
	public static void check(utils.Data data){
		
		init();
		
		for(int i=0;i<data.data.length;i++){
			run(data,i);
		}
		
	}

	private static double run(Data data, int n) {

		ArrayList<Integer[]> res = split(data.serial[n]);

		//System.out.println("Data : " + data.count[n]);
		
		int min = Integer.MAX_VALUE;
		double count = 0;
		//System.out.println(min + "--\t" + count);

		double sum = 0;
		double avgd = 0;
		double d1 = min;
		double c1=count;
		
//		c1=KNN.predict(10, data, n, data.serial[n]);
		KNN.Result re=KNN.predict2(10, data, n, data.serial[n]);

//		for (Integer[] d : res){
//			
//			min = Integer.MAX_VALUE;
//			avgd=0;
//			if (d.length < 3) {
//				sum += d.length;
//				continue;
//			}
//			
//			count=KNN.predict((int)Math.log(d.length), data, n, d);
//
//			sum += count;
//			avgd += min;
//			//System.out.println(min + "\t" + count);
//
//		}
		//avgd/=res.size();
		avgd++;
		d1++;
		//sum=Math.log10(sum);
		//System.out.println(Math.pow(10,sum) + "\t" + Math.pow(10,c1));
		//System.out.println("P:" + ((sum * d1 + c1 * avgd) / (avgd + d1)));
		//System.out.println("P:" + (Math.pow(10,(sum  + c1 ) / 2)));

		//write.println(Math.log10(data.count[n])+"\t"+c1+"\t"+sum);
		
		write.println((Math.log10(data.count[n])+"\t"+re.predict+"\t"+re.distance));
		
		return Math.pow((sum+c1)/2-Math.log10(data.count[n]),2);
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
