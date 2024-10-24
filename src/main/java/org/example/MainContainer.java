package org.example;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainContainer {

    public static void main(String[] args) {
        // Получение экземпляра JADE Runtime
        Runtime rt = Runtime.instance();

        // Создание профиля контейнера
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.MAIN_PORT, "1099");
        p.setParameter(Profile.GUI, "true");

        // Создание главного контейнера
        ContainerController mainContainer = rt.createMainContainer(p);

        try {
            // Создание и запуск агентов
            AgentController agent1 = mainContainer.createNewAgent("Agent1", "org.example.FunctionAgent", new Object[]{"e^(0.2x)"});
            agent1.start();

            AgentController agent2 = mainContainer.createNewAgent("Agent2", "org.example.FunctionAgent", new Object[]{"2^(-1x)"});
            agent2.start();

            AgentController agent3 = mainContainer.createNewAgent("Agent3", "org.example.FunctionAgent", new Object[]{"cos(x)"});
            agent3.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

