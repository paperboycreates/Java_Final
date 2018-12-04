package termproject;

/**
 * Title:        Project #7
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public interface Comparator {

    public boolean isLessThan (Object obj1, Object obj2);

    public boolean isLessThanOrEqualTo (Object obj1, Object obj2);

    public boolean isGreaterThan (Object obj1, Object obj2);

    public boolean isGreaterThanOrEqualTo (Object obj1, Object obj2);

    public boolean isEqual (Object obj1, Object obj2);

    public boolean isComparable (Object obj);
}