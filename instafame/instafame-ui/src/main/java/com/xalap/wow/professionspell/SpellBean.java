package com.xalap.wow.professionspell;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.StringHelper;
import com.xalap.wow.craft.Profession;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.item.ItemWithQuantity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Entity()
@Table(name = SpellBean.NAME)
public class SpellBean implements IdHolder<Integer>, ItemWithQuantity {

    public static final String NAME = "WOW$Receipt";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + SpellBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + SpellBean.NAME,
            sequenceName = "SEQ_" + SpellBean.NAME)
    private Integer id;
    private String rank;
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ItemBean item;//Что производится по этому рецепту
    private int quantity;//и в каком количестве
    private String skill;
    private Integer skillRequirement;
    private Profession profession;
    @ColumnDefault("false")
    private boolean byTrainer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpellBean that = (SpellBean) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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

    public String name() {
        return (StringHelper.isNotEmpty(rank) ? item.getName() + "(" + rank + ")" : item.getName());
    }

    public String skill() {
        return skill +
                (skillRequirement != null ? "-" + skillRequirement : "");
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getSkillRequirement() {
        return skillRequirement;
    }

    public void setSkillRequirement(Integer skillRequirement) {
        this.skillRequirement = skillRequirement;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isByTrainer() {
        return byTrainer;
    }

    public void setByTrainer(boolean byTrainer) {
        this.byTrainer = byTrainer;
    }

    public boolean isRank1Or2() {
        return StringHelper.isNotEmpty(getRank()) && (getRank().equals("1") || getRank().equals("2"));
    }

    @Override
    public String toString() {
        return "ReceiptBean{" +
                "id=" + id +
                ", rank='" + rank + '\'' +
                ", item=" + item +
                ", quantity=" + quantity +
                ", skill='" + skill + '\'' +
                ", skillRequirement=" + skillRequirement +
                ", profession=" + profession +
                '}';
    }
}
