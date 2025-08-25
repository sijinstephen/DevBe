USE devbe;

-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `organization_id` int(11) NOT NULL,
  `acc_no` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `date_format` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fiscal_year` varchar(255) DEFAULT NULL,
  `gmail` varchar(255) DEFAULT NULL,
  `gst_id` varchar(255) DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `organization_name` varchar(255) DEFAULT NULL,
  `pan_no` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `signatory_designation` varchar(255) DEFAULT NULL,
  `signatory_name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street1` varchar(255) DEFAULT NULL,
  `street2` varchar(255) DEFAULT NULL,
  `swift_code` varchar(255) DEFAULT NULL,
  `time_zone` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`organization_id`, `acc_no`, `bank`, `branch`, `city`, `company_id`, `date_format`, `fax`, `fiscal_year`, `gmail`, `gst_id`, `ifsc`, `industry`, `location`, `organization_name`, `pan_no`, `phone`, `signatory_designation`, `signatory_name`, `state`, `street1`, `street2`, `swift_code`, `time_zone`, `website`, `zip`, `company_name`, `cust_id`) VALUES
(924, '', '', '', 'ERNAKULAM', '', 'MM-dd-yy', '', 'April - March', '', '32AADCI6383A1ZY', '', 'Technology', 'India', 'INTUISYZ TECHNOLOGIES PRIVATE LIMITED ', 'AADC16383A', '9544702277', 'MANAGING DIRECTOR ', 'Sijin Stephen', 'Kerala', 'Third Floor, Kolenchery Tower, Angamaly -683572,Kerala,India', '', '', '(GMT 5:30) India Standard Time', 'MGJ', '683572', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(928, '', '', '', '', '', '', '', '', '', '', '', 'Financial Services', 'India', 'BejoyCmpy', '', '', '', '', 'Punjab', '', '', '', '(GMT 5:30) India Standard Time', '', '', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3');

-- --------------------------------------------------------

--
