package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.Account_group_v3;
import com.example.demo.model.Account_ledger_v3;
public interface LedgerServiceRepo extends CrudRepository<Account_ledger_v3,Integer>{
String ss1="select * from account_ledger_v3";
	@Query(nativeQuery =true, value=ss1)
	List<Account_ledger_v3> last_id_Search();
String ss2="select * from account_ledger_v3 where ledger_name = ?1 ";
	@Query(nativeQuery =true, value=ss2)
	List<Account_ledger_v3> ledger_name_search(String ledgerName);
String ss3="select * from account_ledger_v3 ORDER BY id DESC";
	@Query(nativeQuery =true, value=ss3)
	List<Account_ledger_v3> selectData();
String ss4="select * from account_ledger_v3 order by ledger_name";
	@Query(nativeQuery =true, value=ss4)
	List<Account_ledger_v3> ledger_nameA();
String ss5="select * from account_ledger_v3 order by ledger_name DESC";
	@Query(nativeQuery =true, value=ss5)
	List<Account_ledger_v3> ledger_nameD();
String ss6="select * from account_ledger_v3 order by ac_group";
	@Query(nativeQuery =true, value=ss6)
	List<Account_ledger_v3> ac_groupA();
String ss7="select * from account_ledger_v3 order by ac_group DESC";
	@Query(nativeQuery =true, value=ss7)
	List<Account_ledger_v3> ac_groupD();
String ss8="select * from account_ledger_v3 order by cast(open_balance as unsigned) ";
	@Query(nativeQuery =true, value=ss8)
	List<Account_ledger_v3> open_balanceA();
String ss9="select * from account_ledger_v3 order by cast(open_balance as unsigned) DESC";
	@Query(nativeQuery =true, value=ss9)
	List<Account_ledger_v3> open_balanceD();
String ss10="select * from account_ledger_v3 order by mobile";
	@Query(nativeQuery =true, value=ss10)
	List<Account_ledger_v3> mobileA();
String ss11="select * from account_ledger_v3 order by mobile DESC";
	@Query(nativeQuery =true, value=ss11)
	List<Account_ledger_v3> mobileD();
String ss12="select * from account_ledger_v3 order by email";
	@Query(nativeQuery =true, value=ss12)
	List<Account_ledger_v3> emailA();
String ss13="select * from account_ledger_v3 order by email DESC";
	@Query(nativeQuery =true, value=ss13)
	List<Account_ledger_v3> emailD();
String ss14="select * from account_ledger_v3 order by bank";
	@Query(nativeQuery =true, value=ss14)
	List<Account_ledger_v3> bankA();
String ss15="select * from account_ledger_v3 order by bank DESC";
	@Query(nativeQuery =true, value=ss15)
	List<Account_ledger_v3> bankD();
String ss16="select * from account_ledger_v3 where created_date BETWEEN ?1 AND ?2";
	@Query(nativeQuery =true, value=ss16)
	List<Account_ledger_v3> selectDataBnDate(String start, String end);
String ss17="select * from account_ledger_v3 where id = ?1 ";
	@Query(nativeQuery =true, value=ss17)
	List<Account_ledger_v3> ledger_Search(int ledgerId);
String ss18="select * from account_ledger_v3 where id = ?1 ";
	@Query(nativeQuery =true, value=ss18)
	List<Account_ledger_v3> ledger(String j);
//String ss19="UPDATE account_ledger_v3 set amount = :ledgeramount , created_date = :created_date , time = :time  where id = :ledgerID ";
//	
//@Modifying
//	@Query(nativeQuery =true, value=ss19)
////	List<Account_ledger_v3> ledgerAmountUpdate(int ledgerID,String ledgeramount,String created_date,String time);
//	List<Account_ledger_v3> ledgerAmountUpdate(@Param("ledgerID") int ledgerID,@Param("ledgeramount") String ledgeramount,@Param("created_date") String created_date,@Param("time") String time);
//	
String ss19="select * from account_ledger_v3 where ac_group='3' ";
@Query(nativeQuery =true, value=ss19)
List<Account_ledger_v3> selectBank();
String ss20="select ledger_name from account_ledger_v3 where id=?1 ";
@Query(nativeQuery =true, value=ss20)
String getLedger(int id);
String ss21="select * from account_ledger_v3 where id=?1 ";
@Query(nativeQuery =true, value=ss21)
List<Account_ledger_v3> getLedgers(int id);
String ss22="select * from account_ledger_v3 where id = ?1 ";
@Query(nativeQuery =true, value=ss22)
List<Account_ledger_v3> ledger_Search2(int ledgerId);
String ss23="select ledger_name from account_ledger_v3 where id = ?1 ";
@Query(nativeQuery =true, value=ss23)
String ac_ledger_SearchName(int ledgerId);
String ss24="select balance_type from account_ledger_v3 where id = ?1 ";
@Query(nativeQuery =true, value=ss24)
String ac_ledger_balance_type(int ledgerId);
String ss25="select * from account_ledger_v3 where ac_title=?1 ORDER BY ledger_name ";
@Query(nativeQuery =true, value=ss25)
List<Account_ledger_v3> profit_loss(String title);
//String ss26="select * from account_ledger_v3 where ac_title=?1 AND created_date <= ?2  ORDER BY ledger_name ";
//
//
//@Query(nativeQuery =true, value=ss26)
//List<Account_ledger_v3> balanceSheetProfitLossDataBnDate(String title, String end);
String ss26="select * from account_ledger_v3 where ac_title=?1 AND created_date BETWEEN ?2 AND ?3  ORDER BY ledger_name ";
@Query(nativeQuery =true, value=ss26)
List<Account_ledger_v3> balanceSheetProfitLossDataBnDate(String title,  String start ,String end);
String ss27="select * from account_ledger_v3 where ac_type=?1";
@Query(nativeQuery =true, value=ss27)
List<Account_ledger_v3> trial_balances(String acType);
String ss28="select * from account_ledger_v3 where ac_group='3' ";
@Query(nativeQuery =true, value=ss28)
List<Account_ledger_v3> bankData();
String ss29="update account_ledger_v3 SET amount=?2 where id=?1 ";
@Query(nativeQuery =true, value=ss29)
String updatesLedger(int id,String finalAmount);
String ss30="select * from account_ledger_v3 where ac_group='4' ";
@Query(nativeQuery =true, value=ss30)
List<Account_ledger_v3> selectCustomer();
String ss31="select * from account_ledger_v3 where ac_group='5' ";
@Query(nativeQuery =true, value=ss31)
List<Account_ledger_v3> selectService();
String ss32="select * from account_ledger_v3 where ac_type=?1";
@Query(nativeQuery =true, value=ss32)
List<Account_ledger_v3> ledgerLoad(String title);
String ss33="select * from account_ledger_v3 where ac_title=?1";
@Query(nativeQuery =true, value=ss33)
List<Account_ledger_v3> profit_loss_idSearchByTitle(String title);
String ss34="select * from account_ledger_v3 where id = '30' ";
@Query(nativeQuery =true, value=ss34)
List<Account_ledger_v3> ac_dashboardCashDatas();
String ss35="select * from account_ledger_v3 where ac_group = '3' ";
@Query(nativeQuery =true, value=ss35)
List<Account_ledger_v3> ac_dashboardBankDatas();
String ss36="select * from account_ledger_v3 where ac_group = ?1 AND amount IS NOT NULL ";
@Query(nativeQuery =true, value=ss36)
List<Account_ledger_v3> index_customer_vendorApis(String grp);
String ss37="select * from account_ledger_v3 where id=?1 AND ledger_date BETWEEN ?2 AND ?3  ORDER BY ledger_name ";
@Query(nativeQuery =true, value=ss37)
List<Account_ledger_v3> cashBookOpenBalanceDataBnDate(int id,  String start ,String end);
String ss38="select * from account_ledger_v3 where id = ?1 ";
@Query(nativeQuery =true, value=ss38)
Account_ledger_v3 ledger_Search_MigrationDate(int ledgerId);


}

