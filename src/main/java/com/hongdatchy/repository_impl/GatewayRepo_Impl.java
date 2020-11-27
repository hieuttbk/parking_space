package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.FieldGateway;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.repository.FieldRepo;
import com.hongdatchy.repository.GatewayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class GatewayRepo_Impl implements GatewayRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    FieldRepo fieldRepo;

    @Override
    public Gateway createAndUpdate(Gateway gateway) {
        return entityManager.merge(gateway);
    }

    @Override
    public boolean delete(int id) {
        Gateway gateway = entityManager.find(Gateway.class, id);
        if(gateway != null){
            Query query = entityManager.createQuery("delete from Detector x where x.gatewayId =:id");
            query.setParameter("id", id).executeUpdate();

            Query query2 = entityManager.createQuery("delete from FieldGateway x where x.gatewayId =:id");
            query2.setParameter("id", id).executeUpdate();

            entityManager.remove(gateway);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Gateway> findAll() {
        Query query = entityManager.createQuery("select x from Gateway x");
        return query.getResultList();
    }

    @Override
    public Gateway findById(int id) {
        return entityManager.find(Gateway.class, id);
    }

    @Override
    public List<Gateway> managerFind(Manager manager) {
        List<Field> fields = fieldRepo.managerFind(manager);
        List<Gateway> gateways = new ArrayList<>();
        for (Field field: fields) {
            List<FieldGateway> fieldGateways = entityManager
                    .createQuery("select x from FieldGateway x where x.fieldId =:id")
                    .setParameter("id", field.getId())
                    .getResultList();
            for (FieldGateway fieldGateway: fieldGateways) {
                gateways.add(entityManager.find(Gateway.class,fieldGateway.getGatewayId()));
            }
        }
        return gateways;
    }

    @Override
    public Gateway managerUpdate(Gateway gateway, Manager manager) {
        if(gateway.getId() == null || entityManager.find(Gateway.class, gateway.getId()) == null){
            return null;
        }
        return check(gateway, manager) ? entityManager.merge(gateway) : null;
    }

    @Override
    public boolean managerDelete(int id, Manager manager) {
        Gateway gateway = entityManager.find(Gateway.class, id);
        if(gateway == null){
            return false;
        }else if(check(gateway, manager)){
            entityManager.remove(gateway);
        }
        return false;
    }

    boolean check(Gateway gateway, Manager manager){
        List<Gateway> gateways = managerFind(manager);
        for (Gateway g: gateways) {
            if(g.getId() == gateway.getId()) return true;
        }
        return  false;
    }
}
