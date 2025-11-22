package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Defaults;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepo;
import com.example.demo.repository.DefaultsRepo;


@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepo profileRepo;
    private DefaultsRepo defaultsRepo;

    public Profile add_Profiles(Profile fp) {
        logger.info("Adding profile: {}", fp);
        return profileRepo.save(fp);
    }

public List<Profile> getProfileData(String companyName, String custId) {

    Iterable<Profile> iterable =
            profileRepo.defaultProfileDatas(companyName, custId);

    return StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
}


    public List<Profile> profileData() {
        Iterable<Profile> iterable = profileRepo.findAll();
        List<Profile> li = StreamSupport.stream(iterable.spliterator(), false)
                                       .collect(Collectors.toList());

        logger.info("Fetched profile data, size: {}", li.size());
        if (li.size() == 0) {
            logger.info("No profile data found Look for default profile data");

        } 

        return li;
    }

        public List<Profile> defaultProfileData() 
        {
             // 🔹 Get Default company 
                Defaults defaults = null;
                for (Defaults d : defaultsRepo.findAll()) {
                    defaults = d;
                    break;
                }
                if (defaults == null) {
                    throw new IllegalStateException("No defaults row found in database");
                }

                String defaultCompanyName = defaults.getDefault_company_name();
                if (defaultCompanyName == null || defaultCompanyName.isBlank()) {
                    throw new IllegalStateException("Default Company is not configured");
                }
             // 🔹 Get Default Customr  
                String defaultCustomerName = defaults.getDefault_customer_name();
                if (defaultCustomerName == null || defaultCustomerName.isBlank()) {
                    throw new IllegalStateException("Default Company is not configured");
                }
        Iterable<Profile> iterable = profileRepo.defaultProfileDatas(defaultCompanyName, defaultCustomerName);
        List<Profile> li = StreamSupport.stream(iterable.spliterator(), false)
                                      .collect(Collectors.toList());
        logger.info("Fetched default profile data, size: {}", li.size());
        if (li.size() == 0) {
            logger.info("No profile data found Look for default profile data");
        } 
        return li;
    }
}