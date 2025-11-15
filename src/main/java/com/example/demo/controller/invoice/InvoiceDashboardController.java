package com.example.demo.controller.invoice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Legacy dashboard endpoint that does NOT depend on a Spring Data repository
 * and does NOT depend on specific getter names on the Invoice entity.
 */
@RestController
public class InvoiceDashboardController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceDashboardController.class);
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @PersistenceContext
    private EntityManager em;

    // Candidate property names we’ll try in order (getter or field) → legacy key
    private static final List<String[]> PROP_MAP = List.of(
            new String[]{"invId", "inv_id", "invoiceId", "id"},
            new String[]{"invDate", "invoiceDate", "date"},
            new String[]{"invNo", "invoiceNo", "number", "inv_no"},
            new String[]{"custName", "customerName", "customer", "name"},
            new String[]{"service", "serviceId", "serviceCode"},
            new String[]{"placeOfSupply", "place_of_supply"},
            new String[]{"billAddress", "billingAddress", "address", "bill_address"},
            new String[]{"gstNo", "gst", "gstno", "gst_no"},
            new String[]{"tdsRate", "tds", "tds_rate"},
            new String[]{"createdDate", "createdOn", "created_date"},
            new String[]{"createdTime", "created_time"},
            new String[]{"status"},
            new String[]{"totalAmount", "amountTotal", "total_amount"},
            new String[]{"totalTax", "taxTotal", "total_tax"},
            new String[]{"paymentMode", "payment_mode"},
            new String[]{"bankName", "bank", "bank_name"},
            new String[]{"paymentDate", "paidOn", "payment_date"},
            new String[]{"amountReceived", "receivedAmount", "amount_received"},
            new String[]{"invoiceTranId", "tranId", "txId", "invoice_tran_id"},
            new String[]{"swiftCode", "swift", "swift_code"},
            new String[]{"ifsc"}
    );

    @GetMapping(value = "/invoiceDataOnDashBoard", produces = "application/json")
    @Transactional
    public ResponseEntity<List<Map<String, Object>>> invoiceDataOnDashBoards() {
        log.info("Serving legacy /invoiceDataOnDashBoard (reflection/JPA fallback)");
        List<?> raw;
        try {
            // Try the canonical entity name/package first
            raw = em.createQuery("select i from com.example.demo.model.Invoice i").getResultList();
        } catch (Exception e1) {
            try {
                // Fallback to simple entity name if it’s annotated with @Entity(name="Invoice")
                raw = em.createQuery("select i from Invoice i").getResultList();
            } catch (Exception e2) {
                log.error("Failed to fetch invoices via JPA", e2);
                return ResponseEntity.ok(Collections.emptyList());
            }
        }

        if (raw == null || raw.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Map<String, Object>> out = new ArrayList<>(raw.size());
        for (Object inv : raw) {
            out.add(toLegacyRow(inv));
        }
        return ResponseEntity.ok(out);
    }

    private Map<String, Object> toLegacyRow(Object inv) {
        Map<String, Object> m = new LinkedHashMap<>();

        // inv_id (numeric/string passthrough)
        m.put("inv_id", firstNonNull(
                read(inv, "invId", "inv_id", "invoiceId", "id"),
                ""
        ));

        // Dates normalized to "yyyy-MM-dd" or ""
        m.put("inv_date", formatDate(read(inv, "invDate", "invoiceDate", "date")));
        m.put("created_date", formatDate(read(inv, "createdDate", "createdOn", "created_date")));
        m.put("created_time", nvl(read(inv, "createdTime", "created_time")));

        // Strings (null -> "")
        m.put("inv_no", nvl(read(inv, "invNo", "invoiceNo", "number", "inv_no")));
        m.put("cust_name", nvl(read(inv, "custName", "customerName", "customer", "name")));
        m.put("service", nvl(read(inv, "service", "serviceId", "serviceCode")));
        m.put("place_of_supply", nvl(read(inv, "placeOfSupply", "place_of_supply")));
        m.put("bill_address", nvl(read(inv, "billAddress", "billingAddress", "address", "bill_address")));
        m.put("gst_no", nvl(read(inv, "gstNo", "gst", "gstno", "gst_no")));
        m.put("tds_rate", nvl(read(inv, "tdsRate", "tds", "tds_rate")));
        m.put("status", nvl(read(inv, "status")));
        m.put("total_amount", nvl(read(inv, "totalAmount", "amountTotal", "total_amount")));
        m.put("total_tax", nvl(read(inv, "totalTax", "taxTotal", "total_tax")));
        m.put("payment_mode", nvl(read(inv, "paymentMode", "payment_mode")));
        m.put("bank_name", nvl(read(inv, "bankName", "bank", "bank_name")));
        m.put("payment_date", nvl(formatDate(read(inv, "paymentDate", "paidOn", "payment_date"))));
        m.put("amount_received", nvl(read(inv, "amountReceived", "receivedAmount", "amount_received")));
        m.put("invoice_tran_id", nvl(read(inv, "invoiceTranId", "tranId", "txId", "invoice_tran_id")));
        m.put("swift_code", nvl(read(inv, "swiftCode", "swift", "swift_code")));
        m.put("ifsc", nvl(read(inv, "ifsc")));

        return m;
    }

    private Object read(Object bean, String... candidates) {
        for (String name : candidates) {
            // Try getter first
            String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            try {
                Method m = bean.getClass().getMethod(getter);
                m.setAccessible(true);
                Object v = m.invoke(bean);
                if (v != null) return v;
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                log.debug("Getter access failed: {}", getter, e);
            }
            // Try boolean-style isX
            String isser = "is" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            try {
                Method m = bean.getClass().getMethod(isser);
                m.setAccessible(true);
                Object v = m.invoke(bean);
                if (v != null) return v;
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                log.debug("Isser access failed: {}", isser, e);
            }
            // Try direct field
            try {
                Field f = bean.getClass().getDeclaredField(name);
                f.setAccessible(true);
                Object v = f.get(bean);
                if (v != null) return v;
            } catch (NoSuchFieldException ignored) {
            } catch (Exception e) {
                log.debug("Field access failed: {}", name, e);
            }
        }
        return null;
    }

    private String formatDate(Object v) {
        if (v == null) return "";
        try {
            if (v instanceof java.time.LocalDate d) {
                return d.format(DTF);
            }
            if (v instanceof java.time.LocalDateTime dt) {
                return dt.toLocalDate().format(DTF);
            }
            if (v instanceof java.util.Date d2) {
                return SDF.format(d2);
            }
            // If already String
            return Objects.toString(v, "");
        } catch (Exception e) {
            log.warn("Date format failed for value: {}", v, e);
            return "";
        }
        }
    private String nvl(Object v) {
        return v == null ? "" : String.valueOf(v);
    }

    private Object firstNonNull(Object v, Object fallback) {
        return v != null ? v : fallback;
    }
}
