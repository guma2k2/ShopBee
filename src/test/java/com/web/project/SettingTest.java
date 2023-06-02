//package com.web.project;
//
//import com.web.project.entity.LoaiSetting;
//import com.web.project.entity.SanPham;
//import com.web.project.entity.Setting;
//import com.web.project.repository.SanPhamRepository;
//import com.web.project.repository.SettingRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class SettingTest {
//
//	@Autowired
//	private SettingRepository repo;
//
//	@Test
//	public void testListAll() {
//		List<Setting> listByMailServer = repo.listByLoai(LoaiSetting.MAIL_SERVER);
//		listByMailServer.forEach(s -> System.out.println(s));
//		assertThat(listByMailServer.size()).isGreaterThan(0);
//
//	}
//}
