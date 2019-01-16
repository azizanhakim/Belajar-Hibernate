package com.azizanhakim.belajarhibernate.dao;

import com.azizanhakim.belajarhibernate.entity.KategoriBuku;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.List;

public class KategoriBukuDao {

    private final Session session;

    public KategoriBukuDao(Session session) {
        this.session = session;
    }

    /**
     * Penggunaan query Criteria API
     * query ini sama seperti
     * select * from master_ketegori_buku;
     *
     * @return
     */
    public List<KategoriBuku> findAll() {
        return this.session.createQuery("from KategoriBuku").getResultList();
    }

    /**
     * query ini sama seperti
     * select * from master_kategory_buku where kategory like ?
     *
     * @param name
     * @return
     */
    public List<KategoriBuku> findByName(String name) {
        Criteria criteria = this.session.createCriteria(KategoriBuku.class);
        criteria.add(Restrictions.ilike("nama", name, MatchMode.ANYWHERE));
        return criteria.list();
    }

        /**
         * query ini sama seperti
         * select * from master_kategori_buku where kategory like ? and description = ?
         *
         * @param name
         * @param description
         * @return
         */
    public List<KategoriBuku> findByNameAndDescription(String name, String description) {
        Criteria criteria = this.session.createCriteria(KategoriBuku.class);
        criteria.add(Restrictions.and(
                Restrictions.ilike("nama", name, MatchMode.ANYWHERE),
                Restrictions.ilike("description", description, MatchMode.ANYWHERE)
        ));
        return criteria.list();
    }


    /**
     * query ini sama seperti
     * select * from master_kategori_buku where created_date between ? and ?
     * @param lowerDate
     * @param upperDate
     * @return
     */
    public List<KategoriBuku> findBetweenByCreatedDate(Timestamp lowerDate, Timestamp upperDate) {
        Criteria criteria = this.session.createCriteria(KategoriBuku.class);
        criteria.add(Restrictions.between("createdDate", lowerDate, upperDate));
        criteria.addOrder(Order.desc("nama"));
        return criteria.list();
    }

    public KategoriBuku save(KategoriBuku kategory) {
        Integer primaryKey = (Integer) this.session.save(kategory);
        return this.findById(primaryKey);

    }

    public void save(List<KategoriBuku> list) {
        for (KategoriBuku kategory : list) {
            this.session.save(kategory);
        }
    }

    public KategoriBuku findById(Integer id) {
        return this.session.find(KategoriBuku.class, id);
    }
}
