package LR_2;

public class Calculation {

    public float n;

     public float calc(String name, float numb){

         String nameAgent = name;

         Function f = new Function();

             switch (nameAgent){
                 case "agentOne":
                     n = f.exp(numb);
                     break;
                 case "agentTwo":
                     n = f.squaring(numb);
                     break;
                 case "agentThree":
                     n = f.cos(numb);
                     break;
                 default:
                     System.out.println("Ошибка определения функции!");
                     n = 2;
                     break;
             }

         return n;
     }
}
