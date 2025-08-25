package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
public interface InvoiceSubRepo extends CrudRepository<Invoice_sub,Integer>{
	String ss3="select * from invoice_sub where inv_id = ?1 ";
	@Query(nativeQuery =true, value=ss3)
	List<Invoice_sub> invoiceSubDataById(String invoiceId);
}
