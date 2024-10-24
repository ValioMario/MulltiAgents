package LR_2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class CendMsgInitiator extends WakerBehaviour {

    private Agent myAgent;
    private String point;

    public CendMsgInitiator(Agent myAgent, String point, long timeout){
        super(myAgent,timeout);
        this.myAgent = myAgent;
        this.point = point;
    }

    @Override
    public void onStart(){
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        AID receiver1 = new AID("agentOne",false);
        AID receiver2 = new AID("agentTwo",false);
        AID receiver3 = new AID("agentThree",false);
        msg.addReceiver(receiver1);
        msg.addReceiver(receiver2);
        msg.addReceiver(receiver3);
        msg.setContent(point);
        myAgent.send(msg);
        System.out.println("Я агент-инициатор" + myAgent.getLocalName() + " отправил всем агентам значения для построения " + point);
    }
}
