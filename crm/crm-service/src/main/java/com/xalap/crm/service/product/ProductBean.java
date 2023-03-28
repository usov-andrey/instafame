/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.product;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdNameHolder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Entity()
@Table(name = ProductBean.NAME)
public class ProductBean implements IdNameHolder<Integer> {

    public static final String NAME = "CRM$PRODUCT";
    public static final String CODE = "code";
    public static final String NAME_FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(NAME_FIELD_NAME)
    private String name;
    @FieldName(CODE)
    private String code;
    @ColumnDefault("0.0d")
    private double price;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductBean that = (ProductBean) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
