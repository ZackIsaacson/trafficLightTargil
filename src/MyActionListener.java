import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/*
 * Created on Tevet 5770
 */

/**
 * @author לויאן
 */

//TODO sends the controller 2 events. 1- if shabbos/ chol was pressed. 2- what 'shney luchot' was pressed. controller will deal with it himself.~
public class MyActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        Controller controller = Controller.getInstance();
        JRadioButton butt = (JRadioButton) e.getSource();
        boolean selected = false;
//		System.out.println(butt.getName());

        if (Integer.parseInt(butt.getName()) < 16) {
            butt.setSelected(true);
//	        todo wait 1 second
            butt.setSelected(false);
        } else {
            if (butt.isSelected()) {
                selected = false;
            } else
                selected = true;
            butt.setSelected(selected);
        }


        controller.eventFromListener(Integer.parseInt(butt.getName()), selected);

//				butt.setEnabled(false);
//				butt.setSelected(false);
    }

}
