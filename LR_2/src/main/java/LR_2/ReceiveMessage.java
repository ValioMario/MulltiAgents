package LR_2;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMessage extends Behaviour {

    private Agent myAgent;
    private MessageTemplate mNumb;
    private MessageTemplate mKey;
    public float delta;
    public float X;


    public ReceiveMessage(Agent myAgent){
        this.myAgent = myAgent;
    }

    @Override
    public void onStart(){
        mNumb = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        mKey = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    }

    @Override
    public void action() {
        ACLMessage msgNumb = myAgent.receive(mNumb);
        ACLMessage msgKey = myAgent.receive(mKey);

        if (msgNumb != null){
            String msg = msgNumb.getContent();
            String[] res = msg.split(";");
            System.out.println("Я агент " + myAgent.getLocalName() + " получил от агента " + msgNumb.getSender().getLocalName() + " зачение X = " + res[0] + " зачение delta = " + res[1]);
            X = Float.parseFloat(res[0]);
            delta = Float.parseFloat(res[1]);
            Calculation c = new Calculation();
            float result1 = c.calc(myAgent.getLocalName(), X-delta);
            float result2 = c.calc(myAgent.getLocalName(), X);
            float result3 = c.calc(myAgent.getLocalName(), X+delta);
            System.out.println("Я агент" + myAgent.getLocalName() +"Результаты расчета для X-delta = " + result1 + "\nРезультаты расчета для X = " + result2 + "\nРезультаты расчета для X+delta = " + result3);
            myAgent.addBehaviour(new CendMessages(myAgent, msgNumb.getSender().getLocalName(), result1, result2, result3, X, delta, 3000));
        }
        else if (msgKey != null) {
            System.out.println("Я агент " + myAgent.getLocalName() + " получил ключ инициирования");
            myAgent.addBehaviour(new CendMsgInitiator(myAgent, msgKey.getContent(), 3000));
            myAgent.addBehaviour(new ReceiveMsgInitiator(myAgent));
        }
        else {
            block();
        }
    }

    @Override
    public int onEnd(){
        System.err.println("Значение дельты < 0,01");
        return super.onEnd();
    }

    @Override
    public boolean done() {
        return delta < 0.01;
    }
}
