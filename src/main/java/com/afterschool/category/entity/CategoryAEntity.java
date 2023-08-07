package com.afterschool.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.*;

@Entity(name="CATEGORY_A")
@Table(name="TBL_CATEGORY_A")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableGenerator(name = "CL_ID_GENERATOR",
						table = "CL_ID_GEN",
						pkColumnName = "ENTITY",
						pkColumnValue = "CATEGORY_A",
						valueColumnName = "NEXT_ID",
						initialValue = 0,
						allocationSize = 1)
public class CategoryAEntity {
	
	@Id
    @GeneratedValue(generator = "CL_ID_GENERATOR")
	private Long aSeq;

    @Column(name = "class_name")
    private String className;
	
}
