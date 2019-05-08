import javax.swing.*;
import javax.swing.JRadioButton;
import java.awt.*;

public class Controller extends Thread {


    private Event64[] evRedOn = new Event64[16];
    private Event64[] evGreenOn = new Event64[16];
    private Event64[] evShabbatOn = new Event64[16];
    private Event64[] evWeekdayOn = new Event64[16];

    //TODO events dealing with listener
    private Event64 evShabbatOnButton = new Event64();  //16 if not selected and pressed
    private Event64 evShabbatOffButton = new Event64(); //16 if selected and pressed
    private Event64[] evButton = new Event64[12];  //4-15


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

    /*    public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evYellowRedShlosha, Event64 evYellowShlosha,
                           Event64 evTurnedRedOn,Event64 evWeekdayShlosha, Event64 evShabbatOnButton) {
            this.ramzor = ramzor;
            this.panel = panel;
            new CarsMaker(panel, this, key);
            this.evYellowRedShlosha = evYellowRedShlosha;
            this.evYellowShlosha = evYellowShlosha;
            this.evWeekdayShlosha = evWeekdayShlosha;
            this.evShabbatOnButton = evShabbatOnButton;
            this.evTurnedRedOn=evTurnedRedOn;
            start();
        }*/
    private static Controller ourInstance = new Controller();

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
        createRamzorim();
        createButtons();
        start();
    }

    boolean fromReset;

    public void run() {
        try {

            /* try {*/
            boolean finish = false;
            boolean out = false;
            outState = OutState.WEEKDAY;
            fromReset = false;
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
                                            fromReset = true;
                                            turn0Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT0;

                                            break;
                                        } else if (evShabbatOnButton.arrivedEvent()) {
                                            evShabbatOnButton.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;
                                case LIGHT0:
                                    while (true) {
                                        /*TODO MAKE TIMER*/
                                        if (crossWalkButtonPressed(new int[]{4, 5, 8, 11, 14, 15})) {
                                            turn32Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT32;

                                            break;
                                        } else if (/*TODO CHECK IF TIMER OVER*/) {
                                            turn32Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT32;

                                            break;
                                        } else if (evShabbatOnButton.arrivedEvent()) {
                                            evShabbatOnButton.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;
                            /*    case LIGHT32:
                                    while (true) {
                                        if (evYellowRedShlosha.arrivedEvent()) {
                                            evYellowRedShlosha.waitEvent();
                                            yellowRedOn();
                                            InWeekdayState = InWeekdayState.LIGHT12;
                                            break;
                                        } else if (evShabbatOnButton.arrivedEvent()) {
                                            evShabbatOnButton.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;*/
                                case LIGHT32:
                                    while (true) {
                                        if (crossWalkButtonPressed(new int[]{9, 10})) {
                                            turn0Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT0;

                                            break;
                                        } else if (crossWalkButtonPressed(new int[]{6, 7, 12, 13})) {
                                            turn12Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT12;

                                            break;
                                        } else if (/*TODO CHECK IF TIMER OVER*/) {
                                            turn12Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT12;

                                            break;
                                        } else if (evShabbatOnButton.arrivedEvent()) {
                                            evShabbatOnButton.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;
                                case LIGHT12:
                                    while (true) {
                                        /*TODO MAKE TIMER*/
                                        if (crossWalkButtonPressed(new int[]{9, 10, 8, 11, 14, 15})) {
                                            turn0Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT0;

                                            break;
                                        } else if (/*TODO CHECK IF TIMER OVER*/) {
                                            turn0Green();
//                                            InWeekdayState = InWeekdayState.LIGHT32;
                                            InWeekdayState = InWeekdayState.LIGHT0;

                                            break;
                                        } else if (evShabbatOnButton.arrivedEvent()) {
                                            evShabbatOnButton.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;
								/*	case CAN_EXITING:
										while (true)
										{
											if (evWeekdayShlosha.arrivedEvent())
											{
												evWeekdayShlosha.waitEvent();
												numOfC--;
												if (numOfC>0)
												{
													show(sum);
													init();
													InWeekdayState=InWeekdayState.LIGHT0;
												}
												else
												{
													out=true;
													call("have no small can");
													outState=OutState.SHABBAT;
												}
												break;
											}
											else if (evShabbatOnButton.arrivedEvent())
											{
												evShabbatOnButton.waitEvent();
												call("Problem");
												out=true;
												outState=OutState.SHABBAT;
												break;
											}
										}
										break;*/
                            }       // switch of in state at out state 'WEEKDAY'
                        }           // loop at out state 'WEEKDAY'
                        break;

                    case SHABBAT:
                        evShabbatOffButton.waitEvent();
                        out = false;
//                        init();
                        outState = OutState.WEEKDAY;
                        if (!history)
                            sendRedEventToAllRamzorim();
                        InWeekdayState = InWeekdayState.RESET;
                        break;

                }
            }
			/*	sleep(1000);
				setLight(1,Color.GRAY);
				setLight(2,Color.LIGHT12);
				sleep(1000);
				setLight(1,Color.LIGHT0);
				setLight(2,Color.GRAY);*/

        /*} catch (InterruptedException e) {
        }*/


			/*while (true)
			{
				sleep(1000);
				setLight(2,Color.LIGHT32);
				sleep(1000);
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.LIGHT12);
				stop=false;    //makes cars
				sleep(3000);
				stop=true;     //stops making cars
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.LIGHT32);
				setLight(3,Color.LIGHT_GRAY);
				sleep(1000);
				setLight(1,Color.LIGHT0);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.LIGHT_GRAY);
			}*/
        } catch (InterruptedException e) {
        }

    }

    private boolean crossWalkButtonPressed(int[] arrCrossWalk) {
        for (int i = 0; i < arrCrossWalk.length; i++) {
            if (evButton[arrCrossWalk[i]].arrivedEvent()) {
                evButton[arrCrossWalk[i]].waitEvent();
                return true;
            }
        }
        return false;
    }


    private void turnCrossWalksRed(int[] arrCrossWalk) {
        for (int i = 0; i < arrCrossWalk.length; i++) {
            evRedOn[arrCrossWalk[i]].sendEvent();
        }
        //waiting for acks
        for (int i = 0; i < arrCrossWalk.length; i++) {
            evRedOn[arrCrossWalk[i]].waitEvent();
        }
    }

    private void turn0Green() throws InterruptedException {
        if (!fromReset) {
            int[] arrCrossWalk = {4, 5, 8, 11, 14, 15};
            turnCrossWalksRed(arrCrossWalk);
        }
        sleep(500);

        evGreenOn[0].sendEvent();
    }

    private void turn32Green() throws InterruptedException {
        int[] arrCrossWalk = {6, 7, 9, 10, 12, 13};
        turnCrossWalksRed(arrCrossWalk);
        sleep(500);
        evGreenOn[3].sendEvent();
        evGreenOn[2].sendEvent();
    }

    private void turn12Green() throws InterruptedException {

        int[] arrCrossWalk = {4, 5, 8, 11, 9, 10};
        turnCrossWalksRed(arrCrossWalk);

        sleep(500);

        evGreenOn[1].sendEvent();
        evGreenOn[2].sendEvent();
    }


    public void sendRedEventToAllRamzorim() {
        for (int i = 0; i < 16; i++) {
            evRedOn[i].sendEvent();
        }
        //TODO check if right

    }

    private boolean allRamzorimTurnedRed() {
        for (int i = 0; i < 16; i++) {
            evRedOn[i].waitEvent();
        }
        return true;
    }

    public void eventFromListener(int i, boolean selected) {
        if (i < 16)
            evButton[i].sendEvent();
        else if (i == 16 && selected)
            evShabbatOnButton.sendEvent();
        else
            evShabbatOffButton.sendEvent();

        //TODO check if right

    }

    public void shabbatOn() throws InterruptedException {

        for (int i = 0; i < 16; i++) {
            evShabbatOn[i].sendEvent();
        }
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
            new ShloshaAvot(ramzorim[i], tlf.myPanel, 1 + i, evRedOn[i], evGreenOn[i], evShabbatOn[i], evWeekdayOn[i]);
        }
        for (int i = 4; i < 16; i++) {
            new ShneyLuchot(ramzorim[i], tlf.myPanel, evRedOn[i], evGreenOn[i], evShabbatOn[i], evWeekdayOn[i]);
        }
        new Echad(ramzorim[16], tlf.myPanel);
    }

    private void createButtons() {
        myListener = new MyActionListener();

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
