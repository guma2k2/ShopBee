package com.web.project.repository;

import com.web.project.entity.LoaiSetting;
import com.web.project.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SettingRepository  extends JpaRepository<Setting , String> {
    @Query("SELECT s FROM Setting s WHERE s.loaiSetting = ?1")
    public List<Setting> listByLoai(LoaiSetting loai);

    @Query("SELECT s.value FROM Setting s WHERE s.key = ?1")
    public String getByKey(String key);
}
