package murach.exception;

public class DAOException extends RuntimeException{

    public DAOException(String message, Throwable cause){
        super(message, cause);
        //run constructor of RuntimeException.
    }

    public DAOException(String message){
        super(message);
    }
}
