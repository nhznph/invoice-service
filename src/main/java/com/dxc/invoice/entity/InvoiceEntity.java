package com.dxc.invoice.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "InvoiceEntity")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "TYPE_OF_INVOICE", nullable = false)
    private String typeOfInvoice;

    @Column(name = "AMOUNT_OF_MONEY", nullable = false)
    private Integer amountOfMoney;

    @Column(name = "VAT", nullable = false)
    private String VAT;

    @Column(name = "CHARGED_PERIOD", nullable = false)
    private String chargedPeriod;

    @Size(min = 1, max = 36)
    @Column(name = "INVOICE_NO", nullable = false, length = 36, unique = true)
    private String invoiceNo;

    @Size(min = 1, max = 36)
    @Column(name = "USER_ID", nullable = false, length = 36)
    private String userId;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;

    @Column(name = "MODIFIED_DATE", nullable = false)
    private Date modifiedDate;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTypeOfInvoice() {
        return typeOfInvoice;
    }

    public void setTypeOfInvoice(String typeOfInvoice) {
        this.typeOfInvoice = typeOfInvoice;
    }

    public Integer getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(Integer amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public String getVAT() {
        return VAT;
    }

    public void setVAT(String VAT) {
        this.VAT = VAT;
    }

    public String getChargedPeriod() {
        return chargedPeriod;
    }

    public void setChargedPeriod(String chargedPeriod) {
        this.chargedPeriod = chargedPeriod;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
