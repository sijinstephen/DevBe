USE devbe;

-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `inv_id` int(11) NOT NULL,
  `bill_address` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `gst_no` varchar(255) DEFAULT NULL,
  `inv_date` varchar(255) DEFAULT NULL,
  `inv_no` varchar(255) DEFAULT NULL,
  `place_of_supply` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tds_rate` varchar(255) DEFAULT NULL,
  `total_amount` varchar(255) DEFAULT NULL,
  `total_tax` varchar(255) DEFAULT NULL,
  `amount_received` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `payment_date` varchar(255) DEFAULT NULL,
  `payment_mode` varchar(255) DEFAULT NULL,
  `invoice_tran_id` varchar(255) DEFAULT NULL,
  `swift_code` varchar(255) DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`inv_id`, `bill_address`, `created_date`, `created_time`, `cust_name`, `gst_no`, `inv_date`, `inv_no`, `place_of_supply`, `service`, `status`, `tds_rate`, `total_amount`, `total_tax`, `amount_received`, `bank_name`, `payment_date`, `payment_mode`, `invoice_tran_id`, `swift_code`, `ifsc`, `company_name`, `cust_id`) VALUES
(931, 'angamaly', '2022-04-26', '10:31:54', '751', '32BBBB1254A1ZX', '2022-04-26', '20180410089', 'IntraState', '752', 'paid', '9000', '50000', '9000', '50000', '', '2022-04-26', 'cash', 'a724f56-b8-65d3-6be6-cb1826852471', NULL, NULL, 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(933, 'Kalyan Developers ,\nTC 32/204/1, Sitaram Mill Road\nPunkunnam\nThrissur, Kerala 680002   ', '2022-04-28', '11:49:21', '754', '06AACCG0527D1Z8', '2022-04-28', '34566666', 'IntraState', '752', 'pending', '100', '5000', '900', '0', 'null', 'null', 'null', 'ba01bd5-761-7b-2e3-51bc70e31554', '', 'ICICI000742', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2');

-- --------------------------------------------------------

--
