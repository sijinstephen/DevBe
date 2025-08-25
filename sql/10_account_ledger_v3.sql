USE devbe;

-- Table structure for table `account_ledger_v3`
--

CREATE TABLE `account_ledger_v3` (
  `id` int(11) NOT NULL,
  `ac_group` varchar(255) DEFAULT NULL,
  `ac_title` varchar(255) DEFAULT NULL,
  `ac_type` varchar(255) DEFAULT NULL,
  `acc_number` varchar(255) DEFAULT NULL,
  `account_id` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `balance_type` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `branch` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `ledger_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `open_balance` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  `ledger_date` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cust_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_ledger_v3`
--

INSERT INTO `account_ledger_v3` (`id`, `ac_group`, `ac_title`, `ac_type`, `acc_number`, `account_id`, `address`, `amount`, `balance_type`, `bank`, `branch`, `contact`, `created_date`, `email`, `fax`, `ifsc_code`, `ledger_name`, `mobile`, `name`, `open_balance`, `pin`, `state`, `time`, `userid`, `ledger_date`, `company_name`, `cust_id`) VALUES
(30, '121', '1', '1', ' ', NULL, ' ', '0.0', 'debit', 'Nil', 'Nil', ' ', '2022-03-29', ' ', ' ', ' ', 'Cash', ' ', ' ', '15', ' ', ' ', '17:17:18', NULL, '2018-04-01', NULL, NULL),
(32, '140', '3', '2', 'Nil', NULL, ' ', '0.0', 'credit', 'Nil', 'Nil', ' ', '2022-03-16', ' ', ' ', 'Nil', 'GST ', ' ', ' ', '299475.44', ' ', ' ', '17:18:48', NULL, '2018-04-01', NULL, NULL),
(33, '363', '1', '1', 'Nil', NULL, ' ', '0.0', 'debit', 'Nil', 'Nil', ' ', '2022-03-16', ' ', ' ', 'Nil', 'TDS Receivable', ' ', ' ', '125926', ' ', ' ', '17:18:48', NULL, '2018-04-01', NULL, NULL),
(743, '6', '1', '1', '', NULL, '', '46000.0', 'debit', 'Nil', 'Nil', '', '2022-04-28', '', '', '', 'Cash', '', '', '0', '', '', '12:23:01', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(744, '8', '3', '2', 'Nil', NULL, '', '9900.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'GST Payable', '', '', '0', '', '', '11:49:32', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(745, '7', '1', '1', 'Nil', NULL, '', '9100.0', 'debit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'TDS Receivable', '', '', '0', '', '', '11:49:32', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(746, '6', '1', '1', '', NULL, '', '0.0', 'debit', 'Nil', 'Nil', '', '2022-04-25', '', '', '', 'Cash', '', '', '0', '', '', '16:01:37', NULL, '2022-04-25', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3'),
(747, '8', '3', '2', 'Nil', NULL, '', '0.0', 'credit', 'Nil', 'Nil', '', '2022-04-25', '', '', 'Nil', 'GST Payable', '', '', '0', '', '', '16:01:37', NULL, '2022-04-25', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3'),
(748, '7', '1', '1', 'Nil', NULL, '', '0.0', 'debit', 'Nil', 'Nil', '', '2022-04-25', '', '', 'Nil', 'TDS Receivable', '', '', '0', '', '', '16:01:37', NULL, '2022-04-25', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3'),
(749, '6', '1', '1', 'Nil', NULL, '', '10000.0', 'debit', 'Nil', 'Nil', '', '2022-04-25', '', '', 'Nil', 'Php test1', '', '', '10000', '', '', '16:04:48', NULL, '2022-04-25', 'BejoyCmpy', 'bcf1b8-1dcd-d1aa-f42f-c065aa8f5a3'),
(750, '385', '5', '2', 'Nil', NULL, '', '0.0', '', 'Nil', 'Nil', '', '2022-04-26', '', '', 'Nil', 'amma loan', '', '', '100000', '', '', '10:18:38', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(751, '4', '1', '1', 'Nil', NULL, '', '0.0', '', 'Nil', 'Nil', '', '2022-04-26', '', '', 'Nil', 'st.paul\'s institute ', '', '', '0', '', '', '10:31:54', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(752, '5', '6', '3', 'Nil', NULL, '', '55000.0', '', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'consulting', '', '', '0', '', '', '11:49:32', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(753, '386', '1', '1', 'Nil', NULL, '', '0.0', 'debit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'anjali jewel', '', '', '1030', '', '', '10:50:24', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(754, '4', '1', '1', 'Nil', NULL, '', '5800.0', '', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'kalyan developers', '', '', '0', '', '', '11:49:32', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(755, '385', '5', '2', 'Nil', NULL, '', '50000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'brother', '', '', '50000', '', '', '11:12:25', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(756, '6', '1', '1', 'Nil', NULL, '', '-1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Cash in Hand2022-04-27T11:44:30.346293500', '', '', '1000', '', '', '11:44:42', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(757, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'customer', '', '', '1000', '', '', '11:55:02', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(758, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T12:05:50.014286', '', '', '1000', '', '', '12:06:40', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(759, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T12:45:27.432023600', '', '', '1000', '', '', '12:46:15', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(760, '385', '5', '2', 'Nil', NULL, '', '150000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Car loan ', '', '', '150000', '', '', '12:53:07', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(761, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T12:59:14.313658300', '', '', '1000', '', '', '13:00:02', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(762, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T13:11:28.539181200', '', '', '1000', '', '', '13:12:17', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(763, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T15:44:37.658011900', '', '', '1000', '', '', '15:45:27', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(764, '386', '1', '1', 'Nil', NULL, '', '-3000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Customer2022-04-27T15:53:37.238982', '', '', '1000', '', '', '15:54:25', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(765, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Salary2022-04-27T16:06:28.953388800', '', '', '1000', '', '', '16:07:17', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(766, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Salary2022-04-27T16:42:40.281645300', '', '', '1000', '', '', '16:43:28', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(767, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Salary2022-04-27T17:00:50.786917300', '', '', '1000', '', '', '17:01:39', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(768, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'Salary2022-04-27T17:20:53.001931900', '', '', '1000', '', '', '17:21:40', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(769, '385', '4', '2', 'Nil', NULL, '', '10000.0', 'credit', 'Nil', 'Nil', '', '2022-04-27', '', '', 'Nil', 'George Alias', '', '', '10000', '', '', '17:39:41', NULL, '2022-04-25', 'Intuisyz', 'a1074b-fae0-7a7-2dab-cd7bb3cfbb0d'),
(770, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Salary2022-04-28T11:35:28.296372900', '', '', '1000', '', '', '11:36:18', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(771, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Salary2022-04-28T11:51:53.203594400', '', '', '1000', '', '', '11:52:48', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(772, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Salary2022-04-28T11:56:58.025376', '', '', '1000', '', '', '11:57:47', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(773, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Salary2022-04-28T12:10:52.087895500', '', '', '1000', '', '', '12:11:42', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(774, '388', '9', '4', 'Nil', NULL, '', '1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Salary2022-04-28T12:22:11.344693200', '', '', '1000', '', '', '12:23:01', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(775, '389', '2', '1', 'Nil', NULL, '', '-1000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Land2022-04-28T14:47:38.397988200', '', '', '1000', '', '', '14:47:51', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(776, '389', '2', '1', 'Nil', NULL, '', '-101000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Land2022-04-28T14:50:50.890912900', '', '', '1000', '', '', '14:52:16', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(777, '6', '1', '1', 'Nil', NULL, '', '99000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Cash 2022-04-28T14:51:19.790676500', '', '', '1000', '', '', '14:52:16', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(778, '389', '2', '1', 'Nil', NULL, '', '-101000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Land2022-04-28T15:18:54.960132800', '', '', '1000', '', '', '15:20:20', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2'),
(779, '6', '1', '1', 'Nil', NULL, '', '99000.0', 'credit', 'Nil', 'Nil', '', '2022-04-28', '', '', 'Nil', 'Cash 2022-04-28T15:19:24.020834700', '', '', '1000', '', '', '15:20:20', NULL, '2022-04-25', 'Intuisyz', '6b7aad-15ca-bdc8-f266-e673d7e33f2');

-- --------------------------------------------------------

--
