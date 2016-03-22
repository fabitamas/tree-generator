package org.opentamas.treegenerator;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


/**
 * The main method of the TreeGenerator generates a tree structure which is randomly
 * constructed based on the configuration parameters (which have to be set in tree.ini) 
 */
public class TreeGenerator {
	
	public static Properties settingsProps = getTreeParametersFromIniFile("tree.ini");
	
	public static final int NODE_COUNT = Integer.valueOf(settingsProps.getProperty("NodeCount"));
	public static final int MIN_DEPTH = Integer.valueOf(settingsProps.getProperty("MinDepth"));
	public static final int MIN_WIDTH = Integer.valueOf(settingsProps.getProperty("MinWidth"));

	public static void main(String[] args) {
		Tree testTree = buildTree(NODE_COUNT, MIN_DEPTH, MIN_WIDTH);
		
		int count = 1;
		for (Iterator<Node> iterator = testTree.toList().iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			System.out.println("Node " + count++ + " = '" + node.toString() + "'");
		}
	}
	
	/**
	 * The buildTree method generates a random tree structure based on the input parameters
	 * 
	 * @param nrOfNodes the number of the nodes to be added to the tree
	 * @param minDepth a minimum depth the tree should have
	 * @param minWidth a minimum width the tree should have
	 * @return Tree
	 */
	private static Tree buildTree(int nrOfNodes, int minDepth, int minWidth) {
		
		// minDepth and minWidth has to be greater or equal to zero
		if (minDepth < 0) {
			minDepth = 0;
		}
		if (minWidth < 0) {
			minWidth = 0;
		}
		
		// Create the tree and add the root node
		Tree tree = new Tree();
		Node root = new Node(Node.ROOT_NODE_NAME, null, 0);
		tree.setRoot(root);
		
		// Create the random part of the tree
		for (int i = 0; i < nrOfNodes - minDepth - minWidth - 1; i++) {
			
			Node parent = tree.getRandomNode(true);
			Node child = generateChildNode(parent);
			parent.addChild(child);
		}
		
		// Add a deep branch to the tree based on the configuration
		if (minDepth > 0) {
			// Store the elements of the deep branch in a list for easy access of the last one
			List<Node> deepList = new ArrayList<Node>();
			
			// Get a random node to start the deep branch from
			Node randomDeepRoot = tree.getRandomNode(true);
			deepList.add(randomDeepRoot);
			
			for (int i = 0; i < minDepth; i++) {
				Node parent = deepList.get(deepList.size() - 1);
				Node child = generateChildNode(parent);
				
				parent.addChild(child);
				deepList.add(child);
			}
		}
		
		// Add a wide branch to the tree based on the configuration
		if (minWidth > 0) {
			// Get a random node to start the wide branch from
			Node randomWideRoot = tree.getRandomNode(true);

			for (int i = 0; i < minWidth; i++) {				
				Node child = generateChildNode(randomWideRoot);
				
				randomWideRoot.addChild(child);
			}
		}
		
		return tree;
	}

	// Instantiates a node with a name and level
	private static Node generateChildNode(Node pParent) {
		return new Node(generateChildName(pParent), pParent, generateChildLevel(pParent));
	}

	private static int generateChildLevel(Node parent) {
		return parent.getLevel() + 1;
	}

	private static String generateChildName(Node parent) {
		return parent.getName() + "-" + (parent.getChildrenCount() + 1);
	}

	/**
	 * Reads in the properties file 
	 * 
	 * @param fileName the name of the configuration file
	 * @return Properties properties of the tree
	 */
	public static Properties getTreeParametersFromIniFile(String fileName) {
		try {
			Properties p = new Properties();
			String file = System.getProperty("treeConfig", fileName);
			p.load(new FileInputStream(new StringBuilder().append("config/").append(file).toString()));
			
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
