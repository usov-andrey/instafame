/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser.task;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.CreateCommentTasks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Usov Andrey
 * @since 18.06.2021
 */
public class CreateCommentTaskTest {

    private IOBean bean;
    private CreateCommentTasks parser;

    @BeforeEach
    public void initUseCase() {
        bean = new IOBean();
        parser = new CreateCommentTasks();
    }

    @Test
    public void testUrl() {
        String comments = "www.instagram.com/p/CQMCbBkrnSZ/\n" +
                "Мягкая игрушка розовый слон. Постоянно берет с собой в кровать.\n" +
                "Резиновые ящерицы!\n" +
                "Коляска) для кукол\n" +
                "Ниндзя-черепашка. Не расстается с ней даже когда кушает.\n" +
                "Недавно сделали игрушки из носка. Очень понравилась \uD83D\uDE02";
        List<CreateCommentTasks.UrlAndMedias> result = parser.parse(comments);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUrl()).isEqualTo("www.instagram.com/p/CQMCbBkrnSZ/");
        assertThat(result.get(0).getComments().size()).isEqualTo(5);
    }

    @Test
    public void testEmptyStrings() {
        String comments = "https://www.instagram.com/andreyusov.ru/\n" +
                "О, спасибо, было актуально!\n" +
                "\n" +
                "Интересно, все говорят про стратегию, но ведь раньше никто не задавался этим вопросом, и все норм \uD83E\uDD14\n" +
                "\n" +
                "Спасибо))) Часто встречаю у вас полезную информацию \uD83D\uDC4D\uD83C\uDFFB\n" +
                "\n" +
                "Вы можете помочь в построении стратегии ?\n" +
                "\n" +
                "Где можно ознакомиться с ценами на услуги?\n" +
                "\n" +
                "Как получить консультацию ?\n" +
                "\n" +
                "Сложно. Самостоятельно это вообще возможно ?\n" +
                "\n" +
                "Все очень понятным языком написано, спасибо\uD83D\uDE0A\n" +
                "\n" +
                "\uD83D\uDC4D\uD83C\uDFFE\uD83D\uDC4D\uD83C\uDFFE\uD83D\uDC4D\uD83C\uDFFE\n" +
                "\n" +
                "Интересно, спасибо)))))\n" +
                "www.instagram.com/p/CQMCbBkrnSZ/\n" +
                "Мягкая игрушка розовый слон. Постоянно берет с собой в кровать.\n" +
                "Резиновые ящерицы!\n" +
                "Коляска) для кукол\n" +
                "Ниндзя-черепашка. Не расстается с ней даже когда кушает.\n" +
                "Недавно сделали игрушки из носка. Очень понравилась \uD83D\uDE02";
        List<CreateCommentTasks.UrlAndMedias> result = parser.parse(comments);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUrl()).isEqualTo("https://www.instagram.com/andreyusov.ru/");
        assertThat(result.get(0).getComments().size()).isEqualTo(10);
        assertThat(result.get(1).getUrl()).isEqualTo("www.instagram.com/p/CQMCbBkrnSZ/");
        assertThat(result.get(1).getComments().size()).isEqualTo(5);
    }
}
