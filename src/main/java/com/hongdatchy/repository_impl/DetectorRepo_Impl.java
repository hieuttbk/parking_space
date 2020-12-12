package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.repository.DetectorRepo;
import com.hongdatchy.repository.GatewayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class DetectorRepo_Impl implements DetectorRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    GatewayRepo gatewayRepo;

    @Override
    public Detector createAndUpdate(Detector detector) {
        return entityManager.merge(detector);
    }

    @Override
    public boolean delete(int id) {
        Detector detector = entityManager.find(Detector.class, id);
        if(detector != null){
            entityManager.remove(detector);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Detector> findAll() {
        Query query = entityManager.createQuery("select d from Detector d");
        return query.getResultList();
    }

    @Override
    public List<Detector> findBySlotId(int id) {
        Query query = entityManager.createQuery("select d from Detector d where d.slotId = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Detector> managerFind(Manager manager) {
        List<Gateway> gateways = gatewayRepo.managerFind(manager);
        List<Detector> detectors = new ArrayList<>();
        for(Gateway gateway: gateways){
            detectors.addAll(entityManager.createQuery("select x from Detector x where x.gatewayId =:id")
                    .setParameter("id", gateway.getId()).getResultList());
        }
        return detectors;
    }

    @Override
    public Detector managerCreateAndUpdate(Detector detector, Manager manager) {
        return check(detector, manager) ? entityManager.merge(detector) : null;
    }

    @Override
    public boolean managerDelete(int id, Manager manager) {
        Detector detector = entityManager.find(Detector.class, id);
        if(detector == null){
            return false;
        }else if(check(detector, manager)){
            entityManager.remove(detector);
        }
        return false;
    }

    @Override
    public Detector findById(int id) {
        return entityManager.find(Detector.class, id);
    }

    boolean check(Detector detector, Manager manager){
        List<Detector> detectors = managerFind(manager);
        for(Detector d: detectors){
            if(d.getId() == detector.getId()) return true;
        }
        return false;
    }

}
