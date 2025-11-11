package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_sub;
import com.example.demo.repository.InvoiceRepo;
import com.example.demo.repository.InvoiceSubRepo;
import com.example.demo.repository.LedgerServiceRepo;

@Service
public class InvoiceService {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private InvoiceSubRepo invoiceSubRepo;

    @Autowired
    private LedgerServiceRepo ledgerServiceRepo;


        private Integer toIntOrNull(String s) {
            if (s == null) return null;
            String t = s.trim();
            if (t.isEmpty() || "null".equalsIgnoreCase(t)) return null;
            try { return Integer.valueOf(t); } catch (NumberFormatException e) { return null; }
        }

        private String trimSafe(String s) {
            return s == null ? null : s.trim();
        }


    public Invoice add_invoices(Invoice fp) {
        logger.info("Adding invoice: {}", fp);
        return invoiceRepo.save(fp);
    }

    public Invoice_sub add_invoice_subs(Invoice_sub fp) {
        logger.info("Adding invoice sub: {}", fp);
        return invoiceSubRepo.save(fp);
    }

    public Invoice recieve_invoice_payment(Invoice invoice) {
        logger.info("Receiving invoice payment: {}", invoice);
        return invoiceRepo.save(invoice);
    }

    public List<Invoice> invoiceDatas() {
        List<Invoice> li = invoiceRepo.invoiceData();
        logger.info("Fetched invoice data, size: {}", li.size());
        return li;
    }

    public List<Invoice> invoiceDataOnDashBoards() {
        List<Invoice> li = invoiceRepo.invoiceData();
        logger.info("Fetched invoice data for dashboard, size: {}", li.size());
        if (li.size() > 0) {
            List<Account_ledger_v3> li1;
            List<Account_ledger_v3> li2;
            for (int i = 0; i < li.size(); i++) {

        Invoice inv = li.get(i);

        // ---- Customer: treat value as ID only if it is numeric ----
        Integer custId = toIntOrNull(inv.getCust_name());
        if (custId != null) {
            try {
                List<Account_ledger_v3> custLedgers = ledgerServiceRepo.getLedgers(custId);
                if (custLedgers != null && !custLedgers.isEmpty()) {
                    inv.setCust_name(trimSafe(custLedgers.get(0).getLedger_name()));
                }
            } catch (Exception e) {
                logger.warn("Could not resolve customer by id {} for inv_id {}", custId, inv.getInv_id(), e);
            }
        } else {
            // it's a name (or null). Keep whatever came from DB. Just trim trailing spaces.
            inv.setCust_name(trimSafe(inv.getCust_name()));
        }

        // ---- Service: treat value as ID only if it is numeric ----
        Integer svcId = toIntOrNull(inv.getService());
        if (svcId != null) {
            try {
                List<Account_ledger_v3> svcLedgers = ledgerServiceRepo.getLedgers(svcId);
                if (svcLedgers != null && !svcLedgers.isEmpty()) {
                    // If your dashboard wants the service NAME, map it; else keep id as string:
                    inv.setService(trimSafe(svcLedgers.get(0).getLedger_name()));
                }
            } catch (Exception e) {
                logger.warn("Could not resolve service by id {} for inv_id {}", svcId, inv.getInv_id(), e);
            }
        } else {
            // already a name like "consulting" or null. Just trim; don't parse.
            inv.setService(trimSafe(inv.getService()));
        }
/*
                try {
                    li1 = ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getCust_name()));
                    li2 = ledgerServiceRepo.getLedgers(Integer.parseInt(li.get(i).getService()));
                    if (li1.size() > 0) {
                        li.get(i).setCust_name(li1.get(0).getLedger_name());
                    }
                    if (li2.size() > 0) {
                        li.get(i).setService(li2.get(0).getLedger_name());
                    }
                } catch (NumberFormatException e) {
                    logger.error("Invalid cust_name or service ID: {}", li.get(i), e);
                }
*/

            }
        }
        return li;
    }

    public List<Invoice> invoiceDataByIds(String invoiceId) {
        try {
            int id = Integer.parseInt(invoiceId);
            List<Invoice> li = invoiceRepo.invoiceDataById(id);
            logger.info("Fetched invoice data by ID: {}, size: {}", invoiceId, li.size());
            return li;
        } catch (NumberFormatException e) {
            logger.error("Invalid invoiceId: {}", invoiceId, e);
            return List.of();
        }
    }

    public List<Invoice_sub> invoiceSubDataByIds(String invoiceId) {
        List<Invoice_sub> li = invoiceSubRepo.invoiceSubDataById(invoiceId);
        logger.info("Fetched invoice sub data by ID: {}, size: {}", invoiceId, li.size());
        return li;
    }

    public String invoiceSubDelete(String inv_id) {
        List<Invoice_sub> li = invoiceSubRepo.invoiceSubDataById(inv_id);
        logger.info("Deleting invoice sub data for ID: {}, size: {}", inv_id, li.size());
        for (Invoice_sub sub : li) {
            invoiceSubRepo.deleteById(sub.getInv_sub_id());
        }
        return "Deleted successfully";
    }

    public List<Invoice> invoiceDataByTransactionIds(String transactionId) {
        List<Invoice> li = invoiceRepo.invoiceDataByTransactionId(transactionId);
        logger.info("Fetched invoice data by transaction ID: {}, size: {}", transactionId, li.size());
        return li;
    }

    public String invoiceDelete(int inv_id) {
        logger.info("Deleting invoice ID: {}", inv_id);
        invoiceRepo.deleteById(inv_id);
        return "Deleted successfully";
    }
public Invoice update_invoices(Invoice fp) {
    logger.info("Updating invoice: {}", fp.getInv_id());
    // enforce existence
     invoiceRepo.findById(fp.getInv_id())
             .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + fp.getInv_id()));
    return invoiceRepo.save(fp);
}

public Invoice_sub update_invoice_sub(Invoice_sub sub) {
    // JPA save() updates when id exists
    return invoiceSubRepo.save(sub);
}

public void delete_invoice_sub(int inv_sub_id) {
    invoiceSubRepo.deleteById(inv_sub_id);
}

}