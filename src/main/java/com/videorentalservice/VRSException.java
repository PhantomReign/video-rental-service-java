package com.videorentalservice;

/**
 * Created by Rave on 21.03.2017.
 */
public class VRSException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public VRSException()
    {
        super();
    }

    public VRSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public VRSException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public VRSException(String message)
    {
        super(message);
    }

    public VRSException(Throwable cause)
    {
        super(cause);
    }
}
