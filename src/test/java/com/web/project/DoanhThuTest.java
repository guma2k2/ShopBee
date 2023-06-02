//package com.web.project;
//
//import com.web.project.dto.DoanhThuTheoSanPham;
//import com.web.project.dto.LichSuSanPham;
//import com.web.project.entity.Cart;
//import com.web.project.entity.NhanVien;
//import com.web.project.entity.SanPham;
//import com.web.project.repository.CartRepository;
//import com.web.project.repository.CthdRepository;
//import com.web.project.repository.HoaDonRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.annotation.Rollback;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class DoanhThuTest {
//
//
//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private CthdRepository repo ;
//
//	@Autowired
//	private HoaDonRepository hoaDonRepository ;
//
//	@Test
//	public void listDoanhThu() {
//		LocalDate fromDate = LocalDate.parse("2023-01-01");
//		LocalDate toDate = LocalDate.parse("2023-01-01");
//		Double doanhThu = hoaDonRepository.tongDoanhTheoNgay(fromDate,toDate);
//		System.out.println(doanhThu);
//		assertThat(doanhThu).isNull();
//	}
//	@Test
//	public void tongSoHoaDon(){
//		LocalDate fromDate = LocalDate.parse("2023-01-01");
//		LocalDate toDate = LocalDate.parse("2023-12-12");
//		Long tong = hoaDonRepository.countByDate(fromDate , toDate);
//		System.out.println(tong);
//		assertThat(tong).isGreaterThan(0);
//	}
//
//	@Test
//	public void tongSp(){
//		LocalDate fromDate = LocalDate.parse("2023-01-01");
//		LocalDate toDate = LocalDate.parse("2023-12-12");
//		Long tong = repo.countNumerOfProducts(fromDate,toDate);
//		System.out.println(tong);
//		assertThat(tong).isGreaterThan(0);
//	}
//
//	@Test
//	public void tongLoaiSp(){
//		LocalDate fromDate = LocalDate.parse("2023-01-01");
//		LocalDate toDate = LocalDate.parse("2023-12-12");
//		Long tong = repo.soLoaiSp(fromDate,toDate);
//		System.out.println(tong);
//		assertThat(tong).isGreaterThan(0);
//	}
//
//	@Test
//	public void listttt(){
//		String date = "2023-03-18 21:15:12";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
//		List<LichSuSanPham> list = repo.listDoanhThuByExactDay(dateTime);
//		list.forEach(l -> System.out.println(l));
//		assertThat(list.size()).isGreaterThan(0);
//	}
//
//
//
//}
