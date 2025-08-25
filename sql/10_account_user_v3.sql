USE devbe;

-- Table structure for table `account_user_v3`
--

CREATE TABLE `account_user_v3` (
  `id` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `cash_id` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL,
  `gst_id` varchar(255) DEFAULT NULL,
  `tds_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_user_v3`
--

INSERT INTO `account_user_v3` (`id`, `password`, `user_id`, `user_name`, `company_name`, `user_type`, `cash_id`, `cust_id`, `gst_id`, `tds_id`) VALUES
(1, 'U2FsdGVkX19kktP7CqGszwfkbFoz9OtIT66XDlrCREw=', NULL, 'Sherin', '', 'Admin', NULL, 'b04fdeda-0457-4416-9434-82f3867d667c', NULL, NULL),
(925, 'U2FsdGVkX18vqtOf82WVgayf0Ma3xLKOwogNgNNAAKg=', NULL, 'Chinchu', 'Intuisyz', 'User', '743', '6b7aad-15ca-bdc8-f266-e673d7e33f2', '744', '745'),
(926, 'U2FsdGVkX1+u4WNCFBuG0PALZu6vooaGMW9mOdENgdw=', NULL, 'Teena', 'Intuisyz', 'User', '743', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d', '744', '745'),
(930, 'U2FsdGVkX19keoWEKlfzEF+Gv4jmovX/YDrVdvK7ISA=', NULL, 'Bejoys', 'BejoyCmpy', 'User', '746', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3', '747', '748');

-- --------------------------------------------------------

--
