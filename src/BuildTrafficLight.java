import javax.swing.JRadioButton;

/*
 * Created on Mimuna 5767  upDate on Addar 5772
 */

/**
 * @author �����
 */
/*TODO makes controller. controller gets events from: 1. my action listener (1. shabbos/ chol.
/																	         2. what shney luchot was pressed
/                                					  2. all traffic lights that turned red ('reset'- only when all are red can start cycle)
/                                   sends events to: 1. specific light to turn green
/													 2. red
/													 3. to all lights- shabbos/ chol
/
*/
public class BuildTrafficLight {

    public static void main(String[] args) {
        Controller controller=Controller.getInstance();
        /*final int numOfLights = 4 + 12 + 1;
        Ramzor ramzorim[] = new Ramzor[numOfLights];
        ramzorim[0] = new Ramzor(3, 40, 430, 110, 472, 110, 514, 110);
        ramzorim[1] = new Ramzor(3, 40, 450, 310, 450, 352, 450, 394);
        ramzorim[2] = new Ramzor(3, 40, 310, 630, 280, 605, 250, 580);
        ramzorim[3] = new Ramzor(3, 40, 350, 350, 308, 350, 266, 350);

        ramzorim[4] = new Ramzor(2, 20, 600, 18, 600, 40);
        ramzorim[5] = new Ramzor(2, 20, 600, 227, 600, 205);
        ramzorim[6] = new Ramzor(2, 20, 600, 255, 600, 277);
        ramzorim[7] = new Ramzor(2, 20, 600, 455, 600, 433);
        ramzorim[8] = new Ramzor(2, 20, 575, 475, 553, 475);
        ramzorim[9] = new Ramzor(2, 20, 140, 608, 150, 590);
        ramzorim[10] = new Ramzor(2, 20, 205, 475, 193, 490);
        ramzorim[11] = new Ramzor(2, 20, 230, 475, 250, 475);
        ramzorim[12] = new Ramzor(2, 20, 200, 453, 200, 433);
        ramzorim[13] = new Ramzor(2, 20, 200, 255, 200, 277);
        ramzorim[14] = new Ramzor(2, 20, 200, 227, 200, 205);
        ramzorim[15] = new Ramzor(2, 20, 200, 18, 200, 40);

        ramzorim[16] = new Ramzor(1, 30, 555, 645);*/

       /* Event64[] evYellowRedShloshaArray=new Event64[4];
        Event64[] evYellowShloshaArray=new Event64[4];
        Event64[] evTurnedRedOnArray=new Event64[4];
        Event64[] evWeekdayShloshaArray=new Event64[4];
        Event64[] evShabbatShloshaArray=new Event64[4];
        Event64[][] evArr=new Event64[16][5];
        TrafficLightFrame tlf = new TrafficLightFrame(" ���''� installation of traffic lights", ramzorim);
        for (int i = 0; i < 4; i++) {
            Event64 evYellowRedShlosha, evYellowShlosha, evTurnedRedOnShlosha,
                    evWeekdayShlosha, evShabbatShlosha;

            evYellowRedShlosha = new Event64();
            evYellowShlosha = new Event64();
            evWeekdayShlosha = new Event64();
            evShabbatShlosha = new Event64();
            evTurnedRedOnShlosha=new Event64();
evArr[0][0]=evYellowRedShlosha;
evArr[0][1]=evYellowShlosha;
            evYellowRedShloshaArray[i]=evYellowRedShlosha;
            evYellowShloshaArray[i]=evYellowShlosha;
            evTurnedRedOnArray[i]=evTurnedRedOnShlosha;
            evWeekdayShloshaArray[i]=evWeekdayShlosha;
            evShabbatShloshaArray[i]=evShabbatShlosha;

            evArr[i]=new Event64[]{evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i]};
//          evArr[i]={evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i]};
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1+i,evArr[i]);

//            new ShloshaAvot(ramzorim[0], tlf.myPanel, 1,evYellowRedShlosha,evYellowShlosha,evTurnedRedOn, evWeekdayShlosha, evShabbatShlosha);
//            new ShloshaAvot(ramzorim[1], tlf.myPanel, 2);   //was like this
//            new ShloshaAvot(ramzorim[2], tlf.myPanel, 3);
//            new ShloshaAvot(ramzorim[3], tlf.myPanel, 4);
        }

        Event64[] evYellowRedShloshaArray=new Event64[4];
        Event64[] evYellowShloshaArray=new Event64[4];
        Event64[] evTurnedRedOnArray=new Event64[4];
        Event64[] evWeekdayShloshaArray=new Event64[4];
        Event64[] evShabbatShloshaArray=new Event64[4];
        Event64[][] evArr=new Event64[16][5];
        TrafficLightFrame tlf = new TrafficLightFrame(" ���''� installation of traffic lights", ramzorim);
        for (int i = 4; i < 16; i++) {
            Event64 evGreenShney, evRedShney, evTurnedRedOnShney,
                    evWeekdayShney, evShabbatShney;

            evYellowRedShlosha = new Event64();
            evYellowShlosha = new Event64();
            evWeekdayShlosha = new Event64();
            evShabbatShlosha = new Event64();
            evTurnedRedOnShney=new Event64();

            evYellowRedShloshaArray[i]=evYellowRedShlosha;
            evYellowShloshaArray[i]=evYellowShlosha;
            evTurnedRedOnArray[i]=evTurnedRedOn;
            evWeekdayShloshaArray[i]=evWeekdayShlosha;
            evShabbatShloshaArray[i]=evShabbatShlosha;

            evArr[i]=new Event64[]{evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i]};
//            evArr[i]={evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i],evYellowRedShloshaArray[i]};
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1+i,evArr[i]);

//            new ShloshaAvot(ramzorim[0], tlf.myPanel, 1,evYellowRedShlosha,evYellowShlosha,evTurnedRedOn, evWeekdayShlosha, evShabbatShlosha);
//            new ShloshaAvot(ramzorim[1], tlf.myPanel, 2);   //was like this
//            new ShloshaAvot(ramzorim[2], tlf.myPanel, 3);
//            new ShloshaAvot(ramzorim[3], tlf.myPanel, 4);
        }



        new ShneyLuchot(ramzorim[4], tlf.myPanel);
        new ShneyLuchot(ramzorim[5], tlf.myPanel);
        new ShneyLuchot(ramzorim[9], tlf.myPanel);
        new ShneyLuchot(ramzorim[10], tlf.myPanel);*/

//        new Echad(ramzorim[16], tlf.myPanel);

       /* MyActionListener myListener = new MyActionListener();

        JRadioButton butt[] = new JRadioButton[13];

        for (int i = 0; i < butt.length - 1; i++) {
            butt[i] = new JRadioButton();
            butt[i].setName(Integer.toString(i + 4));
            butt[i].setOpaque(false);
            butt[i].addActionListener(myListener);
            tlf.myPanel.add(butt[i]);
        }
        butt[0].setBounds(620, 30, 18, 18);
        butt[1].setBounds(620, 218, 18, 18);
        butt[2].setBounds(620, 267, 18, 18);
        butt[3].setBounds(620, 447, 18, 18);
        butt[4].setBounds(566, 495, 18, 18);
        butt[5].setBounds(162, 608, 18, 18);
        butt[6].setBounds(213, 495, 18, 18);
        butt[7].setBounds(240, 457, 18, 18);
        butt[8].setBounds(220, 443, 18, 18);
        butt[9].setBounds(220, 267, 18, 18);
        butt[10].setBounds(220, 218, 18, 18);
        butt[11].setBounds(220, 30, 18, 18);

        butt[12] = new JRadioButton();   //shabbbos button
        butt[12].setName(Integer.toString(16));
        butt[12].setBounds(50, 30, 55, 20);
        butt[12].setText("Shabbos");
        butt[12].setOpaque(false);
        butt[12].addActionListener(myListener);
        tlf.myPanel.add(butt[12]);*/
    }
}
