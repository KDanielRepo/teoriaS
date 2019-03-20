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
    JFXPanel wykres = new JFXPanel();
    GridBagConstraints layout = new GridBagConstraints();
    JTextField wiek = new JTextField();
    JTextField wzrost = new JTextField();
    JTextField waga = new JTextField();
    JLabel wiekL = new JLabel("Podaj swój wiek:");
    JLabel wagaL = new JLabel("Podaj wagę:");
    JLabel wzrostL = new JLabel("Podaj swój wzrosc (w metrach):");
    JButton obicz = new JButton("OBLICZ BMI");
    JTextArea wynik = new JTextArea();
    JButton test = new JButton();
    JLabel ciaza = new JLabel("Czy jest Pani w ciąży?");
    JTextField odpowiedz = new JTextField();

    public void calosc() {
        ramka.setLayout(new GridBagLayout());
        ramka.setSize(500, 500);
        Dimension centrum = Toolkit.getDefaultToolkit().getScreenSize();
        ramka.setLocation(((int) centrum.getWidth() / 2) - 250, ((int) centrum.getHeight() / 2) - 250);
        ramka.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ramka.setVisible(true);


        layout.fill = GridBagConstraints.BOTH;
        layout.insets = new Insets(0, 0, 10, 10);
        obicz.addActionListener(this);
        layout.gridheight = 2;
        setWyglad(0, 50, 2, 4);
        ramka.add(obicz, layout);


        layout.fill = GridBagConstraints.BOTH;
        layout.gridheight = 2;
        layout.gridwidth = 2;
        setWyglad(0, 0, 0, 4);
        ramka.add(wynik, layout);
        wynik.setEditable(false);

        layout.gridwidth = 1;
        m.addActionListener(this);
        layout.gridheight = 2;
        setWyglad(0, 0, 1, 0);
        ramka.add(m, layout);

        k.addActionListener(this);
        layout.gridheight = 2;
        setWyglad(100, 0, 2, 0);
        ramka.add(k, layout);

        layout.gridheight = 1;
        ciaza.setVisible(false);
        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 0, 2, 2);
        ramka.add(ciaza, layout);

        layout.fill = GridBagConstraints.BOTH;
        odpowiedz.setVisible(false);
        setWyglad(0, 50, 2, 3);
        ramka.add(odpowiedz, layout);

        layout.gridheight = 1;
        setWyglad(0, 50, 0, 1);
        ramka.add(wiek, layout);
        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 0, 0, 0);
        ramka.add(wiekL, layout);

        layout.fill = GridBagConstraints.BOTH;
        setWyglad(0, 50, 1, 3);
        ramka.add(wzrost, layout);
        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 0, 1, 2);
        ramka.add(wzrostL, layout);

        layout.fill = GridBagConstraints.BOTH;
        setWyglad(0, 0, 0, 3);
        ramka.add(waga, layout);
        layout.fill = GridBagConstraints.HORIZONTAL;
        setWyglad(0, 0, 0, 2);
        ramka.add(wagaL, layout);
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
                     }else{
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
        }
    }

    public void setWyglad(int szerokosc, int wysokosc, int pozycjaX, int pozycjaY) {
        layout.ipadx = szerokosc;
        layout.ipady = wysokosc;
        layout.gridx = pozycjaX;
        layout.gridy = pozycjaY;
    }

}
