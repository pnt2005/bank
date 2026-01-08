package com.pnt2005.bank.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
