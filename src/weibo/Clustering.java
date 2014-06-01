package weibo;
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
