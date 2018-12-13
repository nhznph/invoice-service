package com.dxc.invoice.delegate;

import com.dxc.invoice.api.V1ApiDelegate;
import com.dxc.invoice.api.model.Invoice;
import com.dxc.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public ResponseEntity<String> addInvoice(String userId, Invoice body) {
        return ResponseEntity.ok(invoiceService.addInvoice(userId, body));
    }

    @Override
    public ResponseEntity<String> deleteInvoice(String invoiceNo, String userId) {
        return ResponseEntity.ok(invoiceService.deleteInvoice(invoiceNo, userId));
    }

    @Override
    public ResponseEntity<List<Invoice>> readAllInvoice(String userId) {
        return ResponseEntity.ok(invoiceService.readAllInvoice(userId));
    }

    @Override
    public ResponseEntity<Invoice> readInvoice(String invoiceNo) {
        return ResponseEntity.ok(invoiceService.readInvoice(invoiceNo));
    }

    @Override
    public ResponseEntity<String> updateInvoice(String invoiceNo, String userId, Invoice body) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoiceNo, userId, body));
    }

    @Override
    public ResponseEntity<List<Invoice>> viewReportInvoice(String userId, String period, String monthly) {
        return ResponseEntity.ok(invoiceService.viewReport(userId, period, monthly));
    }


}
