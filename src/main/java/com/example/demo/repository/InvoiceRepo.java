package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Invoice;
public interface InvoiceRepo extends CrudRepository<Invoice,Integer>{
	String ss1="select * from invoice";
	@Query(nativeQuery =true, value=ss1)
	List<Invoice> invoiceData();
	String ss2="select * from invoice where inv_id = ?1 ";
	@Query(nativeQuery =true, value=ss2)
	List<Invoice> invoiceDataById(int invoiceId);
	String ss3="select * from invoice where invoice_tran_id = ?1 ";
	@Query(nativeQuery =true, value=ss3)
	List<Invoice> invoiceDataByTransactionId(String transactionId);
}
