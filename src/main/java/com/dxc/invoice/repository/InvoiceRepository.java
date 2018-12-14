package com.dxc.invoice.repository;


import com.dxc.invoice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

    InvoiceEntity findByInvoiceNoAndDeleted(String invoiceNo, boolean deleted);

    @Query("select app from InvoiceEntity app where app.userId =:userId and app.deleted = false")
    List<InvoiceEntity> findAllByUserId(@Param("userId") String userId);

    @Modifying(clearAutomatically = true)
    @Query("update InvoiceEntity app set app.deleted = true, app.modifiedDate = now() where app.invoiceNo =:invoiceNo and app.deleted = false")
    int markDeletedByInvoice(@Param("invoiceNo") String invoiceNo);

    @Query("select app from InvoiceEntity app where app.userId =:userId and app.deleted = false and year(app.createDate) =:year")
    List<InvoiceEntity> findInvoiceInYear(@Param("userId") String userId,
                                             @Param("year") Integer year);

    @Query("select app from InvoiceEntity app where app.userId =:userId and app.deleted = false and month(app.createDate) =:month")
    List<InvoiceEntity> findInvoiceInMonth(@Param("userId") String userId,
                                             @Param("month") Integer month);
}
