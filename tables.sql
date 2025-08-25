-- Create and select the database
CREATE DATABASE IF NOT EXISTS devbe
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
USE devbe;

-- Speed up bulk load
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_MODE='';

-- === Your original dump goes here ===
-- Tip: you can paste the contents of your existing db_init.sql below,
-- or run it separately with `mysql devbe < db_init.sql`

/* ============= OPTIONAL: keys & charset upgrades =============
   Enable if you want proper primary keys + AUTO_INCREMENT and utf8mb4 everywhere.
   Safe even with existing INSERTs that set explicit ids. */

-- Keys for core tables from your dump:
ALTER TABLE account_group_v3
  ADD PRIMARY KEY (group_id),
  MODIFY group_id INT NOT NULL AUTO_INCREMENT;            -- table defined in your dump. :contentReference[oaicite:0]{index=0}

ALTER TABLE account_title_v3
  ADD PRIMARY KEY (ac_id),
  MODIFY ac_id INT NOT NULL AUTO_INCREMENT;               -- table defined in your dump. :contentReference[oaicite:1]{index=1}

ALTER TABLE account_ledger_v3
  ADD PRIMARY KEY (id),
  MODIFY id INT NOT NULL AUTO_INCREMENT;                  -- table defined in your dump. :contentReference[oaicite:2]{index=2}

ALTER TABLE account_transactions_v3
  ADD PRIMARY KEY (tranid),
  MODIFY tranid INT NOT NULL AUTO_INCREMENT;              -- table defined in your dump. :contentReference[oaicite:3]{index=3}

ALTER TABLE invoice
  ADD PRIMARY KEY (inv_id),
  MODIFY inv_id INT NOT NULL AUTO_INCREMENT;              -- table defined in your dump. :contentReference[oaicite:4]{index=4}

ALTER TABLE invoice_sub
  ADD PRIMARY KEY (inv_sub_id),
  MODIFY inv_sub_id INT NOT NULL AUTO_INCREMENT;          -- table defined in your dump. :contentReference[oaicite:5]{index=5}

ALTER TABLE account_pending_v3
  ADD PRIMARY KEY (pendID),
  MODIFY pendID INT NOT NULL AUTO_INCREMENT;              -- table defined in your dump. :contentReference[oaicite:6]{index=6}

ALTER TABLE company
  ADD PRIMARY KEY (company_id),
  MODIFY company_id INT NOT NULL AUTO_INCREMENT;          -- table defined in your dump. :contentReference[oaicite:7]{index=7}

-- Charset upgrades for tables created as latin1 in the dump:
ALTER TABLE account_ledger_v3      CONVERT TO CHARACTER SET utf8mb4;  -- was latin1 :contentReference[oaicite:8]{index=8}
ALTER TABLE invoice_sub            CONVERT TO CHARACTER SET utf8mb4;  -- was latin1 :contentReference[oaicite:9]{index=9}
ALTER TABLE account_pending_v3     CONVERT TO CHARACTER SET utf8mb4;  -- was latin1 :contentReference[oaicite:10]{index=10}
ALTER TABLE company                CONVERT TO CHARACTER SET utf8mb4;  -- was latin1 :contentReference[oaicite:11]{index=11}
-- (tables created with utf8 in the dump—e.g., account_group_v3—can be left or also upgraded)

-- =============================================================

-- Re-enable FK checks
SET FOREIGN_KEY_CHECKS = 1;
