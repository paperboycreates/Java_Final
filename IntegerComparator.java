package termproject;

/**
 * Title:        Project #7
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class IntegerComparator implements Comparator {

    public IntegerComparator() {
    }

    public boolean isLessThan (Object obj1, Object obj2) {
        Integer myInt1;
        Integer myInt2;
        try {
            myInt1 = (Integer) obj1;
            myInt2 = (Integer) obj2;
        }
        catch (ClassCastException exc) {
            throw new InvalidIntegerException ("Object not an integer");
        }

        return ( myInt1.intValue() < myInt2.intValue() );
    }

    public boolean isLessThanOrEqualTo (Object obj1, Object obj2) {
        return ( ! isLessThan (obj2, obj1) );
    }

    public boolean isGreaterThan (Object obj1, Object obj2) {
        return ( isLessThan (obj2, obj1) );
    }

    public boolean isGreaterThanOrEqualTo (Object obj1, Object obj2) {
        return ( ! isLessThan (obj1, obj2) );
    }

    public boolean isEqual (Object obj1, Object obj2) {
        return ( (! isLessThan (obj1, obj2)) && (! isLessThan (obj2, obj1)) );
    }

    public boolean isComparable (Object obj) {
        try {
            Integer myInt = (Integer) obj;
            return true;
        }
        catch (ClassCastException exc) {
            return false;
        }
    }
}