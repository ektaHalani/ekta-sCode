package mvc;



//import tools.Utilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class AppPanel extends JPanel implements ActionListener, PropertyChangeListener {

    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel(AppFactory factory) {
        super();
        this.factory = factory;
        model = factory.makeModel();
        view = factory.makeView(model);
        view.setBackground(Color.WHITE);
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.GRAY);
        setLayout((new GridLayout(1, 2)));
        controlPanel.setLayout(new FlowLayout());
        add(controlPanel);
        add(view);

        if (model != null) {
            model.addPropertyChangeListener(this);
            model.addPropertyChangeListener(view);
        }

        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu = Utilities.makeMenu("File", new String[] {"New",  "Save", "Open", "Quit"}, this);
        result.add(fileMenu);

        JMenu editMenu = Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);

        JMenu helpMenu = Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);

        return result;
    }

    public void display() { frame.setVisible(true); }

    public void propertyChange(PropertyChangeEvent event) {

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model newModel) {
        this.model.removePropertyChangeListener(this);
        this.model = newModel;
        this.model.initSupport();
        this.model.addPropertyChangeListener(this);
        view.setModel(this.model);
        model.changed();
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String cmmd = ae.getActionCommand();
            if (cmmd == "Save") {;
                ObjectOutputStream os;
                if (model.getFileName() != null) {
                    os = new ObjectOutputStream(new FileOutputStream(model.getFileName()));
                } else {
                    String fName = Utilities.getFileName(null, false);
                    os = new ObjectOutputStream(new FileOutputStream(fName));
                }
                os.writeObject(model);
                os.close();
            } else if (cmmd == "Open") {
                String fName = Utilities.getFileName(null, true);
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                model = (Model) is.readObject();
                view.setModel(model);
                is.close();
            } else if (cmmd == "New") {
                setModel(factory.makeModel());
                view.setModel(model);
            } else if (cmmd == "Quit") {
                //Utilities.saveChanges(model);
                System.exit(1);
            } else if (cmmd == "About") {
                Utilities.inform(factory.about());
            } else if (cmmd == "Help") {
                Utilities.inform(factory.getHelp());
            } else {
                Command command = factory.makeEditCommand(model, cmmd);
                command.execute();
            }
        }
        catch (Exception e) {
            handleException(e);
        }
    }

    protected void handleException(Exception e) {
        Utilities.error(e);
    }


//    public static void main(String[] args) {
//        AppPanel app = new AppPanel();
//        app.display();
//    }

}
