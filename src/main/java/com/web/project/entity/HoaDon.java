package com.web.project.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "donDatHang")
public class HoaDon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	
	@Column(name = "ngay_tao")
	private LocalDateTime ngayTao ;

	@Column(name = "thanh_tien" , nullable = false , length = 50)
	private Double thanhTien ;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer ;

	@Enumerated(EnumType.STRING)
	private OrderStatus status ;

	@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL , orphanRemoval = true)
	@OrderBy("updatedTime ASC")
	private List<OrderTrack> tracks = new ArrayList<>();




	public HoaDon(Double thanhTien, Customer customer) {
		this.thanhTien = thanhTien;
		this.customer = customer;
	}

	public HoaDon(LocalDateTime ngayTao, Double thanhTien, Customer customer) {
		this.ngayTao = ngayTao;
		this.thanhTien = thanhTien;
		this.customer = customer;
	}

	@Transient
	public String getRecipientName() {
		return customer.getFullName();
	}
	@Transient
	public String getRecipientAddress() {
		return customer.getDiaChi();
	}
	@Transient
	public String getPhoneNumber() {
		return customer.getSdt();
	}

	public boolean hasStatus(OrderStatus status) {
		for (OrderTrack aTrack : tracks) {
			if (aTrack.getStatus().equals(status)) {
				return true;
			}
		}

		return false;
	}
	@Transient
	public boolean isPicked() {
		return hasStatus(OrderStatus.PICKED);
	}
	@Transient
	public boolean isShipping() {
		return hasStatus(OrderStatus.SHIPPING);
	}
	@Transient
	public boolean isDelivered() {
		return hasStatus(OrderStatus.DELIVERED);
	}

	@Transient
	public boolean isReturned() {
		return hasStatus(OrderStatus.RETURNED);
	}
}
