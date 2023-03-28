/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost.yandex;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVReader;
import com.xalap.crm.service.cost.CostBean;
import com.xalap.crm.service.cost.CostType;
import com.xalap.framework.utils.DateUtils;
import com.xalap.framework.utils.StringHelper;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Импорт затрат в yandex direct в виде csv файла
 *
 * @author Usov Andrey
 * @since 2020-08-26
 */
public class YandexDirectImporter {

    public List<CostBean> importFromCsv(InputStream inputStream) {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return read(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Error on read csv:" + inputStream, e);
        }
    }

    public List<CostBean> importFromCsv(Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            return read(reader);
        } catch (IOException e) {
            throw new IllegalStateException("Error on read csv:" + path, e);
        }
    }

    private List<CostBean> read(Reader reader) throws IOException {
        CSVReader csvReader = new CSVReader(reader);
        csvReader.setSeparatorChar(';');
        return read(csvReader);
    }

    private List<CostBean> read(CSVReader csvReader) throws IOException {
        List<CostBean> result = new ArrayList<>();
        boolean isData = false;
        for (ICommonsList<String> strings : csvReader.readAll()) {
            //Колонки
            //Дата	Показы	Клики	CTR (%)	Расход (руб.)	Ср. цена клика (руб.)	Глубина (стр.)
            if (isData) {
                if (strings.getCount() < 5) {
                    throwIllegal("Wrong format", strings);
                }
                String dateValue = strings.getAtIndex(0);
                if (dateValue == null) {
                    throwIllegal("Date value is null", strings);
                }
                String expenseValue = strings.getAtIndex(4);
                if (expenseValue == null) {
                    throwIllegal("Expense value is null", strings);
                }
                expenseValue = expenseValue.replace(",", ".");
                LocalDate date = DateUtils.parseDateWithRuFormat(dateValue);
                BigDecimal expense = new BigDecimal(expenseValue);
                CostBean bean = getCostBean(date, expense);
                result.add(bean);
            } else {
                for (String string : strings) {
                    if ("Дата".equals(string)) {
                        //Со следующей строки начинаются данные
                        isData = true;
                        break;//переходим на строку с данными
                    }
                }
            }
        }
        return result;
    }

    private CostBean getCostBean(LocalDate date, BigDecimal expense) {
        CostBean bean = new CostBean();
        bean.setCost(expense);
        bean.setCostTime(date);
        bean.setCostType(CostType.yandexDirect);
        return bean;
    }

    private void throwIllegal(String message, ICommonsList<String> strings) {
        throw new IllegalStateException(message + ":" + StringHelper.join(strings, ";"));
    }
}
