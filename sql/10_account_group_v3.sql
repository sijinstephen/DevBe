USE devbe;

-- Table structure for table `account_group_v3`
--

CREATE TABLE `account_group_v3` (
  `group_id` int(11) NOT NULL,
  `ac_title` varchar(255) DEFAULT NULL,
  `ac_type` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL,
  `visibility` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account_group_v3`
--

INSERT INTO `account_group_v3` (`group_id`, `ac_title`, `ac_type`, `created_date`, `group_name`, `time`, `company_name`, `cust_id`, `visibility`) VALUES
(1, '3', '2', '2017-11-20', 'sundry creditors', '10:28:26 AM', NULL, NULL, NULL),
(3, '1', '1', '2021-05-12', 'Bank ', '03:16:42 PM', NULL, NULL, 'All'),
(4, '1', '1', '2021-09-16', 'Customer', '02:24:28', NULL, NULL, 'All'),
(5, '6', '3', '2021-09-16', 'Service', '02:24:48', NULL, NULL, 'All'),
(6, '1', '1', '2021-09-16', 'Cash in Hand', '02:24:48', NULL, NULL, 'All'),
(7, '1', '1', '2021-09-16', 'Tax Credit', '02:24:48', NULL, NULL, 'All'),
(8, '3', '2', '2021-09-16', 'Duties & Taxes', '02:24:48', NULL, NULL, 'All'),
(97, '7', '4', '2017-11-18', 'Direct Expenses', '05:37:23 PM', NULL, NULL, NULL),
(99, '1', '1', '2017-11-18', 'Current Asset', '05:38:56 PM', NULL, NULL, NULL),
(109, '9', '4', '2017-11-18', 'Indirect Expense', '05:36:13 PM', NULL, NULL, NULL),
(121, '1', '1', '2016-10-21', 'Cash in Hand', '08:40:07 AM', NULL, NULL, NULL),
(137, '6', '3', '2017-11-18', 'Sales', '05:38:04 PM', NULL, NULL, NULL),
(138, '1', '1', '29-07-2017', 'Suspense Account', '05:08:13 PM', NULL, NULL, NULL),
(139, '3', '2', '2017-11-18', 'Current Liability', '05:33:47 PM', NULL, NULL, NULL),
(140, '3', '2', '2017-09-08', 'Duties & Taxes', '03:16:43 PM', NULL, NULL, NULL),
(141, '10', '2', '20-11-2017', 'Capital account', '10:36:25 AM', NULL, NULL, NULL),
(142, '6', '3', '20-11-2017', 'Direct Income', '10:37:22 AM', NULL, NULL, NULL),
(143, '8', '3', '20-11-2017', 'Indirect Income', '10:39:34 AM', NULL, NULL, NULL),
(144, '1', '1', '20-11-2017', 'Investment', '10:48:02 AM', NULL, NULL, NULL),
(146, '9', '4', '05-12-2017', 'Server Expense', '05:05:08 PM', NULL, NULL, NULL),
(147, '2', '1', '07-07-2018', 'Fixed Asset', '09:45:57 PM', NULL, NULL, NULL),
(148, '6', '3', '2020-06-23', 'SUSPENSE ACCOUNT FOR CASH', '11:22:04 AM', NULL, NULL, NULL),
(149, '1', '1', '24-06-2020', 'Deposits', '10:11:20 AM', NULL, NULL, NULL),
(150, '3', '2', '26-06-2020', 'Salary ', '01:17:42 PM', NULL, NULL, NULL),
(151, '3', '2', '26-06-2020', 'Salary payable', '01:18:12 PM', NULL, NULL, NULL),
(153, '1', '1', '09-07-2020', 'Sundry debtors', '05:38:27 PM', NULL, NULL, NULL),
(157, '8', '3', '09-07-2020', 'Suspense Account For Bank', '06:12:41 PM', NULL, NULL, NULL),
(158, '9', '4', '10-07-2020', 'Transportation Charges', '12:20:03 PM', NULL, NULL, NULL),
(352, '6', '3', '2021-10-12', 'App Dev', '12:44:38', NULL, NULL, NULL),
(353, '7', '4', '2021-10-12', 'Contract Payments', '12:46:52', NULL, NULL, NULL),
(354, '7', '4', '2021-10-12', 'Employee Salary', '12:48:13', NULL, NULL, NULL),
(355, '4', '2', '2021-10-12', 'Loan', '12:50:21', NULL, NULL, NULL),
(356, '4', '2', '2021-10-12', 'Long Laibility', '12:50:43', NULL, NULL, NULL),
(357, '3', '2', '2021-10-12', 'Rent', '12:51:11', NULL, NULL, NULL),
(358, '7', '4', '2021-10-12', 'Rent Expense', '12:51:42', NULL, NULL, NULL),
(359, '7', '4', '2021-10-12', 'Salary Expense', '12:53:22', NULL, NULL, NULL),
(360, '6', '3', '2021-10-12', 'SEO', '12:54:02', NULL, NULL, NULL),
(361, '3', '2', '2021-10-12', 'Staff', '12:54:44', NULL, NULL, NULL),
(363, '1', '1', '2021-10-20', 'Tax Credit', '19:24:53', NULL, NULL, NULL),
(365, '4', '2', '2021-11-29', 'profit and loss', '14:03:21', NULL, NULL, NULL),
(385, '4', '2', '2022-04-26', 'Loans', '10:18:02', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d', NULL),
(386, '1', '1', '2022-04-26', 'sundry debtor', '10:35:18', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d', NULL),
(387, '3', '2', '2022-04-27', 'Sundry Creditors', '11:51:54', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2', NULL),
(388, '9', '4', '2022-04-27', 'Indirect Expenses', '15:28:00', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2', NULL),
(389, '2', '1', '2022-04-28', 'Land', '14:30:12', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2', NULL);

-- --------------------------------------------------------

--
