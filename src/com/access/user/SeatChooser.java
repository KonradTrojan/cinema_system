package com.access.user;

import com.access.admin.Bookings;
import com.access.admin.Rooms;
import com.access.admin.Showings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class MiejsceKinowe extends JButton { //rozszerzam klasę JButton o miejsce, które on symbolizuje
    Miejsce miejsce;
}

class Miejsce {
    private int nrMiejsca;
    private int nrRzedu;
    private boolean zajetosc;

    public Miejsce(int nrMiejsca, int nrRzedu, boolean zajetosc) {
        this.nrMiejsca = nrMiejsca;
        this.nrRzedu = nrRzedu;
        this.zajetosc = zajetosc;
    }

    public Miejsce(int nrMiejsca, int nrRzedu) {
        this(nrMiejsca, nrRzedu, false);
    }

    public int getNrMiejsca() {
        return nrMiejsca;
    }

    public int getNrRzedu() {
        return nrRzedu;
    }

    public boolean isZajety() {
        return zajetosc;
    }

    public void setZajetosc(boolean zajetosc) {
        this.zajetosc = zajetosc;
    }
}

class Sala {
    private int nrSali;
    private int liczbaMiejscWRzedzie;
    private int liczbaRzedow;

    private ArrayList<Miejsce> miejsca = new ArrayList<Miejsce>();

    public Sala(int nrSali, int liczbaMiejsc, int liczbaRzedow) {
        this.nrSali = nrSali;
        this.liczbaMiejscWRzedzie = liczbaMiejsc;
        this.liczbaRzedow = liczbaRzedow;

        for (int i = 0; i < liczbaRzedow; i++) {
            for (int j = 0; j < liczbaMiejsc; j++) {
                miejsca.add(new Miejsce(i, j, false));
            }
        }
    }

    public int getNrSali() {
        return nrSali;
    }

    public int getLiczbaMiejsc() {
        return liczbaMiejscWRzedzie;
    }

    public int getLiczbaRzedow() {
        return liczbaRzedow;
    }

    public boolean getStanMiejsca(Miejsce miejsce) {
        Miejsce a = miejsca.get(miejsce.getNrRzedu() * liczbaMiejscWRzedzie + miejsce.getNrMiejsca());
        return a.isZajety();
    }

    public void setStanMiejsca(Miejsce miejsce) {
        Miejsce a = miejsca.get(miejsce.getNrRzedu() * liczbaMiejscWRzedzie + miejsce.getNrMiejsca());
        a.setZajetosc(miejsce.isZajety());
    }

    public ArrayList<Miejsce> getMiejsca() {
        return miejsca;
    }
}

public class SeatChooser {
    SeatChooser(int showingId) {
        this.showingId = showingId;
        roomId = Showings.getRoomId(showingId);
        ArrayList<Integer> bookings = Bookings.getAllBookings(showingId);
        for(int i=0; i<bookings.size(); i++) {
            //sala.setStanMiejsca(new Miejsce());
        }
        rysuj();
    }

    private int roomId;
    private int showingId;
    private ArrayList<Miejsce> wybraneMiejsca = new ArrayList<Miejsce>();
    private JFrame f = new JFrame("Wskaż miejsce");
    private Sala sala = new Sala(roomId, Rooms.getNumOfSeats(roomId), Rooms.getNumOfRows(roomId));

    private void rysuj() {
        f.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel ekran = new JLabel("EKRAN", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        ekran.setBorder(border);
        c.gridx = 1;
        c.gridy = 0;
        c.ipady = 10;
        c.ipadx = 0;
        c.gridwidth = sala.getLiczbaMiejsc();
        f.add(ekran, c);

        for (int i = 0; i < sala.getLiczbaMiejsc(); i++) {
            JLabel l = new JLabel(i + 1 + "", SwingConstants.CENTER);
            c.gridy = 1;
            c.gridx = i + 1;
            c.gridwidth = 1;
            c.ipady = 10;
            c.ipadx = 0;
            c.fill = GridBagConstraints.BOTH;
            f.add(l, c);
        }

        for (int i = 0; i < sala.getLiczbaMiejsc(); i++) {
            JLabel l = new JLabel(i + 1 + "", SwingConstants.CENTER);
            c.gridy = sala.getLiczbaRzedow() + 2;
            c.gridx = i + 1;
            c.gridwidth = 1;
            c.ipady = 10;
            c.ipadx = 0;
            c.fill = GridBagConstraints.BOTH;
            f.add(l, c);
        }

        for (int i = 0; i < sala.getLiczbaRzedow(); i++) {
            JLabel l = new JLabel(i + 1 + "", SwingConstants.CENTER);
            c.gridy = i + 2;
            c.gridx = 0;
            c.gridwidth = 1;
            c.ipadx = 10;
            c.ipady = 0;
            c.fill = GridBagConstraints.BOTH;
            f.add(l, c);
        }

        for (int i = 0; i < sala.getLiczbaRzedow(); i++) {
            JLabel l = new JLabel(i + 1 + "", SwingConstants.CENTER);
            c.gridy = i + 2;
            c.gridx = sala.getLiczbaMiejsc() + 2;
            c.gridwidth = 1;
            c.ipady = 0;
            c.ipadx = 10;
            c.fill = GridBagConstraints.BOTH;
            f.add(l, c);
        }

        for (int i = 0; i < sala.getLiczbaMiejsc(); i++) {
            for (int j = 0; j < sala.getLiczbaRzedow(); j++) {
                MiejsceKinowe b = new MiejsceKinowe();
                b.miejsce = new Miejsce(i, j);
                if (sala.getStanMiejsca(b.miejsce)) {
                    b.setBackground(Color.RED);
                    b.setEnabled(false);
                } else {
                    b.setBackground(Color.GREEN);
                }
                c.gridx = i + 1;
                c.gridy = j + 2;
                c.gridwidth = 1;
                c.ipadx = 20;
                c.ipady = 40;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (!sala.getStanMiejsca(b.miejsce)) {
                            if (b.miejsce.isZajety()) {
                                b.setBackground(Color.GREEN);
                                wybraneMiejsca.remove(b.miejsce);
                                b.miejsce.setZajetosc(false);
                            } else {
                                b.setBackground(Color.YELLOW);
                                b.miejsce.setZajetosc(true);
                                wybraneMiejsca.add(b.miejsce);
                            }
                        } //zajetosc w MiejscuKinowym oznacza po prostu czy miejsce zostało wybrane
                    }
                });
                f.add(b, c);
            }
        } //rysowanie krzesel

        //rysowanie przycisku kontynuuj
        JButton kontynuuj = new JButton("Kontynuuj");
        kontynuuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (Miejsce a : wybraneMiejsca) {
                    sala.setStanMiejsca(a);
                    Bookings.addBooking(showingId, a.getNrMiejsca(), a.getNrRzedu());
                }
                f.dispose();
                f = new JFrame("Wskaż miejsce");
            }
        });
        c.gridx = 0;
        c.gridy = sala.getLiczbaRzedow() + 3;
        c.ipady = 10;
        c.ipadx = 0;
        c.gridwidth = sala.getLiczbaMiejsc() + 3;
        f.add(kontynuuj, c);

        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        c = null;
    }
}
