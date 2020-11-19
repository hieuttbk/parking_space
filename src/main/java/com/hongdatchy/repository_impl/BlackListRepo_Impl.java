package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.BlackList;
import com.hongdatchy.repository.BlackListRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class BlackListRepo_Impl implements BlackListRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public BlackList create(String token) {
        return entityManager.merge(new BlackList(null, token));
    }

    public List<BlackList> findByToken(String token){
        Query query = entityManager.createQuery("select b from BlackList b where b.token = :token");
        query.setParameter("token", token);
        return query.getResultList();
    }

}
