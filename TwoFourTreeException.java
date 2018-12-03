package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */


public class TwoFourTreeException extends RuntimeException {

    public TwoFourTreeException() {
        super ("Problem with TwoFourTree");
    }
    public TwoFourTreeException(String errorMsg) {
        super (errorMsg);
    }
}