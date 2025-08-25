package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_transactions_v3;
public interface TransactionServiceRepo extends CrudRepository<Account_transactions_v3,Integer> {
String ss1="select * from account_transactions_v3 ORDER BY tran_date ASC";
	@Query(nativeQuery =true, value=ss1)
	List<Account_transactions_v3> transactionDate();
String ss2="delete from account_transactions_v3 where tranID=?1 ASC";
	@Query(nativeQuery =true, value=ss2)
	List<Account_transactions_v3> transactionDelete(int id);
String ss3="select * from account_transactions_v3 where type='Voucher'";
	@Query(nativeQuery =true, value=ss3)
	List<Account_transactions_v3> selectData();
String ss4="select * from account_transactions_v3 where type='Voucher' order by tran_Date";
	@Query(nativeQuery =true, value=ss4)
	List<Account_transactions_v3> tran_DateA();
String ss5="select * from account_transactions_v3 where type='Voucher' order by tran_Date DESC";
	@Query(nativeQuery =true, value=ss5)
	List<Account_transactions_v3> tran_DateD();
String ss6="select * from account_transactions_v3 where type='Voucher' order by dbt_ac";
	@Query(nativeQuery =true, value=ss6)
	List<Account_transactions_v3> dbt_acA();
String ss7="select * from account_transactions_v3 where type='Voucher' order by dbt_ac DESC";
	@Query(nativeQuery =true, value=ss7)
	List<Account_transactions_v3> dbt_acD();
String ss8="select * from account_transactions_v3 where type='Voucher' order by cast(amount as unsigned) ";
	@Query(nativeQuery =true, value=ss8)
	List<Account_transactions_v3> amountA();
String ss9="select * from account_transactions_v3 where type='Voucher' order by cast(amount as unsigned) DESC";
	@Query(nativeQuery =true, value=ss9)
	List<Account_transactions_v3> amountD();
String ss10="select * from account_transactions_v3 where type='Voucher' order by mode";
	@Query(nativeQuery =true, value=ss10)
	List<Account_transactions_v3> modeA();
String ss11="select * from account_transactions_v3 where type='Voucher' order by mode DESC";
	@Query(nativeQuery =true, value=ss11)
	List<Account_transactions_v3> modeD();
String ss12="select * from account_transactions_v3 where type='Voucher' order by description";
	@Query(nativeQuery =true, value=ss12)
	List<Account_transactions_v3> descriptionA();
String ss13="select * from account_transactions_v3 where type='Voucher' order by description DESC";
	@Query(nativeQuery =true, value=ss13)
	List<Account_transactions_v3> descriptionD();
String ss14="select * from account_transactions_v3 where type='Voucher' AND tran_Date BETWEEN ?1 AND ?2";
	@Query(nativeQuery =true, value=ss14)
	List<Account_transactions_v3> selectDataBnDate(String start, String end);
String ss15="select * from account_transactions_v3 where transactionid = ?1 ";
	@Query(nativeQuery =true, value=ss15)
	List<Account_transactions_v3> transaction_Search(String transactionId);
String ss16="select * from account_transactions_v3 where type='Contra' AND status='paid' OR status='not paid' OR status='1' order by tranID DESC";
	@Query(nativeQuery =true, value=ss16)
	List<Account_transactions_v3> selectDataJournal();
String ss17="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by tran_Date";
	@Query(nativeQuery =true, value=ss17)
	List<Account_transactions_v3> tran_Date_journalA();
String ss18="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by tran_Date DESC";
	@Query(nativeQuery =true, value=ss18)
	List<Account_transactions_v3> tran_Date_journalD();
String ss19="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by dbt_ac";
	@Query(nativeQuery =true, value=ss19)
	List<Account_transactions_v3> dbt_ac_journalA();
String ss20="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by dbt_ac DESC";
	@Query(nativeQuery =true, value=ss20)
	List<Account_transactions_v3> dbt_ac_journalD();
String ss21="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by cast(amount as unsigned) ";
	@Query(nativeQuery =true, value=ss21)
	List<Account_transactions_v3> amount_journalA();
String ss22="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by cast(amount as unsigned) DESC";
	@Query(nativeQuery =true, value=ss22)
	List<Account_transactions_v3> amount_journalD();
String ss23="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by crdt_ac";
	@Query(nativeQuery =true, value=ss23)
	List<Account_transactions_v3> crdt_ac_journalA();
String ss24="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by crdt_ac DESC";
	@Query(nativeQuery =true, value=ss24)
	List<Account_transactions_v3> crdt_ac_journalD();
String ss25="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by description";
	@Query(nativeQuery =true, value=ss25)
	List<Account_transactions_v3> description_journalA();
String ss26="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') order by description DESC";
	@Query(nativeQuery =true, value=ss26)
	List<Account_transactions_v3> description_journalD();
String ss27="select * from account_transactions_v3 where type='Contra' AND (status='paid' OR status='not paid' OR status='1') AND tran_Date BETWEEN ?1 AND ?2";
	@Query(nativeQuery =true, value=ss27)
	List<Account_transactions_v3> selectJournalDataBnDate(String start, String end);
String ss28="select * from account_transactions_v3 where tranID = ?1 ";
	@Query(nativeQuery =true, value=ss28)
	List<Account_transactions_v3> journal_Search(String tranId);
String ss29="select * from account_transactions_v3 where dbt_ac = ?1 AND crdt_ac = ?2";
@Query(nativeQuery =true, value=ss29)
List<Account_transactions_v3> ledger_transaction_search(String dbt_ac,String crdt_ac);
///////////////////////receipt/////////////
String ss30="select * from account_transactions_v3 where type='Receipt'";
@Query(nativeQuery =true, value=ss30)
List<Account_transactions_v3> selectDataReceipt();
String ss31="select * from account_transactions_v3 where type='Receipt' order by tran_Date";
@Query(nativeQuery =true, value=ss31)
List<Account_transactions_v3> tran_DateReceiptA();
String ss32="select * from account_transactions_v3 where type='Receipt' order by tran_Date DESC";
@Query(nativeQuery =true, value=ss32)
List<Account_transactions_v3> tran_DateReceiptD();
String ss33="select * from account_transactions_v3 where type='Receipt' order by dbt_ac";
@Query(nativeQuery =true, value=ss33)
List<Account_transactions_v3> dbt_acReceiptA();
String ss34="select * from account_transactions_v3 where type='Receipt' order by dbt_ac DESC";
@Query(nativeQuery =true, value=ss34)
List<Account_transactions_v3> dbt_acReceiptD();
String ss35="select * from account_transactions_v3 where type='Receipt' order by cast(amount as unsigned) ";
@Query(nativeQuery =true, value=ss35)
List<Account_transactions_v3> amountReceiptA();
String ss36="select * from account_transactions_v3 where type='Receipt' order by cast(amount as unsigned) DESC";
@Query(nativeQuery =true, value=ss36)
List<Account_transactions_v3> amountReceiptD();
String ss37="select * from account_transactions_v3 where type='Receipt' order by mode";
@Query(nativeQuery =true, value=ss37)
List<Account_transactions_v3> modeReceiptA();
String ss38="select * from account_transactions_v3 where type='Receipt' order by mode DESC";
@Query(nativeQuery =true, value=ss38)
List<Account_transactions_v3> modeReceiptD();
String ss39="select * from account_transactions_v3 where type='Receipt' order by description";
@Query(nativeQuery =true, value=ss39)
List<Account_transactions_v3> descriptionReceiptA();
String ss40="select * from account_transactions_v3 where type='Receipt' order by description DESC";
@Query(nativeQuery =true, value=ss40)
List<Account_transactions_v3> descriptionReceiptD();
String ss41="select * from account_transactions_v3 where type='Receipt' AND tran_Date BETWEEN ?1 AND ?2";
@Query(nativeQuery =true, value=ss41)
List<Account_transactions_v3> selectReceiptDataBnDate(String start, String end);
String ss42="select DISTINCT dbt_ac from account_transactions_v3";
@Query(nativeQuery =true, value=ss42)
List<String> selectDbt_account_statements();
String ss43="select DISTINCT crdt_ac from account_transactions_v3";
@Query(nativeQuery =true, value=ss43)
List<String> selectCrdt_account_statements();
String ss44="select * from account_transactions_v3 where (dbt_ac=?1 OR crdt_ac=?1) AND description!=?2 ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss44)
List<Account_transactions_v3> selectAccStmtTransaction(String id, String description);
   /////old code/////
//String ss45="select * from account_transactions_v3 where (dbt_ac=?1 OR crdt_ac=?1) AND description!=?2 ORDER BY tran_Date";
//
//
//@Query(nativeQuery =true, value=ss45)
//List<Account_transactions_v3> selectAccStmtTransaction2(int id, String description);
String ss45="select * from account_transactions_v3 where (dbt_ac=?1 OR crdt_ac=?1) AND description!=?2 ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss45)
List<Account_transactions_v3> selectAccStmtTransaction2(int id, String description);
String ss46="select * from account_transactions_v3 where tran_Date BETWEEN ?3 AND ?4 AND (dbt_ac=?1 OR crdt_ac=?1) AND description!=?2 ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss46)
List<Account_transactions_v3> selectAccStmtTransactionBndates(String id, String description,String start, String end);
String ss47="select * from account_transactions_v3 where tran_Date BETWEEN ?3 AND ?4 AND (dbt_ac=?1 OR crdt_ac=?1) AND description!=?2 ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss47)
List<Account_transactions_v3> selectAccStmtTransaction2Bndates(int id, String description,String start, String end);
String ss48="select * from account_transactions_v3 where tran_Date BETWEEN ?2 AND ?3 AND (dbt_ac=?1 OR crdt_ac=?1)  ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss48)
List<Account_transactions_v3> selectBalanceSheetDataBnDates(int id,String start, String end);
String ss49="select SUM(amount) from account_transactions_v3 where dbt_ac=?1";
@Query(nativeQuery =true, value=ss49)
Double selectTrialBalanceDbt(int id);
String ss50="select SUM(amount) from account_transactions_v3 where crdt_ac=?1";
@Query(nativeQuery =true, value=ss50)
Double selectTrialBalanceCrdt(int id);
String ss51="select SUM(account_transactions_v3.amount) as debittotal from account_transactions_v3 JOIN account_ledger_v3 ON account_ledger_v3.id = account_transactions_v3.dbt_ac";
@Query(nativeQuery =true, value=ss51)
Double selectTotalDbtAmnt();
String ss52="select SUM(account_transactions_v3.amount) as credittotal from account_transactions_v3 JOIN account_ledger_v3 ON account_ledger_v3.id = account_transactions_v3.crdt_ac";
@Query(nativeQuery =true, value=ss52)
Double selectTotalCrdAmnt();
String ss53="select SUM(amount) from account_transactions_v3 where dbt_ac=?1 AND (tran_Date >= ?2 AND tran_Date <= ?3) ";
@Query(nativeQuery =true, value=ss53)
Double selectTrialBalanceDbtBnDates(int id,String start,String end);
String ss54="select SUM(amount) from account_transactions_v3 where crdt_ac=?1 AND (tran_Date >= ?2 AND tran_Date <= ?3) ";
@Query(nativeQuery =true, value=ss54)
Double selectTrialBalanceCrdtBnDates(int id,String start,String end);
String ss55="select SUM(account_transactions_v3.amount) as debittotal from account_transactions_v3 JOIN account_ledger_v3 ON account_ledger_v3.id = account_transactions_v3.dbt_ac WHERE account_transactions_v3.tran_Date >=?1 AND account_transactions_v3.tran_Date <=?2";
@Query(nativeQuery =true, value=ss55)
Double selectTotalDbtAmntBnDates(String start,String end);
String ss56="select SUM(account_transactions_v3.amount) as credittotal from account_transactions_v3 JOIN account_ledger_v3 ON account_ledger_v3.id = account_transactions_v3.crdt_ac WHERE account_transactions_v3.tran_Date >=?1 AND account_transactions_v3.tran_Date <=?2";
@Query(nativeQuery =true, value=ss56)
Double selectTotalCrdAmntBnDates(String start,String end);
String ss57="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by tran_Date";
@Query(nativeQuery =true, value=ss57)
List<Account_transactions_v3> cashBookDatas();
String ss58="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by tran_Date";
@Query(nativeQuery =true, value=ss58)
List<Account_transactions_v3> tran_DateCashBookA();
String ss59="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by tran_Date DESC";
@Query(nativeQuery =true, value=ss59)
List<Account_transactions_v3> tran_DateCashBookD();
String ss60="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by dbt_ac";
@Query(nativeQuery =true, value=ss60)
List<Account_transactions_v3> ledgerCashBookA();
String ss61="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by dbt_ac DESC";
@Query(nativeQuery =true, value=ss61)
List<Account_transactions_v3> ledgerCashBookD();
String ss62="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by type ";
@Query(nativeQuery =true, value=ss62)
List<Account_transactions_v3> typeCashBookA();
String ss63="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by type DESC";
@Query(nativeQuery =true, value=ss63)
List<Account_transactions_v3> typeCashBookD();
String ss64="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by type,tranID";
@Query(nativeQuery =true, value=ss64)
List<Account_transactions_v3> typeWithNoCashBookA();
String ss65="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by type  DESC";
@Query(nativeQuery =true, value=ss65)
List<Account_transactions_v3> typeWithNoCashBookD();
String ss66="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by description";
@Query(nativeQuery =true, value=ss66)
List<Account_transactions_v3> descriptionCashBookA();
String ss67="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by description DESC";
@Query(nativeQuery =true, value=ss67)
List<Account_transactions_v3> descriptionCashBookD();
String ss68="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by cast(amount as unsigned)";
@Query(nativeQuery =true, value=ss68)
List<Account_transactions_v3> amountCashBookA();
String ss69="select * from account_transactions_v3 where dbt_ac='30' OR crdt_ac='30' order by cast(amount as unsigned) DESC";
@Query(nativeQuery =true, value=ss69)
List<Account_transactions_v3> amountCashBookD();
String ss70="select * from account_transactions_v3 where  tran_Date BETWEEN ?1 AND ?2  AND  (dbt_ac='30' OR crdt_ac='30') order by tran_Date";
@Query(nativeQuery =true, value=ss70)
List<Account_transactions_v3> cashBookBnDate(String start,String end);
String ss71="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3'))";
@Query(nativeQuery =true, value=ss71)
List<Account_transactions_v3> bankBookDatas();
String ss72="select * from account_transactions_v3 where tran_Date  BETWEEN ?1 AND ?2 AND (dbt_ac=?3  OR crdt_ac=?3) ";
@Query(nativeQuery =true, value=ss72)
List<Account_transactions_v3> bankBookDatasB(String start,String end,String bankId);
String ss73="select * from account_transactions_v3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss73)
List<Account_transactions_v3> dayBookDatas();
String ss74="select * from account_transactions_v3";
@Query(nativeQuery =true, value=ss74)
List<Account_transactions_v3> debitAcDatas();
String ss75="select * from account_transactions_v3";
@Query(nativeQuery =true, value=ss75)
List<Account_transactions_v3> creditAcDatas();
String ss76="select * from account_transactions_v3  where  (dbt_ac=?3 AND crdt_ac=?4) AND tran_Date  BETWEEN ?1 AND ?2 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss76)
List<Account_transactions_v3> dayBookDataBnDate1(String start,String end,String debit,String credit);
String ss77="select * from account_transactions_v3  where  (dbt_ac=?4 OR crdt_ac=?3) AND tran_Date  BETWEEN ?1 AND ?2 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss77)
List<Account_transactions_v3> dayBookDataBnDate2(String start,String end,String debit,String credit);
String ss78="select * from account_transactions_v3  where  dbt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss78)
List<Account_transactions_v3> dayBookDataBnDate3(String start,String end,String debit);
String ss79="select * from account_transactions_v3  where  crdt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss79)
List<Account_transactions_v3> dayBookDataBnDate4(String start,String end,String credit);
String ss80="select * from account_transactions_v3  where  tran_Date  BETWEEN ?1 AND ?2 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss80)
List<Account_transactions_v3> dayBookDataBnDate5(String start,String end);
String ss81="select * from account_transactions_v3  where  dbt_ac=?1";
@Query(nativeQuery =true, value=ss81)
List<Account_transactions_v3> dbtAcLoad(int id);
String ss82="select * from account_transactions_v3  where  crdt_ac=?1";
@Query(nativeQuery =true, value=ss82)
List<Account_transactions_v3> crdtAcLoad(int id);
String ss83="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by tran_Date";
@Query(nativeQuery =true, value=ss83)
List<Account_transactions_v3> tran_DateBankBookA();
String ss84="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by tran_Date DESC";
@Query(nativeQuery =true, value=ss84)
List<Account_transactions_v3> tran_DateBankBookD();
String ss85="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by dbt_ac";
@Query(nativeQuery =true, value=ss85)
List<Account_transactions_v3> ledgerBankBookA();
String ss86="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by dbt_ac DESC";
@Query(nativeQuery =true, value=ss86)
List<Account_transactions_v3> ledgerBankBookD();
String ss87="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by type ";
@Query(nativeQuery =true, value=ss87)
List<Account_transactions_v3> typeBankBookA();
String ss88="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by type DESC";
@Query(nativeQuery =true, value=ss88)
List<Account_transactions_v3> typeBankBookD();
String ss89="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by type,tranID";
@Query(nativeQuery =true, value=ss89)
List<Account_transactions_v3> typeWithNoBankBookA();
String ss90="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by type  DESC";
@Query(nativeQuery =true, value=ss90)
List<Account_transactions_v3> typeWithNoBankBookD();
String ss91="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by description";
@Query(nativeQuery =true, value=ss91)
List<Account_transactions_v3> descriptionBankBookA();
String ss92="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by description DESC";
@Query(nativeQuery =true, value=ss92)
List<Account_transactions_v3> descriptionBankBookD();
String ss93="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by cast(amount as unsigned)";
@Query(nativeQuery =true, value=ss93)
List<Account_transactions_v3> amountBankBookA();
String ss94="select * from account_transactions_v3 where (dbt_ac IN (select id from account_ledger_v3 where ac_group='3')  OR crdt_ac  IN (select id from account_ledger_v3 where ac_group='3')) order by cast(amount as unsigned) DESC";
@Query(nativeQuery =true, value=ss94)
List<Account_transactions_v3> amountBankBookD();
String ss95="select * from account_transactions_v3 ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss95)
List<Account_transactions_v3> tran_DateDayBookA();
String ss96="select * from account_transactions_v3 ORDER BY tran_Date DESC";
@Query(nativeQuery =true, value=ss96)
List<Account_transactions_v3> tran_DateDayBookD();
String ss97="select * from account_transactions_v3 ORDER BY description";
@Query(nativeQuery =true, value=ss97)
List<Account_transactions_v3> descriptionDayBookA();
String ss98="select * from account_transactions_v3 ORDER BY description DESC";
@Query(nativeQuery =true, value=ss98)
List<Account_transactions_v3> descriptionDayBookD();
String ss99="select * from account_transactions_v3 ORDER BY amount";
@Query(nativeQuery =true, value=ss99)
List<Account_transactions_v3> amountDayBookA();
String ss100="select * from account_transactions_v3 ORDER BY amount DESC";
@Query(nativeQuery =true, value=ss100)
List<Account_transactions_v3> amountDayBookD();
String ss101="select * from account_transactions_v3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss101)
List<Account_transactions_v3> debitDayBookA();
String ss102="select * from account_transactions_v3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss102)
List<Account_transactions_v3> debitDayBookD();
String ss103="select * from account_transactions_v3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss103)
List<Account_transactions_v3> creditDayBookA();
String ss104="select * from account_transactions_v3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss104)
List<Account_transactions_v3> creditDayBookD();
String ss105="select * from account_transactions_v3 where tran_gen = ?1 and crdt_ac=?2 and dbt_ac=?3";
@Query(nativeQuery =true, value=ss105)
List<Account_transactions_v3> journal_searchInvoice(String tranId,String creditAc,String debitAc);
String ss106="select * from account_transactions_v3 where tran_Date BETWEEN ?2 AND ?3 AND (dbt_ac=?1 OR crdt_ac=?1) ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss106)
List<Account_transactions_v3> profit_loss_bn_date(int ledgerId,String start, String end);
String ss107="select * from account_transactions_v3 where description='ledger creation' AND  mode='Nil' AND (dbt_ac='30' OR crdt_ac='30')   order by tran_Date";
@Query(nativeQuery =true, value=ss107)
List<Account_transactions_v3> cashBookOpenBalanceDataFetch();
String ss108="select SUM(amount) from account_transactions_v3 where dbt_ac=?1 AND description!='ledger creation' AND  mode!='Nil'";
@Query(nativeQuery =true, value=ss108)
Double selectTrialBalanceDbtWithoutOpenBalance(int id);
String ss109="select SUM(amount) from account_transactions_v3 where crdt_ac=?1 AND description!='ledger creation' AND  mode!='Nil'";
@Query(nativeQuery =true, value=ss109)
Double selectTrialBalanceCrdtWithoutOpenBalance(int id);
String ss110="select * from account_transactions_v3  where  (dbt_ac=?3 AND crdt_ac=?4) AND tran_Date  BETWEEN ?1 AND ?2 AND description=?5 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss110)
List<Account_transactions_v3> transactionHistoryNarrationSearch1(String start,String end,String debit,String credit,String val);
String ss111="select * from account_transactions_v3  where  (dbt_ac=?4 OR crdt_ac=?3) AND tran_Date  BETWEEN ?1 AND ?2 AND description=?5 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss111)
List<Account_transactions_v3> transactionHistoryNarrationSearch2(String start,String end,String debit,String credit,String val);
String ss112="select * from account_transactions_v3  where  dbt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 AND description=?4 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss112)
List<Account_transactions_v3> transactionHistoryNarrationSearch3(String start,String end,String debit,String val);
String ss113="select * from account_transactions_v3  where  crdt_ac=?3 AND tran_Date  BETWEEN ?1 AND ?2 AND description=?4 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss113)
List<Account_transactions_v3> transactionHistoryNarrationSearch4(String start,String end,String credit,String val);
String ss114="select * from account_transactions_v3  where  tran_Date  BETWEEN ?1 AND ?2 AND description=?3 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss114)
List<Account_transactions_v3> transactionHistoryNarrationSearch5(String start,String end,String val);
String ss115="select * from account_transactions_v3  where  (dbt_ac=?3 AND crdt_ac=?4) AND tran_Date  BETWEEN ?1 AND ?2 AND cast(amount as unsigned) BETWEEN ?5 AND ?6 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss115)
List<Account_transactions_v3> transactionHistoryAmountSearch1(String start,String end,String debit,String credit,String val1,String val2);
String ss116="select * from account_transactions_v3  where  (dbt_ac=?4 OR crdt_ac=?3) AND tran_Date  BETWEEN ?1 AND ?2 AND cast(amount as unsigned) BETWEEN ?5 AND ?6 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss116)
List<Account_transactions_v3> transactionHistoryAmountSearch2(String start,String end,String debit,String credit,String val1,String val2);
String ss117="select * from account_transactions_v3  where  dbt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 AND cast(amount as unsigned) BETWEEN ?4 AND ?5 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss117)
List<Account_transactions_v3> transactionHistoryAmountSearch3(String start,String end,String debit,String val1,String val2);
String ss118="select * from account_transactions_v3  where  crdt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 AND cast(amount as unsigned) BETWEEN ?4 AND ?5 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss118)
List<Account_transactions_v3> transactionHistoryAmountSearch4(String start,String end,String credit,String val1,String val2);
String ss119="select * from account_transactions_v3  where  tran_Date  BETWEEN ?1 AND ?2 AND  cast(amount as unsigned)  BETWEEN ?3 AND ?4 ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss119)
List<Account_transactions_v3> transactionHistoryAmountSearch5(String start,String end,String val1,String val2);
String ss120="select * from account_transactions_v3  where  (dbt_ac=?3 AND crdt_ac=?4) AND tran_Date  BETWEEN ?1 AND ?2 AND (dbt_ac=?5 OR crdt_ac=?5) ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss120)
List<Account_transactions_v3> transactionHistoryGroupSearch1(String start,String end,String debit,String credit,String val);
String ss121="select * from account_transactions_v3  where  (dbt_ac=?4 OR crdt_ac=?3) AND tran_Date  BETWEEN ?1 AND ?2 AND  (dbt_ac=?5 OR crdt_ac=?5) ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss121)
List<Account_transactions_v3> transactionHistoryGroupSearch2(String start,String end,String debit,String credit,String val);
String ss122="select * from account_transactions_v3  where  dbt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 AND  (dbt_ac=?4 OR crdt_ac=?4) ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss122)
List<Account_transactions_v3> transactionHistoryGroupSearch3(String start,String end,String debit,String val);
String ss123="select * from account_transactions_v3  where  crdt_ac=?3  AND tran_Date  BETWEEN ?1 AND ?2 AND  (dbt_ac=?4 OR crdt_ac=?4) ORDER BY tranID DESC";
@Query(nativeQuery =true, value=ss123)
List<Account_transactions_v3> transactionHistoryGroupSearch4(String start,String end,String credit,String val);
String ss124="select * from account_transactions_v3  JOIN account_ledger_v3 ON (account_ledger_v3.id = account_transactions_v3.dbt_ac) OR (account_ledger_v3.id = account_transactions_v3.crdt_ac) where account_transactions_v3.tran_Date  BETWEEN ?1 AND ?2 AND account_ledger_v3.ac_group=?3 ORDER BY account_transactions_v3.tranID DESC";
@Query(nativeQuery =true, value=ss124)
List<Account_transactions_v3> transactionHistoryGroupSearch5(String start,String end,String val);
String ss125="select * from account_transactions_v3 where tran_gen=?1  ORDER BY tran_Date";
@Query(nativeQuery =true, value=ss125)
List<Account_transactions_v3> tran_gen_Search(String tran_gen_id);
String ss126="select * from account_transactions_v3 where (dbt_ac=?1 OR crdt_ac=?1) AND description=?2 ORDER BY created_date,created_time";
@Query(nativeQuery =true, value=ss126)
List<Account_transactions_v3> transactionFilter(int id, String description);
}
