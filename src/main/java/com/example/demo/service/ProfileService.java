package com.example.demo.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Invoice;
import com.example.demo.model.Invoice_template;
import com.example.demo.model.Profile;
import com.example.demo.repository.LedgerServiceRepo;
import com.example.demo.repository.ProfileRepo;
@Service
public class ProfileService {
	@Autowired
	private ProfileRepo profileRepo;
    public Profile add_Profiles(Profile fp) {
    	   profileRepo.save(fp);
			return null;
		}
    public List<Profile> profileData()  {
    	List<Profile> li=(List<Profile>) profileRepo.profileDatas();
    	  System.out.println("li "+ li.size());
    	  return li;
    }
}
