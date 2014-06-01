import mdsj.MDSJ;

public class MDS {

	public static double[][]run(int[][]data,String[]files)
	{
		double[][]input=new double[data.length][data.length];
		for(int i=0;i<data.length;i++)
			for(int j=0;j<data.length;j++)
				input[i][j]=data[i][j];
		
		double[][] output=MDSJ.classicalScaling(input);
		
		for(int i=0; i<input.length; i++)
		    System.out.println(files[i]+"\t"+output[0][i]+"\t"+output[1][i]);
		return output;
	}
}
