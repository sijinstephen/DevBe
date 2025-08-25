USE devbe;

-- Table structure for table `account_pending_v3`
--

CREATE TABLE `account_pending_v3` (
  `pendID` int(11) NOT NULL,
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
  `filename` varchar(255) NOT NULL,
  `filepath` varchar(255) NOT NULL,
  `created_By` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `createdTime` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
