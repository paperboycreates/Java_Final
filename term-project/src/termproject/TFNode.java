package termproject;
/**
 * Basic storage element for the 2-4 Tree
 *
 * @author Dr. Gallagher
 * @version 1.0
 * Created 2 Mar 2001
 * Summary of Modifications
 *      3 Dec 2009 - DMG - changed type for data stored in TFNode to Item
 *          and changed necessary methods to deal with Item instead of Object
 * Description: The basic node for a 2-4 tree.  Contains an array of Items,
 * an array of references to children TFNodes, a pointer to a parent TFNode,
 * and a count of how many Items are stored in the node.
 */

public class TFNode {

    private static final int MAX_ITEMS = 3;

    private int numItems = 0;
    private TFNode nodeParent;
    private TFNode[] nodeChildren;
    // DMG 3 Dec 09 - changed type to Item
    private Item[] nodeItems;

    public TFNode() {
            // make them one bigger than needed, so can handle oversize nodes
            // during inserts
        nodeChildren = new TFNode[MAX_ITEMS+2];
        nodeItems = new Item[MAX_ITEMS+1];
    }

    public int getNumItems () {
        return numItems;
    }

    public int getMaxItems() {
        return MAX_ITEMS;
    }

    public TFNode getParent() {
        return nodeParent;
    }
    public void setParent (TFNode parent) {
        nodeParent = parent;
    }
    public Item getItem(int index) {
        if ( (index < 0) || (index > (numItems-1) ) )
            throw new TFNodeException();
        return nodeItems[index];
    }
        // adds, but does not extend array; so it overwrites anything there
    public void addItem (int index, Item data) {
            // always add at end+1; check that you are within array
        if ( (index < 0) || (index > numItems) || (index > MAX_ITEMS) )
            throw new TFNodeException();
        nodeItems[index] = data;
        numItems++;
    }
        // this function inserts an item into the node, and adjusts into child
        // pointers to add the proper corresponding pointer
    public void insertItem (int index, Item data) {
        if ( (index < 0) || (index > numItems) || (index > MAX_ITEMS) )
            throw new TFNodeException();
            // adjust Items
        for (int ind=numItems; ind > index; ind--) {
            nodeItems[ind] = nodeItems[ind-1];
        }
            // insert new data into hole made
        nodeItems[index] = data;
            // adjust children pointers; if inserting into index=1, we make
            // pointers 1 and 2 to point to 1; this is because whoever called
            // this function will fix one of them later; index 0 doesn't change;
            // pointer 3 becomes pointer 2; pointer 4 becomes 3, etc.
        for (int ind=numItems+1; ind > index; ind--) {
            nodeChildren[ind] = nodeChildren[ind-1];
        }

        numItems++;
    }

        // this method removes item, and shrinks array
    public Item removeItem (int index) {
        if ( (index < 0) || (index > (numItems-1) ) )
            throw new TFNodeException();
        Item removedItem = nodeItems[index];

        for (int ind=index; ind < numItems-1; ind++) {
            nodeItems[ind] = nodeItems[ind+1];
        }
        nodeItems[numItems-1] = null;
            // fix children pointers also
            // typically, you wouldn't expect to do a removeItem unless
            // children are null, because removal of an item will mess up the
            // pointers; however, here we will simply delete the child to the
            // left of the removed item; i.e., the child with same index
        for (int ind=index; ind < numItems; ind++) {
            nodeChildren[ind] = nodeChildren[ind+1];
        }
        nodeChildren[numItems] = null;
        numItems--;
        return removedItem;
    }

        // this method removes item, but does not shrink array
    public Item deleteItem (int index) {
        if ( (index < 0) || (index > (numItems-1) ) )
            throw new TFNodeException();
        Item removedItem = nodeItems[index];
        nodeItems[index] = null;

        numItems--;
        return removedItem;
    }
        // replaces Item at index with newItem, returning the old Item
    public Item replaceItem (int index, Item newItem) {
        if ( (index < 0) || (index > (numItems-1) ) )
            throw new TFNodeException();
        Item returnItem = nodeItems[index];

        nodeItems[index] = newItem;
        return returnItem;
    }

    public TFNode getChild (int index) {
        if ( (index < 0) || (index > (MAX_ITEMS+1)) )
            throw new TFNodeException();
        return nodeChildren[index];
    }
    public void setChild (int index, TFNode child) {
        if ( (index < 0) || (index > (MAX_ITEMS+1)) )
            throw new TFNodeException();
        nodeChildren[index] = child;
    }
    
    // EXTRA METHODS WE CREATED

    public boolean isFull () {
        return (getNumItems() == 3);
    }
    
    public boolean isExternal () {
        return (nodeChildren[0] == null);
    }
    
    public boolean isInternal () {
        return (!isExternal());
    }
    
    // method assumes new item won't overflow
    public void placeItem (Item newItem, Comparator comp) {
        
        switch (numItems) {
            case 0:
                // nothing there, so just add
                addItem(0, newItem);
                break;
            case 1:
                if (comp.isLessThanOrEqualTo(newItem.key(), getItem(0).key())) {
                    insertItem(0, newItem);
                } else {
                    addItem(1, newItem);
                }
                break;
            default:
                if (comp.isLessThanOrEqualTo(newItem.key(), getItem(0).key())) {
                    // new < 0
                    insertItem(0, newItem);
                } else {
                    if (comp.isLessThanOrEqualTo(newItem.key(), getItem(1).key())) {
                        // new < 1
                        insertItem(1, newItem);
                    } else {
                        // new > 0 and 1
                        addItem(2, newItem);
                    }
                }
                break;
        }
        
    }
    
    public void split (Comparator comp) {
        
        // we need to grab the items
        Item rightItem = removeItem(2);
        Item midItem = removeItem(1);
        Item leftItem = removeItem(0);
        
        if (nodeParent != null) {
            // place middle item in parent
            placeItem(midItem, comp);
        } else {
            // make new parent node
            nodeParent = new TFNode();
            nodeParent.insertItem(0, midItem);
        }
        
        // TODO: we need to handle creating the new nodes
                
    }
    
}
