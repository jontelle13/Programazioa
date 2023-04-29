import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComboListener implements ActionListener {
    private Main main;

    public ComboListener(Main main){
        this.main = main;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.main.load_image();
    }
}