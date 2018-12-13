package com.dxc.invoice.common;

import org.springframework.http.HttpStatus;

public enum InvoiceError {
    UNEXPECTED(0, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_INVALID(1, HttpStatus.BAD_REQUEST),
    MARKED_DELETE(2001, HttpStatus.BAD_REQUEST),
    NOT_FOUND(2002, HttpStatus.BAD_REQUEST),
    USER_NOT_INVOICE(2003, HttpStatus.BAD_REQUEST),
    YEAR_WRONG(2004, HttpStatus.BAD_REQUEST),
    PERIOD_WRONG(2006, HttpStatus.BAD_REQUEST),
    MONTH_WRONG(2005, HttpStatus.BAD_REQUEST);

    private final int code;
    private final HttpStatus httpStatus;

    InvoiceError(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
