package LR_2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class CendMessages extends WakerBehaviour {

    private Agent myAgent;
    private String nameRec;
    private final String numb1;
    private final String numb2;
    private final String numb3;
    private final String delta;
    private final String X;

    public CendMessages(Agent myAgent, String name, float numb1, float numb2, float numb3, float X, float delta, long timeout) {
        super(myAgent, timeout);
        this.myAgent = myAgent;
        this.nameRec = name;
        this.numb1 = String.valueOf(numb1);
        this.numb2 = String.valueOf(numb2);
        this.numb3 = String.valueOf(numb3);
        this.X = String.valueOf(X);
        this.delta = String.valueOf(delta);

        String numb = numb1 + ";" + numb2 + ";" + numb3 + ";" + X + ";" + delta;
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        AID receiver = new AID(nameRec, false);
        msg.addReceiver(receiver);
        msg.setContent(numb);
        myAgent.send(msg);
        System.out.println("Я агент " + myAgent.getLocalName() + " отправил сообщение " + numb + " агенту " + receiver);
    }
}