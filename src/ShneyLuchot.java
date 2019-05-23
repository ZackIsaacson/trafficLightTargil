/*import java.awt.Color;

import javax.swing.JPanel;

*//*
 * Created on Mimuna 5767  upDate on Tevet 5770
 *//*

 *//**
 * @author לויאן
 *//*

class ShneyLuchot extends Thread {
    Ramzor ramzor;
    JPanel panel;
    private Event64 evFromController,evTimeChangeFromController, evToController;


    enum OutState {WEEKDAY, SHABBAT};
    OutState outState;

    enum InState {RED, GREEN};
    InState inState;
    private boolean history = false;

    *//*public ShneyLuchot( Ramzor ramzor,JPanel panel)
    {
        this.ramzor=ramzor;
        this.panel=panel;
        start();
    }*//*
    public ShneyLuchot(Ramzor ramzor, JPanel panel, Event64 evFromController,Event64 evTimeChangeFromController, Event64 evToController) {
        this.ramzor = ramzor;
        this.panel = panel;
        this.evFromController = evFromController;
        this.evTimeChangeFromController = evTimeChangeFromController;

        this.evToController = evToController;


        setDaemon(true);
        start();
    }

    public void run() {
        *//* try {*//*
        boolean finish = false;
        boolean out = false;
        outState = OutState.WEEKDAY;
        inState = InState.RED;
//        redOn();
        int sum = 0;
        while (!finish) {
            switch (outState) {
                case WEEKDAY:
                    while (!out) {
                        switch (inState) {
                            case RED:
                                while (true) {
                                    if (evFromController.arrivedEvent()) {
                                        evFromController.waitEvent();
                                        greenOn();
                                        inState = InState.GREEN;
                                        break;
                                    } else if (evTimeChangeFromController.arrivedEvent()) {
                                        evTimeChangeFromController.waitEvent();
                                        shabbatOn();
                                        out = true;
                                        outState = OutState.SHABBAT;
                                        break;
                                    }else
                                        yield();

                                }
                                break;

                            case GREEN:
                                while (true) {
                                    if (evFromController.arrivedEvent()) {
                                        evFromController.waitEvent();
                                        greenOn();
                                        inState = InState.GREEN;
                                        break;
                                    } else if (evTimeChangeFromController.arrivedEvent()) {
                                        evTimeChangeFromController.waitEvent();
                                        shabbatOn();
                                        out = true;
                                        outState = OutState.SHABBAT;
                                        break;
                                    }else
                                        yield();

                                }
                                break;

								*//*	case CAN_EXITING:
										while (true)
										{
											if (evWeekdayShney.arrivedEvent())
											{
												evWeekdayShney.waitEvent();
												numOfC--;
												if (numOfC>0)
												{
													show(sum);
													init();
													inState=InState.RED;
												}
												else
												{
													out=true;
													call("have no small can");
													outState=OutState.SHABBAT;
												}
												break;
											}
											else if (evShabbatShney.arrivedEvent())
											{
												evShabbatShney.waitEvent();
												call("Problem");
												out=true;
												outState=OutState.SHABBAT;
												break;
											}
										}
										break;*//*
                        }       // switch of in state at out state 'WEEKDAY'
                    }           // loop at out state 'WEEKDAY'
                    break;

                case SHABBAT:
                    evTimeChangeFromController.waitEvent();
                    out = false;
//                        init();
                    outState = OutState.WEEKDAY;
                    if (!history)
                        redOn();
                    inState = InState.RED;
                    break;

            }
        }
			*//*	sleep(1000);
				setLight(1,Color.GRAY);
				setLight(2,Color.GREEN);
				sleep(1000);
				setLight(1,Color.RED);
				setLight(2,Color.GRAY);*//*

 *//*} catch (InterruptedException e) {
        }*//*

    }

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }

    public void greenOn() {
        setLight(1, Color.GRAY);
        setLight(2, Color.GREEN);
    }

    public void redOn() {
        setLight(1, Color.RED);
        setLight(2, Color.GRAY);
        evToController.sendEvent();
//        TODO make sure send event is right. that really goes back to controller. (if not create another event with controller holding other side and send back to controller that turned red)
    }

    public void shabbatOn() {
        setLight(1, Color.GRAY);
        setLight(2, Color.GRAY);
    }

}*/

