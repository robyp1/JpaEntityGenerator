package it.gen.exceptions;

public class GenConfigException extends Throwable {

    public GenConfigException() {
        super();
    }

    public GenConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenConfigException(Throwable cause) {
        super(cause);
    }

    protected GenConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GenConfigException(String s) {
        super(s);
    }

}
