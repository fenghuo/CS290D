package weibo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import mdsj.MDSJ;

public class MDS {

	public static double[][] run(int[][] data, String[] files) {
		double[][] input = new double[data.length][data.length];
		for (int i = 0; i < data.length; i++)
			for (int j = 0; j < data.length; j++)
				input[i][j] = data[i][j];

		double[][] output = MDSJ.classicalScaling(input);

		PrintWriter write = null;
		try {
			write = new PrintWriter(new BufferedWriter(new FileWriter(new File(
					"MDS"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < input.length; i++) {
			String t = files[i] + "\t" + output[0][i] + "\t" + output[1][i];
			System.out.println(t);
			write.println(t);
		}

		write.close();
		return output;
	}
}
