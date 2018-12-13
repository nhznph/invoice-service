package com.dxc.invoice.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Invoice
 */
@Validated

public class Invoice   {
  @JsonProperty("typeOfInvoice")
  private String typeOfInvoice = null;

  @JsonProperty("amountOfMoney")
  private Integer amountOfMoney = null;

  @JsonProperty("vat")
  private String vat = null;

  @JsonProperty("period")
  private String period = null;

  @JsonProperty("invoiceNo")
  private String invoiceNo = null;

  @JsonProperty("userId")
  private String userId = null;

  public Invoice typeOfInvoice(String typeOfInvoice) {
    this.typeOfInvoice = typeOfInvoice;
    return this;
  }

  /**
   * Get typeOfInvoice
   * @return typeOfInvoice
  **/
  @ApiModelProperty(example = "Electric", value = "")


  public String getTypeOfInvoice() {
    return typeOfInvoice;
  }

  public void setTypeOfInvoice(String typeOfInvoice) {
    this.typeOfInvoice = typeOfInvoice;
  }

  public Invoice amountOfMoney(Integer amountOfMoney) {
    this.amountOfMoney = amountOfMoney;
    return this;
  }

  /**
   * Get amountOfMoney
   * @return amountOfMoney
  **/
  @ApiModelProperty(example = "300000", value = "")


  public Integer getAmountOfMoney() {
    return amountOfMoney;
  }

  public void setAmountOfMoney(Integer amountOfMoney) {
    this.amountOfMoney = amountOfMoney;
  }

  public Invoice vat(String vat) {
    this.vat = vat;
    return this;
  }

  /**
   * Get vat
   * @return vat
  **/
  @ApiModelProperty(example = "0.5", value = "")


  public String getVat() {
    return vat;
  }

  public void setVat(String vat) {
    this.vat = vat;
  }

  public Invoice period(String period) {
    this.period = period;
    return this;
  }

  /**
   * Get period
   * @return period
  **/
  @ApiModelProperty(example = "monthly", value = "")


  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public Invoice invoiceNo(String invoiceNo) {
    this.invoiceNo = invoiceNo;
    return this;
  }

  /**
   * Get invoiceNo
   * @return invoiceNo
  **/
  @ApiModelProperty(example = "819d15a3-9692-19ba-9974-180df0e334c1", value = "")


  public String getInvoiceNo() {
    return invoiceNo;
  }

  public void setInvoiceNo(String invoiceNo) {
    this.invoiceNo = invoiceNo;
  }

  public Invoice userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(example = "001", value = "")


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) o;
    return Objects.equals(this.typeOfInvoice, invoice.typeOfInvoice) &&
        Objects.equals(this.amountOfMoney, invoice.amountOfMoney) &&
        Objects.equals(this.vat, invoice.vat) &&
        Objects.equals(this.period, invoice.period) &&
        Objects.equals(this.invoiceNo, invoice.invoiceNo) &&
        Objects.equals(this.userId, invoice.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeOfInvoice, amountOfMoney, vat, period, invoiceNo, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Invoice {\n");
    
    sb.append("    typeOfInvoice: ").append(toIndentedString(typeOfInvoice)).append("\n");
    sb.append("    amountOfMoney: ").append(toIndentedString(amountOfMoney)).append("\n");
    sb.append("    vat: ").append(toIndentedString(vat)).append("\n");
    sb.append("    period: ").append(toIndentedString(period)).append("\n");
    sb.append("    invoiceNo: ").append(toIndentedString(invoiceNo)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

