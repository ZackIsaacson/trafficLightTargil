

import java.awt.Color;

import javax.swing.JPanel;

/* *Created on Mimuna 5767upDate on Tevet 5770


         *
         *

@author לויאן*/


public class ShloshaAvot extends Thread {
    Ramzor ramzor;
    JPanel panel;
    //    private Event64 evGreenShlosha, evRedShlosha, evWeekdayShlosha, evShabbatShlosha, evAck,evAckToGreen;
    private Event64 evRecieved, evAck;

    enum OutState {WEEKDAY, SHABBAT}

    ;
    OutState outState;

    enum InWeekdayState {RED, YELLOW, GREEN}

    ;
    InWeekdayState InWeekdayState;

//	enum InShabbatState {YELLOW, GREY};
//	InWeekdayState InShabbatState;

    private boolean history = false;
    private boolean stop = true;
    EventEnum evRec;

    public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evRecieved, Event64 evAck) {
        this.ramzor = ramzor;
        this.panel = panel;
        new CarsMaker(panel, this, key);
        this.evRecieved = evRecieved;
        this.evAck = evAck;

        start();
    }

  /*  public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evFromController,
                       Event64 evToController) {
        this.ramzor = ramzor;
        this.panel = panel;
        new CarsMaker(panel, this, key);
        this.evGreenShlosha = evGreenShlosha;
        this.evRedShlosha = evRedShlosha;
        this.evWeekdayShlosha = evWeekdayShlosha;
        this.evShabbatShlosha = evShabbatShlosha;

        start();
    }*/

    public void run() {
        try {


            boolean finish = false;
            boolean out = false;
            outState = OutState.WEEKDAY;
            InWeekdayState = InWeekdayState.RED;
//            redOn();
            int sum = 0;
            while (!finish) {
                switch (outState) {
                    case WEEKDAY:
                        while (!out) {
                            switch (InWeekdayState) {
                                case RED:
                                    while (true) {
                                        evRec = (EventEnum) evRecieved.waitEvent();
                                        if (evRec == EventEnum.GREEN) {
                                            InWeekdayState = InWeekdayState.GREEN;
                                            yellowRedOn();
//                                            InWeekdayState = InWeekdayState.YELLOW;
                                            break;
                                        } else if (evRec == EventEnum.RED) {
                                            evAck.sendEvent();
                                            break;
                                        } else if (evRec == EventEnum.SHABBAT) {
                                            outState = OutState.SHABBAT;
//                                            shabbatOn();
                                            out = true;
                                            break;
                                        }
                                     /*   if (evGreenShlosha.arrivedEvent()) {
                                            evGreenShlosha.waitEvent();
                                            InWeekdayState = InWeekdayState.GREEN;
                                            yellowRedOn();
//                                            InWeekdayState = InWeekdayState.YELLOW;

                                            break;
                                        } else if (evRedShlosha.arrivedEvent()) {
                                            evAck.sendEvent();
                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
                                            outState = OutState.SHABBAT;
                                            shabbatOn();
                                            out = true;
                                            break;
                                        } else
                                            yield();*/

                                    }
                                    break;
                             /*   case YELLOW:
                                    while (true) {
                                        if (evGreenShlosha.arrivedEvent()) {
                                            evGreenShlosha.waitEvent();
                                            InWeekdayState = InWeekdayState.GREEN;
                                            yellowRedOn();
                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
                                            outState = OutState.SHABBAT;
                                            shabbatOn();
                                            out = true;
                                            break;
                                        }
                                    }
                                    break;*/

                                case GREEN:
                                    while (true) {
                                        evRec = (EventEnum) evRecieved.waitEvent();
                                        if (evRec == EventEnum.RED) {
                                            InWeekdayState = InWeekdayState.RED;
                                            yellowOn();
                                            break;
                                        } else if (evRec == EventEnum.SHABBAT) {
                                            outState = OutState.SHABBAT;
//                                            shabbatOn();
                                            out = true;
                                            break;
                                        }
                                      /*  if (evRedShlosha.arrivedEvent()) {
                                            evRedShlosha.waitEvent();
                                            InWeekdayState = InWeekdayState.RED;
                                            yellowOn();
                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
                                            outState = OutState.SHABBAT;
                                            shabbatOn();
                                            out = true;
                                            break;
                                        } else
                                            yield();*/

                                    }
                                    break;


                            }       // switch of in state at out state 'WEEKDAY'
                        }           // loop at out state 'WEEKDAY'
                        break;

                    case SHABBAT:
//                        while (true) {

//                            if (EventEnum.WEEKDAY ==  evRecieved.waitEvent()) {
                        shabbatOn();
                        out = false;
                        outState = OutState.WEEKDAY;
                        if (!history)
                            redOn();
                        InWeekdayState = InWeekdayState.RED;
                        break;
//                        }
//                        yield(); //todo anywhere else?

                }
            }


        } catch (
                InterruptedException e)

        {
        }


    }

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }

    public void yellowRedOn() throws InterruptedException {
        setLight(1, Color.RED);
        setLight(2, Color.YELLOW);
        setLight(3, Color.LIGHT_GRAY);
        sleep(1000);
        greenOn();

    }

    public void greenOn() {
        setLight(1, Color.LIGHT_GRAY);
        setLight(2, Color.LIGHT_GRAY);
        setLight(3, Color.GREEN);
        stop = false;
        evAck.sendEvent();  //so that controller can start timer.
    }

    public void yellowOn() throws InterruptedException {
        stop = true;
        setLight(1, Color.LIGHT_GRAY);
        setLight(2, Color.YELLOW);
        setLight(3, Color.LIGHT_GRAY);
        sleep(1000);
        redOn();

    }

    public void redOn() {
        stop = true;

        setLight(1, Color.RED);
        setLight(2, Color.LIGHT_GRAY);
        setLight(3, Color.LIGHT_GRAY);
        evAck.sendEvent();//TODO check if right

    }

    public void shabbatOn() throws InterruptedException {
        stop = false;
        setLight(1, Color.LIGHT_GRAY);
        setLight(3, Color.LIGHT_GRAY);
        while (!evRecieved.arrivedEvent()) {
            setLight(2, Color.GRAY);
            sleep(1000);
            setLight(2, Color.YELLOW);
            sleep(1000);
        }
        evRecieved.waitEvent();
       /*     setLight(1, Color.LIGHT_GRAY);
        setLight(2, Color.YELLOW);
        setLight(3, Color.LIGHT_GRAY);
        while (evRecieved.waitEvent() != EventEnum.WEEKDAY)
//			while (outState==OutState.SHABBAT)
        {

            sleep(1000);
            setLight(2, Color.GRAY);
            sleep(1000);
            setLight(2, Color.YELLOW);
        }*/

    }

    public boolean isStop() {
        return stop;
    }
}
