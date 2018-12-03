package termproject;

/**
 * Term Project 2-4 Trees
 *
 * @author Dr. Gallagher
 * @version 1.0
 * Created 2 Mar 2001
 * Summary of Modifications:
 *
 * Description: Abstraction for Dictionary ADT.  Works for either ordered
 * or unordered dictionary
 */

public interface Dictionary {

    public int size();
    public boolean isEmpty();

    /**
    * Searches dictionary to determine if key is present
    * @param key to be searched for
    * @return object corresponding to key; null if not found
    */
    public Object findElement (Object key);

    /**
    * Inserts provided element into the Dictionary
    * @param key of object to be inserted
    * @param element to be inserted
    */
    public void insertElement (Object key, Object element);

    /**
    * Searches dictionary to determine if key is present, then
    * removes and returns corresponding object
    * @param key of data to be removed
    * @return object corresponding to key
    * @exception ElementNotFoundException if the key is not in dictionary
    */
    public Object removeElement (Object key) throws ElementNotFoundException;
}