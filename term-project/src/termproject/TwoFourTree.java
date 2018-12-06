/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

package termproject;

import com.sun.xml.internal.ws.util.DOMUtil;
import java.awt.event.ItemEvent;

public class TwoFourTree implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Searches dictionary to determine if key is present
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement(Object key) {
        return null;
    }

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {
        
        // 2, 3, 4 children (except for leaves)
        // each node can store 1, 2, 3 entries
        // num of children equals entries + 1 or 0
        // 2-Node: Node has two child pointers and 1 data element.
        // 3-Node: Node has three child pointers and 2 data elements.
        // 4-Node: Node has four child pointers and 3 data elements.
        // All leaf nodes are the same height
        // Each node Stores 3 values at most sorted from smallest to greatest
        // A leaf node can have 2, 3 or 4 items but no children. 
        // In other words, a leaf is 2-Node, 3-Node or 4-Node where all children are null.
        // INSERT IS DONE AT THE LEAF!
        
        Item newItem = new Item(key, element);
        
        //
        // FIND EXTERNAL NODE METHOD
        // this method recursively finds an external node (assumes root isnt null)
        //
        
        // check for root
        if (!isEmpty()) {
            findExternal(treeRoot, newItem);
        } else {
            createRoot(newItem);
        }
        
        // check the tree
        // checkTree();
        
        size++;
        
    }
    
    // method recursively finds an external node to place new item
    private void findExternal(TFNode currTFNode, Item newItem) {
        
        if (currTFNode.isExternal()) {

            // the node is a leaf, so check if its full (3 items)
            if (currTFNode.isFull()) {
                // TODO: split the node
                split(currTFNode);    
            } else {
                // TODO: test this bad boi
                currTFNode.placeItem(newItem, treeComp);   
            }

        } else {

            // the node is not a leaf, so try again
            // check if the node is full (3 items)
            if (currTFNode.isFull()) {
                // TODO: split the node
                split(currTFNode); 
            }
            
            // TODO: test this
            currTFNode = moveCurr(currTFNode, newItem);
            findExternal(currTFNode, newItem);
            
        }

        
    }
    
    // method creates a root node with index 0
    private void createRoot(Item newItem) {
        // create new node with item and set as root
        TFNode newTFNode = new TFNode();
        newTFNode.insertItem(0, newItem);
        setRoot(newTFNode);
    }
    
     private void split (TFNode curr) {
        
        // we need to grab the items
        Item rightItem = curr.removeItem(2);
        Item midItem = curr.removeItem(1);
        Item leftItem = curr.removeItem(0);
        
        if (curr.getParent() == null) {
            // place middle item in parent
            curr.getParent().placeItem(midItem, treeComp);
        } else {
            // make new parent node
            TFNode Parent = new TFNode();
            Parent.placeItem(midItem, treeComp);
        }
        
        // IF Current Doesnt havent any Children split with ease
        if (curr.isExternal()) {
        
            // Make new Left Node
            TFNode newLeft = new TFNode();
            // Place org. left item
            newLeft.placeItem(leftItem, treeComp);
            // set parent to curr.Parent which removes curr
            newLeft.setParent(curr.getParent());
            
            // Make new Right Node
            TFNode newRight = new TFNode();
            // Place org. right Item
            newRight.placeItem(rightItem, treeComp);
            //  set parent to curr.parent which removes curr
            newRight.setParent(curr.getParent());
        
        } else {
            
            // Currnet is internal, handle the children
          
            
        }
        
        
        
        
        // TODO: we need to handle creating the new nodes
                
    }
    
    

    
    

    private TFNode treeSearch(Object key, TFNode start) {
    
        TFNode currNode = start;
        
        while (currNode != null){
            
            int numItems = currNode.getNumItems();
            
            for (int i = 0; currNode.getNumItems() < i; i++) {
                
                
                
                if (treeComp.isLessThan(((Item)currNode.getItem(i)).key(), key)){
                    
                    
                    
                }
                
                if (treeComp.isEqual((Item)currNode.getItem(i).key(), key)) {
                    
                    
                }
                
            }
            

        }
        
        return null;
    }

    // method takes a node and returns a new current for whichever child is smaller
    public TFNode moveCurr (TFNode node, Item newItem) {  
        TFNode currTFNode = node;
        
        if (treeComp.isLessThanOrEqualTo(newItem.key(), currTFNode.getItem(0).key())) {
            // new < 0
            currTFNode = currTFNode.getChild(0);
        } else {
            // new > 0
            currTFNode = currTFNode.getChild(1);
        }
        
        return currTFNode;
    }


    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        // TEST USING NUMS FROM WEBSITE
        myTree.insertElement(3, 3);
        myTree.insertElement(2, 2);
        myTree.insertElement(5, 5);
        myTree.insertElement(4, 4);
        myTree.insertElement(2, 2);
        myTree.insertElement(9, 9);
        myTree.insertElement(10, 10);
        myTree.insertElement(8, 8);
        myTree.insertElement(7, 7);
        myTree.insertElement(6, 6);
        
        
        Integer myInt1 = new Integer(47);
        myTree.insertElement(myInt1, myInt1);
        
        Integer myInt2 = new Integer(83);
        myTree.insertElement(myInt2, myInt2);
        
        Integer myInt3 = new Integer(22);
        myTree.insertElement(myInt3, myInt3);

        Integer myInt4 = new Integer(16);
        myTree.insertElement(myInt4, myInt4);

        Integer myInt5 = new Integer(49);
        myTree.insertElement(myInt5, myInt5);

        Integer myInt6 = new Integer(100);
        myTree.insertElement(myInt6, myInt6);

        Integer myInt7 = new Integer(38);
        myTree.insertElement(myInt7, myInt7);

        Integer myInt8 = new Integer(3);
        myTree.insertElement(myInt8, myInt8);

        Integer myInt9 = new Integer(53);
        myTree.insertElement(myInt9, myInt9);

        Integer myInt10 = new Integer(66);
        myTree.insertElement(myInt10, myInt10);

        Integer myInt11 = new Integer(19);
        myTree.insertElement(myInt11, myInt11);

        Integer myInt12 = new Integer(23);
        myTree.insertElement(myInt12, myInt12);

        Integer myInt13 = new Integer(24);
        myTree.insertElement(myInt13, myInt13);

        Integer myInt14 = new Integer(88);
        myTree.insertElement(myInt14, myInt14);

        Integer myInt15 = new Integer(1);
        myTree.insertElement(myInt15, myInt15);

        Integer myInt16 = new Integer(97);
        myTree.insertElement(myInt16, myInt16);

        Integer myInt17 = new Integer(94);
        myTree.insertElement(myInt17, myInt17);

        Integer myInt18 = new Integer(35);
        myTree.insertElement(myInt18, myInt18);

        Integer myInt19 = new Integer(51);
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(new Integer(i), new Integer(i));
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
        System.out.println("removing");
        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(new Integer(i));
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        System.out.println("done");
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }

            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
}
