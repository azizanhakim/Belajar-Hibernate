package com.azizanhakim.testing;

import com.azizanhakim.belajarhibernate.configuration.SessionFactoryUtil;
import com.azizanhakim.belajarhibernate.dao.BukuDao;
import com.azizanhakim.belajarhibernate.dao.KategoriBukuDao;
import com.azizanhakim.belajarhibernate.entity.Buku;
import com.azizanhakim.belajarhibernate.entity.KategoriBuku;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TestBukuDao extends TestCase {

    private SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception{
        SessionFactoryUtil util = new SessionFactoryUtil();
        this.sessionFactory = util.getSessionFactory();
    }

    @Test
    public void testBukuDao(){

        Session session = sessionFactory.openSession();
        BukuDao bukuDao = new BukuDao(session);
        KategoriBukuDao kategoriBukuDao = new KategoriBukuDao(session);
        session.beginTransaction();

        KategoriBuku matematika = new KategoriBuku("Matematika", "Buku Matematika");

        KategoriBuku it = new KategoriBuku("Ilmu Komputer", "Buku Ilmu Komputer");

        kategoriBukuDao.save(Arrays.asList(it, matematika));

        List<KategoriBuku> listKategoryBuku = kategoriBukuDao.findAll();

        Buku pemrograman = new Buku(
                "978-979-18346-3-6",
                "Pemrograman Lanjut",
                "Azizan Hakim",
                2019,
                "Gramedia",
                listKategoryBuku,
                it);



        log.info("BEFORE SAVE PEMROGRAMAN: {}", pemrograman.toString());
        log.info("BEFORE SAVE MATEMATIKA : {}", matematika.toString());

        pemrograman = bukuDao.save(pemrograman);
        matematika = kategoriBukuDao.save(matematika);

        log.info("AFTER SAVE MATEMATIKA : {}", matematika.toString());
        log.info("AFTER SAVE PEMROGRAMAN: {}", pemrograman.toString());
        session.getTransaction().commit();

        assertNotNull(pemrograman.getId());
        assertEquals("NAMA PENGARANG", pemrograman.getNamaPengarang(), "Azizan Hakim");


        List<Buku> daftarBuku = bukuDao.findAll();
        assertEquals("JUMLAH DAFTAR BUKU BERTAMBAH", daftarBuku.size(), 1);

        session.beginTransaction();
        Buku bukuPemrograman = bukuDao.findById(pemrograman.getId());
        bukuPemrograman.setNamaPengarang("elsa nabila");
        bukuDao.update(bukuPemrograman);

        bukuPemrograman = bukuDao.findById(bukuPemrograman.getId());
        log.info("AFTER UPDATE NAMA PENGARANG : {}", bukuPemrograman);

        assertSame("NAMA PENGARANG SAMA DENGAN ELSA NABILA : ", bukuPemrograman.getNamaPengarang(),"elsa nabila");

        session.getTransaction().commit();

        daftarBuku = bukuDao.findAll();
        log.info("Buku findAll(): {}", daftarBuku);
        daftarBuku = bukuDao.findByName("kom");
        log.info("Buku findByName(): {}", daftarBuku);
        daftarBuku = bukuDao.findByKategoryBuku(it.getId());
        log.info("Buku findByKategoriId(): {}", daftarBuku);

        List<KategoriBuku> daftarKategori = kategoriBukuDao.findAll();
        log.info("Kategori findAll(): {}", daftarKategori);
        daftarKategori = kategoriBukuDao.findByName("kom");
        log.info("Kategori findByName(): {}", daftarKategori);
        daftarKategori = kategoriBukuDao.findByNameAndDescription("Ilmu Komputer", "Buku Ilmu Komputer");
        log.info("Kategori findByName(): {}", daftarKategori);
        daftarKategori = kategoriBukuDao.findBetweenByCreatedDate(
                Timestamp.valueOf(
                        LocalDateTime.of(2018, 12, 1, 0, 0, 0)
                ),
                Timestamp.valueOf(
                        LocalDateTime.now().plusDays(2)
                )
        );
        log.info("Kategori findBetweenDate(): {}", daftarKategori);

//        session.getTransaction().commit();

        session.close();
    }

}
