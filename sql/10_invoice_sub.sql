USE devbe;

-- Table structure for table `invoice_sub`
--

CREATE TABLE `invoice_sub` (
  `inv_sub_id` int(11) NOT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `created_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hsn` varchar(255) DEFAULT NULL,
  `inv_id` varchar(255) DEFAULT NULL,
  `qty` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `tax` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice_sub`
--

INSERT INTO `invoice_sub` (`inv_sub_id`, `amount`, `created_date`, `created_time`, `description`, `hsn`, `inv_id`, `qty`, `remarks`, `tax`) VALUES
(932, '50000', '2022-04-26', '10:25:28', 'WEBSITE LAYOUT ', '998313', '931', '1', '', '18'),
(934, '5000', '2022-04-28', '11:49:32', 'Google Ad ', '998313', '933', '1', '', '18');

-- --------------------------------------------------------

--
