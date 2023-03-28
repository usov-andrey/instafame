/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.recovery;

import java.util.Date;

/**
 * Колонки: Дата, Доход, Расход, Расход на восстановление, Процент восстановления в расходах, Прибыль в рублях.
 *
 * @author Усов Андрей
 * @since 06/08/2019
 */
public class IORecoveryGridBean {

    private Date time;
    private Double expense;
    private Double expenseByRecovery;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public Double getExpenseByRecovery() {
        return expenseByRecovery;
    }

    public void setExpenseByRecovery(Double expenseByRecovery) {
        this.expenseByRecovery = expenseByRecovery;
    }

    public double getExpenseByRecoveryPercent() {
        return expense != 0 ? expenseByRecovery * 100 / expense : 0;
    }

}
