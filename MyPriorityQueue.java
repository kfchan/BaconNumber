/**
 * Makes a priority queue with a comparator based on the search query.
 *
 * @author Benjamin Kuperman (Spring 2011)
 * @author Katherine Chan
 * @author Derek Wong
 */

import java.util.*;

public class MyPriorityQueue<AnyType> extends AbstractQueue<AnyType> {

    /** Item to use for making comparisons */
    private Comparator<? super AnyType> cmp;

    /** ArrayList for the heap itself */
    private ArrayList<AnyType> heap;

    /**
     * 
     * @param cmp must be valid comparator.
     */
    public void setComparator(Comparator<AnyType> cmp) {
    	this.cmp = cmp;
    	for (int i = (this.heap.size()-2)/2; i > -1 ; i--) {
    	    percolateDown(i);
    	}
    }

    public Comparator<? super AnyType> comparator() {
	   return this.cmp;
    }
    
    /**
     * @return the size of the heap.
     */
    public int size() {
	   return this.heap.size();
    }

    /**
     * clears the heap
     */
    public void clear() {
	   this.heap = new ArrayList<AnyType>();
    }
    
    /**
     * @param o must be valid AnyType matching heap.
     * @return whether or not adding was successful.
     */
    public boolean offer(AnyType o) {
    	if (this.heap.size() == 0) {
    	    this.heap.add(o);
    	    return true;
    	}
    	this.heap.add(o);
    	this.percolateUp(this.heap.size()-1);
    	return true;
    }
    
    /**
     * @return AnyType value at the beginning of the heap.
     */
    public AnyType peek() {
	return this.heap.get(0);
    }

    /**
     * Takes out highest priority value and then bubbles downtown.
     * 
     * @return AnyType removed value
     */
    public AnyType poll() {
    	AnyType t = this.heap.get(0);
    	this.percolateDown(0);
    	int del = this.heap.indexOf(t);
    	this.heap.remove(del);
    	return t;
    }

    /**
     * Makes an iterator.
     * 
     * @return iterator over the items in the heap.
     */
    public Iterator<AnyType> iterator() {
    	Iterator<AnyType> it = this.heap.iterator();
    	return it;
    }

    /**
     * Construct a heap with a given comparator.
     * 
     * @param cmp Comparator to use for ordering nodes.
     */
    public MyPriorityQueue(Comparator<? super AnyType> cmp) {
    	this.heap = new ArrayList<AnyType>();
    	this.cmp = cmp;
    }
    
    /**
     * switches parent value with appropriate child value at index hole.
     * 
     * @param hole must be valid index in the heap.
     * 
     */
    private void percolateDown(int hole) {
    	AnyType v = this.heap.get(hole);
    	int left = this.lchild(hole);
    	int right = this.rchild(hole);
    	if (left == -1 && right == -1) {
    	    return;
    	} else if (right == -1) {
    	    if (this.cmp.compare(v,this.heap.get(left)) > 0) { //hole > left
    		AnyType temp = this.heap.get(left);
    		this.heap.set(hole,temp);
    		this.heap.set(left,v);
    	    } 
    	} else if (this.cmp.compare(this.heap.get(left),this.heap.get(right)) > 0) { //left > right
    	    AnyType temp = this.heap.get(right);
    	    this.heap.set(hole,temp);
    	    this.heap.set(right,v);
    	    this.percolateDown(right);		
    	} else if (this.cmp.compare(this.heap.get(left),this.heap.get(right)) < 0) { //right > left
    	    AnyType temp = this.heap.get(left);
    	    this.heap.set(hole,temp);
    	    this.heap.set(left,v);
    	    this.percolateDown(left);
    	}
    }
    
    /**
     * 
     * Switches child value with appropriate Parent value at index hole
     * 
     * @param hole must be valid index in the heap.
     */
    private void percolateUp(int hole) {
    	AnyType v = this.heap.get(hole);
    	int parent = this.parent(hole);
    	if (parent < 0) {
    	    return;
    	} else if (this.cmp.compare(this.heap.get(parent), v) > 0) { //if parent < hole
    	    AnyType temp = this.heap.get(parent);
    	    this.heap.set(parent,v);
    	    this.heap.set(hole,temp);
    	    this.percolateUp(parent);
    	}
    }

    /**
     * Calculate the parent index of a node in a complete binary tree
     * 
     * @param index
     *            node to find parent index of
     * @return index of parent or -1 if there is no parent
     */
    private int parent(int index) {
	   return (index-1)/2;
    }

    /**
     * Calculate the index for the left child of the given index in a complete
     * binary tree.
     * @param index node to find left child of
     * @return index of left child or -1 if there is no left child
     */
    private int lchild(int index) {
    	int i = 2*index+1;
    	if (i >= this.heap.size()) {
    	    return -1;
    	}
    	return i;
    }

    /**
     * Calculate the index for the right child of the given index in a complete
     * binary tree.
     * @param index node to find right child of
     * @return index of right child or -1 if there is no right child
     */
    private int rchild(int index) {
    	int i = 2*index+2;
    	if (i >= this.heap.size()-1) {
    	    return -1;
    	}
    	return i;
    }
}
