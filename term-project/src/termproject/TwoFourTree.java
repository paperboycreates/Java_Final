/**
 * Title:       Term Project 2-4 Trees
 * File:        TwoFourTree.java
 * Description: Class builds a 2-4 Tree which can store items based on its key
 * Due:         Dec 13, 2018
 * 
 * @author Jake Allinson
 * @author Jacob Sheets
 * @version 1.0
 */
package termproject;

import java.util.Random;
//import com.sun.xml.internal.ws.util.DOMUtil;
//import java.awt.event.ItemEvent;

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
    public Object findElement (Object key) {
               
        // find node with key in it (returns null if not found)
        TFNode resultNode = findNode(treeRoot, (int) key);
        
        // could not find the key so return null
        if (resultNode == null) {
            return null;
        }
        
        // check each item
        for (int i = 0; i < resultNode.getNumItems(); ++i) {
            
            // get the key of the current item to compare
            int itemKey = (int) resultNode.getItem(i).key();
            if (treeComp.isEqual(itemKey, key)) {
                // the key is in this node, so return it
                return resultNode.getItem(i);
            }
            
        }
        
        // item was not found
        return null;
        
    }
    
    
    // recursively finds node to put key into
    private TFNode findNode (TFNode currNode, int key) {
        
        // find FFGTE of key looking for
        int index = FFGTE(currNode, key); 
        
        // check each item
        for (int i = 0; i < currNode.getNumItems(); ++i) {
            
            // get the key of the current item to compare
            int itemKey = (int) currNode.getItem(i).key();
            if (treeComp.isEqual(itemKey, key)) {
                // the key is in this node, so return it
                return currNode;
            }
            
        }
        
        // if the curr node is external, the key is not in the tree
        if (currNode.isExternal()){
            return null;
        }

        // move curr, and try again
        currNode = currNode.getChild(index);
        return findNode(currNode, key);   
        
    }
    

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {

        // create node that we will insert into
        TFNode insertNode = null;
        
        if (isEmpty()) { 
            // no root node, so make one
            insertNode = new TFNode();
            setRoot(insertNode);
        } else {
            // find node to insert item into
            insertNode = findExternalNode(treeRoot, key);
        }
        
        // now that we have our node, find index and insert our new item
        int index = FFGTE(insertNode, key);
        Item newItem = new Item(key, element);
        insertNode.insertItem(index, newItem);
        
        // if node has more than the max items allowed, run overflow
        if (insertNode.getNumItems() > insertNode.getMaxItems()) {
            OverFlow(insertNode);
        }

        // check the tree
        checkTree();
        printTree(treeRoot, index);

        // increment the size of our tree
        size++;
        
    }
    
    
    // method corrects an overflowed node
    private void OverFlow (TFNode currNode) {
        
        // 1. grab item at position 2 (non-shifting remove)
        // 2. put removed item in parent (check that parent is there)
        // 3. create new node for after split
        // 4. move items to new node
        // 5. adjust children
        // 6. clean up where needed
        
        // keep running until our curr node, which we move up the tree, isn't overflowed
        while (currNode.getNumItems() > currNode.getMaxItems()) {

            // perform a none-shifting remove
            Item removedItem = currNode.getItem(2);
            
            // make parent, which is where we will put our removed item
            TFNode parentNode;
            
            // EDGE CASE: if curr node is root, we need a new parent (new root)
            if (currNode == treeRoot) {
                
                // make our parent node and connect it to curr node
                parentNode = new TFNode();
                currNode.setParent(parentNode);
                parentNode.setChild(0, currNode);
                
                // make parent the new root
                setRoot(parentNode);
                
            } else {
                
                // make our parent node
                parentNode = currNode.getParent();
                
            }
            
            // put removed item in parent
            int index = FFGTE(parentNode, removedItem.key());
            parentNode.insertItem(index, removedItem);
            
            // create new node
            // we want to connect new node to parent's last child by asking what child am i + 1 and add item
            TFNode newNode = new TFNode();
            newNode.addItem(0, currNode.getItem(3));
            parentNode.setChild(index + 1, newNode);
            
            // current node's 3rd child
            // move 3rd child to new node, and then remove its pointer from current
            TFNode currNodeChild3 = currNode.getChild(3);
            newNode.setChild(0, currNodeChild3);
            currNode.setChild(3, null);
            
            // current node's 4th child
            // move 4th child to new node, and then remove its pointer from current
            TFNode currNodeChild4 = currNode.getChild(4);
            newNode.setChild(1, currNodeChild4);
            currNode.setChild(4, null);
            
            // now we can delete the items from curr node
            currNode.deleteItem(3);
            currNode.deleteItem(2);
                        
            // EDGE CASE: check if first child of new node (child 3 of current) is null
            if (currNodeChild3 != null) {
                // if we have children, connect them to their newly created parent node (newNode)
                currNodeChild3.setParent(newNode);
                currNodeChild4.setParent(newNode);
            }

            // make sure that our 2 nodes are connected to the parent
            newNode.setParent(parentNode);
            currNode.setParent(parentNode);
            
            // finally move current and try again
            currNode = parentNode;
     
        }
    }
    
    
    // method finds which position key to place item into in a node
    private int FFGTE (TFNode currNode, Object key) {
        // iterate through current node's items to find one >= the key
        for (int i = 0; i < currNode.getNumItems(); i++) {
            if (treeComp.isGreaterThanOrEqualTo(currNode.getItem(i).key(), key)) {
                return i;
            }
        } 
        return currNode.getNumItems();
    }
    
    
    // method recursively finds an external node to place new item
    private TFNode findExternalNode(TFNode currNode, Object key) {
        
        if (currNode.isExternal()) {
            
            // our node is a leaf
            return currNode;
            
        } else { 
            
            // find position such that a < key < b
            int pos = FFGTE (currNode, key);
            // lets try again with our new current
            return findExternalNode (currNode.getChild(pos), key);
            
        }
        
    }
    

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        
        TFNode foundNode = findNode(treeRoot, (int) key);
        Item foundItem;
        
        if (foundNode == null) {
            // could not find item in any node
            throw new ElementNotFoundException ("element not found");
        }
        
        if (foundNode.isExternal()) {
            
            // the node is a leaf, so we can just remove (no swap)
            // get the index for the item in found node
            int index = FFGTE(foundNode, key);

            // check if if the key matched the key at index
            int indexKey = (int) foundNode.getItem(index).key();
            if (treeComp.isEqual(indexKey, key)) { 
                // found the item
               foundItem = foundNode.removeItem(index);
            } else {
                // could not find item
                throw new ElementNotFoundException("element not found.");
            }
            
        } else {
            
            // the node is internal, so we have to swap and remove
            // get the index for the item in found node
            int index = FFGTE(foundNode, key);

            // check if if the key matched the key at index
            int indexKey = (int) foundNode.getItem(index).key();
            if (treeComp.isEqual(indexKey, key)) { 
                
                // grab item of foundNode
                Item removeItem = foundNode.getItem(index);
                
                // grab suc node, its pos, and its item
                TFNode sucNode = findSuccessor(foundNode.getChild(index+1));
                Item sucItem = sucNode.getItem(0);
                
                // Swap items in orginal pos and suc pos
                foundNode.replaceItem(index, sucItem);
                sucNode.replaceItem(0, removeItem);
                 
                // remove FoundNode's Item which was placed in sucNode sucPos
                foundNode = sucNode;
                foundItem = foundNode.removeItem(0);

            } else {
                // could not find item
                throw new ElementNotFoundException("element not found.");
            }
        }
        
        // check if we need to underflow and rebalance the tree
        if (foundNode.getNumItems() == 0 && foundNode != root()) {
            underflow(foundNode);
        }
        
        // decrement size of the tree and return the item we found
        size--;
        return (Object) foundItem.element();
        
    }


    // method to find the successor of a node
    private TFNode findSuccessor(TFNode currNode) {
    
        if (!currNode.isExternal()) {
            // if not external recurse for the farthest left child of currNode
            return findSuccessor (currNode.getChild(0));
        } else {
            // return leaf Node
            return currNode;
        }
        
    }
    

    // method finds the first null position in node
    private int findNullPosition(TFNode currNode) {

        
        for (int i = 0; i < 3; i++) {
            if (currNode.getItem(i) == null) {
                return i;
            }
        }
        throw new TwoFourTreeException("remove not found!!");
        
    }
    
    
    // method returns the index that the curr node is in its parent
    private int whatChildAmI(TFNode currNode) {
        
        // create parent and loop through to find matching node
        TFNode parent = currNode.getParent();
        for (int i = 0; i < 3; i++) {
            if (parent.getChild(i) == currNode) {
                // we found the index, so return it
                return i;
            }
        }
        return parent.getNumItems();
        
    }
    
    
    // method corrects underflow in the tree by running LR transfers and LR fusions
    private void underflow(TFNode currNode) {
        
        TFNode parent = currNode.getParent();
        
        if (parent == null) {
            treeRoot = currNode.getChild(0);
            treeRoot.setParent(null);
            return;
        }
        
        int currPos = whatChildAmI(currNode);
        
        // create sib nodes for LR sibs of curr node
        TFNode leftSib = null;
        TFNode rightSib = null;
        
        // check for left sib
        if (currPos > 0) {
            leftSib = parent.getChild(currPos - 1);
        }
        
        // check for right sib
        if (currPos != parent.getNumItems()) {
            rightSib = parent.getChild(currPos + 1);
        }
        
        // LEFT TRANSFER
        if (currPos >= 1 && leftSib != null && leftSib.getNumItems() > 1) {
            
            currNode.addItem(0, parent.getItem(currPos-1));
            parent.replaceItem(currPos-1, leftSib.getItem(leftSib.getNumItems()-1));
            
           currNode.setChild(0, leftSib.getChild(leftSib.getNumItems()-1));
            if (currNode.getChild(0) != null) {
                currNode.getChild(0).setParent(currNode);
            }
           
           
            leftSib.removeItem(leftSib.getNumItems()-1);
            
        
        // RIGHT TRANSFER
        } else if (currPos <=2 && rightSib != null && rightSib.getNumItems() > 1){
            
            currNode.addItem(0, parent.getItem(currPos));
            parent.replaceItem(currPos, rightSib.getItem(0));
            
            currNode.setChild(1, rightSib.getChild(0));
            if (currNode.getChild(1) != null) {
                currNode.getChild(1).setParent(currNode);
            }
            
            rightSib.removeItem(0);

        // LEFT FUSION
        } else if (leftSib != null) {
            
            // put parent item  at curr pos in right sib
            leftSib.insertItem(leftSib.getNumItems() - 1, parent.getItem(currPos-1));
            parent.removeItem(currPos-1);
            
            // move curr child to left sib and set parent
            leftSib.setChild(leftSib.getNumItems(), currNode.getChild(0));
            
            if (leftSib.getChild(0) != null) {
                leftSib.getChild(0).setParent(leftSib);
            }
            
            
        // RIGHT FUSION
        } else if (rightSib != null) {
            
            // put parent item  at curr pos in right sib
            rightSib.insertItem(0, parent.getItem(currPos));
            parent.removeItem(currPos);
            
            // move curr child to right sib and set parent
            rightSib.setChild(0, currNode.getChild(0));
            
            if (rightSib.getChild(0) != null) {
                rightSib.getChild(0).setParent(rightSib);
            }
            
            if (parent.getNumItems() == 0) {
                underflow(parent);
            } 
        }  
    }
            
            
    public static void main(String[] args) {
        
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        // TEST FINDING ELEMENT
        
     //   Object element = myTree.findElement(89);
        
        // TEST REMOVING ELEMENT
        
//        int test = (Integer) myTree.removeElement(75);
//        System.out.println(test);
//         myTree.printAllElements();
//         
//        int test2 = (Integer) myTree.removeElement(76);
//        System.out.println(test2);
//        myTree.printAllElements();
//         
//        int test3 = (Integer) myTree.removeElement(65);
//        System.out.println(test3);
//        myTree.printAllElements();
//       
//        System.out.println("done");

        // TEST INSERTING AND REMOVING ELEMENTS
        
        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 25;
        
        int seed = 2;
        Random generator = new Random(seed);
        int num =0;
        int myArray[] = new int[TEST_SIZE];
        
        for (int i = 0; i < TEST_SIZE; i++) {
           num = generator.nextInt(TEST_SIZE);
            myTree.insertElement(num, num);
            myArray[i] = num;
            myTree.checkTree();
        }
        
        System.out.println("REMOVING ELEMENTS");
        for (int i = 0; i < TEST_SIZE; i++) {
             System.out.println("-----");
            System.out.println("removing..." + myArray[i]);
            int out = (Integer) myTree.removeElement(myArray[i]);
            
            System.out.println("removing... array." + myArray[i]);
             System.out.println("-----");
            
            
            myTree.checkTree();
            if (out != myArray[i]) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            //if (i > TEST_SIZE - 15) { UNCOMMENT FOR FULL TEST
                myTree.printAllElements();
            //} SMALL TESTS
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
