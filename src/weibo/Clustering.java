package weibo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import ch.usi.inf.sape.hac.*;
import ch.usi.inf.sape.hac.dendrogram.*;
import ch.usi.inf.sape.hac.experiment.*;
import ch.usi.inf.sape.hac.agglomeration.*;

public class Clustering {
	
	private static int[][]data=null;
	public static String[]names=null;
	
	
	public static void Hac(utils.Data data)
	{
		Clustering.data=data.data;
		Clustering.names=data.name;
		
		Experiment experiment = pickExperiment();
		DissimilarityMeasure dissimilarityMeasure = pickDissimilarityMeasure();
		AgglomerationMethod agglomerationMethod = pickAgglomerationMethod();
		DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(experiment.getNumberOfObservations());
		HierarchicalAgglomerativeClusterer clusterer = new HierarchicalAgglomerativeClusterer(experiment, dissimilarityMeasure, agglomerationMethod);
		clusterer.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();
		dendrogram.dump2();
		
		clusterer.setAgglomerationMethod(new AverageLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new CentroidLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new CompleteLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new MedianLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new SingleLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new WardLinkage());
		run(dendrogramBuilder,clusterer);
		clusterer.setAgglomerationMethod(new WeightedAverageLinkage());
		run(dendrogramBuilder,clusterer);
	
	}

	private static void run(DendrogramBuilder dendrogramBuilder,HierarchicalAgglomerativeClusterer clusterer){
		clusterer.cluster(dendrogramBuilder);
		Dendrogram dendrogram = dendrogramBuilder.getDendrogram();
		dendrogram.dump2();

		int n=4;
		ArrayList<ArrayList<Integer>> clusters=null;
		clusters=dendrogram.merge(n);
		dump(clusters,clusterer.getAgglomerationMethod());
	}
	
	private static void dump(ArrayList<ArrayList<Integer>> clusters,
			Object agglomerationMethod){

		PrintWriter write = null;
		try {
			write = new PrintWriter(new BufferedWriter(new FileWriter(agglomerationMethod.toString()+"."+n+".cluster")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<clusters.size();i++)
		{
			for(int j=0;j<clusters.size();j++)
			{
				double dis=clusterDistance(clusters,i,j);
				System.out.print(dis+"\t");
				write.print(dis+"\t");
			}
			System.out.println();
			write.println();
		}
		write.close();
	}
	
	private static double clusterDistance(ArrayList<ArrayList<Integer>> clusters,int a,int b){
		double sum=0;
		for(int i:clusters.get(a))
			for(int j:clusters.get(b))
				sum+=data[i][j];
		sum/=clusters.get(a).size()*clusters.get(b).size();
		return sum;
	}
	
	private static AgglomerationMethod pickAgglomerationMethod() {
		return new CentroidLinkage();
		//return new CompleteLinkage();
	}

	private static DissimilarityMeasure pickDissimilarityMeasure() {
		return new MyMeasure();
	}

	private static Experiment pickExperiment() {
		return new MyExperiment();
	}

	private static class MyMeasure implements DissimilarityMeasure{

		@Override
		public double computeDissimilarity(Experiment experiment,
				int n1, int n2) {
			return data[n1][n2];
		}
		
	}
	
	private static class MyExperiment implements Experiment	{

		@Override
		public int getNumberOfObservations() {
			// TODO Auto-generated method stub
			return data.length;
		}
		
	}
}
