package com.example.bestwish;

import com.example.bestwish.Data.Postgre;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, ParseException, InterruptedException {
        Postgre postgre = new Postgre();
        Front.main(args);
//        Postgre.getDateLeft("29-10-2003");
    }

    }
