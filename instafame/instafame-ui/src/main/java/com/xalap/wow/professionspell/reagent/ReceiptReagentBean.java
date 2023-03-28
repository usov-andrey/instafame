package com.xalap.wow.professionspell.reagent;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemWithQuantity;
import com.xalap.wow.professionspell.SpellBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Сообщение контакту, входящее и исходящее
 *
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Entity()
@Table(name = ReceiptReagentBean.NAME)
public class ReceiptReagentBean implements IdHolder<Integer>, ItemWithQuantity {

    public static final String NAME = "WOW$ReceiptReagent";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SpellBean receipt;
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemBean item;
    private int quantity;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public SpellBean getReceipt() {
        return receipt;
    }

    public void setReceipt(SpellBean receipt) {
        this.receipt = receipt;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ReceiptReagentBean{" +
                "id=" + id +
                ", receipt=" + receipt +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }
}
