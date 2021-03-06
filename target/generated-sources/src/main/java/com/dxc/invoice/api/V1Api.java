/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.dxc.invoice.api;

import com.dxc.invoice.api.model.Invoice;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Api(value = "v1", description = "the v1 API")
public interface V1Api {

    @ApiOperation(value = "add a new invoice  .", nickname = "addInvoice", notes = "add a new invoice  .", response = String.class, tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successfull created", response = String.class),
        @ApiResponse(code = 400, message = "Bad request, validation error"),
        @ApiResponse(code = 404, message = "The invoice is not found."),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(value = "/v1/invoice/{userId}",
        produces = { "text/plain" }, 
        method = RequestMethod.POST)
    ResponseEntity<String> addInvoice(@ApiParam(value = "Id of user",required=true) @PathVariable("userId") String userId,@ApiParam(value = "Invoice object need to be add in Invoice" ,required=true )  @Valid @RequestBody Invoice body);


    @ApiOperation(value = "delete invoice by id", nickname = "deleteInvoice", notes = "delete invoice by id from path", response = String.class, tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successfull delete", response = String.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Invoice not found"),
        @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/v1/{userId}/invoices/{invoiceNo}",
        method = RequestMethod.DELETE)
    ResponseEntity<String> deleteInvoice(@ApiParam(value = "ID of invoice to delete",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId);


    @ApiOperation(value = "Get the list of invoices.", nickname = "readAllInvoice", notes = "Get the list of invoices by userId.", response = Invoice.class, responseContainer = "List", tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Invoice.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(value = "/v1/{userId}/invoice/lists",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Invoice>> readAllInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId);


    @ApiOperation(value = "Get the invoice by  invoiceNo.", nickname = "readInvoice", notes = "Get the invoice by invoiceNo.", response = Invoice.class, tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Invoice specified by Invoice No.", response = Invoice.class),
        @ApiResponse(code = 404, message = "The invoice is not found."),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(value = "/v1/invoices/{invoiceNo}",
        produces = { "application/json", "text/plain" }, 
        method = RequestMethod.GET)
    ResponseEntity<Invoice> readInvoice(@ApiParam(value = "invoice code",required=true) @PathVariable("invoiceNo") String invoiceNo);


    @ApiOperation(value = "Update an existing invoice", nickname = "updateInvoice", notes = "just update the invoice by invoiceNo", response = String.class, tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successfull update", response = String.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Invoice not found"),
        @ApiResponse(code = 405, message = "Validation exception") })
    @RequestMapping(value = "/v1/{userId}/invoices/{invoiceNo}",
        produces = { "text/plain" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<String> updateInvoice(@ApiParam(value = "ID of invoice to update",required=true) @PathVariable("invoiceNo") String invoiceNo,@ApiParam(value = "ID of user exist",required=true) @PathVariable("userId") String userId,@ApiParam(value = "invoice object need to be updated" ,required=true )  @Valid @RequestBody Invoice body);


    @ApiOperation(value = "Report invoice monthly / yearly.", nickname = "viewReportInvoice", notes = "Report invoice monthly / yearly by userId.", response = Invoice.class, responseContainer = "List", tags={ "invoice", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Invoice.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal Server Error") })
    @RequestMapping(value = "/v1/{userId}/invoice/viewReport",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Invoice>> viewReportInvoice(@ApiParam(value = "user Id",required=true) @PathVariable("userId") String userId,@NotNull @ApiParam(value = "changed period .", required = true) @Valid @RequestParam(value = "period", required = true) String period,@NotNull @ApiParam(value = "input month or year need view report", required = true) @Valid @RequestParam(value = "monthly", required = true) Integer monthly);

}
