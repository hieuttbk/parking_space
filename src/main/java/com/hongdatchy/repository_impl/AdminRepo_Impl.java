package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Admin;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.repository.AdminRepo;
import com.hongdatchy.security.SHA256Service;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class AdminRepo_Impl implements AdminRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean login(LoginForm loginForm) {
        List<Admin> admins = entityManager
                .createQuery("select a from Admin a where a.phone= :phone and a.pass = :password")
                .setParameter("phone", loginForm.getPhone())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        return admins.size() != 0;
    }
    @Override
    public Admin findByPhone(String phone) {
        List<Admin> admins = entityManager
                .createQuery("select a from Admin a where a.phone= :phone").setParameter("phone", phone)
                .getResultList();
        if(admins.size() == 1){
            return admins.get(0);
        }
        return null;
    }
}