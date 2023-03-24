package com.web.project.service;

import com.web.project.entity.LoaiSetting;
import com.web.project.entity.Setting;
import com.web.project.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public List<Setting> listByLoai(LoaiSetting loai){
        return settingRepository.listByLoai(loai);
    }

    public List<Setting> saveAll(Iterable<Setting> list){
        return settingRepository.saveAll(list);
    }
    public int getPort(){
        return Integer.parseInt(settingRepository.getByKey("MAIL_PORT"));
    }
    public String getHost(){
        return settingRepository.getByKey("MAIL_HOST");
    }
    public String getUsername(){
        return settingRepository.getByKey("MAIL_USERNAME");
    }
    public String getPassword(){
        return settingRepository.getByKey("MAIL_PASSWORD");
    }
    public String getSmtpAuth(){
        return settingRepository.getByKey("SMTP_AUTH");
    }
    public String getSmtpSecured(){
        return settingRepository.getByKey("SMTP_SECURED");
    }
    public String getFromAddress(){
        return settingRepository.getByKey("MAIL_FROM");
    }
    public String getSenderName(){
        return settingRepository.getByKey("MAIL_SENDER_NAME");
    }
    public String getCustomerVerifySubject(){
        return settingRepository.getByKey("CUSTOMER_VERIFY_SUBJECT");
    }
    public String getCustomerVerifyContent(){
        return settingRepository.getByKey("CUSTOMER_VERIFY_CONTENT");
    }

}
