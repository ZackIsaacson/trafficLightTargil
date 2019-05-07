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
public class MyActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		System.out.println(butt.getName());
//				butt.setEnabled(false);
//				butt.setSelected(false);
	}

}
