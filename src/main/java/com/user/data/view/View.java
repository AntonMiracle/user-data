package com.user.data.view;

import com.user.data.controller.Choice;

import java.util.HashMap;
import java.util.Map;

public class View {

    public Map<Choice, Integer> showMenu(String head, Choice... choices) {
        Map<Choice, Integer> choiceWithNumber = new HashMap<>();
        int choiceNumber = 0;

        showHead(head);
        for (Choice choice : choices) {
            System.out.println(++choiceNumber + " | " + choice);
            choiceWithNumber.put(choice, Integer.valueOf(choiceNumber));
        }
        showFoot(head);
        System.out.println("Make your choice (1" + "-" + choices.length + "):");
        return choiceWithNumber;
    }

    private void showHead(String head) {
        System.out.println("=========== " + head + " ===========");
    }

    private void showFoot(String head) {
        StringBuilder foot = new StringBuilder("========================");
        for (int i = 0; i < head.length(); ++i) foot.append("=");
        System.out.println(foot.toString());
    }


}


