package com.dxc.invoice.service;


import com.dxc.invoice.TypeOfInvoice;
import com.dxc.invoice.api.model.Invoice;
import com.dxc.invoice.common.InvoiceError;
import com.dxc.invoice.entity.InvoiceEntity;
import com.dxc.invoice.exception.InvoiceException;
import com.dxc.invoice.repository.InvoiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional
    public String addInvoice(String userId, Invoice invoice) {
        InvoiceEntity check = invoiceRepository.findByInvoiceNoAndDeleted(invoice.getInvoiceNo(), false);
        if (check != null) {
           throw new InvoiceException(InvoiceError.INVOICE_EXIST_BEFORE);
        } else {
            InvoiceEntity in = new InvoiceEntity();
            in.setTypeOfInvoice(invoice.getTypeOfInvoice());
            in.setAmountOfMoney(invoice.getAmountOfMoney());
            in.setVAT(invoice.getVat());
            in.setChargedPeriod(invoice.getPeriod());
            in.setUserId(userId);
            in.setInvoiceNo(invoice.getInvoiceNo());
            in.setDeleted(false);
            in.setCreateDate(new Date());
            if (!checkTypeOfInvoice(invoice.getTypeOfInvoice())) {
//                return "type Of invoice is wrong { Water, Electric, Telephone, Internet}";
                throw new InvoiceException(InvoiceError.TYPE_OF_INVOICE_WRONG);
            }
            String checkMonth = checkInvoiceOfMonth(userId, in, invoice.getTypeOfInvoice());
            if (checkMonth != null) {
                return checkMonth;
            }
            in.setModifiedDate(new Date());
            invoiceRepository.saveAndFlush(in);
            return in.getInvoiceNo();
        }
    }

    public List<Invoice> readAllInvoice(String userId) {
        List<InvoiceEntity> invoices = invoiceRepository.findAllByUserId(userId);
        if (invoices == null) {
            throw new InvoiceException(InvoiceError.USER_INVALID, userId);
        }
        return invoices.stream().map(this::entity2Invoice).collect(Collectors.toList());
    }

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

    public Invoice readInvoice(String invoiceNo) {
        InvoiceEntity invoice = invoiceRepository.findByInvoiceNoAndDeleted(invoiceNo, false);
        if (invoice == null) {
            throw new InvoiceException(InvoiceError.NOT_FOUND, invoiceNo);
        } else {
            return entity2Invoice(invoice);
        }
    }

    @Transactional
    public String deleteInvoice(String invoiceNo, String userId) {
        int count = invoiceRepository.markDeletedByInvoice(invoiceNo);
        if (count <= 0) {
            throw new InvoiceException(InvoiceError.INVOICE_DOES_NOT_EXIST);
        }
        return  invoiceNo;
    }

    @Transactional
    public String updateInvoice(String invoiceNo, String userId, Invoice invoice) {
        InvoiceEntity in = invoiceRepository.findByInvoiceNoAndDeleted(invoiceNo, false);
        if (in == null) {
            throw new InvoiceException(InvoiceError.INVOICE_DOES_NOT_EXIST);
        } else {
            if (userId.equals(in.getUserId())) {
                if (!checkTypeOfInvoice(invoice.getTypeOfInvoice())) {
                  throw new InvoiceException(InvoiceError.TYPE_OF_INVOICE_WRONG);
                }
                String check = checkInvoiceOfMonth(userId, in, invoice.getTypeOfInvoice());
                if (check != null) {
                    return check;
                }
                in.setTypeOfInvoice(invoice.getTypeOfInvoice());
                in.setAmountOfMoney(invoice.getAmountOfMoney());
                in.setVAT(invoice.getVat());
                in.setModifiedDate(new Date());
                invoiceRepository.saveAndFlush(in);
                return invoiceNo;
            } else {
                throw new InvoiceException(InvoiceError.USER_HAVE_NO_INVOICE);
            }
        }
    }

    public List<Invoice> viewReport(String userId, String period, Integer values) {
        if (period.trim().equals("monthly")) {
            // check year
            if (values < 1 || values > 12) {
                throw new InvoiceException(InvoiceError.MONTH_WRONG,values);
            }
            return invoiceRepository.findInvoiceInMonth(userId,values).stream()
                    .map(this::entity2Invoice).collect(Collectors.toList());
        } else if (period.trim().equals("yearly")) {
            Date a = new Date();
            if (values < 2000 || values > getYear(a)) {
                throw new InvoiceException(InvoiceError.YEAR_WRONG,values);
            }
           return invoiceRepository.findInvoiceInYear(userId,values).stream()
                   .map(this::entity2Invoice).collect(Collectors.toList());
        } else {
            throw new InvoiceException(InvoiceError.PERIOD_WRONG, period);
        }
    }

