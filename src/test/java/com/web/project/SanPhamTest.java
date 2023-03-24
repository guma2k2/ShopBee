package com.web.project;

import com.web.project.entity.LoaiSanPham;
import com.web.project.entity.SanPham;
import com.web.project.repository.LoaiSanPhamRepository;
import com.web.project.repository.SanPhamRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SanPhamTest {

	@Autowired
	private SanPhamRepository repo;
	
	@Test
	@Transactional
	public void testListAll() {
		Sort sort = Sort.by("gia");
		sort =  sort.descending();
		Pageable pageable = PageRequest.of(0 , 5 , sort);
		Page<SanPham> page = repo.findByCategory(5,pageable);
		List<SanPham> lists = page.getContent();
		lists.forEach(l -> System.out.println(l.getId()));
		assertThat(lists.size()).isGreaterThan(0);
		
	}
}
