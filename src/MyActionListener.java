import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

import static java.lang.Thread.sleep;

/*
 * Created on Tevet 5770
 */

/**
 * @author לויאן
 */

//TODO sends the controller 2 events. 1- if shabbos/ chol was pressed. 2- what 'shney luchot' was pressed. controller will deal with it himself.~
public class MyActionListener implements ActionListener {
    Event64 evButtonPressed;

    MyActionListener(Event64 evButtonPressed){
        this.evButtonPressed=evButtonPressed;
    }


    public void actionPerformed(ActionEvent e) {
        Controller controller = Controller.getInstance();
        JRadioButton butt = (JRadioButton) e.getSource();
        boolean selected = false;
//		System.out.println(butt.getName());

        if (Integer.parseInt(butt.getName()) < 16) {
            butt.setSelected(true);
            try {
                sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
//	        todo wait 1 second
            butt.setSelected(false);
        } else {
            selected=!butt.isSelected();
//            if (butt.isSelected()) { //was selected -> unselect
//                selected = false;
//            } else  //wasnt selected ->select.
//                selected = true;
            butt.setSelected(selected);
        }


//        controller.eventFromListener(Integer.parseInt(butt.getName()), selected);

//				butt.setEnabled(false);
//				butt.setSelected(false);
    }

}
