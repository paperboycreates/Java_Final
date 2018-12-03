package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */


public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException() {
        super ("Problem with TwoFourTree");
    }
    public ElementNotFoundException(String errorMsg) {
        super (errorMsg);
    }
}