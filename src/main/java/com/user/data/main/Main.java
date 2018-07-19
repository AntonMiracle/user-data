package com.user.data.main;

import com.user.data.controller.Controller;
import com.user.data.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        controller.loginPage();
    }
}
