package com.web.project.service;

import com.web.project.entity.AuthenticationType;
import com.web.project.entity.Customer;
import com.web.project.entity.NhanVien;
import com.web.project.entity.Role;
import com.web.project.repository.CustomerRepository;
import com.web.project.repository.NhanVienRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private NhanVienRepository nhanVienRepo;
    @Autowired
    private CustomerRepository customerRepository;

    public void addNewCustomerOauth(String name , String email ,AuthenticationType type ){
        NhanVien customer = new NhanVien();
        Customer newCustomer = new Customer() ;
        setName(name , customer);
        newCustomer.setEmail(email);
        newCustomer.setHo(customer.getHo());
        newCustomer.setTen(customer.getTen());
        customer.addRole(new Role(3));
        customer.setEmail(email);
        customer.setTrangThai(true);
        customer.setDiaChi("");
        customer.setPhotos("");
        customer.setPassword("");
        customer.setSdt("");
        customer.setAuthenticationType(type);
        customerRepository.save(newCustomer) ;
        nhanVienRepo.save(customer);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer) ;
    }
    public void setName(String name ,NhanVien customer ){
        String[] full = name.split(" ");
        if(full.length < 2){

            customer.setHo(name);
            customer.setTen("");
        }else{
            String first = full[0];
            customer.setHo(first);
            customer.setTen(name.replace(first , ""));
        }
    }
    public NhanVien findByEmail(String email) {
        return nhanVienRepo.findByEmail(email);
    }
    public Customer findByEmailCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    public void updateAuthenticationType(AuthenticationType type , Integer id) {
            nhanVienRepo.updateAuthenticationType(type , id);
    }

}
