/*
 * This file is licensed to You under the "Simplified BSD License".
 * You may not use this software except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/bsd-license.php
 * 
 * See the COPYRIGHT file distributed with this work for information
 * regarding copyright ownership.
 */
package ch.usi.inf.sape.hac.dendrogram;

import java.util.ArrayList;

/**
 * A Dendrogram represents the results of hierachical agglomerative clustering.
 * The root represents a single cluster containing all observations.
 * 
 * @author Matthias.Hauswirth@usi.ch
 */
public final class Dendrogram {

	private final DendrogramNode root;

	public Dendrogram(final DendrogramNode root) {
		this.root = root;
	}

	public DendrogramNode getRoot() {
		return root;
	}

	public void dump2() {
		dumpNode2("  ", root);
	}

	public void dump() {
		dumpNode("  ", root);
	}

	public ArrayList<ArrayList<Integer>> merge(int n) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();

		getNodes(root,res,root.getObservationCount()/n);

		return res;
	}

	private void getNodes(DendrogramNode node,ArrayList<ArrayList<Integer>> res,int n) {
		if(node!=null)
		{
			if(node.getObservationCount()>n){
				getNodes(node.getRight(),res,n);
				getNodes(node.getLeft(),res,n);
			}
			else if(node.getObservationCount()<=n && node.getObservationCount()>10){
				ArrayList<Integer> cluster=new ArrayList<Integer>();
				get(node,cluster);
				res.add(cluster);
			}
		}
	}

	private void dumpNode(final String indent, final DendrogramNode node) {
		if (node == null) {
			System.out.println(indent + "<null>");
		} else if (node instanceof ObservationNode) {
			System.out.println(indent + "Observation: " + node);
		} else if (node instanceof MergeNode) {
			System.out.println(indent + "Merge:");
			dumpNode(indent + "  ", ((MergeNode) node).getLeft());
			dumpNode(indent + "  ", ((MergeNode) node).getRight());
		}
	}

	private void dumpNode2(final String indent, final DendrogramNode node) {
		if (node == null) {
			System.out.println(indent + "<null>");
		} else if (node instanceof ObservationNode) {
			System.out.println(indent + node);
		} else if (node instanceof MergeNode) {
			dumpNode2(indent + "  ", ((MergeNode) node).getLeft());
			dumpNode2(indent + "  ", ((MergeNode) node).getRight());
		}
	}

	private void get(DendrogramNode node, ArrayList<Integer> cluster) {
		if (node == null) {
		} else if (node instanceof ObservationNode) {
			cluster.add(((ObservationNode) node).getObservation());
		} else if (node instanceof MergeNode) {
			get(((MergeNode) node).getLeft(), cluster);
			get(((MergeNode) node).getRight(), cluster);
		}
	}
}
