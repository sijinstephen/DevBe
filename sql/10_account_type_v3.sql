USE devbe;

-- Table structure for table `account_type_v3`
--

CREATE TABLE `account_type_v3` (
  `ac_type_id` int(11) NOT NULL,
  `ac_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account_type_v3`
--

INSERT INTO `account_type_v3` (`ac_type_id`, `ac_type`) VALUES
(1, 'Asset'),
(2, 'Liability'),
(3, 'Income'),
(4, 'Expense');

-- --------------------------------------------------------

--
