package com.web.project.service;

import com.web.project.entity.*;
import com.web.project.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private OrderTrackService orderTrackService ;

    @Autowired
    private CustomerService customerService ;

    public HoaDon saveHoaDon(Double tongTien , NhanVien khachHang ){
        LocalDateTime now = LocalDateTime.now();
        Customer customer = customerService.findByEmailCustomer(khachHang.getEmail()) ;
        HoaDon hoaDon = new HoaDon(now, tongTien , customer );
        hoaDon.setStatus(OrderStatus.NEW);
        return hoaDonRepository.save(hoaDon);
    }

    public List<HoaDon> listAll(){
        return hoaDonRepository.findAll();
    }

    public HoaDon get(Integer id) throws HoaDonNotFoundExeption {
        try{
            HoaDon hoaDon = hoaDonRepository.findById(id).get();
            return  hoaDon;
        }catch (NoSuchElementException ex){
            throw new HoaDonNotFoundExeption("Khong tim thay hoa don nao ca ");
        }
    }
    public Long countByDate(LocalDate fromDate , LocalDate toDate){
        return hoaDonRepository.countByDate(fromDate,toDate);
    }
    public Double tongDoanhTheoNgay(LocalDate fromDate , LocalDate toDate){
        return hoaDonRepository.tongDoanhTheoNgay(fromDate,toDate);
    }
    public List<HoaDon> findByEmail(String email){
        return hoaDonRepository.findByEmail(email);
    }

    public boolean canDeleteNv(Integer id) {
        // kiểm tra xem nhân viên đó có nằm trong đơn đặt hàng không
        return hoaDonRepository.findByIdNhanVien(id).isEmpty();
    }

    public void updateOrderTrack(Integer orderId , String statusName, NhanVien shipper)
            throws HoaDonNotFoundExeption {

        // Lấy ra đơn đặt hàng theo id
        HoaDon order = hoaDonRepository.findById(orderId).orElseThrow(() -> new HoaDonNotFoundExeption("Hoa don not found")) ;

        // Tạo ra một OrderTrack
        OrderTrack orderTrack = new OrderTrack() ;
        orderTrack.setUpdatedTime(LocalDateTime.now());
        orderTrack.setShipper(shipper);

        // Các trạng thái phải đươc cập nhật tuần tự
        // Ví dụ Trước khi cập nhật tràng thái SHIPPING thì phải cập nhật trạng thái PICKED
        switch (statusName){
            case "PICKED":
                orderTrack.setStatus(OrderStatus.PICKED);
                orderTrack.setNotes(OrderStatus.PICKED.defaultDescription());
                break;
            case "SHIPPING":
                if(!order.isPicked()){
                    throw new HoaDonNotFoundExeption("Vui lòng cập nhật trạng thái picked trước khi shipping") ;
                }
                orderTrack.setStatus(OrderStatus.SHIPPING);
                orderTrack.setNotes(OrderStatus.SHIPPING.defaultDescription());
                break;
            case "DELIVERED":
                if(!order.isShipping()){
                    throw new HoaDonNotFoundExeption("Vui lòng cập nhật trạng thái SHIPPING trước khi DELIVERED") ;
                }
                orderTrack.setStatus(OrderStatus.DELIVERED);
                orderTrack.setNotes(OrderStatus.DELIVERED.defaultDescription());
                break;
            default:
                if(!order.isReturned()){
                    throw new HoaDonNotFoundExeption("Vui lòng cập nhật trạng thái DELIVERED trước khi RETURNED") ;
                }
                orderTrack.setStatus(OrderStatus.RETURNED);
                orderTrack.setNotes(OrderStatus.RETURNED.defaultDescription());
                break;
        }
        orderTrack.setOrder(order);
        OrderStatus statusToUpdate = OrderStatus.valueOf(statusName);
        order.setStatus(statusToUpdate);
        order.getTracks().add(orderTrack);
        hoaDonRepository.save(order);
    }
    public void returnOrder(Integer orderId, String reason , String note) throws HoaDonNotFoundExeption {
        HoaDon order = hoaDonRepository.findById(orderId).orElseThrow(() ->
                new HoaDonNotFoundExeption("Hoa don not found")) ;
        // Tạo một OrderTrack với trạng thái `RETURNED`
        OrderTrack orderTrack = new OrderTrack() ;
        orderTrack.setUpdatedTime(LocalDateTime.now());
        orderTrack.setStatus(OrderStatus.RETURNED);
        orderTrack.setOrder(order);
        String notes = "" ;
        if(!"".equals(reason)) {
            notes+="Reason: " + reason ;
        }
        String firstChar = note.substring(0, 1);
        String upperCaseFirstChar = firstChar.toUpperCase();
        String result = upperCaseFirstChar + note.substring(1);
        notes+= " " +  result;
        orderTrack.setNotes(notes);
        OrderStatus statusToUpdate = OrderStatus.RETURNED;
        order.setStatus(statusToUpdate);
        order.getTracks().add(orderTrack);

        // Lưu từng thông tin của Order Track
        hoaDonRepository.save(order);
    }
}
