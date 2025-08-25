USE devbe;

-- Table structure for table `account_title_v3`
--

CREATE TABLE `account_title_v3` (
  `ac_id` int(11) NOT NULL,
  `ac_title` varchar(255) DEFAULT NULL,
  `ac_type` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account_title_v3`
--

INSERT INTO `account_title_v3` (`ac_id`, `ac_title`, `ac_type`, `created_date`, `time`) VALUES
(1, 'Current Asset', '1', '	\r\n2015-03-26', NULL),
(2, 'Fixed Asset', '1', '2015-03-26', NULL),
(3, 'Current Liability', '2', '2015-03-26', NULL),
(4, 'Fixed Liability', '2', '2015-03-26', NULL),
(5, 'Loans', '2', '2015-03-26', NULL),
(6, 'Direct Income', '3', '2015-03-26', NULL),
(7, 'Direct Expense', '4', '2015-03-26', NULL),
(8, 'Indirect Income', '3', '2015-03-26', NULL),
(9, 'Primary Expense', '4', '2015-03-26', NULL),
(10, 'Capital Account', '2', '2015-03-26', NULL);

-- --------------------------------------------------------

--
