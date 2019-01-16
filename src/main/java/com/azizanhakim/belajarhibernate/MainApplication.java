package com.azizanhakim.belajarhibernate;

import com.azizanhakim.belajarhibernate.configuration.SessionFactoryUtil;
import com.azizanhakim.belajarhibernate.dao.BukuDao;
import com.azizanhakim.belajarhibernate.entity.Buku;
import com.azizanhakim.belajarhibernate.entity.KategoriBuku;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication {

    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args){

        System.out.println("saya saya dia dia");

        SessionFactoryUtil util = new SessionFactoryUtil();
        SessionFactory sessionFactory = util.getSessionFactory();
        Session session = sessionFactory.openSession();

        BukuDao dao = new BukuDao(session);

        session.beginTransaction();

        log.info("hibernate connection open!");

        session.getTransaction().commit();

        session.close();
        sessionFactory.close();

        log.info("hibernate connection close!");

    }
}
