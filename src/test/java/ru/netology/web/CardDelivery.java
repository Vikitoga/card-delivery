package ru.netology.web;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDelivery {

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    public static String createDate() {
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String myDate = date.format(pattern);
        return myDate;
    }

    @Test
    public void shouldPositiveTest() {
        String deliveryDate = createDate();
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(deleteString);
        $("[data-test-id='date'] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79895646936");
        $("[class='checkbox__box']").click();
        $$("button").find(exactText("Забронировать")).click();
        String text = $(".notification_visible .notification__content").shouldBe(visible, Duration.ofSeconds(15)).getText();
        assertEquals("Встреча успешно забронирована на "+ deliveryDate, text.trim());
    }
}