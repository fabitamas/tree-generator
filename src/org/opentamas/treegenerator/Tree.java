package org.opentamas.treegenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class represents a tree data structure, which contains Node elements.
 * 
 * The toList method returns all the nodes of the tree in a list format
 */
public class Tree implements Serializable {

	private static final long serialVersionUID = 1L;
	private Node root;


	/**
	 * Returns the root element.
	 * @return Node the root of the tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * Sets the root element.
	 * @param rootElement the root element.
	 */
	public void setRoot(Node rootElement) {
		this.root = rootElement;
	}


	/**
	 * Returns all the elements of the tree in a list.
	 * The elements are traversed in pre-order.
	 * 
	 * @return List<Node> the elements of the tree.
	 */
	public List<Node> toList() {
		List<Node> list = new ArrayList<Node>();
		walk(root, list);
		return list;
	}

	/**
	 * Returns all the elements of the tree from a certain node in a list.
	 * The elements are traversed in pre-order.
	 * 
	 * @param Node
	 * @return
	 */
	public List<Node> toList(Node pNode) {
		List<Node> list = new ArrayList<Node>();
		walk(pNode, list);
		return list;
	}

	/**
	 * Returns the number of the elements in the tree
	 * 
	 * @return int nr of the elements in the tree
	 */
	public int size() {
		return toList().size();
	}

	/**
	 * Traverses the tree in pre-order. 
	 * 
	 * @param Node the element to start with.
	 * @param pList the result of the traversal.
	 */
	private void walk(Node pNode, List<Node> pList) {
		pList.add(pNode);
		for (Node children : pNode.getChildren()) {
			walk(children, pList);
		}
	}


	/**
	 * Returns the list of nodes which leads to the root from the actual node.
	 * 
	 * @param Node the node to reach
	 * @return List<Node> the list of nodes from the actual node to the root
	 */
	public static List<Node> getPath(Node pNode) {
		List<Node> result = new ArrayList<>();

		// path starts with the parent
		pNode = pNode.getParent();

		// there's no path if parent is null
		if (pNode == null) {
			return result;
		}

		// search until the parent is null
		while (pNode.getParent() != null) {
			result.add(pNode);
			pNode = pNode.getParent();
		}
		result.add(pNode);

		return result;
	}

	/**
	 * Returns a subtree in a list format
	 *
	 * @param pNode the root of the subtree
	 * @return List<Node> list of nodes of the subtree
	 */
	public static List<Node> getSubTree(Node pNode) {
		List<Node> result = new ArrayList<>();

		for (Iterator<Node> iterator = pNode.getChildren().iterator(); iterator.hasNext();) {
			Node child = iterator.next();
			result.add(child);
			result.addAll(getSubTree(child));
		}

		return result;
	}
	
	/**
	 * Returns the deepest node of the tree
	 * 
	 * @return Node the deepest node
	 */
	public Node getDeepestNode() {
		Node deepestNode = null;
		for (Iterator<Node> iterator = toList().iterator(); iterator.hasNext();) {
			Node item = iterator.next();
			if (item.getLevel() > (deepestNode == null ? 0 : deepestNode.getLevel())) {
				deepestNode = item;
			}
		}
		
		return deepestNode;
	}

	/**
	 * Returns a random node of the tree (root excluded from randomization)
	 * 
	 * @return Node a random node
	 */
	public Node getRandomNode() {
		return getRandomNode(false);
	}

	/**
	 * Returns a random node of the tree
	 * 
	 * @param includeRoot whether to include the root element
	 * @return Node a random node
	 */
	public Node getRandomNode(boolean includeRoot) {
		int random = new Random().nextInt(size());
		if (!includeRoot) {
			random = new Random().nextInt(size() - 1) + 1;
		}

		return this.toList().get(random);
	}
}
