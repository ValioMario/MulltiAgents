package LR_2;

import jade.core.Agent;

public class FunctionAgent extends Agent {


    @Override
    protected void setup(){

        System.out.println("Hello, i'm "+this.getLocalName());
        if(this.getLocalName().equals("agentOne")){

            String name = this.getLocalName();

            this.addBehaviour(new Starter(name));
        }

        this.addBehaviour(new ReceiveMessage(this));
    }
}
