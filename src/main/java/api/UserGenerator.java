package api;

import io.qameta.allure.Step;


public class UserGenerator {

    @Step("Создание пользователя")
    public static User testUser() {
        return new User()
                .withEmail("test" + System.currentTimeMillis() + "@example.com")
                .withPassword("1111122222")
                .withName("Lenkapenka");
    }

}
