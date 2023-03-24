package com.web.project.controller;

import com.web.project.entity.LoaiSetting;
import com.web.project.entity.Setting;
import com.web.project.service.SettingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.List;

@Controller
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/setting")
    public String listSetting(Model model){
        List<Setting> listSettingMailServer = settingService.listByLoai(LoaiSetting.MAIL_SERVER);
        List<Setting> listSettingMailTemplate = settingService.listByLoai(LoaiSetting.MAIL_TEMPLATES);
        listSettingMailServer.forEach(setting -> model.addAttribute(setting.getKey() , setting.getValue()));
        listSettingMailTemplate.forEach(setting -> model.addAttribute(setting.getKey() , setting.getValue()));
        return "setting/setting";
    }
    @PostMapping("/setting/save")
    public String saveSetting(HttpServletRequest request , RedirectAttributes redirectAttributes){
        List<Setting> listSettingMailServer = settingService.listByLoai(LoaiSetting.MAIL_SERVER);
        List<Setting> listSettingMailTemplate = settingService.listByLoai(LoaiSetting.MAIL_TEMPLATES);
        updateSetting(request,listSettingMailServer);
        updateSetting(request,listSettingMailTemplate);
        redirectAttributes.addFlashAttribute("message" , "Luu thanh cong");
        return "redirect:/setting";
    }
    private void updateSetting(HttpServletRequest request , List<Setting> settings ) {
        settings.forEach(setting -> {
            if(setting.getValue() != request.getParameter(setting.getKey())){
                setting.setValue((String) request.getParameter(setting.getKey()));
            }
        });
        settingService.saveAll(settings);
    }
}
