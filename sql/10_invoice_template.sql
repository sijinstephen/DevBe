USE devbe;

-- Table structure for table `invoice_template`
--

CREATE TABLE `invoice_template` (
  `template_id` int(11) NOT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `template_company_address` varchar(255) DEFAULT NULL,
  `template_company_contact` varchar(255) DEFAULT NULL,
  `template_company_name` varchar(255) DEFAULT NULL,
  `template_logo` varchar(255) DEFAULT NULL,
  `template_pay_to` varchar(255) DEFAULT NULL,
  `template_sig` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice_template`
--

INSERT INTO `invoice_template` (`template_id`, `template_name`, `template_company_address`, `template_company_contact`, `template_company_name`, `template_logo`, `template_pay_to`, `template_sig`, `company_name`, `cust_id`) VALUES
(923, 'Sijin Stephen\nMANAGING DIRECTOR ', 'Third Floor, Kolenchery Tower, Angamaly -683572,Kerala,India\n', '|India-+919544702277', 'INTUISYZ TECHNOLOGIES PRIVATE LIMITED ', 'logo.jpg intuisyz (1).jpg', 'INTUISYZ TECHNOLOGIES PRIVATE LIMITED \nA/c No:\n,,\nIFSC:\nSwift Code:\nPAN NO:AADC16383A\n', 'Screenshot (398).png sign.png', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(929, '\n', '\n', '|India-+91', 'BejoyCmpy', 'Artboard 1 (1).png', 'BejoyCmpy\nA/c No:\n,,\nIFSC:\nSwift Code:\nPAN NO:\n', 'sijin-sign85 (1) (1).jpg', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3');

-- --------------------------------------------------------

--
