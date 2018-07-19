package com.user.data.view;

import com.user.data.controller.Choice;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.user.data.controller.Choice.*;
import static com.user.data.controller.Choice.LOGIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ViewTest {
    @Test
    public void showMenu() throws Exception {
        View view = new View();
        Map<Choice, Integer> menu = new HashMap<>();

        menu = view.showMenu("MENU", LOGIN, REGISTRATION, EXIT);

        assertThat(menu.get(LOGIN)).isEqualTo(1);
        assertThat(menu.get(REGISTRATION)).isEqualTo(2);
        assertThat(menu.get(EXIT)).isEqualTo(3);
    }

}