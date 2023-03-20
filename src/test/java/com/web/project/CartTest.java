package com.web.project;

import com.web.project.entity.Cart;
import com.web.project.entity.LoaiSanPham;
import com.web.project.entity.NhanVien;
import com.web.project.entity.SanPham;
import com.web.project.repository.CartRepository;
import com.web.project.repository.LoaiSanPhamRepository;
import com.web.project.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartTest {


	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private  CartRepository repository;
	@Test
	public void testListAll() {
		Integer saphamId = 1 ;
		int quantity = 2 ;
		NhanVien kh = entityManager.find(NhanVien.class,1 );
		SanPham sp = entityManager.find(SanPham.class , 2);
		Cart t = new Cart();
		t.setSoLuong(quantity);
		t.setKhachHang(kh);
		t.setSanPham(sp);
		Cart cart  = repository.save(t);
		assertThat(cart).isNotNull();

	}
}
