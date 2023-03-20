package com.web.project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.web.project.entity.LoaiSanPham;
import com.web.project.repository.LoaiSanPhamRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LoaiSanPhamTest {

	@Autowired
	private LoaiSanPhamRepository repo;
	
	@Test
	public void testListAll() {
		List<LoaiSanPham> lists = repo.findAll();
		lists.forEach(l -> System.out.println(l));
		assertThat(lists.size()).isGreaterThan(0);
		
	}
}
