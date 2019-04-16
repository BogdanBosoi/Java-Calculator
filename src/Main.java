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

    boolean wasSignLastWritten = false;         // Asta imi spune daca ultimul caracter introdus a fost un semn pentru a evita scrierea continua de semne matematice

    private String equalText(String arg) {
        double dRes = 0;                                // Rezultat
        // Impart operatiile pe bucati
        ArrayList<String> operation = new ArrayList<>(Arrays.asList(arg.split(" ")));
        int sign = 1;                                   // Imi spune fie semnul pentru adunare / scadere, fie daca am inmultire/ impartire

        // Probabil va trebui sa optimizez codul decat sa evit cazurile naspa
        // Aici din cauza splitului obtin stringuri nule si dupa arunca exceptii parseDouble
        // Asa ca orice e string null, il scot
        // Daca dau de un * sau / atunci:
        // Aplic operatia de * sau / de aici pe termenii adiacenti si pun rezultatul peste semn
        // Ulterior scot valorile adiacente
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
                if(text == "CE")                            // Daca a dat pe CE atunci dau reset la labelul cu operatiile
                    fieldLabel.setText("");
                else if(text == "=") {                      // Daca am egal chem functia care prelucreaza sirul de date
                    fieldLabel.setText(equalText(fieldLabel.getText()));
                    wasSignLastWritten = false;
                }
                else {
                    if (wasSignLastWritten == true && number <= 9) {            // Daca am scris un semn inainte si acum scriu o cifra
                        fieldLabel.setText(fieldLabel.getText() + " " + text);  // Scriu ceea ce e de pe buton + un spatiu
                        wasSignLastWritten = false;                             // Marchez faptul ca ultimul caracter scris nu este un semn
                    }
                    else if(wasSignLastWritten == false && number <= 9)         // Daca nu am scris un semn inainte si inca scriu cifre, atunci doar pun cifra fara spatiu
                        fieldLabel.setText(fieldLabel.getText() + text);
                    else if(wasSignLastWritten == false && (text == "+" || text == "-" || text == "*" || text == "/")) {        // Daca nu am scris mai inainte un semn si il scriem acum
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
