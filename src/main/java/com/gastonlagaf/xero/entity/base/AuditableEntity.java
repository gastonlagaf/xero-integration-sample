package com.gastonlagaf.xero.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

    public LocalDate createdAt = LocalDate.now();

    public LocalDate updatedAt = LocalDate.now();

}
