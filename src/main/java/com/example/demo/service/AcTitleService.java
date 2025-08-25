package com.example.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Account_ledger_v3;
import com.example.demo.model.Account_title_v3;
import com.example.demo.repository.AcTitleRepo;
@Service
public class AcTitleService {
	@Autowired
	private AcTitleRepo acTitleRepo;
	public List<Account_title_v3> ac_Titles() {
		// TODO Auto-generated method stub
		List<Account_title_v3> list=(List<Account_title_v3>) acTitleRepo.findAll();
		return list;
	}
	public String list_acTitles(String acId) {
		// TODO Auto-generated method stub
		String  acTitle= acTitleRepo.selectAcStatementTitle(acId);
		return acTitle;
	}
	public List<Account_title_v3> acTitle_search(String acId) {
		// TODO Auto-generated method stub
		  List<Account_title_v3> li=(List<Account_title_v3>) acTitleRepo.acTitle_searchs(acId);
		return li;
	}
}
