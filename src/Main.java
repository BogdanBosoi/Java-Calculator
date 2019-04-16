import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel fieldLabel;
    private JPanel textPanel;
    private JPanel buttonsPanel;
    private JPanel opPanel;
    private JButton[] buttons;
    private int noButtons = 15;

    boolean wasSignLastWritten = false;

    private String equalText(String arg) {
        double dRes = 0;                                // Rezultat
        // Impart operatiile pe bucati
        ArrayList<String> operation = new ArrayList<>(Arrays.asList(arg.split(" ")));
        int sign = 1;                                   // Imi spune fie semnul pentru adunare / scadere, fie daca am inmultire/ impartire

        // Probabil va trebui sa optimizez codul decat sa evit cazurile naspa
        // Aici din cauza splitului obtin stringuri nule si dupa arunca exceptii parseDouble
        // Asa ca orice e string null, il fac "0"
        // Daca dau de un * sau / atunci:
        // Aplic operatia de * sau / de aici pe termenii adiacenti si pun rezultatul peste semn
        // Ulterior pun valorile "0" in pozitiile adiacente
        for(int i = 0; i < operation.size(); i++) {
            if ((operation.get(i)).equals(""))
                operation.remove(i);
            if ((operation.get(i)).equals("*")){

                operation.set(i, (Double.parseDouble(operation.get(i - 1)) * Double.parseDouble(operation.get(i + 1)) + ""));
                System.out.println(operation.get(i - 1) + " " + operation.get(i + 1));
                operation.remove(i - 1);
                i--;
                operation.remove(i + 1);

            }
            if((operation.get(i)).equals("/")) {

                operation.set(i, (Double.parseDouble(operation.get(i - 1)) / Double.parseDouble(operation.get(i + 1)) + ""));
                operation.remove(i  -1);
                i--;
                operation.remove(i + 1);

            }
        }

        for(String it : operation) {
                if (it.equals("+"))
                    sign = 1;
                else if (it.equals("-"))
                    sign = -1;
                else
                    dRes += sign * Double.parseDouble(it);
        }

        return dRes + "";
    }

    private ActionListener listenerWriter = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JButton){

                String text;

                text = ((JButton)e.getSource()).getText();      // Text imi ia textul de pe buton

                int number;                                  // Vad daca e numar
                try {
                    number = Integer.parseInt(text);

                } catch (NumberFormatException g) {
                    number = 10;
                }
                if(text == "CE")
                    fieldLabel.setText("");
                else if(text == "=") {
                    fieldLabel.setText(equalText(fieldLabel.getText()));
                    wasSignLastWritten = false;
                }
                else {
                    if (wasSignLastWritten == true && number <= 9) {
                        fieldLabel.setText(fieldLabel.getText() + " " + text);
                        wasSignLastWritten = false;
                    }
                    else if(wasSignLastWritten == false && number <= 9)
                        fieldLabel.setText(fieldLabel.getText() + text);
                    else if(wasSignLastWritten == false && (text == "+" || text == "-" || text == "*" || text == "/")) {
                        fieldLabel.setText(fieldLabel.getText() + " " + text);
                        wasSignLastWritten = true;
                    }
                }
                }

        }

    };


    private void buttonsInitializer() {

        buttons = new JButton[noButtons + 1];

        for(int i = 0; i <= 9; i++) {
            buttons[i] = new JButton(i + "");
            buttons[i].addActionListener(listenerWriter);
        }
        buttons[10] = new JButton("+");
        buttons[10].addActionListener(listenerWriter);
        buttons[11] = new JButton("-");
        buttons[11].addActionListener(listenerWriter);
        buttons[12] = new JButton("=");
        buttons[12].addActionListener(listenerWriter);
        buttons[13] = new JButton("CE");
        buttons[13].addActionListener(listenerWriter);
        buttons[14] = new JButton("*");
        buttons[14].addActionListener(listenerWriter);
        buttons[15] = new JButton("/");
        buttons[15].addActionListener(listenerWriter);
        //int o = 1;
        /*for(int i = 0; i <= 64; i += 32)
            for(int j = 100; j <= 164; j += 32)
                buttons[o++].setBounds(i, j, 32, 32);

            buttons[0].setBounds(0, 196, 32, 228);
            buttons[10].setBounds(96, 100, 32, 32);
            buttons[11].setBounds(96, 132, 32, 32);
            buttons[12].setBounds(96, 164, 32, 32);*/

    }

    private void init() {


        mainFrame = new JFrame("Calculator");
        mainFrame.setSize(400, 450);
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);


        mainFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        headerLabel = new JLabel("Calculator", JLabel.CENTER);
        headerLabel.setSize(100, 50);
        headerLabel.setSize(380, 50);

        fieldLabel = new JLabel("", JLabel.RIGHT);
        fieldLabel.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        fieldLabel.setBorder(border);

        textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1));
        textPanel.setBounds(0, 0, 400, 100);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 3));
        buttonsPanel.setBounds(0, 100, 300, 300);

        opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(4, 1));
        opPanel.setBounds(300, 100, 100, 300);

        buttonsInitializer();
        for(int i = 1; i <= 9; i++)
            buttonsPanel.add(buttons[i]);
        buttonsPanel.add(buttons[0]);

        opPanel.add(buttons[13]);
        opPanel.add(buttons[10]);
        opPanel.add(buttons[11]);
        opPanel.add(buttons[12]);
        opPanel.add(buttons[14]);
        opPanel.add(buttons[15]);
        textPanel.add(headerLabel);
        textPanel.add(fieldLabel);

        mainFrame.add(textPanel);
        mainFrame.add(buttonsPanel);
        mainFrame.add(opPanel);
        mainFrame.setVisible(true);


    }

    public Main() {
        init();
    }
    public static void main(String[] args) {

        Main mainObj = new Main();



    }













}
