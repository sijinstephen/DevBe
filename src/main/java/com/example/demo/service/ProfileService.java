package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Profile;
import com.example.demo.repository.ProfileRepo;

@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepo profileRepo;

    public Profile add_Profiles(Profile fp) {
        logger.info("Adding profile: {}", fp);
        return profileRepo.save(fp);
    }

    public List<Profile> profileData() {
        Iterable<Profile> iterable = profileRepo.findAll();
        List<Profile> li = StreamSupport.stream(iterable.spliterator(), false)
                                       .collect(Collectors.toList());
        logger.info("Fetched profile data, size: {}", li.size());
        return li;
    }
}