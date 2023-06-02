//package com.web.project;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import com.web.project.entity.Role;
//import com.web.project.repository.RoleRepository;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
//public class RoleRepositoryTest {
//	@Autowired
//	private RoleRepository repo;
//
//	@Test
//	public void testCreateRole() {
//		Role role = new Role();
//		role.setId(2);
//		role.setName("nhan vien");
//		role.setMota("quan ly hoa don");
//		Role savedRole =  repo.save(role);
//		assertThat(savedRole).isNotNull();
//
//	}
//
//}
