package com.realfinance.sofa.cg.sup.repository;


import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface SupplierAnnouncementRepository extends JpaRepositoryImplementation<SupplierAnnouncement,Integer> {

    /*  @Query("UPDATE cg_sup_supplier_announcement SET channels_id= ?1 WHERE id=?2")
    @Modifying
    void updateAnnouncement(Integer id,Integer id1);*/

}
