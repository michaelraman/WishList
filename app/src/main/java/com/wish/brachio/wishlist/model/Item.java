package com.wish.brachio.wishlist.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Item {

    String itemname = "Plates";
    ArrayList<User> contributers;

    Calendar cal = Calendar.getInstance();
    Date date = cal.getTime();

    String id="this is an id";

    int contributors = 3;

    int quantityneeded = 4;
    int slotsfilled = 3;
    int availableslots = 1;

    String contributer1 = "Jessica Bule";
    int amountcontributedby1 = 1;

    String contributer2 = "Ty Johnson";
    int amountcontributedby2 = 1;

    String contributer3 = "Mattie B";
    int amountcontributedby3 = 1;


}
