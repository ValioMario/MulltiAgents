package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.core.AID;
import java.util.ArrayList;
import java.util.List;

public class FunctionAgent extends Agent {

    private double currentX;
    private double delta;
    private boolean isInitiator;
    private List<AID> otherAgents;
    private List<Double> functionValues;
    private String functionType; // Тип функции для каждого агента
    private boolean waitingForReplies; // Состояние ожидания ответов
    private double[] points; // Добавляем переменную points
    private static final double DELTA_THRESHOLD = 0.01; // Порог точности для delta

    @Override
    protected void setup() {
        // Инициализация начальных значений
        currentX = Math.random() * 10; // Случайное начальное значение X в диапазоне [0, 10)
        delta = 1.0; // Начальное значение delta
        isInitiator = false; // По умолчанию агент не является инициатором
        otherAgents = new ArrayList<>();
        functionValues = new ArrayList<>(); // Инициализация списка значений функций
        waitingForReplies = false; // Инициализация состояния ожидания ответов

        // Проверяем, является ли агент инициатором и тип функции
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            isInitiator = (Boolean) args[0];
            functionType = (String) args[1]; // Тип функции
            for (int i = 2; i < args.length; i++) {
                otherAgents.add((AID) args[i]);
            }
        }

        // Добавляем поведение для обработки сообщений
        addBehaviour(new MessageHandler());

        // Добавляем поведение для инициации расчетов
        addBehaviour(new InitiateCalculationBehaviour());
    }

    // Поведение для обработки сообщений
    private class MessageHandler extends CyclicBehaviour {
        @Override
        public void action() {
            // Ожидаем сообщения с запросом на расчет значения функции
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Получили запрос на расчет значения функции
                double x = Double.parseDouble(msg.getContent());
                double y = calculateFunction(x);

                // Отправляем ответ с рассчитанным значением функции
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(Double.toString(y));
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    // Поведение для инициации расчетов
    private class InitiateCalculationBehaviour extends CyclicBehaviour {
        private int expectedReplies;

        @Override
        public void action() {
            if (isInitiator && !waitingForReplies && delta >= DELTA_THRESHOLD) {
                // Агент-инициатор отправляет сообщения всем известным агентам для выполнения расчета графика функций
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                for (AID agent : otherAgents) {
                    msg.addReceiver(agent);
                }

                // Рассчитываем значения функций для трех точек: X - delta, X, X + delta
                points = new double[]{currentX - delta, currentX, currentX + delta}; // Инициализация переменной points
                expectedReplies = otherAgents.size() * points.length;
                for (double point : points) {
                    msg.setContent(Double.toString(point));
                    myAgent.send(msg);
                }

                // Устанавливаем состояние ожидания ответов
                waitingForReplies = true;
            }

            if (waitingForReplies) {
                // Ожидаем ответы от других агентов
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                ACLMessage reply = myAgent.receive(mt);
                if (reply != null) {
                    // Обрабатываем ответы и определяем экстремум суммарного значения функций
                    double y = Double.parseDouble(reply.getContent());
                    functionValues.add(y);

                    // Если получили ответы от всех агентов, определяем экстремум
                    if (functionValues.size() == expectedReplies) {
                        double totalValue = calculateTotalFunctionValue(functionValues);
                        // Определяем экстремум и обновляем значения X и delta
                        updateExtremePoint(points, functionValues);
                        functionValues.clear(); // Очищаем список значений функций для следующей итерации
                        waitingForReplies = false; // Снимаем состояние ожидания ответов
                    }
                } else {
                    block();
                }
            }
        }
    }

    // Метод для расчета значения функции
    protected double calculateFunction(double x) {
        switch (functionType) {
            case "exp":
                return Math.exp(0.2 * x); // y = e^(0.2x)
            case "pow":
                return Math.pow(2, -x); // y = 2^(-x)
            case "cos":
                return Math.cos(x); // y = cos(x)
            default:
                throw new IllegalArgumentException("Неизвестный тип функции: " + functionType);
        }
    }

    // Метод для расчета суммарного значения функций
    private double calculateTotalFunctionValue(List<Double> values) {
        double totalValue = 0.0;
        for (double value : values) {
            totalValue += value;
        }
        return totalValue;
    }

    // Метод для определения экстремума и обновления значений X и delta
    private void updateExtremePoint(double[] points, List<Double> values) {
        double maxValue = -1;
        double bestPoint = currentX;

        // Ищем точку с максимальным значением функции
        for (int i = 0; i < points.length; i++) {
            if (values.get(i) > maxValue) {
                maxValue = values.get(i);
                bestPoint = points[i];
            }
        }

        // Обновляем значение X
        currentX = bestPoint;

        // Если экстремум найден в одной из точек X - delta или X + delta, то delta не меняем
        if (bestPoint == currentX) {
            delta /= 2; // Уменьшаем delta в два раза
        }

        // Выводим конечные результаты расчетов в консоль
        System.out.println("Агент " + getAID().getName() + ":");
        System.out.println("Текущее значение X: " + currentX);
        System.out.println("Текущее значение delta: " + delta);
        System.out.println("Максимальное значение функции: " + maxValue);
        System.out.println("----------------------------------------");

        // Проверяем, достигнута ли требуемая точность
        if (delta < DELTA_THRESHOLD) {
            System.out.println("Достигнута требуемая точность. Расчеты завершены.");
            doDelete(); // Завершаем работу агента
        }
    }
}