package com.afterschool.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name="school")
@Table(name="tbl_school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolEntity {

    @Id
    @Column(name="school_seqno")
    private int schoolSeqno;
    
    @Column(name="school_name", length=100, nullable=true)
    private String schoolName;

    @Column(name="school_addr", length=200, nullable=true)
    private String schoolAddr;
    
}
