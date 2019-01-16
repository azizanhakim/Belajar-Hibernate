package com.azizanhakim.belajarhibernate.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"list"})
@Entity
@Table(name = "master_buku")
public class Buku {

    @Id
    @GenericGenerator(name = "uuid_gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid_gen")
    @Column(name = "kode", nullable = false, unique = true, length = 64)
    private String id;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String judul;

    @Column(name = "nama_pengarang")
    private String namaPengarang;

    @Column(name = "tahun_terbit")
    private Integer tahunTerbit;

    @Column(name = "nama_penerbit")
    private String namaPenerbit;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @OneToOne
    @JoinColumn(name = "kategori_id")
    private KategoriBuku kategoryBuku;

    @OneToMany
    @JoinTable(
            name = "daftar_kategory_buku",
            joinColumns = @JoinColumn(
                    name = "buku_id", nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "kategori_id", nullable = false)
    )
    private List<KategoriBuku> list = new ArrayList<>();

    public Buku(String isbn, String judul, String namaPengarang, Integer tahunTerbit, String namaPenerbit, List<KategoriBuku> listKategory, KategoriBuku kategory) {
        this.isbn = isbn;
        this.judul = judul;
        this.namaPengarang = namaPengarang;
        this.tahunTerbit = tahunTerbit;
        this.namaPenerbit = namaPenerbit;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.createdBy = "admin";
        this.kategoryBuku = kategory;
        this.setList(listKategory);
    }

    public Buku(){

    }

}
