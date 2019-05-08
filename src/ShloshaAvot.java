import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770
 */

/**
 * @author לויאן
 */

public class ShloshaAvot extends Thread {
    Ramzor ramzor;
    JPanel panel;
    private Event64 evGreenShlosha, evRedShlosha, evWeekdayShlosha, evShabbatShlosha,evRedShlosha;


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

/*    public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evGreenShlosha, Event64 evRedShlosha,
                       Event64 evTurnedRedOn,Event64 evWeekdayShlosha, Event64 evShabbatShlosha) {
        this.ramzor = ramzor;
        this.panel = panel;
        new CarsMaker(panel, this, key);
        this.evGreenShlosha = evGreenShlosha;
        this.evRedShlosha = evRedShlosha;
        this.evWeekdayShlosha = evWeekdayShlosha;
        this.evShabbatShlosha = evShabbatShlosha;
        this.evTurnedRedOn=evTurnedRedOn;
        start();
    }*/
    public ShloshaAvot(Ramzor ramzor, JPanel panel, int key, Event64 evRedShlosha, Event64 evGreenShlosha,Event64 evShabbatShlosha,Event64 evWeekdayShlosha,) {
        this.ramzor = ramzor;
        this.panel = panel;
        new CarsMaker(panel, this, key);
        this.evGreenShlosha = evGreenShlosha;
        this.evRedShlosha = evRedShlosha;
        this.evWeekdayShlosha = evWeekdayShlosha;
        this.evShabbatShlosha = evShabbatShlosha;
       
        start();
    }

    public void run() {
        try {

            /* try {*/
            boolean finish = false;
            boolean out = false;
            outState = OutState.WEEKDAY;
            InWeekdayState = InWeekdayState.RED;
            redOn();
            int sum = 0;
            while (!finish) {
                switch (outState) {
                    case WEEKDAY:
                        while (!out) {
                            switch (InWeekdayState) {
                                case RED:
                                    while (true) {
                                        if (evGreenShlosha.arrivedEvent()) {
                                            evGreenShlosha.waitEvent();
                                            yellowRedOn();
//                                            InWeekdayState = InWeekdayState.YELLOW;
                                            InWeekdayState = InWeekdayState.GREEN;

                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;
                            /*    case YELLOW:
                                    while (true) {
                                        if (evGreenShlosha.arrivedEvent()) {
                                            evGreenShlosha.waitEvent();
                                            yellowRedOn();
                                            InWeekdayState = InWeekdayState.GREEN;
                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
                                            shabbatOn();
                                            out = true;
                                            outState = OutState.SHABBAT;
                                            break;
                                        }
                                    }
                                    break;*/
                                case GREEN:
                                    while (true) {
                                        if (evRedShlosha.arrivedEvent()) {
                                            evRedShlosha.waitEvent();
                                            yellowOn();
                                            InWeekdayState = InWeekdayState.RED;
                                            break;
                                        } else if (evShabbatShlosha.arrivedEvent()) {
                                            evShabbatShlosha.waitEvent();
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
													InWeekdayState=InWeekdayState.RED;
												}
												else
												{
													out=true;
													call("have no small can");
													outState=OutState.SHABBAT;
												}
												break;
											}
											else if (evShabbatShlosha.arrivedEvent())
											{
												evShabbatShlosha.waitEvent();
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
                        evWeekdayShlosha.waitEvent();
                        out = false;
//                        init();
                        outState = OutState.WEEKDAY;
                        if (!history)
                            redOn();
                        InWeekdayState = InWeekdayState.RED;
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

		
			/*while (true)
			{
				sleep(1000);
				setLight(2,Color.YELLOW);
				sleep(1000);
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.GREEN);
				stop=false;    //makes cars
				sleep(3000);
				stop=true;     //stops making cars
				setLight(1,Color.LIGHT_GRAY);
				setLight(2,Color.YELLOW);
				setLight(3,Color.LIGHT_GRAY);
				sleep(1000);
				setLight(1,Color.RED);
				setLight(2,Color.LIGHT_GRAY);
				setLight(3,Color.LIGHT_GRAY);
			}*/
        } catch (InterruptedException e) {
        }

    }

    public void setLight(int place, Color color) {
        ramzor.colorLight[place - 1] = color;
        panel.repaint();
    }

    public void yellowRedOn()throws InterruptedException {
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
    }

    public void yellowOn()throws InterruptedException{

            setLight(1, Color.LIGHT_GRAY);
            setLight(2, Color.YELLOW);
            setLight(3, Color.LIGHT_GRAY);
            sleep(1000);
            redOn();

    }

    public void redOn() {
        setLight(1, Color.RED);
        setLight(2, Color.LIGHT_GRAY);
        setLight(3, Color.LIGHT_GRAY);
        evRedShlosha.sendEvent();//TODO check if right

    }

    public void shabbatOn() throws InterruptedException{

            setLight(1, Color.LIGHT_GRAY);
            setLight(2, Color.YELLOW);
            setLight(3, Color.LIGHT_GRAY);
            while (!evWeekdayShlosha.arrivedEvent())
//			while (outState==OutState.SHABBAT)
            {

                sleep(1000);
                setLight(2, Color.GRAY);
                sleep(1000);
                setLight(2, Color.YELLOW);
            }

    }

    public boolean isStop() {
        return stop;
    }
}
