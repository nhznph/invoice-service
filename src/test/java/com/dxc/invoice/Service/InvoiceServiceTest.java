package com.dxc.invoice.Service;

import com.dxc.invoice.TypeOfInvoice;
import com.dxc.invoice.api.model.Invoice;
import com.dxc.invoice.common.InvoiceError;
import com.dxc.invoice.entity.InvoiceEntity;
import com.dxc.invoice.exception.InvoiceException;
import com.dxc.invoice.repository.InvoiceRepository;
import com.dxc.invoice.service.InvoiceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {
    private final static String TYPE_OF_INVOICE = "Electric";
    private final static Integer AMOUNT_OF_MONEY = 300000;
    private final static String VAT = "0.5";
    private final static String PERIOD = "Monthly";
    private final static String INVOICE_NO = "819d15a3-9692-19ba-9974-180df0e334c1";
    private final static String USER_ID = "001";

    List<InvoiceEntity> list = new ArrayList<>();

    @InjectMocks
    InvoiceService invoiceService;

    @Spy
    InvoiceService invoiceServiceSpy;

    @Mock
    Invoice invoice;

    @Mock
    InvoiceEntity invoiceEntity;

    @Mock
    InvoiceRepository invoiceRepository;

    @Before
    public void setUp() {
        invoice = new Invoice();
        invoice.setTypeOfInvoice(TYPE_OF_INVOICE);
        invoice.setAmountOfMoney(AMOUNT_OF_MONEY);
        invoice.setVat(VAT);
        invoice.setPeriod(PERIOD);
        invoice.setInvoiceNo(INVOICE_NO);
        invoice.setUserId(USER_ID);
    }


    //check invoiNo exist
    @Test
    public void addInvoiceExist() {
//        doAnswer(invocation -> {
//            invoiceEntity = invocation.getArgument(0);
//            invoiceEntity.setTypeOfInvoice(TYPE_OF_INVOICE);
//            invoiceEntity.setAmountOfMoney(AMOUNT_OF_MONEY);
//            invoiceEntity.setVAT(VAT);
//            invoiceEntity.setChargedPeriod(PERIOD);
//            invoiceEntity.setInvoiceNo(INVOICE_NO);
//            invoiceEntity.setUserId(USER_ID);
//            invoiceEntity.setDeleted(false);
//            invoiceEntity.setCreateDate(new Date());
//            invoiceEntity.setModifiedDate(new Date());
//            return null;
//        }).when(invoiceRepository).saveAndFlush(any(InvoiceEntity.class));
        when(invoiceRepository.findByInvoiceNoAndDeleted(invoice.getInvoiceNo(), false)).thenReturn(invoiceEntity);
        String actual = invoiceService.addInvoice(USER_ID, invoice);
        Assert.assertEquals("InvoiceNo exist", actual);
    }
    //invoice does not exist, check type of invoice
//    @Test
//    public  void addInvoiceTypeOf() {
//        when(invoiceRepository.findByInvoiceNoAndDeleted(invoice.getInvoiceNo(),false))
//                .thenReturn(null);
////        boolean result = invoiceService.checkTypeOfInvoice("electric");
////////        doReturn(false).when(InvoiceServiceSpy.checkTypeOfInvoice(TYPE_OF_INVOICE)).booleanValue();
//////        when(invoiceServiceSpy.checkTypeOfInvoice(TYPE_OF_INVOICE)).thenReturn(false);
//////        System.out.println(result);
//////        String actual ="" ;
//////        if(!result){
//////            actual = invoiceService.addInvoice(USER_ID, invoice);
//////        }
////        when(invoiceService.checkTypeOfInvoice(TYPE_OF_INVOICE)).thenReturn(true);
////        String actual = invoiceService.addInvoice(USER_ID, invoice);
////        Assert.assertEquals("type Of invoice is wrong { Water, Electric, Telephone, Internet}", actual);
////        Assert.assertEquals(false,invoiceService.checkTypeOfInvoice("electric"));
////        boolean result = invoiceService.checkTypeOfInvoice("electric");
////        String actual ="";
////        if(!result){
////            actual = invoiceService.addInvoice(USER_ID,invoice);
////        }else {
////            actual = invoiceService.addInvoice(USER_ID,invoice);
////        }
//        String actual= "";
//       if( !invoiceService.checkTypeOfInvoice("electric")) {
//         actual= invoiceService.addInvoice(USER_ID, invoice);
//       }
//        Assert.assertEquals("ssss",actual);
//
//
//    }

//    //invoice does not exist, invoiceType is right , check month have invoice
//    @Test
//    public  void addInvoiceMonth(){
//        doAnswer(invocation -> {
//            invoiceEntity = invocation.getArgument(0);
//            invoiceEntity.setTypeOfInvoice(TYPE_OF_INVOICE);
//            invoiceEntity.setAmountOfMoney(AMOUNT_OF_MONEY);
//            invoiceEntity.setVAT(VAT);
//            invoiceEntity.setChargedPeriod(PERIOD);
//            invoiceEntity.setInvoiceNo(INVOICE_NO);
//            invoiceEntity.setUserId(USER_ID);
//            invoiceEntity.setDeleted(false);
//            invoiceEntity.setCreateDate(new Date());
//            invoiceEntity.setModifiedDate(new Date());
//            return null;
//        }).when(invoiceRepository).saveAndFlush(any(InvoiceEntity.class));
//        when(invoiceService.checkTypeOfInvoice(TYPE_OF_INVOICE)).thenReturn(false);
//        String actual = invoiceService.addInvoice(USER_ID,invoice);
//        Assert.assertEquals("InvoiceNo exist",actual);
//    }

    @Test
    public void addInvoiceSuccess() {
        when(invoiceRepository.findByInvoiceNoAndDeleted(invoice.getInvoiceNo(), false)).thenReturn(null);
        String actual = invoiceService.addInvoice(USER_ID, invoice);
        Assert.assertEquals(INVOICE_NO, actual);
    }

    @Test
    public void getInvoiceByInvoiceNo() {
        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO, false)).thenReturn(invoiceEntity);
        Invoice invoiceActual = invoiceService.readInvoice(INVOICE_NO);
        invoice = entity2Invoice(invoiceEntity);
        Assert.assertEquals(invoice, invoiceActual);
    }

    @Test(expected = InvoiceException.class)
    public void getInvoiceByInvoiceNoNull() {
        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO, false))
                .thenThrow(new InvoiceException(InvoiceError.MARKED_DELETE));
        Assert.assertEquals(new InvoiceException(InvoiceError.MARKED_DELETE), invoiceService.readInvoice(invoice.getInvoiceNo()));

    }

    @Test
    public void getInvoiceList() {
        when(invoiceRepository.findAllByUserId(USER_ID)).thenReturn(list);
        List<Invoice> invoiceList = invoiceService.readAllInvoice(USER_ID);
        Assert.assertEquals(list, invoiceList);
    }

    @Test(expected = InvoiceException.class)
    public void getInvoiceNull() {
        when(invoiceRepository.findAllByUserId(USER_ID))
                .thenThrow(new InvoiceException(InvoiceError.USER_INVALID, USER_ID));
        Assert.assertEquals(new InvoiceException(InvoiceError.USER_INVALID), invoiceService.readAllInvoice(USER_ID));
    }

    @Test
    public void deleteInvoiceFail() {
        when(invoiceRepository.markDeletedByInvoice(INVOICE_NO)).thenReturn(0);
        String actual = invoiceService.deleteInvoice(INVOICE_NO, USER_ID);
        Assert.assertEquals("InvoiceNo does not exist or deleted before", actual);
    }

    @Test
    public void deleteInvocieSuccess() {
        when(invoiceRepository.markDeletedByInvoice(INVOICE_NO)).thenReturn(1);
        String actual = invoiceService.deleteInvoice(INVOICE_NO, USER_ID);
        Assert.assertEquals("delete successfully at " + INVOICE_NO, actual);
    }

    //invoice does not exist
    @Test
    public void updateInvoiceExist() {
        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO, false)).thenReturn(null);
        String actual = invoiceService.updateInvoice(INVOICE_NO, USER_ID, invoice);
        Assert.assertEquals("InvoiceNo does not exist or deleted before", actual);
    }

    // invoice exist, check user have no invoice
    @Test
    public void updateInvoiceUserHaveNot() {
        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO, false)).thenReturn(invoiceEntity);// not null
        String actual = "";
        if (!invoice.getUserId().equals("002")) {
            actual = invoiceService.updateInvoice(INVOICE_NO, "002", invoice);
        }
        Assert.assertEquals("user have no invoice", actual);
    }

    // invoice exist, user have invoice
