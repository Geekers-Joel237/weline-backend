package com.geekersjoel237.weline.iam.infrastructure.models;

import com.geekersjoel237.weline.iam.domain.entities.Customer;
import com.geekersjoel237.weline.iam.domain.enums.CustomerStatusEnum;
import com.geekersjoel237.weline.iam.domain.vo.PhoneNumber;
import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;
import com.geekersjoel237.weline.shared.domain.vo.Id;
import com.geekersjoel237.weline.shared.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity extends BaseEntity {


    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatusEnum status;


    public CustomerEntity(String id, String phoneNumber, CustomerStatusEnum status) {
        this.setId(id);
        this.phoneNumber = phoneNumber;
        this.status = status;
    }


    public static CustomerEntity fromDomain(Customer.Snapshot customer) {
        return new CustomerEntity(
                customer.id(),
                customer.phoneNumber(),
                CustomerStatusEnum.valueOf(customer.status())
        );
    }



    public Customer toDomain() {
        try {
            return Customer.createFromAdapter(
                    Id.of(this.getId()),
                    new PhoneNumber(this.getPhoneNumber()),
                    this.getStatus()
            );
        } catch (CustomIllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String status) {
        this.status = CustomerStatusEnum.valueOf(status);
    }
}
