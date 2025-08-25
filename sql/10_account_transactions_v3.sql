USE devbe;

-- Table structure for table `account_transactions_v3`
--

CREATE TABLE `account_transactions_v3` (
  `tranid` int(11) NOT NULL,
  `ac_no` varchar(255) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `chq_date` varchar(255) DEFAULT NULL,
  `chq_no` varchar(255) DEFAULT NULL,
  `crdt_ac` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `credit_blnc_bfore_txn` varchar(255) DEFAULT NULL,
  `dbt_ac` varchar(255) DEFAULT NULL,
  `debit_blnc_bfore_txn` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filepath` varchar(255) DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tran_date` varchar(255) DEFAULT NULL,
  `tran_gen` varchar(255) DEFAULT NULL,
  `transactionid` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_bank` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_transactions_v3`
--

INSERT INTO `account_transactions_v3` (`tranid`, `ac_no`, `amount`, `bank`, `branch`, `chq_date`, `chq_no`, `crdt_ac`, `created_by`, `created_date`, `created_time`, `credit_blnc_bfore_txn`, `dbt_ac`, `debit_blnc_bfore_txn`, `description`, `filename`, `filepath`, `mode`, `status`, `tran_date`, `tran_gen`, `transactionid`, `type`, `user_bank`, `company_name`, `cust_id`) VALUES
(1189, 'Nil', '10000', 'Nil', 'Nil', 'Nil', 'Nil', '31', '', '2022-04-25', '16:04:48', NULL, '749', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-25', NULL, '8500f54-a47c-1a7-0fe3-6d6b58f08df', 'Nil', 'Nil', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3'),
(1190, 'Nil', '100000', 'Nil', 'Nil', 'Nil', 'Nil', 'Nil', '', '2022-04-26', '10:18:38', NULL, 'Nil', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-26', NULL, '83c657-0bf2-c7-5d16-613a86367d87', 'Nil', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1191, '', '50000', '', '', '', '', '752', '', '2022-04-26', '10:25:29', NULL, '751', NULL, 'Invoice', 'Nil', '', '', '1', '2022-04-26', 'a724f56-b8-65d3-6be6-cb1826852471', 'a70adb4-ba2e-606f-4a8a-683c0b184fe', 'Contra', '', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1192, '', '9000', '', '', '', '', '744', '', '2022-04-26', '10:25:29', NULL, '751', NULL, 'GST charges', 'Nil', '', '', '1', '2022-04-26', 'a724f56-b8-65d3-6be6-cb1826852471', 'd2e8fa0-58ae-c8ff-3bac-030040c8150', 'Contra', '', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1193, '', '9000', '', '', '', '', '751', '', '2022-04-26', '10:25:29', NULL, '745', NULL, 'TDS Deducted', 'Nil', '', '', '1', '2022-04-26', 'a724f56-b8-65d3-6be6-cb1826852471', '0433eec-1020-8f3c-eee7-c5e44af1fd85', 'Contra', '', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1194, 'Nil', '50000', '', 'Nil', '', '', '751', '', '2022-04-26', '10:31:54', NULL, '743', NULL, 'Received', 'Nil', '', 'cash', 'Recieve', '2022-04-26', 'a724f56-b8-65d3-6be6-cb1826852471', '4dedb5-3b11-c8fa-47e2-a41fb6d342f', 'Receipt', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1195, 'Nil', '1030', 'Nil', 'Nil', 'Nil', 'Nil', 'Nil', '', '2022-04-26', '10:35:40', NULL, 'Nil', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-26', NULL, '43a3ed6-cb17-cc01-f2e0-47e0ec747466', 'Nil', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1196, 'Nil', '50000', 'Nil', 'Nil', 'Nil', 'Nil', '755', '', '2022-04-27', '11:12:25', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '6aa831-4274-5018-ca08-881e655d3e2', 'Nil', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1197, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '756', '', '2022-04-27', '11:44:42', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '446c01-cb61-4f0f-8e0b-873a15477ae', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1198, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '757', '', '2022-04-27', '11:54:12', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'a8287d0-484-db2-d052-02ae1e20bf', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1199, 'Nil', '2000', '', 'Nil', '', '', '757', '', '2022-04-27', '11:55:02', NULL, '743', NULL, 'receipt2000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, '2632d18-5acf-bc8d-6e67-757d7d3bfe3', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1200, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '758', '', '2022-04-27', '12:06:03', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'a7da8-3183-002-d6f5-da1a4a4001b2', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1201, 'Nil', '2000', '', 'Nil', '', '', '758', '', '2022-04-27', '12:06:40', NULL, '743', NULL, 'Receipt2022-04-27T12:06:36.9530066002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, '85005-1285-e24-8fd5-777f6eec45f3', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1202, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '759', '', '2022-04-27', '12:45:40', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'e131056-db4d-5385-3001-852265a1075', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1203, 'Nil', '2000', '', 'Nil', '', '', '759', '', '2022-04-27', '12:46:15', NULL, '743', NULL, 'Receipt2022-04-27T12:46:11.5836306002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, 'd215827-0ed0-7388-d034-8d8bb86cab4e', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1204, 'Nil', '150000', 'Nil', 'Nil', 'Nil', 'Nil', '760', '', '2022-04-27', '12:53:07', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '87dc05e-5fe8-04f-b8cf-8637185308fa', 'Nil', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1205, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '761', '', '2022-04-27', '12:59:27', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '3620f7e-06b1-230b-dbea-d0c1f1b326ac', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1206, 'Nil', '2000', '', 'Nil', '', '', '761', '', '2022-04-27', '13:00:02', NULL, '743', NULL, 'Receipt2022-04-27T12:59:59.0401573002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, '4e2227-12d6-67c-7c74-451266105d37', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1207, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '762', '', '2022-04-27', '13:11:42', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'dee07b5-735f-aa3-ff2b-2c2f235b31c3', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1208, 'Nil', '2000', '', 'Nil', '', '', '762', '', '2022-04-27', '13:12:17', NULL, '743', NULL, 'Receipt2022-04-27T13:12:13.6255299002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, '408cf1-6a5d-3605-3725-1cbe5b31142d', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1209, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '763', '', '2022-04-27', '15:44:51', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '1d1eab1-3f68-5c2f-b546-d37b375ecff2', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1210, 'Nil', '2000', '', 'Nil', '', '', '763', '', '2022-04-27', '15:45:27', NULL, '743', NULL, 'Receipt2022-04-27T15:45:23.4225018002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, 'f67b6e4-1e8d-cb84-8d1b-fec70bf74c6', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1211, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '764', '', '2022-04-27', '15:53:50', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '8740f-e1b-8440-8a2d-e1b0fb16bdd8', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1212, 'Nil', '2000', '', 'Nil', '', '', '764', '', '2022-04-27', '15:54:25', NULL, '743', NULL, 'Receipt2022-04-27T15:54:21.8790276002000', 'Nil', 'Nil', 'cash', 'Recieve', '2022-04-27', NULL, 'e40a48d-d720-2b0e-bb5b-ee04df0026c2', 'Receipt', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1213, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '765', '', '2022-04-27', '16:06:42', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '8f60ddf-8131-de84-863-e212da358bf', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1214, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-27', '16:07:17', NULL, '765', NULL, 'Payment2022-04-27T16:07:13.6567464002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-27', NULL, '222fc3e-281-446-2ffd-0a5d0261c71', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1215, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '766', '', '2022-04-27', '16:42:53', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'a8e166-a8fe-b046-c44c-18f00b1be71a', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1216, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-27', '16:43:28', NULL, '766', NULL, 'Payment2022-04-27T16:43:25.2153452002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-27', NULL, 'd8a4577-713e-c75-3bbc-428f2c1037', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1217, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '767', '', '2022-04-27', '17:01:03', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '68f8d20-a087-75-b18e-5682424b6626', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1218, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-27', '17:01:39', NULL, '767', NULL, 'Payment2022-04-27T17:01:35.4295207002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-27', NULL, '64d878b-23f5-1cf5-44ea-ce4aec3e03', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1219, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '768', '', '2022-04-27', '17:21:06', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, 'a82b4b1-a77-f0df-e5cb-836410ccb116', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1220, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-27', '17:21:40', NULL, '768', NULL, 'Payment2022-04-27T17:21:37.5403685002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-27', NULL, '0e3b2e-64e1-15e-8bef-4b820a71ba4', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1221, 'Nil', '10000', 'Nil', 'Nil', 'Nil', 'Nil', '769', '', '2022-04-27', '17:39:41', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-27', NULL, '7f0007-6e7c-501-d38e-cfc510b7e08', 'Nil', 'Nil', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(1222, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '770', '', '2022-04-28', '11:35:42', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, '07803ec-aac4-dfd-dee-745d51e2c4', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1223, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-28', '11:36:18', NULL, '770', NULL, 'Payment2022-04-28T11:36:14.6616912002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-28', NULL, '5c7f00b-c34d-db68-f480-548ed5f5d42', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1224, '', '5000', '', '', '', '', '752', '', '2022-04-28', '11:49:32', NULL, '754', NULL, 'Invoice', 'Nil', '', '', '1', '2022-04-28', 'ba01bd5-761-7b-2e3-51bc70e31554', 'ba7f808-6bdd-f1a1-f4ac-2b17b43f44ab', 'Contra', '', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1225, '', '100', '', '', '', '', '754', '', '2022-04-28', '11:49:32', NULL, '745', NULL, 'TDS Deducted', 'Nil', '', '', '1', '2022-04-28', 'ba01bd5-761-7b-2e3-51bc70e31554', '203eed2-23-26f2-c6a5-6d6b73d776a', 'Contra', '', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1226, '', '900', '', '', '', '', '744', '', '2022-04-28', '11:49:32', NULL, '754', NULL, 'GST charges', 'Nil', '', '', '1', '2022-04-28', 'ba01bd5-761-7b-2e3-51bc70e31554', 'd701bb8-a6a-acc-c1b1-4c6e0b7a7a', 'Contra', '', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1227, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '771', '', '2022-04-28', '11:52:08', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, '82665e0-1ffa-e633-a244-e600d6680b7', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1228, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-28', '11:52:48', NULL, '771', NULL, 'Payment2022-04-28T11:52:43.6006832000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-28', NULL, 'f0bff6-3350-4fda-2a-0cc7024a4f46', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1229, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '772', '', '2022-04-28', '11:57:11', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'fc13e5e-826-a81-2b45-d0837bb6ba4c', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1230, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-28', '11:57:47', NULL, '772', NULL, 'Payment2022-04-28T11:57:43.8192442002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-28', NULL, 'e807df-c7-cf24-32e3-7cec7ad0582', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1231, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '773', '', '2022-04-28', '12:11:05', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'ffe6f02-31f5-4cb1-56ca-abf8beeef0c', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1232, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-28', '12:11:42', NULL, '773', NULL, 'Payment2022-04-28T12:11:38.4320532000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-28', NULL, '0716dc-46ae-a302-ab1-1fe33edd57e', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1233, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '774', '', '2022-04-28', '12:22:25', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'dfd60eb-03c1-1f70-861e-ce360bf8ea', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1234, 'Nil', '2000', '', 'Nil', '', '', '743', '', '2022-04-28', '12:23:01', NULL, '774', NULL, 'Payment2022-04-28T12:22:57.6309242002000', 'Nil', 'Nil', 'cash', 'Pay_Now', '2022-04-28', NULL, '41c827e-78aa-e3b-b203-abcf63787a76', 'Voucher', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1235, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '775', '', '2022-04-28', '14:47:51', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, '1a0256-b3ae-031-2365-a8ff42008b8', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1236, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '776', '', '2022-04-28', '14:51:03', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'a5a3bcf-fa43-4255-43cc-761b75c80b6', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1237, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '777', '', '2022-04-28', '14:51:32', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, '5b2af0d-1b7d-3d23-c7c-abb6b2ac80ad', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1238, '', '100000', '', '', '', '', '776', '', '2022-04-28', '14:52:16', NULL, '777', NULL, 'Journal2022-04-28T14:52:13.377042700100000', 'Nil', 'Nil', '', 'paid', '2022-04-28', NULL, '05e2fa-148f-4bb6-b635-4d44ecaeb16', 'Contra', '', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1239, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '778', '', '2022-04-28', '15:19:08', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'cf46861-7708-8ffd-36c0-ee6b51742e', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1240, 'Nil', '1000', 'Nil', 'Nil', 'Nil', 'Nil', '779', '', '2022-04-28', '15:19:36', NULL, '31', NULL, 'ledger creation', 'Nil', 'Nil', 'Nil', 'Pay_Now', '2022-04-28', NULL, 'cc21e3-24f-ce1d-83d0-881bf3fb48e1', 'Nil', 'Nil', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(1241, '', '100000', '', '', '', '', '778', '', '2022-04-28', '15:20:20', NULL, '779', NULL, 'Journal2022-04-28T15:20:17.635650800100000', 'Nil', 'Nil', '', 'paid', '2022-04-28', NULL, '467bb1-35d5-36aa-7dcd-5c18b607176', 'Contra', '', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2');

-- --------------------------------------------------------

--