//    @Test
//    public  void updateInvoiceUserTypeWrong(){
//        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO,false)).thenReturn(invoiceEntity);// not null
//
////        when(USER_ID.equals(invoice.getUserId())).thenReturn(true);
//        when(invoiceServiceSpy.checkTypeOfInvoice(TYPE_OF_INVOICE)).thenReturn(false);
//        String actual =invoiceService.updateInvoice(INVOICE_NO,USER_ID,invoice);
//        Assert.assertEquals("type Of invoice is wrong { Water, Electric, Telephone, Internet}",actual);
//    }
//    @Test
//    public  void updateInvoiceSuccess(){
//        when(invoiceRepository.findByInvoiceNoAndDeleted(INVOICE_NO,false)).thenReturn(invoiceEntity);
//        String actual= invoiceService.updateInvoice(INVOICE_NO,USER_ID,invoice);
//        Assert.assertEquals("update successfully at " + INVOICE_NO,actual);
//    }


    private Invoice entity2Invoice(InvoiceEntity invoiceEntity) {
        Invoice in = new Invoice();
        in.setTypeOfInvoice(invoiceEntity.getTypeOfInvoice());
        in.setAmountOfMoney(invoiceEntity.getAmountOfMoney());
        in.setVat(invoiceEntity.getVAT());
        in.setPeriod(invoiceEntity.getChargedPeriod());
        in.setInvoiceNo(invoiceEntity.getInvoiceNo());
        in.setUserId(invoiceEntity.getUserId());
        return in;
    }


}
