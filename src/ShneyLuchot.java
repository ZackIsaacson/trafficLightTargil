import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770
 */

/**
 * @author לויאן
 */

class ShneyLuchot extends Thread {
    Ramzor ramzor;
    JPanel panel;
    private Event64 evGreenShney, evRedShney, evWeekdayShney, evShabbatShney;


    enum OutState {WEEKDAY, SHABBAT};
    OutState outState;

    enum InState {RED, GREEN};
    InState inState;
    private boolean history = false;

    /*public ShneyLuchot( Ramzor ramzor,JPanel panel)
    {
        this.ramzor=ramzor;
        this.panel=panel;
        start();
    }*/
    public ShneyLuchot(Ramzor ramzor, JPanel panel, Event64 evGreenShney, Event64 evRedShney,
                       Event64 evWeekdayShney, Event64 evShabbatShney) {
        this.ramzor = ramzor;
        this.panel = panel;
        this.evGreenShney = evGreenShney;
        this.evRedShney = evRedShney;
        this.evWeekdayShney = evWeekdayShney;
        this.evShabbatShney = evShabbatShney;

        setDaemon(true);
        start();
    }

    public void run() {
        /* try {*/
        boolean finish = false;
        boolean out = false;
        outState = OutState.WEEKDAY;
        inState = InState.RED;
        redOn();
        int sum = 0;
        while (!finish) {
            switch (outState) {
                case WEEKDAY:
                    while (!out) {
                        switch (inState) {
                            case RED:
                                while (true) {
                                    if (evGreenShney.arrivedEvent()) {
                                        evGreenShney.waitEvent();
                                        greenOn();
                                        inState = InState.GREEN;
                                        break;
                                    } else if (evShabbatShney.arrivedEvent()) {
                                        evShabbatShney.waitEvent();
                                        shabbatOn();
                                        out = true;
                                        outState = OutState.SHABBAT;
                                        break;
                                    }
                                }
                                break;

                            case GREEN:
                                while (true) {
                                    if (evGreenShney.arrivedEvent()) {
                                        evGreenShney.waitEvent();
                                        redOn();
                                        inState = InState.GREEN;
                                        break;
                                    } else if (evShabbatShney.arrivedEvent()) {
                                        evShabbatShney.waitEvent();
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
										break;*/
                        }       // switch of in state at out state 'WEEKDAY'
                    }           // loop at out state 'WEEKDAY'
                    break;

                case SHABBAT:
                    evWeekdayShney.waitEvent();
                    out = false;
//                        init();
                    outState = OutState.WEEKDAY;
                    if (!history)
                        redOn();
                    inState = InState.RED;
                    break;

            }
        }
			/*	sleep(1000);
				setLight(1,Color.GRAY);
				setLight(2,Color.GREEN);
				sleep(1000);
				setLight(1,Color.RED);
				setLight(2,Color.GRAY);*/

        /*} catch (InterruptedException e) {
        }*/

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
    }

    public void shabbatOn() {
        setLight(1, Color.GRAY);
        setLight(2, Color.GRAY);
    }

}
