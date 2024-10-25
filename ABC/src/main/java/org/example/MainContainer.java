package org.example;

import jade.core.Profile;//Профиль содержит параметры конфигурации для запуска контейнера
import jade.core.ProfileImpl;
import jade.core.Runtime;//отвечает за создание и управление контейнерами
import jade.wrapper.AgentController;//управлять агентами
import jade.wrapper.ContainerController;//управление контейнерами
import jade.core.AID;//идентификатор агента

public class MainContainer {

    public static void main(String[] args) {
        // Создаем экземпляр JADE Runtime
        Runtime rt = Runtime.instance();

        // Создаем профиль контейнера
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");//хост, где запускается контейнер
        p.setParameter(Profile.MAIN_PORT, "1099");//порт, на котором будет запущен основной контейнер
        p.setParameter(Profile.GUI, "true"); // Запускаем GUI для управления агентами

        // Создаем основной контейнер
        ContainerController cc = rt.createMainContainer(p);

        try {
            // Создаем и запускаем агентов
            AgentController agent1 = cc.createNewAgent("Agent1", "org.example.FunctionAgent", new Object[]{true, "exp", new AID("Agent2", AID.ISLOCALNAME), new AID("Agent3", AID.ISLOCALNAME)});
            AgentController agent2 = cc.createNewAgent("Agent2", "org.example.FunctionAgent", new Object[]{false, "pow", new AID("Agent1", AID.ISLOCALNAME), new AID("Agent3", AID.ISLOCALNAME)});
            AgentController agent3 = cc.createNewAgent("Agent3", "org.example.FunctionAgent", new Object[]{false, "cos", new AID("Agent1", AID.ISLOCALNAME), new AID("Agent2", AID.ISLOCALNAME)});
            //агенты создаются с помощщью класса FunctionAgent
            agent1.start();
            agent2.start();
            agent3.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}