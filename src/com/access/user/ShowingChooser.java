package com.access.user;

import com.access.admin.Showings;
import de.jaret.util.date.JaretDate;
import de.jaret.util.ui.timebars.TimeBarViewerInterface;
import de.jaret.util.ui.timebars.mod.DefaultIntervalModificator;
import de.jaret.util.ui.timebars.swing.TimeBarViewer;
import de.jaret.util.ui.timebars.swing.renderer.OldDefaultTimeScaleRenderer;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ShowingChooser {
    public ShowingChooser(int movieId) {
        this.movieId = movieId;
        draw();
    }

    private int movieId;
    static TimeBarViewer _tbv;

    private void draw() {
        JFrame f = new JFrame("Wybór seansu");
        f.getContentPane().setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CalendarModel model = new CalendarModel();
        //dodawanie seansów
        ArrayList<Integer> allShowings = Showings.getAllShowings();
        ArrayList<Pair<Integer, Integer>> months = new ArrayList<>();
        for (int i = 0; i < allShowings.size(); i++) {
            Calendar cal = Calendar.getInstance();
            Timestamp beginTimestamp = Objects.requireNonNull(Showings.getDateStart(allShowings.get(i)));
            cal.setTime(beginTimestamp);
            if (!months.contains(cal.get(Calendar.MONTH))) {
                months.add(new Pair<Integer, Integer>(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
                model.createMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
            }
            JaretDate begin = new JaretDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            Timestamp endTimestamp = Objects.requireNonNull(Showings.getDateEnd(allShowings.get(i)));
            cal.setTime(endTimestamp);
            JaretDate end = new JaretDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            Day day = model.getDay(begin);
            Appointment a = new Appointment(begin, end, "test");
            day.addInterval(a);
        }
        //koniec dodawania seansów
        _tbv = new TimeBarViewer(model);
        _tbv.addIntervalModificator(new DefaultIntervalModificator());

        _tbv.setPixelPerSecond(0.018);
        _tbv.setDrawRowGrid(true);

        _tbv.setSelectionDelta(6);
        // this is the col width!
        _tbv.setRowHeight(150);

        _tbv.setTBOrientation(TimeBarViewerInterface.Orientation.VERTICAL);
        // vertical: the y axiswidth is the height of the row headers!
        _tbv.setYAxisWidth(20);

        // do not adjust the displayed time according to the model
        // use the basedate day!
        _tbv.setAdjustMinMaxDatesByModel(false);
        _tbv.setMinDate(CalendarModel.BASEDATE.copy());
        _tbv.setMaxDate(CalendarModel.BASEDATE.copy().advanceDays(1));

        _tbv.setDrawOverlapping(false);
        _tbv.setSelectionDelta(6);
        _tbv.setTimeScalePosition(TimeBarViewerInterface.TIMESCALE_POSITION_TOP);

        // modifications are restricted
        _tbv.addIntervalModificator(new AppointmentModificator());

        _tbv.setTimeScaleRenderer(new OldDefaultTimeScaleRenderer());

        _tbv.setGridRenderer(new CalendarGridRenderer());

        _tbv.registerTimeBarRenderer(Appointment.class, new AppointmentRenderer());


        f.getContentPane().add(_tbv, BorderLayout.CENTER);

        //f.getContentPane().add(new CalendarControlPanel(_tbv), BorderLayout.SOUTH);

        f.pack();
        f.setVisible(true);

    }

    public static void main(String[] args) {
        ShowingChooser x = new ShowingChooser(41);
    }
}
