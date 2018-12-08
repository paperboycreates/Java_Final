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

        // Node to Insert into
        TFNode insertNode = null;
        
        // Make Item to insert
        Item newItem = new Item(key, element);
        
        // check for root
        if (isEmpty()) {

            createRoot(newItem);
            
        } else {
            
            //Find node to insert into
            insertNode = FFGTE(treeRoot, key);
        }
        
        // Add Item at Correct Position
        insertNode.insertItem(findPos(insertNode, key), newItem);
        
        
        //OverFlow if Currentt Items == Max Items (4)
        if (insertNode.getNumItems() == insertNode.getMaxItems()) {
            OverFlow(insertNode);
        }

        // check the tree
        checkTree();

        //Increment Size, Item Added
        size++;
    }
    
    
    // Checks and Corrects if Overflown
    private void OverFlow (TFNode currentNode) {
        
        while (currentNode.getNumItems() == currentNode.getMaxItems()) {
            
            
        }
    }
    
    
    // Find which position key needs to go into the node a < key < b
    private int findPos (TFNode currNode, Object key) {
        
        for (int i =0; i < currNode.getNumItems(); i++) {
            
            if(treeComp.isGreaterThanOrEqualTo(currNode.getItem(i).key(), key)) {
                
                return i;
            }
        } 
        return currNode.getNumItems();
    }
    
    
    // method recursively finds an external node to place new item
    private TFNode FFGTE(TFNode currNode, Object key) {
        
        if (currNode.isExternal()) {

                return currNode;

        } else { 
            
            //Find Position between a < key < b
            int pos = findPos(currNode, key);
            Item currItem = currNode.getItem(pos);
             
            //If key == Key then take this node 
            if (treeComp.isEqual(currItem.key(), key)) {
                 
                 return currNode;
                 
            } else {
                
                // continue to child node
                return FFGTE(currNode.getChild(pos), key);
            }
        } 
    }
    
    
    // method creates a root node with index 0
    private void createRoot(Item newItem) {
        
        // create new node with item and set as root
        TFNode newTFNode = new TFNode();
        newTFNode.insertItem(0, newItem);
        setRoot(newTFNode);
    }
    
    
    // method splits the current node into 2 new nodes
    // 4 in there
    // none shifting remove
    // what child am i, what child am 1 + 1
    // assume valid childeren
    
    // TODO: Needs Some Love
    private void split (TFNode curr) {
        
        // we need to grab the items
        Item rightItem = curr.removeItem(2);
        Item midItem = curr.removeItem(1);
        Item leftItem = curr.removeItem(0);
        TFNode newParent = null;
        
        if (curr.getParent() != null) {
            // place middle item in parent
            // TODO: edge case of adding when full
            curr.getParent().placeItem(midItem, treeComp);
        } else {
            // no parent, so we need to make a new root
            createRoot(midItem);
            newParent = treeRoot;
        }
        
        if (curr.isExternal()) {
        
            // if current does not have any children, split with ease
            // make 2 new nodes
            TFNode newLeft = new TFNode();
            TFNode newRight = new TFNode();
            
            // place items in new left and right nodes
            newLeft.placeItem(leftItem, treeComp);
            newRight.placeItem(rightItem, treeComp);
            
            // set parent to currents parent, which removes current
            // TODO: we are losing this data because of only setting parent relationship
            newLeft.setParent(newParent);
            newRight.setParent(newParent);
        
        } else {
            
            // current is internal
            // TODO: how do we handle this??
            
        }
                
    }
   

    // method takes a node and returns a new current for whichever child is smaller
    // TODO: Dont know if this needed? maybe.
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
        myTree.insertElement(1, 1);
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
