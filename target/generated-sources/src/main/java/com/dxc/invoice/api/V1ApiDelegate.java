package com.dxc.invoice.api;

import com.dxc.invoice.api.model.Invoice;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface V1ApiDelegate {

    /**
     * @see V1Api#addInvoice
     */
    ResponseEntity<String> addInvoice(String userId,
        Invoice body);

    /**
     * @see V1Api#deleteInvoice
     */
    ResponseEntity<String> deleteInvoice(String invoiceNo,
        String userId);

    /**
     * @see V1Api#readAllInvoice
     */
    ResponseEntity<List<Invoice>> readAllInvoice(String userId);

    /**
     * @see V1Api#readInvoice
     */
    ResponseEntity<Invoice> readInvoice(String invoiceNo);

    /**
     * @see V1Api#updateInvoice
     */
    ResponseEntity<String> updateInvoice(String invoiceNo,
        String userId,
        Invoice body);

    /**
     * @see V1Api#viewReportInvoice
     */
    ResponseEntity<List<Invoice>> viewReportInvoice(String userId,
        String period,
        String monthly);

}
