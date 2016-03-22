package org.opentamas.treegenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a node of the tree. This could be anything basically, 
 * here it only has a name and a level. Could be easily extended as needed. 
 *
 */
public class Node implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected static final String ROOT_NODE_NAME = "ROOT";
	
	// name of the node
	private String name;

	// level of the node in the tree structure
	private int level;
	
	// parent of the node
	private Node parent;
	
	
	// list of the children of the node
	private List<Node> children = new ArrayList<>();
	
	public Node (String pName, Node pParent, int pLevel) {
		this.name = pName;
		this.parent = pParent;
		this.level = pLevel;
	}
	
    /**
     * Adds a child to the node
     * 
     * @param Node the child node to be added
     */
    public void addChild(Node child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }
    
	/**
     * Returns the number of the children of this node
     * @return int the number of the children.
     */
    public int getChildrenCount() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }
    
    /**
     * Removes the node (only leaf nodes can be removed)
     */
    public boolean remove() {
    	if (this.getChildren().isEmpty()) {
    		this.getParent().getChildren().remove(this);
    		return true;
    	} 
    	return false;
    }
    
    /*
     * Getters and setters
     */

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int pLevel) {
		this.level = pLevel;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> pChildren) {
		this.children = pChildren;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String toString() {
		return this.getName();
	}
}