package com.access.user;

import com.access.admin.Movies;
import com.access.admin.Showings;
import de.jaret.util.date.IntervalImpl;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.model.DefaultRowHeader;
import de.jaret.util.ui.timebars.model.DefaultTimeBarModel;
import de.jaret.util.ui.timebars.model.DefaultTimeBarRowModel;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowingChooser {
    public ShowingChooser(int movieId) {
        this.movieId = movieId;
        draw();
    }

    private int movieId;

    private void draw() {
        JFrame f = new JFrame("Wybór seansu");
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTimeBarModel model = new DefaultTimeBarModel();
        TimeBarViewer tbv = new TimeBarViewer(model);
        DefaultRowHeader header = new DefaultRowHeader(Movies.getTitle(movieId));
        DefaultTimeBarRowModel tbr = new DefaultTimeBarRowModel(header);
        ArrayList <Integer> showings = Showings.getAllShowings(movieId);
        for (int i = 0; i < showings.size(); i++) {
            IntervalImpl interval = new IntervalImpl();
            JaretDate start = new JaretDate();
            JaretDate end = new JaretDate();
            interval.setBegin(start.copy());
            interval.setEnd(end.copy());
            tbr.addInterval(interval);
        }
        model.addRow(tbr);
        f.getContentPane().add(tbv, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }
}
