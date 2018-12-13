package com.dxc.invoice.api;

import com.dxc.invoice.api.model.Invoice;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class V1ApiController implements V1Api {

    private final V1ApiDelegate delegate;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(V1ApiDelegate delegate) {
        this.delegate = delegate;
    }
    public ResponseEntity<String> addInvoice(@ApiParam(value = "Id of user",required=true) @PathVariable("userId") String userId,@ApiParam(value = "Invoice object need to be add in Invoice" ,required=true )  @Valid @RequestBody Invoice body) {
        return delegate.addInvoice(userId, body);
    }

    public ResponseEntity<String> deleteInvoice(@ApiParam(value = "ID of invoice to delete",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId) {
        return delegate.deleteInvoice(invoiceNo, userId);
    }

    public ResponseEntity<List<Invoice>> readAllInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId) {
        return delegate.readAllInvoice(userId);
    }

    public ResponseEntity<Invoice> readInvoice(@ApiParam(value = "invoice code",required=true) @PathVariable("invoiceNo") String invoiceNo) {
        return delegate.readInvoice(invoiceNo);
    }

    public ResponseEntity<String> updateInvoice(@ApiParam(value = "ID of invoice to update",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId,@ApiParam(value = "invoice object need to be updated" ,required=true )  @Valid @RequestBody Invoice body) {
        return delegate.updateInvoice(invoiceNo, userId, body);
    }

    public ResponseEntity<List<Invoice>> viewReportInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId,@NotNull @ApiParam(value = "changed period .", required = true) @Valid @RequestParam(value = "period", required = true) String period,@NotNull @ApiParam(value = "input month or year need view report", required = true) @Valid @RequestParam(value = "monthly", required = true) String monthly) {
        return delegate.viewReportInvoice(userId, period, monthly);
    }

}
