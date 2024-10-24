package LR_2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class ReceiveMsgInitiator extends Behaviour {

    private Agent myAgent;
    private MessageTemplate mResult;
    private int count = 0;
    private float[] sum = new float[5];
    public float nextX;
    public float nextDelta;
    private String[] nameAgent = new String[3];

    private float sum1 = 0;
    private float sum2 = 0;
    private float sum3 = 0;
    private float sum4;
    private float sum5;

    float numb1;
    float numb2;
    float numb3;
    float X;
    float delta;

    public ReceiveMsgInitiator(Agent myAgent){
        this.myAgent = myAgent;
    }

    @Override
    public void onStart(){
        mResult = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
    }

    @Override
    public void action() {
        ACLMessage msgResult = myAgent.receive(mResult);
       if(msgResult != null){
           String msg = msgResult.getContent();
           String[] numb = msg.split(";");
           numb1 = Float.parseFloat(numb[0]);
           numb2 = Float.parseFloat(numb[1]);
           numb3 = Float.parseFloat(numb[2]);
           X = Float.parseFloat(numb[3]);
           delta = Float.parseFloat(numb[4]);
            if (count<3){
                count++;
                System.out.println(" count = " + count);
                System.out.println("ЗНАЧЕНИЕ СУММЫ 1" + numb1 +" 2 "+ numb2 + " 3 " + numb3 + " 4 " + X +" 5 " + delta );
                sum1 += numb1;
                sum2 += numb2;
                sum3 += numb3;
                sum4 = X;
                sum5 = delta;
                System.out.println("ЗНАЧЕНИЕ СУММЫ 1" + sum1 +" 2 "+ sum2 + " 3 " + sum3 + " 4 " + sum4 +" 5 " + sum5 );
            }
            else if(count == 3){
                if (sum1<sum2 && sum1<sum3){
                    nextX = sum4-sum5;
                    nextDelta = sum5;
                    System.out.println("СЛЕДУЮЩИЙ X" + nextX + " и delt " + nextDelta);
                }
                else if (sum3<sum2 && sum3<sum1){
                    nextX = sum4+sum5;
                    nextDelta = sum5;
                    System.out.println("СЛЕДУЮЩИЙ X" + nextX + " и delt " + nextDelta);
                }
                else {
                    nextX = sum4;
                    nextDelta = sum5/2;
                    System.out.println("СЛЕДУЮЩИЙ X" + nextX + " и delt " + nextDelta);
                }

                System.out.println("\nНовые значения Х =" + nextX + "delta = " + nextDelta);
                String point = nextX + ";" + nextDelta;
                nameAgent[0] = "agentOne";
                nameAgent[1] = "agentTwo";
                nameAgent[2] = "agentThree";

                System.out.println("Пытаемся рандомить имея значения: "+nameAgent.length);
                double i = Math.random()*(nameAgent.length);
                String nextInitiator = nameAgent[(int) i];
                System.out.println("Следующим инициатором назначен " + nextInitiator);
                System.out.println("\nНовые значения Х =" + nextX + "delta = " + nextDelta);

                ACLMessage key = new ACLMessage(ACLMessage.REQUEST);
                AID receiver = new AID(nextInitiator,false);
                key.addReceiver(receiver);
                key.setContent(point);
                myAgent.send(key);

            }
            else{
                System.out.println("Ошибка расчета минимальной суммы!");
            }
       }
    }
    @Override
    public boolean done() {
        return count>4;
    }
}
