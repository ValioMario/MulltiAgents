package org.example;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Random;

public class FunctionAgent extends Agent {

    private double x;
    private double delta;
    private String functionType;

    @Override
    protected void setup() {
        // Получаем аргументы из командной строки
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            functionType = (String) args[0];
        } else {
            functionType = "default";
        }

        // Инициализация начальных значений
        Random random = new Random();
        x = random.nextDouble() * 10; // Случайное значение в диапазоне [0, 10)
        delta = 1.0;

        // Добавляем поведение для обработки сообщений
        addBehaviour(new MessageHandler());
    }

    private class MessageHandler extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                // Обработка запроса на расчет значения функции
                String content = msg.getContent();
                String[] parts = content.split(",");
                double x1 = Double.parseDouble(parts[0]);
                double x2 = Double.parseDouble(parts[1]);
                double x3 = Double.parseDouble(parts[2]);

                // Расчет значений функции для трех точек
                double y1 = FunctionCalculator.calculate(functionType, x1);
                double y2 = FunctionCalculator.calculate(functionType, x2);
                double y3 = FunctionCalculator.calculate(functionType, x3);

                // Отправка ответа
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(y1 + "," + y2 + "," + y3);
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
