


import javax.swing.JRadioButton;


public class Controller extends Thread {

    private Event64[] evSend = new Event64[16];

//    private Event64[] evRedOn = new Event64[16];
//
//    private Event64[] evGreenOn = new Event64[16];
//    private Event64[] evShabbatOn = new Event64[16];
//    private Event64[] evWeekdayOn = new Event64[16];

    private Event64[] evAck = new Event64[16];
//    private Event64[] evAck = new Event64[4];


    //TODO events dealing with listener
    private Event64 evButtonPressed;// = new Event64();  //16 if not selected and pressed

//    private Event64 evButtonPressed;// = new Event64();  //16 if not selected and pressed
//    private Event64 evShabbatOffButton;// = new Event64(); //16 if selected and pressed
//    private Event64[] evButtonPressed = new Event64[16];  //4-15  //


    Ramzor ramzorim[];
    TrafficLightFrame tlf;
    MyActionListener myListener;
    JRadioButton butt[];

    enum OutState {WEEKDAY, SHABBAT}

    ;
    OutState outState;

    enum InWeekdayState {RESET, LIGHT0, LIGHT32, LIGHT12}

    ;
    InWeekdayState InWeekdayState;

//	enum InShabbatState {LIGHT32, GREY};
//	InWeekdayState InShabbatState;

