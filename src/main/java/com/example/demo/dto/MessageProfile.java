package com.example.demo.dto;

import java.time.Instant;

public class MessageProfile {

    public String msg_id;
    public String timestamp;
    public String category;
    public Payload payload = new Payload();

    public static class Payload {
        public Organization organization = new Organization();
        public Banking banking = new Banking();
        public GST gst = new GST();
        public Contact contact = new Contact();
        public Meta meta = new Meta();
    }

    public static class Organization {
        public String organization_name;
        public String industry;
        public String street1;
        public String street2;
        public String city;
        public String state;
        public String zip;
    }

    public static class Banking {
        public String ifsc;
        public String acc_no;
        public String bank;
        public String branch;
    }

    public static class GST {
        public String gst_id;
    }

    public static class Contact {
        public String phone;
        public String website;
        public String gmail;
    }

    public static class Meta {
        public String signatory_name;
        public String signatory_designation;
        public String company_name;
        public String cust_id;
        public String fiscal_year;
        public String date_format;
    }

    // helper factory for read messages
    public static MessageProfile newReadMessage() {
        MessageProfile m = new MessageProfile();
        m.msg_id = "profile-read-" + System.currentTimeMillis();
        m.timestamp = Instant.now().toString();
        m.category = "PROFILE_READ";
        return m;
    }
}
