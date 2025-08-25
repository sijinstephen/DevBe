-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2015 at 12:48 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `accounts_new`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_group`
--

CREATE TABLE IF NOT EXISTS `account_group` (
  `group_id` int(10) NOT NULL AUTO_INCREMENT,
  `ac_title` varchar(255) DEFAULT NULL,
  `ac_type` varchar(255) DEFAULT NULL,
  `group_name` varchar(1000) DEFAULT NULL,
  `created_date` varchar(1000) DEFAULT NULL,
  `time` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `account_group`
--

INSERT INTO `account_group` (`group_id`, `ac_title`, `ac_type`, `group_name`, `created_date`, `time`) VALUES
(1, '3', '2', 'Sundry Creditor', '2015-03-29', '04:31:54 PM'),
(3, '1', '1', 'Bank a/c', '2015-04-08', '04:23:18 PM'),
(5, '1', '1', 'Sundry Debtor', '2015-03-29', '04:31:23 PM'),
(6, '10', '2', 'Capital', '27-03-2015', '08:31:03 PM'),
(7, '1', '1', 'Cash in hand', '2015-03-30', '01:55:29 PM'),
(8, '2', '1', 'Systems', '09-04-2015', '06:17:28 PM'),
(9, '2', '1', 'Furniture', '09-04-2015', '06:59:54 PM'),
(10, '5', '2', 'Building loan', '09-04-2015', '07:43:14 PM'),
(11, '6', '3', 'Product Sale', '10-04-2015', '02:27:36 PM'),
(12, '8', '3', 'News paper', '10-04-2015', '03:02:25 PM'),
(13, '7', '4', 'maintenance', '10-04-2015', '03:21:30 PM'),
(14, '9', '4', 'Office Expense', '10-04-2015', '03:35:12 PM'),
(15, '7', '4', 'Product Purchase', '23-04-2015', '12:52:54 PM'),
(16, '4', '2', 'hhh', '10-05-2015', '09:56:29 PM');

-- --------------------------------------------------------

--
-- Table structure for table `account_ledger`
--

CREATE TABLE IF NOT EXISTS `account_ledger` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ledger_name` varchar(1000) DEFAULT NULL,
  `ac_type` varchar(255) NOT NULL,
  `ac_title` varchar(255) DEFAULT NULL,
  `ac_group` varchar(255) DEFAULT NULL,
  `name` varchar(1000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `state` varchar(1000) DEFAULT NULL,
  `pin` varchar(1000) DEFAULT NULL,
  `contact` varchar(1000) DEFAULT NULL,
  `mobile` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `acc_number` varchar(1000) DEFAULT NULL,
  `bank` varchar(1000) DEFAULT NULL,
  `branch` varchar(1000) DEFAULT NULL,
  `ifsc_code` varchar(1000) DEFAULT NULL,
  `open_balance` varchar(1000) DEFAULT NULL,
  `amount` varchar(1000) DEFAULT NULL,
  `created_date` varchar(1000) DEFAULT NULL,
  `time` varchar(1000) DEFAULT NULL,
  `userID` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=90 ;

--
-- Dumping data for table `account_ledger`
--

INSERT INTO `account_ledger` (`id`, `ledger_name`, `ac_type`, `ac_title`, `ac_group`, `name`, `address`, `state`, `pin`, `contact`, `mobile`, `fax`, `email`, `acc_number`, `bank`, `branch`, `ifsc_code`, `open_balance`, `amount`, `created_date`, `time`, `userID`) VALUES
(30, 'Cash in hand', '1', '1', '7', 'Intuisyz', 'Angamaly', 'Kerala', '683572', '9497057806', '9497057806', '6549847', 'mails2geethuz@gmail.com', '  1234', 'HDFC', 'Angamaly', '1234', '5000', '9500', '', '', 'ACNT1'),
(68, 'Acer INDIA LTD', '2', '3', '1', '', '', '', '', '', '', '', '', '   ', '', '', '', '   ', '', '', '03:12:49 PM', 'ACNT1'),
(70, 'Dona System', '2', '3', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '1500', '', '', 'ACNT1'),
(71, 'Fed Loan', '1', '1', '5', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '06:06:01 PM', ''),
(72, 'Federal Bank ', '1', '1', '3', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-12', '05:19:46:PM', ''),
(73, 'Gardens', '1', '1', '5', 'Gardens', 'Angamaly', 'Kerala', '683572', '9497057806', '9497057806', '', '', '', '', '', '', '', '0', '2015-05-13', '03:25:33 PM', 'ACNT1'),
(74, 'ICICI', '1', '1', '3', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-12', '03:00:39 PM', 'ACNT1'),
(75, 'Jasmin Sijin', '2', '10', '6', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '06:07:47 PM', ''),
(76, 'Royal Furniture', '2', '3', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '06:08:43 PM', ''),
(77, 'Samuel Sijin', '2', '10', '6', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '06:09:20 PM', ''),
(78, 'Sijin Stephen', '2', '10', '6', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-12', '03:49:45 PM', 'ACNT1'),
(79, 'Computer', '1', '2', '8', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '06:18:06 PM', ''),
(80, 'Office Chair', '1', '2', '9', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '07:00:17 PM', ''),
(81, 'Room loan', '2', '5', '10', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-04-09', '07:43:38 PM', ''),
(82, 'Website Sale', '3', '6', '11', '', '', '', '', '', '', '', '', '', '', '', '', '', '10000', '2015-05-13', '03:29:05 PM', 'ACNT1'),
(83, 'News paper sales', '3', '8', '12', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'ACNT1'),
(84, 'System maintenance', '4', '7', '13', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-13', '02:59:43 PM', 'ACNT1'),
(85, 'Tea Expense', '4', '9', '14', '', '', '', '', '', '', '', '', '', '', '', '', '', '2000', '2015-05-13', '03:29:36 PM', 'ACNT1'),
(86, 'Geethu', '1', '1', '5', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-13', '01:06:52 PM', 'ACNT1'),
(87, 'vendor1', '4', '7', '15', '', '', '', '', '', '', '', '', '  ', '', '', '', '  ', '', '2015-04-23', '12:59:43 PM', ''),
(88, 'Mahindra Loan', '2', '3', '1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-13', '10:02:56 AM', 'ACNT1'),
(89, 'pp', '2', '4', '16', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '2015-05-10', '09:56:55 PM', '');

-- --------------------------------------------------------

--
-- Table structure for table `account_pending`
--

CREATE TABLE IF NOT EXISTS `account_pending` (
  `pendID` int(11) NOT NULL AUTO_INCREMENT,
  `transactionID` varchar(255) NOT NULL,
  `ac_tranID` varchar(255) NOT NULL,
  `dbt_ac` varchar(255) NOT NULL,
  `crdt_ac` varchar(255) NOT NULL,
  `sundry` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `paid` varchar(255) NOT NULL,
  `balance` varchar(255) NOT NULL,
  `tran_Date` date NOT NULL,
  `due_Date` date NOT NULL,
  `mode` varchar(255) NOT NULL,
  `chq_no` varchar(255) NOT NULL,
  `chq_date` date NOT NULL,
  `bank` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_By` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `createdTime` varchar(255) NOT NULL,
  PRIMARY KEY (`pendID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `account_pending`
--

INSERT INTO `account_pending` (`pendID`, `transactionID`, `ac_tranID`, `dbt_ac`, `crdt_ac`, `sundry`, `amount`, `paid`, `balance`, `tran_Date`, `due_Date`, `mode`, `chq_no`, `chq_date`, `bank`, `type`, `description`, `status`, `created_By`, `createdDate`, `createdTime`) VALUES
(27, '5553208874d3f', '173', '85', '30', '70', '2000', '500', '1500', '2015-05-08', '2015-05-11', 'cash', '', '0000-00-00', '', 'Voucher', 'cggfhf', '2', 'ACNT1', '2015-05-13', '03:38:39:PM');

-- --------------------------------------------------------

--
-- Table structure for table `account_title`
--

CREATE TABLE IF NOT EXISTS `account_title` (
  `ac_id` int(10) NOT NULL AUTO_INCREMENT,
  `ac_title` varchar(1000) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `time` varchar(1000) DEFAULT NULL,
  `ac_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ac_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `account_title`
--

INSERT INTO `account_title` (`ac_id`, `ac_title`, `created_date`, `time`, `ac_type`) VALUES
(1, 'Current Asset', '2015-03-26', NULL, '1'),
(2, 'Fixed Asset', '2015-03-26', NULL, '1'),
(3, 'Current Liability', '2015-03-26', NULL, '2'),
(4, 'Fixed Liability', '2015-03-26', NULL, '2'),
(5, 'Loans', '2015-03-26', NULL, '2'),
(6, 'Direct Income', '2015-03-26', NULL, '3'),
(7, 'Direct Expense', '2015-03-26', NULL, '4'),
(8, 'Indirect Income', '2015-03-26', NULL, '3'),
(9, 'Primary Expense', '2015-03-26', NULL, '4'),
(10, 'Capital Account', '2015-03-26', NULL, '2');

-- --------------------------------------------------------

--
-- Table structure for table `account_transactions`
--

CREATE TABLE IF NOT EXISTS `account_transactions` (
  `tranID` int(11) NOT NULL AUTO_INCREMENT,
  `transactionID` varchar(255) NOT NULL,
  `dbt_ac` varchar(255) NOT NULL,
  `crdt_ac` varchar(255) NOT NULL,
  `mode` varchar(10) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `tran_Date` date NOT NULL,
  `description` text NOT NULL,
  `ac_no` varchar(255) NOT NULL,
  `chq_no` varchar(255) NOT NULL,
  `chq_date` date NOT NULL,
  `branch` varchar(255) NOT NULL,
  `user_bank` varchar(255) NOT NULL,
  `bank` varchar(255) NOT NULL,
  `status` varchar(10) NOT NULL,
  `createdBy` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `createdTime` varchar(255) NOT NULL,
  PRIMARY KEY (`tranID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=174 ;

--
-- Dumping data for table `account_transactions`
--

INSERT INTO `account_transactions` (`tranID`, `transactionID`, `dbt_ac`, `crdt_ac`, `mode`, `amount`, `type`, `tran_Date`, `description`, `ac_no`, `chq_no`, `chq_date`, `branch`, `user_bank`, `bank`, `status`, `createdBy`, `createdDate`, `createdTime`) VALUES
(170, '555320694f0c4', '30', '82', 'cash', '10000', 'Receipt', '2015-05-02', 'asdsa', '', '', '0000-00-00', '', '', '0', '1', 'ACNT1', '2015-05-13', '03:29:05 PM'),
(171, '5553208874d3f', '85', '70', 'cash', '2000', 'Voucher', '2015-05-08', 'cggfhf', '', '', '0000-00-00', '', '', '0', '2', 'ACNT1', '2015-05-13', '03:29:36 PM'),
(172, '5553208874d3f', '70', '30', 'cash', '500', 'Voucher', '2015-05-08', 'cggfhf', '', '', '0000-00-00', '', '', '', '2', 'ACNT1', '2015-05-13', '03:37:22 PM');

-- --------------------------------------------------------

--
-- Table structure for table `account_type`
--

CREATE TABLE IF NOT EXISTS `account_type` (
  `ac_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `ac_type` varchar(255) NOT NULL,
  PRIMARY KEY (`ac_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `account_type`
--

INSERT INTO `account_type` (`ac_type_id`, `ac_type`) VALUES
(1, 'Asset'),
(2, 'Liability'),
(3, 'Income'),
(4, 'Expense');

-- --------------------------------------------------------

--
-- Table structure for table `account_user`
--

CREATE TABLE IF NOT EXISTS `account_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `account_user`
--

INSERT INTO `account_user` (`id`, `userID`, `userName`, `password`) VALUES
(1, 'ACNT1', 'admin@abc.com', 'e268443e43d93dab7ebef303bbe9642f');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
