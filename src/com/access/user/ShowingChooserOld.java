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

public class ShowingChooserOld {
    public ShowingChooserOld(int movieId) {
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
        //DefaultTimeBarModel model = new DefaultTimeBarModel();
        //dodawanie seansów
        ArrayList<Integer> allShowings = Showings.getAllShowingsByMovie(movieId);
        ArrayList<Pair<Integer, Integer>> months = new ArrayList<>();
        for (int i = 0; i < allShowings.size(); i++) {
            Calendar cal = Calendar.getInstance();
            Timestamp beginTimestamp = Objects.requireNonNull(Showings.getDateStart(allShowings.get(i)));
            cal.setTime(beginTimestamp);
            JaretDate begin = new JaretDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
            if (!months.contains(new Pair<Integer, Integer>(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)))) {
                months.add(new Pair<Integer, Integer>(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)));
                model.createDay(begin);
                //model.createMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
                Timestamp endTimestamp = Objects.requireNonNull(Showings.getDateEnd(allShowings.get(i)));
                cal.setTime(endTimestamp);
                JaretDate end = new JaretDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
                Day day = model.getDay(begin);
                Appointment a = new Appointment(begin, end, "test");
                day.addInterval(a);
            }
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
        _tbv.setAdjustMinMaxDatesByModel(true);
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
    /*public static TimeBarModel createModel() {
        DefaultTimeBarModel model = new DefaultTimeBarModel();

        JaretDate date = new JaretDate();

        int length = 120;

        DefaultRowHeader header = new DefaultRowHeader("r1");
        DefaultTimeBarRowModel tbr = new DefaultTimeBarRowModel(header);
        IntervalImpl interval = new IntervalImpl();
        interval.setBegin(date.copy());
        interval.setEnd(date.copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(30));
        interval.setEnd(date.copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(60));
        interval.setEnd(interval.getBegin().copy().advanceMinutes(length));
        tbr.addInterval(interval);

        model.addRow(tbr);

        header = new DefaultRowHeader("r2");
        tbr = new DefaultTimeBarRowModel(header);
        interval = new IntervalImpl();
        interval.setBegin(date.copy());
        interval.setEnd(date.copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(120));
        interval.setEnd(date.copy().advanceMinutes(length + length));
        tbr.addInterval(interval);

        model.addRow(tbr);

        header = new DefaultRowHeader("r3");
        tbr = new DefaultTimeBarRowModel(header);
        interval = new IntervalImpl();
        interval.setBegin(date.copy());
        interval.setEnd(date.copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(30));
        interval.setEnd(date.copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(60));
        interval.setEnd(interval.getBegin().copy().advanceMinutes(length));
        tbr.addInterval(interval);

        interval = new IntervalImpl();
        interval.setBegin(date.copy().advanceMinutes(90));
        interval.setEnd(interval.getBegin().copy().advanceMinutes(length));
        tbr.addInterval(interval);

        model.addRow(tbr);

        // add some empty rows for drag&drop fun
        header = new DefaultRowHeader("r4");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r5");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r6");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r7");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r8");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        header = new DefaultRowHeader("r9");
        tbr = new DefaultTimeBarRowModel(header);
        model.addRow(tbr);

        return model;
    }*/

    public static void main(String[] args) {
        ShowingChooserOld x = new ShowingChooserOld(33);
    }
}