    private boolean history = false;
    private boolean stop = true;
    DanTimer75 timer;


    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
        createEvents(); //todo need?
        createRamzorimAndButtons();
//        createRamzAndButtons();
//        createRamzorim();
//        createButtons();
        start();
    }



    private void createEvents() {

        for (int i = 0; i < 16; i++) {

            evSend[i] = new Event64();
            evAck[i] = new Event64();


            //4 to 15
        }
        evButtonPressed = new Event64();
    /*    for (int i = 0; i < 4; i++) {

            evAck[i] = new Event64();


            //4 to 15
        }
        for (int i = 0; i < 16; i++) {
            evRedOn[i] = new Event64();
            evGreenOn[i] = new Event64();
            evShabbatOn[i] = new Event64();
            evWeekdayOn[i] = new Event64();
            evAck[i] = new Event64();


            //4 to 15
        }
        for (int i = 0; i < 16; i++) {
            if (i < 4)
                evButtonPressed[i] = null;
            else
                evButtonPressed[i] = new Event64();
        }
        evButtonPressed = new Event64();  //16 if not selected and pressed
        evShabbatOffButton = new Event64(); //16 if selected and pressed*/
    }

    boolean fromReset;

    public void run() {


        try {

            boolean finish = false;
            boolean out = false;
            outState = OutState.WEEKDAY;
            fromReset = true;
            InWeekdayState = InWeekdayState.RESET;
            sendRedEventToAllRamzorim();
            int sum = 0;
            while (!finish) {
                switch (outState) {
                    case WEEKDAY:
                        while (!out) {
                            switch (InWeekdayState) {
                                case RESET:
                                    while (true) {
                                        if (allRamzorimTurnedRed()) {
                                            fromReset = false;
                                            turn0Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT0;

                                            break;
                                        }/* else if (evButtonPressed.arrivedEvent()) {

                                            if (EventEnum.SHABBAT == evButtonPressed.waitEvent()) {
                                                shabbatOn();
                                                out = true;
                                                outState = OutState.SHABBAT;
                                                break;
                                            }
                                        }*/ else
                                            yield();

                                    }
                                    break;
                                case LIGHT0:
//                                    if((evAck[0].waitEvent()==EventAckEnum.GREEN));
                                    evAck[0].waitEvent(); //todo check that only evAck from green can 
                                    timer = new DanTimer75(5000);
                                    while (true) {
//TODO MAKE TIMER
                                   /*     if (buttonPressed(InWeekdayState.LIGHT0)) {
                                        }*/
                                        if (evButtonPressed.arrivedEvent()) {
                                            int num = (int) evButtonPressed.waitEvent();
                                            if (num == 16) {
                                                shabbatOn();
                                                out = true;
                                                outState = OutState.SHABBAT;
                                            } else if (num == 4 || num == 5 || num == 8 || num == 11 ||
                                                    num == 14 || num == 15) {
                                                turnLightRed(new int[]{0});
                                                turn32Green();
                                                InWeekdayState = InWeekdayState.LIGHT32;
                                            }
                                            break;
                                        } else if (!timer.isAlive()) {
                                            turnLightRed(new int[]{0});
                                            turn32Green();
                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            break;
                                        } else
                                            yield();

                                    }
                                    break;



                                case LIGHT32:
                                    evAck[2].waitEvent();  //todo can it possibly be event from red??
                                    evAck[3].waitEvent();  //todo supposed to be evAckGreen
                                    timer = new DanTimer75(5000);
//TODO MAKE TIMER


                                    while (true) {
                                        if (evButtonPressed.arrivedEvent()) {
                                            int num = (int) evButtonPressed.waitEvent();
                                            if (num == 16) {
                                                shabbatOn();
                                                out = true;
                                                outState = OutState.SHABBAT;
                                            } else if (num == 9 || num == 10) {
                                                turnLightRed(new int[]{2, 3});
                                                turn0Green();
                                                InWeekdayState = InWeekdayState.LIGHT0;
                                                break;
                                            } else if (num == 6 || num == 7 || num == 12 || num == 13) {
                                                turnLightRed(new int[]{3});
                                                turn12Green();
                                                InWeekdayState = InWeekdayState.LIGHT12;
                                            }
                                            break;
                                        } else if (!timer.isAlive()) {
                                            turnLightRed(new int[]{3});
                                            turn12Green();
                                            InWeekdayState = InWeekdayState.LIGHT12;
                                            break;
                                        } else
                                            yield();
                                    }
                                    break;
                                case LIGHT12:
                                    evAck[1].waitEvent();
//                                    evAck[2].waitEvent();
                                    timer = new DanTimer75(5000);
                                    while (true) {
                                        if (evButtonPressed.arrivedEvent()) {
                                            int num = (int) evButtonPressed.waitEvent();
                                            if (num == 16) {
                                                shabbatOn();
                                                out = true;
                                                outState = OutState.SHABBAT;
                                            } else if (num == 8 || num == 11 || num == 14 || num == 15) {
                                                turnLightRed(new int[]{1});
                                                turn32Green();
                                                InWeekdayState = InWeekdayState.LIGHT32;
                                                break;
                                            } else if (num == 9 || num == 10 ) {
                                                turnLightRed(new int[]{1, 2});
                                                turn0Green();
                                                InWeekdayState = InWeekdayState.LIGHT0;
                                            }
                                            break;
                                        } else if (!timer.isAlive()) {
                                            turnLightRed(new int[]{1, 2});
                                            turn0Green();
                                            InWeekdayState = InWeekdayState.LIGHT0;
                                            break;
                                        } else
                                            yield();
                                    }
                                    break;
                            }       // switch of in state at out state 'WEEKDAY'
                        }           // loop at out state 'WEEKDAY'
                        break;

                    case SHABBAT:
                        if (16 == (int) evButtonPressed.waitEvent()) {
                            out = false;
//                        init();
                            outState = OutState.WEEKDAY;
                            if (!history)
                                sendRedEventToAllRamzorim();
                            InWeekdayState = InWeekdayState.RESET;
                            break;
                        }

                }
            }


        } catch (InterruptedException e) {
        }
    }



    private boolean crossWalkButtonPressed(int[] arrCrossWalk) {
        if (evButtonPressed.arrivedEvent()) {
            int buttonPressed = (int) evButtonPressed.waitEvent();
            for (int i = 0; i < arrCrossWalk.length; i++) {
                if (buttonPressed == arrCrossWalk[i])
                    return true;
            }
        }
        return false;
    }
    /*    for(
    int i = 0;
    i<arrCrossWalk.length;i++)

    {
        if (evButtonPressed[arrCrossWalk[i]].arrivedEvent()) {
            evButtonPressed[arrCrossWalk[i]].waitEvent();
            return true;
        }
    }
        return false;
}*/

    private void turnLightRed(int[] arrLight) {
        for (int i = 0; i < arrLight.length; i++) {
            evSend[arrLight[i]].sendEvent(EventEnum.RED);
        }
        //waiting for acks
        for (int i = 0; i < arrLight.length; i++) {
            evAck[arrLight[i]].waitEvent();  //todo evAck red only. can evAckGreen get here?
        }

    }


    private void turn0Green() throws InterruptedException {
        if (!fromReset) {
            int[] arrCrossWalk = {4, 5, 8, 11, 14, 15};
            turnLightRed(arrCrossWalk);
        }
        sleep(500);

        //car light
        evSend[0].sendEvent(EventEnum.GREEN);

        //pedestrian light
        evSend[6].sendEvent(EventEnum.GREEN);
        evSend[7].sendEvent(EventEnum.GREEN);
        evSend[9].sendEvent(EventEnum.GREEN);
        evSend[10].sendEvent(EventEnum.GREEN);
        evSend[12].sendEvent(EventEnum.GREEN);
        evSend[13].sendEvent(EventEnum.GREEN);

//        evGreenOn[0].sendEvent();
    }

    private void turn32Green() throws InterruptedException {
        int[] arrCrossWalk = {6, 7, 9, 10, 12, 13};
        turnLightRed(arrCrossWalk);
        sleep(500);
        evSend[3].sendEvent(EventEnum.GREEN);
        evSend[2].sendEvent(EventEnum.GREEN);

        evSend[4].sendEvent(EventEnum.GREEN);
        evSend[5].sendEvent(EventEnum.GREEN);
        evSend[8].sendEvent(EventEnum.GREEN);
        evSend[11].sendEvent(EventEnum.GREEN);
        evSend[14].sendEvent(EventEnum.GREEN);
        evSend[15].sendEvent(EventEnum.GREEN);
//        evGreenOn[3].sendEvent();
//        evGreenOn[2].sendEvent();
    }

    private void turn12Green() throws InterruptedException {

        int[] arrCrossWalk = {14, 15, 8, 11, 9, 10};
        turnLightRed(arrCrossWalk);

        sleep(500);
        evSend[1].sendEvent(EventEnum.GREEN);
//        evSend[2].sendEvent(EventEnum.GREEN);

        evSend[4].sendEvent(EventEnum.GREEN);
        evSend[5].sendEvent(EventEnum.GREEN);
        evSend[6].sendEvent(EventEnum.GREEN);
        evSend[7].sendEvent(EventEnum.GREEN);
        evSend[12].sendEvent(EventEnum.GREEN);
        evSend[13].sendEvent(EventEnum.GREEN);
//        evGreenOn[1].sendEvent();
//        evGreenOn[2].sendEvent();
    }


    public void sendRedEventToAllRamzorim() {
        for (int i = 0; i < 16; i++) {
            evSend[i].sendEvent(EventEnum.RED);
        }
        //TODO check if right

    }

    //todo what if green?
    private boolean allRamzorimTurnedRed() {
        for (int i = 0; i < 16; i++) {
            evAck[i].waitEvent();
        }
        return true;
    }

   /* public void eventFromListener(int i, boolean selected) {
        if (i < 16)
            evButtonPressed[i].sendEvent();
        else if (i == 16 && selected)
            evButtonPressed.sendEvent();
        else
            evShabbatOffButton.sendEvent();

        //TODO check if right

    }*/

    public void shabbatOn() throws InterruptedException {

        for (int i = 0; i < 16; i++) {
            evSend[i].sendEvent(EventEnum.SHABBAT);
        }
    }

    /*private void createRamzAndButtons() {
        final int numOfLights = 4 + 12 + 1;
        ramzorim = new Ramzor[numOfLights];
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

        ramzorim[16] = new Ramzor(1, 30, 555, 645);
        tlf = new TrafficLightFrame(" úùò''á installation of traffic lights", ramzorim);

        butt = new JRadioButton[13];

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
        tlf.myPanel.add(butt[12]);
        for (int i = 0; i < 4; i++) {
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1 + i, evSend[i], evAck[i]);
        }
        for (int i = 4; i < 16; i++) {
            new ShneyLuchot(ramzorim[i], tlf.myPanel, evSend[i], evAck[i],butt[i]);//,butt[i]
        }
        new Echad(ramzorim[16], tlf.myPanel);


        myListener = new MyActionListener(evButtonPressed);


    }*/
    private void createRamzorimAndButtons() {
        final int numOfLights = 4 + 12 + 1;
        ramzorim = new Ramzor[numOfLights];
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

        ramzorim[16] = new Ramzor(1, 30, 555, 645);
        tlf = new TrafficLightFrame(" úùò''á installation of traffic lights", ramzorim);

        myListener = new MyActionListener(evButtonPressed);

        butt = new JRadioButton[13];

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
        tlf.myPanel.add(butt[12]);

        for (int i = 0; i < 4; i++) {
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1 + i, evSend[i], evAck[i]);
        }
        for (int i = 4; i < 16; i++) {
            new ShneyLuchot(ramzorim[i], tlf.myPanel, evSend[i], evAck[i],butt[i-4]);//,butt[i]
        }
        new Echad(ramzorim[16], tlf.myPanel);
    }
    private void createRamzorim() {
        final int numOfLights = 4 + 12 + 1;
        ramzorim = new Ramzor[numOfLights];
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

        ramzorim[16] = new Ramzor(1, 30, 555, 645);
        tlf = new TrafficLightFrame(" úùò''á installation of traffic lights", ramzorim);
        for (int i = 0; i < 4; i++) {
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1 + i, evSend[i], evAck[i]);
        }
        for (int i = 4; i < 16; i++) {
            new ShneyLuchot(ramzorim[i], tlf.myPanel, evSend[i], evAck[i]);//,butt[i]
        }
        new Echad(ramzorim[16], tlf.myPanel);
    }

    private void createButtons() {
        myListener = new MyActionListener(evButtonPressed);

        butt = new JRadioButton[13];

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
        tlf.myPanel.add(butt[12]);
    }


}
