package com.dxc.invoice.exception;

import com.dxc.invoice.common.InvoiceError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceException extends RuntimeException {
    private static final long serialVersionUID = 1491607362490161046L;
    private final InvoiceError response;
    private final transient List<Object> parameters = new ArrayList<>();

    /**
     * Constructs a new {@code ProposalGenException} with the specified detail message. The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param response NumberGeneratorRestError
     */
    public InvoiceException (InvoiceError response) {
        this(response, null, new Object[0]);
    }

    /**
     * Constructs a new {@code ProposalGenException} with the specified detail message including parameters. The cause is not initialized, and may subsequently
     * be initialized by a call to {@link #initCause}.
     *
     * @param response Sensor flow response * @param params list of parameters.
     */
    public InvoiceException (InvoiceError response, Object... params) {
        this(response, null, params);
    }


    public InvoiceException (InvoiceError response, Throwable cause, Object... params) {
        super(response.name() + Arrays.stream(params).map(Object::toString).collect(Collectors.joining(",", "[", "]")), cause);
        this.response = response;
        Collections.addAll(this.parameters, params);
    }

    /**
     * @return
     */
    public List<Object> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    /**
     * @param param
     */
    public void addParameters(Object param) {
        this.parameters.add(param);
    }

    /**
     * @return
     */
    public InvoiceError getResponse() {
        return response;
    }
}
