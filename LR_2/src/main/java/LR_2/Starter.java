package LR_2;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Starter extends OneShotBehaviour {

    private String name;

    public Starter(String name) {
        this.name = name;
    }

    @Override
    public void action() {
        float maxX = 50;
        float minX = -50;
        float x1 = (float) (minX + (Math.random() * (maxX - minX) + 1));
        float delta = 10;
        System.out.println("Я агент " + name + " стартовые значения X=" + x1 + " delta = " + delta);

        String d = String.valueOf(delta);
        String x = String.valueOf(x1);
        String point = x + ";" + d;

        ACLMessage key = new ACLMessage(ACLMessage.REQUEST);
        AID receiver = new AID(name,false);
        key.addReceiver(receiver);
        key.setContent(point);
        myAgent.send(key);
    }
}