//    public List<Invoice> viewReport(String userId, String period, String monthly) {
//        if (period.trim().equals("monthly")) {
//            List<InvoiceEntity> invoices = invoiceRepository.findAllByUserId(userId);
//            if (invoices.size() == 0) {
//                throw new InvoiceException(InvoiceError.USER_NOT_INVOICE, userId);
//            }
//            List<InvoiceEntity> invoiceList = new ArrayList<>();
//            int month = Integer.valueOf(monthly.trim());
//            if (month < 1 || month > 12) {
//                throw new InvoiceException(InvoiceError.MONTH_WRONG);
//            }
//            for (InvoiceEntity invoice : invoices) {
//                if (month == getMonth(invoice.getCreateDate())) {
//                    invoiceList.add(invoice);
//                }
//            }
//            if (invoiceList.size() <= 0) {
//                throw new InvoiceException(InvoiceError.NOT_FOUND);
//            }
//            System.out.println(getMoneyMonth(monthly, invoiceList));
//            return invoiceList.stream().map(this::entity2Invoice).collect(Collectors.toList());
//        } else if (period.trim().equals("yearly")) {
//            List<InvoiceEntity> invoices = invoiceRepository.findAllByUserId(userId);
//            if (invoices.size() == 0) {
//                throw new InvoiceException(InvoiceError.USER_NOT_INVOICE, userId);
//            }
//            List<InvoiceEntity> invoiceList = new ArrayList<>();
//            int year = Integer.valueOf(monthly.trim());
//            Date a = new Date();
//            if (year < 2000 || year > getYear(a)) {
//                throw new InvoiceException(InvoiceError.YEAR_WRONG);
//            }
//            double printTotalMoney = 0.0;
//            String printInvoice = " ";
//            for (InvoiceEntity invoice : invoices) {
//                if (year == getYear(invoice.getCreateDate())) {
//                    invoiceList.add(invoice);
//                }
//            }
//            if (invoiceList.size() <= 0) {
//                throw new InvoiceException(InvoiceError.NOT_FOUND);
//            }
//            // system out
//            List<InvoiceEntity> list11;
//            HashMap<Integer, List<InvoiceEntity>> listHashMap = new HashMap<>();
//            for (InvoiceEntity invoice : invoiceList) {
//                for (int i = 0; i < 12; i++) {
//                    list11 = new ArrayList<>();
//                    if (getMonth(invoice.getCreateDate()) == (i + 1)) {
//                        if (listHashMap.keySet().contains(getMonth(invoice.getCreateDate()))) {
//                            List<InvoiceEntity> invoiceList1 = listHashMap.get(i + 1);
//                            invoiceList1.add(invoice);
//                            list11 = invoiceList1;
//                        } else {
//                            list11.add(invoice);
//                        }
//                    }
//                    if (list11.size() > 0) {
//                        listHashMap.put(i + 1, list11);
//                    }
//                }
//            }
//            List<Integer> in = new ArrayList<>(listHashMap.keySet());
//            List<List<InvoiceEntity>> printInvoiceList = new ArrayList<>();
//            for (Integer integer : in) {
//                List<InvoiceEntity> invoiceList1 = listHashMap.get(integer);
//                // print
//                printInvoice += getMoneyMonth(String.valueOf(integer), invoiceList1);
//                //get total monney;
//                printTotalMoney += getTotalMoneyMonth(invoiceList1);
//                printInvoiceList.add(invoiceList1);
//            }
//            System.out.println("YEAR: " + monthly + printInvoice + "\nTOTAL OF MONEY YEAR : " + printTotalMoney);
//            //end system.out
//            return invoiceList.stream().map(this::entity2Invoice).collect(Collectors.toList());
//        } else {
//            throw new InvoiceException(InvoiceError.PERIOD_WRONG, userId);
//        }
//    }

    //helper function
    private int getMonth(Date a) {
        LocalDate localDate = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }

    private int getYear(Date a) {
        LocalDate localDate = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        return year;
    }

    private String checkInvoiceOfMonth(String userId, InvoiceEntity in, String typeOf) {
        List<InvoiceEntity> invoiceList = invoiceRepository.findAllByUserId(userId);
        List<InvoiceEntity> invoices = new ArrayList<>();
        for (InvoiceEntity invoice1 : invoiceList) {
            if (getMonth(invoice1.getCreateDate()) == getMonth(in.getCreateDate())) {
                invoices.add(invoice1);
            }
        }
        for (InvoiceEntity ink : invoices) {
            if (ink.getTypeOfInvoice().equals(typeOf)) {
                return "Type Of Invoice " + ink.getTypeOfInvoice() + " exist in month " + getMonth(in.getCreateDate())
                        + " of user " + userId;
            }
        }
        return null;
    }


    private boolean checkTypeOfInvoice(String type) {
        if (type.equals(TypeOfInvoice.Internet.name())) {
            return true;
        }
        if (type.equals(TypeOfInvoice.Water.name())) {
            return true;
        }
        if (type.equals(TypeOfInvoice.Electric.name())) {
            return true;
        }
        if (type.equals(TypeOfInvoice.Telephone.name())) {
            return true;
        }
        return false;
    }

    private String getMoneyMonth(String month, List<InvoiceEntity> invoiceList) {
        String water = "";
        String electric = "";
        String telephone = "";
        String internet = "";
        double total = 0.0;

        for (InvoiceEntity invoice : invoiceList) {
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Water.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
                String s = "";
                water = s.concat("Money of water: ").concat(String.valueOf(money)).concat("\n\t");
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Electric.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
                String s = "";
                electric = s.concat("Money of electric: ").concat(String.valueOf(money)).concat("\n\t");
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Internet.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
                String s = "";
                internet = s.concat("Money of internet: ").concat(String.valueOf(money)).concat("\n\t");
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Telephone.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
                String s = "";
                telephone = s.concat("Money of telephone: ").concat(String.valueOf(money)).concat("\n");
            }
        }
        return "\nMonth: " + month + " have " + invoiceList.size() + " invoices :" + "\n\t" + water
                + electric + internet + telephone + "Total of money :" + total;
    }

    private double getTotalMoneyMonth(List<InvoiceEntity> invoiceList) {
        double total = 0.0;
        for (InvoiceEntity invoice : invoiceList) {
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Water.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Electric.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Internet.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
            }
            if (invoice.getTypeOfInvoice().equals(TypeOfInvoice.Telephone.name())) {
                double money = invoice.getAmountOfMoney() * Double.valueOf(invoice.getVAT()) + invoice.getAmountOfMoney();
                total += money;
            }
        }
        return total;
    }


}
