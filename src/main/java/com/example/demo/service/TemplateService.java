package com.example.demo.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Invoice_template;
import com.example.demo.repository.TemplateRepo;

@Service
public class TemplateService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    private TemplateRepo templateRepo;

    public List<Invoice_template> templateData() {
        List<Invoice_template> li = templateRepo.templateDatas();
        logger.info("Fetched template data, size: {}", li.size());
        return li;
    }
}