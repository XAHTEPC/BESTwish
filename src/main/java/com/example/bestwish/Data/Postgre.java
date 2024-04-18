package com.example.bestwish.Data;

import com.example.bestwish.Model.Client;
import com.example.bestwish.Model.Wish;
import com.example.bestwish.Model.WishList;
import com.example.bestwish.View.MainView;

import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Postgre {
    static Connection connection;
    static Statement statmt;

    static ResultSet resultSet;

    public Postgre() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BESTwish","postgres","123");
        statmt = connection.createStatement();
    }
    public  static void addClient(String login, String password, String name, String birth) throws SQLException, FileNotFoundException, ClassNotFoundException {
        String passHash = Crypto.hash(password);
        statmt.execute("INSERT INTO public.\"Client\"(\n" +
                "\tname_client, login_client, pass_client, birth_client)\n" +
                "\tVALUES ('" + name + "', '" + login + "', '"+passHash+"', '"+birth+"');");
        MainView.id = getID(login,password);
        MainView.name = name;
    }
    public static Boolean checkLogin(String login) throws SQLException {
        String s = "true";
        resultSet = statmt.executeQuery("SELECT id_client FROM public.\"Client\" WHERE login_client = '" + login +"';");
        while (resultSet.next()){
            s = resultSet.getString("id_client");
        }
        if(s.equals("true"))
            return true;
        else
            return false;
    }
    public static String getID(String login, String password) throws SQLException {
//        System.out.println("login "+login+" | pass "+password);
        String passHash = Crypto.hash(password);
//        System.out.println("login "+login+" | pass "+passHash);
        String id="error";
        String name = "";
        resultSet = statmt.executeQuery("SELECT * FROM public.\"Client\" WHERE login_client = '" + login +
                "' and pass_client ='" + passHash+"';");
        while(resultSet.next()){
            id = resultSet.getString("id_client");
            name = resultSet.getString("name_client");
        }
        MainView.name = name;
        MainView.id = id;
        System.out.println("id:" + id);
        return id;
    }

    public static ArrayList<Client> findFriend(String el) throws SQLException {
        ArrayList<Client> mas = new ArrayList<>();
        resultSet = statmt.executeQuery("SELECT *\n" +
                "FROM public.\"Client\" \n" +
                "WHERE position('" + el + "' in name_client)>0 \n" +
                "\tor position('" + el + "' in login_client)>0 and id_client <> '"+MainView.id+"';");
        while (resultSet.next()) {
            String id = resultSet.getString("id_client");
            String name = resultSet.getString("name_client");
            String birthday = resultSet.getString("birth_client");
            String login = resultSet.getString("login_client");
            Client client = new Client(id, login, birthday, name);
            System.out.println("id: " + id + " | name: " + name + " | birthday: " + birthday + " | login: " + login);

            mas.add(client);
        }
        return mas;
    }
    public static ArrayList<Client> getMyFriends() throws SQLException {
        ArrayList<Client> mas = new ArrayList<>();
        System.out.println(MainView.id);
        resultSet = statmt.executeQuery("SELECT *\n" +
                "FROM public.\"Friend\" \n" +
                "JOIN public.\"Client\" on public.\"Client\".id_client = public.\"Friend\".id_friend2 \n" +
                "LEFT JOIN public.\"WishList\" using(id_client)\n" +
                "WHERE id_friend1='"+MainView.id+"' and status_friend = 2;");
        while (resultSet.next()){
            String id = resultSet.getString("id_friend2");
            String name = resultSet.getString("name_client");
            String birthday = resultSet.getString("birth_client");
            String idWishList = resultSet.getString("id_wishList");
            String dateLeft = getDateLeft(birthday);
            Client client = new Client(id,birthday,name,dateLeft,idWishList);
            System.out.println("id: "+id+" | name: "+ name+" | birthday: "+birthday + " | dateLeft: "
                    +dateLeft+" | idWishList: "+ idWishList);

            mas.add(client);
        }
        return mas;
    }
    public static String getDateLeft(String dateBirth){
        char[] mas = dateBirth.toCharArray();
        String day =""+ mas[0]+mas[1];
        int day_ = Integer.parseInt(day);
        String month = "" + mas[3]+mas[4];
        int month_ = Integer.parseInt(month);
        Month a = null;
        switch (month_) {
            case 1:
                a = Month.JANUARY;
                break;
            case 2:
                a = Month.FEBRUARY;
                break;
            case 3:
                a = Month.MARCH;
                break;
            case 4:
                a = Month.APRIL;
                break;
            case 5:
                a = Month.MAY;
                break;
            case 6:
                a = Month.JUNE;
                break;
            case 7:
                a = Month.JULY;
                break;
            case 8:
                a = Month.AUGUST;
                break;
            case 9:
                a = Month.SEPTEMBER;
                break;
            case 10:
                a = Month.OCTOBER;
                break;
            case 11:
                a = Month.NOVEMBER;
                break;
            case 12:
                a = Month.DECEMBER;
                break;
        }
        LocalDate birthday = LocalDate.of(2024, a, day_);
        System.out.println(birthday);
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        if (currentDate.isAfter(birthday)) {
            birthday = birthday.plusYears(1);
        }
        System.out.println(birthday);
        Period period = Period.between(currentDate, birthday);
        System.out.println(period);
        long hoursExact = ChronoUnit.HOURS.between(currentDate.atStartOfDay(), birthday.atStartOfDay());
        long days = hoursExact/24;
        System.out.println("Дней до дня рождения: " + days);
        return String.valueOf(days);
    }

    public static void deleteFriend(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Friend\"\n" +
                "\tWHERE (id_friend1 ='"+MainView.id+"' and id_friend2 = '" + id +"') \n" +
                "\tor (id_friend1 = '"+id+"' and id_friend2 = '"+MainView.id+"')");
    }

    public static ArrayList<WishList> getMyWishLists() throws SQLException {
        ArrayList<WishList> wishLists = new ArrayList<>();
        resultSet = statmt.executeQuery("SELECT * FROM public.\"WishList\"\n" +
                "WHERE id_client = '"+MainView.id+"' ORDER BY \"id_wishList\" ASC ");
        while (resultSet.next()){
            String id = resultSet.getString("id_wishList");
            String name = resultSet.getString("name_wishList");
            String status = resultSet.getString("status_wishList");
            WishList wishList = new WishList(id,name,status);
            System.out.println("id: "+id+" | name: "+ name+" | status: "+status);
            wishLists.add(wishList);
        }
        return wishLists;
    }

    public static void addWishList(ArrayList<Wish> wishes ,String name) throws SQLException {
        statmt.execute("INSERT INTO public.\"WishList\"(\n" +
                "\tid_client, \"name_wishList\")\n" +
                "\tVALUES ('"+MainView.id+"', '"+ name+"');");
        String id = getIdWishList(name,MainView.id);
        if(!id.equals("error")){
            for(int i=0;i<wishes.size();i++){
                System.out.println(wishes.get(i).name);
                addWish(wishes.get(i).name,id);
            }
        }
    }
    public static String getIdWishList(String name, String id) throws SQLException {
        String ans = "error";
        resultSet = statmt.executeQuery("SELECT * \n" +
                "\tFROM public.\"WishList\"\n" +
                "\twhere id_client ='"+id+"' and \"name_wishList\" = '"+name+"';");
        while (resultSet.next()){
            ans = resultSet.getString("id_wishList");
        }
        return ans;
    }

    public static void addWish(String name, String id_wishList) throws SQLException {
        statmt.execute("INSERT INTO public.\"Wish\"(\n" +
                "\tname_wish,\"id_wishList\")\n" +
                "\tVALUES ('"+name+"', '"+id_wishList+"');");
    }

    public static void deleteWishList(String id) throws SQLException {
        statmt.execute("DELETE FROM public.\"Wish\"\n" +
                "\tWHERE \"id_wishList\" = '"+id+"';");
        statmt.execute("DELETE FROM public.\"WishList\"\n" +
                "\tWHERE \"id_wishList\" = '"+id+"';");
    }

    public static ArrayList<Wish> getWishesById(String id) throws SQLException {
        ArrayList<Wish> wishes = new ArrayList<>();
        resultSet = statmt.executeQuery("SELECT *\n" +
                "FROM public.\"Wish\"\n" +
                "WHERE \"id_wishList\" ='"+id+"';");
        while (resultSet.next()){
            String idWish = resultSet.getString("id_wish");
            String name = resultSet.getString("name_wish");
            String status = resultSet.getString("status_wish");
            Wish wish = new Wish(name,idWish,status);
            wishes.add(wish);
        }
        return wishes;
    }

    public static void deleteWishById(String idWishList) throws SQLException {
        statmt.execute("DELETE FROM public.\"Wish\"\n" +
                "\tWHERE \"id_wishList\" = '"+idWishList+"';");
    }

    public static void updateWishListName(String idWishList, String nameWishList) throws SQLException {
        statmt.execute("UPDATE public.\"WishList\"\n" +
                "\tSET \"name_wishList\"='"+nameWishList+"' \n" +
                "\tWHERE \"id_wishList\"= '"+idWishList+"';");
    }

    public static ArrayList<Client> getEntries() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        resultSet = statmt.executeQuery("SELECT *\n" +
                "FROM public.\"Friend\" \n" +
                "RIGHT JOIN public.\"Client\" on  public.\"Client\".id_client = public.\"Friend\".id_friend2" +
                " WHERE id_friend1 ='"+MainView.id+"' and status_friend = 1;");
        while (resultSet.next()){
            String id = resultSet.getString("id_friend2");
            String name = resultSet.getString("name_client");
            Client client = new Client(id,name);
            clients.add(client);
        }
        return clients;
    }
    public static void updateFriendStatus(String id) throws SQLException {
        statmt.execute("UPDATE public.\"Friend\"\n" +
                "\tSET status_friend=2\n" +
                "\tWHERE id_friend1 ='"+id+"'  and id_friend2 = '"+MainView.id+"';");
        statmt.execute("UPDATE public.\"Friend\"\n" +
                "\tSET status_friend=2\n" +
                "\tWHERE id_friend2 ='"+id+"'  and id_friend1 = '"+MainView.id+"';");
    }

    public static void addFriend(String friendId) throws  SQLException {
        statmt.execute("INSERT INTO public.\"Friend\"(\n" +
                "\tid_friend1, id_friend2)\n" +
                "\tVALUES ('"+MainView.id+"', '"+ friendId+"');");
        statmt.execute("INSERT INTO public.\"Friend\"(\n" +
                "\tid_friend2, id_friend1, status_friend)\n" +
                "\tVALUES ('"+MainView.id+"', '"+ friendId+"', 1);");
    }

    public static Set<String> getMyEntriesToFriend() throws SQLException {
        Set<String> setId = new HashSet<String>(); ;
        resultSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"Friend\"\n" +
                "\tWHERE id_friend1 ='" +MainView.id+"' and status_friend=0;");
        while (resultSet.next()){
            String id = resultSet.getString("id_friend2");
            setId.add(id);
        }
        return setId;
    }

    public static void updateWishListStatus(String idWishList, String newStatus) throws SQLException{
        statmt.execute("UPDATE public.\"WishList\"\n" +
                "\tSET \"status_wishList\"= '0'\n" +
                "\tWHERE \"id_client\"= '"+MainView.id+"'");
        statmt.execute("UPDATE public.\"WishList\"\n" +
                "\tSET \"status_wishList\"= '"+newStatus+"'\n" +
                "\tWHERE \"id_wishList\"= '"+idWishList+"'");
    }

    public static ArrayList<Wish> getFriendWishes(String id) throws SQLException {
        ArrayList<Wish> wishes = new ArrayList<>();
        resultSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"WishList\"\n" +
                "\tLEFT JOIN public.\"Wish\" using (\"id_wishList\")\n" +
                "\tWHERE public.\"WishList\".id_client ='"+id+"' and \"status_wishList\" = 1");
        while (resultSet.next()){
            String idWish = resultSet.getString("id_wish");
            String nameWish = resultSet.getString("name_wish");
            String statusWish = resultSet.getString("status_wish");
            String client = resultSet.getString("client");
            Wish wish = new Wish(nameWish,idWish,statusWish,client);
            wishes.add(wish);
        }
        return wishes;
    }

    public static WishList getActualFriendWishList(String idClient) throws SQLException {
        WishList wishList = null;
        System.out.println("id_WishList"+idClient);
        resultSet = statmt.executeQuery("SELECT *\n" +
                "\tFROM public.\"WishList\"\n" +
                "\tJOIN public.\"Client\" using (id_client)\n" +
                "\tWHERE \"id_client\" ='"+idClient+"' and \"status_wishList\" = 1");
        while (resultSet.next()){
            String owner = resultSet.getString("name_client");
            String nameWishList = resultSet.getString("name_wishList");
            wishList = new WishList(nameWishList,owner);
        }
        return wishList;
    }

    public static void updateFriendWish(String idWish, boolean fl) throws SQLException{
        if(fl){
            statmt.execute("UPDATE public.\"Wish\"\n" +
                    "\tSET status_wish= 1, client='"+MainView.id+"'\n" +
                    "\tWHERE id_wish = '"+idWish+"';");
        }else{
            statmt.execute("UPDATE public.\"Wish\"\n" +
                    "\tSET status_wish= 0, client= 0\n" +
                    "\tWHERE id_wish = '"+idWish+"';");
        }
    }
}
