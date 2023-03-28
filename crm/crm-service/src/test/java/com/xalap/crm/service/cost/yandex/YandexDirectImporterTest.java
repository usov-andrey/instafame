/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost.yandex;

import com.xalap.crm.service.cost.CostBean;
import com.xalap.crm.service.cost.CostType;
import com.xalap.framework.utils.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Usov Andrey
 * @since 2020-08-26
 */
public class YandexDirectImporterTest {

    private YandexDirectImporter importer;

    @BeforeAll
    public void initUseCase() {
        importer = new YandexDirectImporter();
    }

    @Test
    public void testImport() {
        Path csvFile = Paths.get("src", "test", "resources", "yandexDirect.csv");
        List<CostBean> costBeans = importer.importFromCsv(csvFile);
        assertThat(costBeans).hasSize(25);
        CostBean costBean = costBeans.get(0);
        assertThat(costBean.getCostType()).isEqualTo(CostType.yandexDirect);
        assertThat(costBean.getCost()).isEqualTo(BigDecimal.valueOf(413.89d));
        assertThat(costBean.getCostTime()).isEqualTo(DateUtils.parseDateWithRuFormat("01.08.2020"));
        costBean = costBeans.get(24);
        assertThat(costBean.getCostType()).isEqualTo(CostType.yandexDirect);
        assertThat(costBean.getCost()).isEqualTo(BigDecimal.valueOf(1003.69));
        assertThat(costBean.getCostTime()).isEqualTo(DateUtils.parseDateWithRuFormat("25.08.2020"));
    }
}