import java.awt.Color;

import javax.swing.*;

/**
 * Created on Mimuna 5767upDate on Tevet 5770
 *
 * @author לויאן
 */


class ShneyLuchot extends Thread {
    Ramzor ramzor;
    JPanel panel;
    //    private Event64 evGreenShney, evRedShney, evWeekdayShney, evShabbatShney, evAck;
    private Event64 evRecieved, evAck;

    enum OutState {WEEKDAY, SHABBAT}

    ;
    OutState outState;

    enum InState {RED, GREEN}

    ;
    JRadioButton button;
    InState inState;
    private boolean history = false;
    EventEnum evRec;


    public ShneyLuchot(Ramzor ramzor, JPanel panel, Event64 evRecieved, Event64 evAck) {//, JRadioButton button
        this.ramzor = ramzor;
        this.panel = panel;
        this.evRecieved = evRecieved;
        this.evAck = evAck;
//        this.button = button;
//        this.evRedShney = evRedShney;
//        this.evWeekdayShney = evWeekdayShney;
//        this.evShabbatShney = evShabbatShney;

        setDaemon(true);
        start();
    }
    public ShneyLuchot(Ramzor ramzor, JPanel panel, Event64 evRecieved, Event64 evAck, JRadioButton button) {//
        this.ramzor = ramzor;
        this.panel = panel;
        this.evRecieved = evRecieved;
        this.evAck = evAck;
        this.button = button;
//        this.evRedShney = evRedShney;
//        this.evWeekdayShney = evWeekdayShney;
//        this.evShabbatShney = evShabbatShney;

        setDaemon(true);
        start();
    }

    public void run() {
        try {

            boolean finish = false;
            boolean out = false;
            outState = OutState.WEEKDAY;
            inState = InState.RED;
//        redOn();
            int sum = 0;
            while (!finish) {
                switch (outState) {
                    case WEEKDAY:
                        while (!out) {
                            switch (inState) {
                                case RED:
                                    while (true) {
                                        evRec = (EventEnum) evRecieved.waitEvent();
                                        if (evRec == EventEnum.GREEN) {
                                            inState = InState.GREEN;
                                            greenOn();
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
                                    }
                                    break;

                                case GREEN:
                                    while (true) {
                                        evRec = (EventEnum) evRecieved.waitEvent();
                                        if (evRec == EventEnum.RED) {
                                            inState = InState.RED;
                                            redOn();
                                            break;
                                        } else if (evRec == EventEnum.SHABBAT) {
                                            outState = OutState.SHABBAT;
//                                             shabbatOn();
                                            out = true;
                                            break;
                                        }
                                    }
                                    break;


                            }       // switch of in state at out state 'WEEKDAY'
                        }           // loop at out state 'WEEKDAY'
                        break;

                    case SHABBAT:
                        shabbatOn();
                        while (true) {
                            if (evRecieved.waitEvent() == EventEnum.RED) {
                                out = false;
                                outState = OutState.WEEKDAY;
                                if (!history)
                                    redOn();
                                inState = InState.RED;
                                break;
                            }
                        }
                        break;


                }
            }
            sleep(1);


        } catch (InterruptedException e) {
        }


    }

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }

    public void greenOn() {
        button.setEnabled(false);
        button.setSelected(false);
        setLight(1, Color.GRAY);
        setLight(2, Color.GREEN);

    }

    public void redOn() {
        button.setEnabled(true);
        setLight(1, Color.RED);
        setLight(2, Color.GRAY);

        evAck.sendEvent();
//        TODO make sure send event is right. that really goes back to controller. (if not create another event with controller holding other side and send back to controller that turned red)
    }

    public void shabbatOn() {
        button.setEnabled(false);
        setLight(1, Color.GRAY);
        setLight(2, Color.GRAY);
    }

}
