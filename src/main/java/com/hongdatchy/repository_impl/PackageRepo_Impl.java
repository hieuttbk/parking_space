package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.MyPackage;
import com.hongdatchy.repository.PackageRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class, timeout = 30000)
public class PackageRepo_Impl implements PackageRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public MyPackage create(MyPackage myPackage) {
        return entityManager.merge(myPackage);
    }

    @Override
    public List<MyPackage> findAll() {
        return entityManager.createQuery("select x from MyPackage x").getResultList();
    }
}
