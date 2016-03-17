package com.ajeetkg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by agupta2 on 3/16/16.
 */
public class BlackJack {

    // Get a random number generator between 1-11
    // [2,3,4,5,6,7,8,9,10,10,10,10,A]

    int[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

    List<Integer> player = new ArrayList<>();
    List<Integer> dealer = new ArrayList<>();
    // deal -> Start a new game and give player two card and banker two cards ( one hidden)

    //hit -> get one card for the player and decide


    //stand -> Give chance to the dealer to take as many hit or stand


    //result - calculate sum and find the one who is closest to 21 or over - win or busted

    public static void main1(String[] args) {

        BlackJack blackJack = new BlackJack();
        String action = blackJack.takeAction();
        if(action.equals("Y")){
            blackJack.getRandomNumber();
            blackJack.deal();
            System.out.println("Players sum:" + blackJack.getTotal(blackJack.player, "player"));
            System.out.println("Dealers sum:" + blackJack.dealer.get(0));
            Scanner in = new Scanner(System.in);

            System.out.println("Enter action: Hit or Stand");
            String action1 = in.nextLine();
            System.out.println("You entered string " + action1);
            //decides to take hit  or stand
            if (action1.equalsIgnoreCase("hit")) {
                blackJack.addNumberToList(blackJack.player, blackJack.getRandomNumber());
                System.out.println("Players sum:" + blackJack.getTotal(blackJack.player, "player"));
            }
        }

    }

    private String takeAction(){
        Scanner in = new Scanner(System.in);

        System.out.println("Deal (Y/N)");
        String action = in.nextLine();
        System.out.println("You entered string " + action);
        return action;
    }
    private Integer getRandomNumber() {
        Random random = new Random();
        Integer randomNumber = cards[random.nextInt(cards.length)];
        return randomNumber;
    }

    private void deal() {
        addNumberToList(player, getRandomNumber());
        addNumberToList(dealer, getRandomNumber());
        addNumberToList(player, getRandomNumber());
        addNumberToList(dealer, getRandomNumber());
    }

    private void addNumberToList(List<Integer> list, Integer randomNumber) {
        list.add(randomNumber);
    }

    private void takeHit(List<Integer> list, String name) {
        Integer random = getRandomNumber();
        System.out.println(name + ":" + random);
        list.add(random);
    }

    private Integer getTotal(List<Integer> list, String name) {
        Integer sum = 0;
        for (Integer num : list) {
            if (num == 1) {
                addOneOrEleven(sum);
            }
            System.out.println(name + ":" + num);
            sum = sum + num;
        }
        return sum;
    }

    private void addOneOrEleven(Integer sum) {

        //add 1 only for the time being
        if (sum <= 10) {
            sum = sum + 11;
        }
    }

    private Boolean isBusted(List<Integer> list, String name) {
        BlackJack blackJack = new BlackJack();
        Integer sum = blackJack.getTotal(list, name);
        if (sum > 21) return true;
        else return false;


    }

    private String getResult(List<Integer> player, List<Integer> dealer) {
        if (isBusted(player, "Player")) return "Player looses";
        if (isBusted(dealer, "Player")) return "Dealer looses";

        return null;
    }

}
