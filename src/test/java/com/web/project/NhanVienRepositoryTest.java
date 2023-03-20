package com.web.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.web.project.entity.NhanVien;
import com.web.project.entity.Role;
import com.web.project.repository.NhanVienRepository;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NhanVienRepositoryTest {

	@Autowired 
	private NhanVienRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNhanVien() {
		NhanVien nhanVien = new NhanVien(2 , "Ngo" ,"Thuan" , "asfafafa" , "asfasf" );
		Role roleAdmin = entityManager.find(Role.class, 1);
		
		nhanVien.addRole(roleAdmin);
		
		NhanVien NV = repo.save(nhanVien);
		assertThat(NV.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNhanVienWithNhieuRole() {
		Role role1 = new Role(2);
		Role role2 = new Role(1);
		NhanVien nhanVien = new NhanVien("Ngo" ,"Thuan" , "091414132" , "thuan@gmail.com" );
		nhanVien.addRole(role1);
		nhanVien.addRole(role2);
		NhanVien NV = repo.save(nhanVien);
		assertThat(NV.getId()).isGreaterThan(0);
	}
	@Test
	public void listAllNhanVien() {
		Iterable<NhanVien> listNhanViens = repo.findAll();
		listNhanViens.forEach(nv -> System.out.println(nv));
		assertThat(listNhanViens).isNotNull();
	}
	@Test 
	public void testUpdateNhanVien() {
		NhanVien nv = repo.findById(1).get();
		Role role = new Role(1);
		nv.getRoles().remove(role);
		repo.save(nv);
		
	}
	@Test 
	public void testDelete() {
		NhanVien nv = repo.findById(5).get();
		repo.delete(nv);
		
	}
	@Test
	public void checkEmail() {
		String email  = "nam@codejava.net"; 
		NhanVien nhanVien = repo.findByEmail(email);
		assertThat(nhanVien).isNotNull();
	}
	
	
}
