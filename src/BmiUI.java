import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import javax.print.attribute.standard.NumberUp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class BmiUI implements ActionListener {
    JFrame ramka = new JFrame("Kalkulator BMI");
    JButton m = new JButton("Mężczyzna");
    JButton k = new JButton("Kobieta");
    Color bg = new Color(200,200,200);
    JFXPanel wykres = new JFXPanel();
    GridBagConstraints layout = new GridBagConstraints();
    JTextField wiek = new JTextField();
    JTextField wzrost = new JTextField();
    JTextField waga = new JTextField();
    JTextField imie = new JTextField();
    JLabel imieL  = new JLabel(" Podaj imię: ");
    JButton save = new JButton("Zapisz");
    JLabel wiekL = new JLabel("Podaj wiek:");
    JLabel wagaL = new JLabel("Podaj wagę:");
    JLabel wzrostL = new JLabel("Podaj swój wzrosc (w metrach):");
    JButton obicz = new JButton("OBLICZ BMI");
    JTextArea wynik = new JTextArea();
    JButton test = new JButton();
    JLabel ciaza = new JLabel("Czy jest Pani w ciąży?");
    JTextField odpowiedz = new JTextField();
    JButton show = new JButton("Wykres");
    Dimension centrum = Toolkit.getDefaultToolkit().getScreenSize();

    public void calosc() {
        File czek = new File("./test.txt");
        if(!czek.exists()){
            first();
        }
        ramka.setLayout(new GridBagLayout());
        ramka.setSize(500, 500);
        ramka.setLocation(((int) centrum.getWidth() / 2) - 250, ((int) centrum.getHeight() / 2) - 250);
        ramka.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ramka.setVisible(true);


        layout.fill = GridBagConstraints.BOTH;
        layout.insets = new Insets(0, 0, 0, 0);
        obicz.addActionListener(this);
        setWyglad(0, 20, 2, 3);
        ramka.add(obicz, layout);


        layout.fill = GridBagConstraints.BOTH;
        layout.gridheight = 2;
        layout.gridwidth = 2;
        setWyglad(0, 40, 0, 2);
        wynik.setBackground(bg);
        ramka.add(wynik, layout);
        wynik.setEditable(false);


        show.addActionListener(this);
        layout.gridheight = 1;
        layout.gridwidth = 1;
        setWyglad(0, 20, 2, 2);
        ramka.add(show, layout);

        ciaza.setVisible(false);
        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 20, 2, 0);
        ramka.add(ciaza, layout);

        layout.fill = GridBagConstraints.BOTH;
        odpowiedz.setVisible(false);
        setWyglad(0,20, 2, 1);
        ramka.add(odpowiedz, layout);

        setWyglad(0, 20, 0, 1);
        ramka.add(wiek, layout);
        layout.fill = GridBagConstraints.HORIZONTAL;
        wiekL.setFont(new Font("New Times Roma", Font.PLAIN, 18));
        setWyglad(0, 0, 0, 0);
        ramka.add(wiekL, layout);


        layout.fill = GridBagConstraints.BOTH;
        setWyglad(0, 20, 1, 1);
        ramka.add(waga, layout);
        layout.fill = GridBagConstraints.HORIZONTAL;
        wagaL.setFont(new Font("New Times Roma", Font.PLAIN, 18));
        setWyglad(0, 0, 1, 0);
        ramka.add(wagaL, layout);


    }
    public void first(){
        ramka.setLayout(new GridBagLayout());
        ramka.setSize(300, 300);
        ramka.setLocation(((int) centrum.getWidth() / 2) - 100, ((int) centrum.getHeight() / 2) - 120);
        ramka.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ramka.setVisible(true);

        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 0, 0, 0);
        layout.gridwidth = 2;
        imieL.setFont(new Font("New Times Roma", Font.PLAIN, 18));
        ramka.add(imieL, layout);

        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 15, 2, 0);
        layout.gridwidth = 2;
        ramka.add(imie, layout);

        m.addActionListener(this);
        setWyglad(50, 30, 2, 1);
        ramka.add(m, layout);

        k.addActionListener(this);
        setWyglad(50, 30, 0, 1);
        ramka.add(k, layout);

        layout.fill = GridBagConstraints.BOTH;
        layout.gridwidth = 3;
        layout.insets = new Insets(0, 0, 0, 0);
        setWyglad(0, 30, 1, 2);
        ramka.add(save, layout);

    }
    public void wykresy(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle("test");
                final NumberAxis osX = new NumberAxis();
                final NumberAxis osY = new NumberAxis();
                LineChart<Number,Number> lineChart = new LineChart<Number, Number>(osX,osY);
                XYChart.Series<Number,Number> series = new XYChart.Series<Number, Number>();
                try{
                    FileWriter fileWriter = new FileWriter("./test.txt");
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        for(int i = 0; i<10; i++){
                            bufferedWriter.write(""+i);
                            bufferedWriter.newLine();
                        }
                    bufferedWriter.close();
                    FileReader fileReader = new FileReader("./test.txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String linia;
                    while ((linia = bufferedReader.readLine())!=null){
                        series.getData().add(new XYChart.Data<Number,Number>(Integer.parseInt(linia),Integer.parseInt(linia)));
                    }
                    bufferedReader.close();
                }catch (IOException |NumberFormatException e){
                    e.printStackTrace();
                }
                lineChart.getData().add(series);
                Scene scene = new Scene(lineChart,500,500);
                wykres.setVisible(true);
                wykres.setScene(scene);
                JFrame frame = new JFrame();
                frame.add(wykres);
                frame.setVisible(true);
                frame.setSize(500,500);
                frame.setLocation(((int) centrum.getWidth() / 2) + 235, ((int) centrum.getHeight() / 2) - 250);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String spr = e.getActionCommand();
        switch (spr) {
            case "OBLICZ BMI":
                float ile = Integer.parseInt(waga.getText()) / (Float.parseFloat(wzrost.getText()) * Float.parseFloat(wzrost.getText()));
                wynik.setWrapStyleWord(true);
                wynik.setLineWrap(true);
                wynik.setText("" + ile);

                if (Integer.parseInt(wiek.getText()) < 18) {
                    wynik.setText("Do 18 roku życia powinno się patrzeć na siatkę centylową do której potrzebny jest wynik BMI,który wynosi:   " + wynik.getText());
                } else if (Integer.parseInt(wiek.getText()) > 60) {
                    wynik.setText("Zalecamy dokładne badania, gdyż wynik BMI dla osób powyżej 60 roku życia zostaje zazwyczaj zawyżony.  " + wynik.getText());
                } else {
                    if (ile < 18.4) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz niedowagę - Sugerujemy lepiej zbilansować swoją dietę.");
                    } else if (ile > 18.5 & ile < 24.9) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz prawidlową wagę - Tak trzymaj!");
                    } else if (ile > 25 & ile < 29.9) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz nadwagę - Sugerujmy czestrzą aktywność fizyczną oraz zdrowsze posiłki.");
                    } else if (ile > 30 & ile < 34.9) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz otyłość - Sugerujemy wizytę u dietetyka oraz na siłowni.");
                    } else if (ile > 35 & ile < 40) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz otyłość drugiego stopnia - Polecamy szybką wizytę u lekarzy i na siłowni.");
                    } else if (ile > 41) {
                        wynik.setText(" Twoje bmi jest równe: " + wynik.getText() + ". Pokazuje to, że masz otyłość olbrzymią - Polecamy jak najszybszą wizyte u lekarzy!");
                    }
                    if (odpowiedz.getText().toUpperCase().equals("TAK")|odpowiedz.getText().toUpperCase().equals("T")){
                        wynik.setText( " BMI dla kobiet w ciąży nie powinno być obliczane.");
                    } else if (odpowiedz.getText().toUpperCase().equals("NIE")|odpowiedz.getText().toUpperCase().equals("N")|odpowiedz.getText().isEmpty()){
                        return;
                    } else {
                        wynik.setText("Prosimy o poprawne uzupełnienie danych na temat ewentualnej ciąży.");
                    }
                }
                break;
            case "Kobieta":
                ciaza.setVisible(true);
                odpowiedz.setVisible(true);
                break;
            case "Mężczyzna":
                ciaza.setVisible(false);
                odpowiedz.setVisible(false);
                odpowiedz.setText(null);
                break;
            case "Wykres":
                wykresy();
                break;
            case "Zapisz":
                try{
                    FileWriter filewriter = new FileWriter("./test.txt");
                }catch (IOException e1){
                    e1.printStackTrace();
                }break;
        }
    }

    public void setWyglad(int szerokosc, int wysokosc, int pozycjaX, int pozycjaY) {
        layout.ipadx = szerokosc;
        layout.ipady = wysokosc;
        layout.gridx = pozycjaX;
        layout.gridy = pozycjaY;
    }

}
