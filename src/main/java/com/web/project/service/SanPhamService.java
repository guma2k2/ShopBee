package com.web.project.service;

import com.web.project.entity.SanPham;
import com.web.project.repository.SanPhamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class SanPhamService {

    public static final  int sanPhamMoiPage = 5 ;

    @Autowired
    private SanPhamRepository repo;

    public void updateTrangThai(Integer id , boolean status){
        repo.updateStatus(id , status);
    }

    public Page<SanPham> listByPage(String sortDir , String sortField , String keywod , int pageNum){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1 , sanPhamMoiPage , sort);
        if(keywod != null){
            return repo.findAll(keywod , pageable);
        }
        return repo.findAll(pageable);
    }

    public List<SanPham> listAll(){
        return repo.findAll();
    }
    public SanPham save(SanPham sanPham){
        return repo.save(sanPham);
    }

    public SanPham get(Integer id) throws SanPhamNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException e){
            throw new SanPhamNotFoundException("không tìm thấy sản phẩm có id là " + id);
        }
    }

    public void delete(Integer id) throws SanPhamNotFoundException {
        Long countedSp = repo.countById(id);
        if(countedSp == null || countedSp == 0){
            throw new SanPhamNotFoundException("Không tìm thấy sản phẩm có id là " + id);
        }
        repo.deleteById(id);
    }

    public boolean checkTenUnique(String ten , Integer id){
        SanPham sanPham = repo.findByTen(ten);
        if(sanPham == null) return true;
        boolean isCreateNew = (id == null);
        if(isCreateNew){
            if(sanPham != null){
                return false ;
            }
        }else{
            if(sanPham.getId() != id){
                return false ;
            }
        }
        return true;
    }

    public List<SanPham> getSpTheoLoai(Integer id){
        return repo.listSpTheoLoai(id);
    }
}
