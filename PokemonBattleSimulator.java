/**
Name: Luke Pan
Date: June 8, 2022
Description: Summative - Pokemon Battle Simulator
This program tries to emulate a pokemon battle factory game with only text
*/
import java.io.*;
import java.util.*;
public class PokemonBattleSimulator {
    public static void main(String[] args) {

        //declaring and initializing arrays for pokemon stats
        String fileName = "pokemon.csv";
        String[] name = new String[81];
        String[] type1 = new String[81];
        String[] type2 = new String[81];
        int[] hp = new int[81];
        int[] atk = new int[81];
        int[] def = new int[81];
        int[] spAtk = new int[81];
        int[] spDef = new int[81];
        int[] speed = new int[81];

        //declaring and initializing arrays for momve stats
        String movesFileName = "moves.csv";
        String[] moveName = new String[56];
        String[] moveEffect = new String[56];
        String[] moveType = new String[56];
        String[] moveKind = new String[56];
        int[] movePower = new int[56];
        double[] moveAccuracy = new double[56];
        int[] movePP = new int[56];

        int counter = 0; //basic counter variabe
        int i; //used for randomizer
        int input = 1; //used for user input, should only accept numbers
        int oppInput = 1; //used to help with opponent selection
        
        //boolean variables - used for the while loops
        boolean badInput = true;
        boolean pokemonAlive = true;
        boolean validInput = false;

        //try block with loops to read pokemon.csv and moves.csv and store all stats into arrays
        try {
            //declaring scanner
            Scanner sc = new Scanner(new File(fileName));

            sc.nextLine(); //get rid of first line
            while (counter < 81) {
                String line = sc.nextLine();
                String[] values = line.split(",");
                name[counter] = values[1];
                type1[counter] = values[2];
                type2[counter] = values[3];
                hp[counter] = Integer.parseInt(values[5]);
                atk[counter] = Integer.parseInt(values[6]);
                def[counter] = Integer.parseInt(values[7]);
                spAtk[counter] = Integer.parseInt(values[8]);
                spDef[counter] = Integer.parseInt(values[9]);
                speed[counter] = Integer.parseInt(values[10]);
                counter++;
            }
            sc.close();

            counter = 0;
            sc = new Scanner(new File(movesFileName)); //new scanner for moves.csv

            sc.nextLine(); //gets rid of first line
            while (counter < 56) {
                String line = sc.nextLine();
                String[] values = line.split(",");
                moveName[counter] = values[1];
                moveEffect[counter] = values[2];
                moveType[counter] = values[3];
                moveKind[counter] = values[4];
                movePower[counter] = Integer.parseInt(values[5]);
                moveAccuracy[counter] = Double.parseDouble(values[6]);
                movePP[counter] = Integer.parseInt(values[7]);
                counter++;
            }
            sc.close();
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println(e + "  Problem reading " + fileName);
        }

        //generates 6 random pokemon for the user's team each with 4 random moves
        Pokemon[] userTeam = new Pokemon[6];
        Moves[] userMoves = new Moves[4];
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 4; k++) {
                i = (int)(Math.random() * 56);
                userMoves[k] = new Moves(moveName[i], moveEffect[i], moveType[i], moveKind[i], movePower[i], movePP[i], moveAccuracy[i]);
            }
            i = (int)(Math.random() * 81);
            userTeam[j] = new Pokemon(name[i], hp[i], atk[i], def[i], spAtk[i], spDef[i], speed[i], userMoves, false);
        }

        //generates 6 random pokemon each with 4 random moves for opponent's team
        Pokemon[] oppTeam = new Pokemon[6];
        Moves[] opponentMoves = new Moves[4];
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 4; k++) {
                i = (int)(Math.random() * 56);
                opponentMoves[k] = new Moves(moveName[i], moveEffect[i], moveType[i], moveKind[i], movePower[i], movePP[i], moveAccuracy[i]);
            }
            i = (int)(Math.random() * 81);
            oppTeam[j] = new Pokemon(name[i], hp[i], atk[i], def[i], spAtk[i], spDef[i], speed[i], opponentMoves, false);
        }

        //prints the welcome screen
        Scanner in = new Scanner(System.in); //creates scanner to prompt for name
//         System.out.println("This is a program that is meant to emulate the Pokemon Factory battle mode in Pokemon Showdown");
//         System.out.println("The goal is to tactically use your team of random pokemon to defeat the opponent's also random team");
//         System.out.println("You will be required to select choices using the numbers on your keyboard");
        System.out.println("To begin, please enter your username");
        System.out.print("Username: "); //prompts for username to initiate program
        String username = in.nextLine();
        System.out.println("---------------------------------------------------------------------\n");
        System.out.println("                Welcome to the Pokemon Battle Factory!\n");
        System.out.println("---------------------------------------------------------------------\n");
        System.out.println("\n" + username + "'s Team:");
        System.out.println(userTeam[0].name + " / " + userTeam[1].name + " / " + userTeam[2].name + " / " + userTeam[3].name + " / " + userTeam[4].name + " / " + userTeam[5].name);
        System.out.println("\nTrainer Blue's Team:");
        System.out.println(oppTeam[0].name + " / " + oppTeam[1].name + " / " + oppTeam[2].name + " / " + oppTeam[3].name + " / " + oppTeam[4].name + " / " + oppTeam[5].name);


        //prints both teams and prompts user if they want info about a certain pokemon
        //not included here


        System.out.println("\n=====================================================================\n");
        System.out.println("Battle started between " + username + " and Trainer Blue!\n");
        System.out.println("=====================================================================\n");

        int oppPokeSel = 0; //the pokemon that the user has selected
        int userPokeSel = 0; //the pokemon the opponent has selected (goes from 0-5 in a set order, to make it easier for user once typing is added)
        int turnNumber = 1;

        System.out.println("Blue sent out " + oppTeam[oppPokeSel].name + "!\n");

        //do while + try catch for user input
        do {
            try {
                //battle loop
                while (pokemonAlive) { //while all of either user or opponent's entire teams are alive

                    //checks if user or opp team is fainted
                    if (userTeam[0].fainted == true && userTeam[1].fainted == true && userTeam[2].fainted == true && userTeam[3].fainted == true && userTeam[4].fainted == true && userTeam[5].fainted == true) {
                        pokemonAlive = false;
                        System.out.println("All your pokemon have fainted!");
                        System.out.println("You blacked out");
                        System.out.println("GAME OVER");
                        System.exit(0);
                    } else if (oppTeam[0].fainted == true && oppTeam[1].fainted == true && oppTeam[2].fainted == true && oppTeam[3].fainted == true && oppTeam[4].fainted == true && oppTeam[5].fainted == true) {
                        pokemonAlive = false;
                        System.out.println("All of Trainer Blue's pokemon have fainted!");
                        System.out.println(username + " is Victorious!");
                        System.out.println("Program Ended.");
                        System.exit(0);
                    }

                    //checks if opponent pokemon is alive or not
                    if (oppTeam[oppPokeSel].hp <= 0) {
                        oppPokeSel++;
                        System.out.println("Blue sent out " + oppTeam[oppPokeSel].name + "!");
                    //moves on to user pokemon switch selection
                    } else { 
                        //loop until user selects a valid pokemon to send out
                        do {
                            do {
                                System.out.println("Choose a Pokemon:");
                                System.out.println("1." + userTeam[0].name + " / 2." + userTeam[1].name + " / 3." + userTeam[2].name + " / 4." + userTeam[3].name + " / 5." + userTeam[4].name + " / 6." + userTeam[5].name);
                                input = in .nextInt();

                                if ((input < 1) || (input > 6)) {
                                    System.out.println("Invalid choice!");
                                } else {
                                    validInput = true;
                                }
                            } while (validInput == false);

                            //checks if chosen pokemon is already fainted - wont let user switch into a fainted pokemon
                            validInput = false;
                            if (userTeam[input - 1].fainted == true) {
                                System.out.println(userTeam[input - 1].name + " is unable to battle!\nPlease choose a different Pokemon\n2");
                                validInput = false;
                            } else {
                                validInput = true;
                                //assigns user's active pokemon based on input
                                switch (input) {
                                    case 1:
                                        userPokeSel = 0;
                                        break;
                                    case 2:
                                        userPokeSel = 1;
                                        break;
                                    case 3:
                                        userPokeSel = 2;
                                        break;
                                    case 4:
                                        userPokeSel = 3;
                                        break;
                                    case 5:
                                        userPokeSel = 4;
                                        break;
                                    case 6:
                                        userPokeSel = 5;
                                        break;
                                    default:
                                        System.out.println("Invalid input!");
                                        break;
                                }
                                System.out.println("Go! " + userTeam[userPokeSel].name + "!\n");
                            }
                        } while (validInput == false);
                    }

                    //method for battle (damage calculations)
                    turnNumber = battle(userTeam[userPokeSel], oppTeam[oppPokeSel], turnNumber);

                    //prints turn number
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("Turn " + turnNumber);
                    System.out.println("----------------------------------------------------------------------");

                    //adds to turn counter
                    turnNumber++;
                }
            } catch (InputMismatchException e) {
                System.out.println(e + ": \nInvalid input, please try again!\n"); 
                in .nextLine();
            } catch (ArrayIndexOutOfBoundsException e ) {
                System.out.println(e + ": \nInvalid input, please try again!\n"); 
                in .nextLine();
            }
        } while (badInput = true);
    }

    /**
       int battle (Pokemon user, Pokemon opp)
       This method is responsible for the primary battling functions of this pokemon battle simulator.
       It will handle the calculations for user and opponent attacks; editing the hitpoints based on the amount of damage done.
       This will differentiate between special and physical, have cirt and miss chances, and print fainted messages.
       @param user Pokemon - this parameter is the user's pokemon of the Pokemon datatype, it carries all the information of the user's pokemon and is used to get it's stats
       @param opp Pokemon - likewise, this parameter is the opponent's pokemon of the Pokemon datatype, it carries all the information of the opponent's pokemon and is used to get it's stats
       @param turnNumer int - turn number of battle, for the turn headers
       @return int - this returns the turn number so that the headers can continue to be printed even after switching and exiting the method
    */

    public static int battle(Pokemon user, Pokemon opp, int turnNumber) {
        
        //creating a new scanner
        Scanner in = new Scanner(System.in);
        
        //variable declaration 
        int input;
        int oppInput;
        int userMoveSel = 0;
        int oppMoveSel = 0;
        int damage = 0;
        double i;
        boolean valid = true; //valid means pokemon are alive and able to battle
        
        //while loop to repeat untill a pokemon faints or switches out
        while (valid) {

            //prints turn header
            System.out.println("----------------------------------------------------------------------");
            System.out.println("Turn " + turnNumber);
            System.out.println("----------------------------------------------------------------------");

            try {
               //user selects move
               System.out.println("What should " + user.name + " do?");
               System.out.println("1." + user.moves[0].moveName + " / 2." + user.moves[1].moveName + " / 3." + user.moves[2].moveName + " / 4." + user.moves[3].moveName + "\n5.Switch Out");
               input = Integer.parseInt(in.nextLine());
               
               switch (input) {
                   case 1:
                       userMoveSel = 0;
                       break;
                   case 2:
                       userMoveSel = 1;
                       break;
                   case 3:
                       userMoveSel = 2;
                       break;
                   case 4:
                       userMoveSel = 3;
                       break;
                   case 5: //switches out, exits loop and method
                       System.out.println(user.name + ", come back!");
                       turnNumber++;
                       return turnNumber;
                   default:
                       System.out.println("Invalid choice! Your Pokemon acts on its own!");
                       break;
               }
            } catch (NumberFormatException e) {
               System.out.println(e + ": \nInvalid choice! Your Pokemon acts on its own!");
            }
            

            //user attacks
            System.out.println(user.name + " used " + user.moves[userMoveSel].moveName + "!");
            //physical attack
            if ((user.moves[userMoveSel].moveKind).equals("Physical")) {
                damage = (4 * user.moves[userMoveSel].movePower * user.atk / opp.def) / 50 + 2;
            //special attack
            } else if ((user.moves[userMoveSel].moveKind).equals("Special")) {
                damage = (4 * user.moves[userMoveSel].movePower * user.spAtk / opp.spDef) / 50 + 2;
            }

            //checks if move hits or not
            i = Math.random();
            if (user.moves[userMoveSel].moveAccuracy <= i) {
                damage = 0;
                System.out.println("(It missed!)");
            }

            //checks if move crits or not
            i = Math.random();
            if (i <= 0.0625) {
                damage = damage * 2;
                if (damage > 0) {
                    System.out.println("(A critical hit!)");
                }
            }
           
            //prints opp damage recieved and edits opponents hp
            System.out.println("(The opposing " + opp.name + " took " + damage + " damage!)\n");
            System.out.print("Opposing " + opp.name + "'s hp: " + opp.hp + " -> ");
            opp.hp = opp.hp - damage;
            
            //ends method and marks opp pokemon as fainted if hp reaches 0
            if (opp.hp <= 0) {
                System.out.println("0");
                System.out.println("The opposing " + opp.name + " fainted!");
                turnNumber++;
                opp.name = opp.name + " (fainted)";
                opp.fainted = true;
                return turnNumber;
            } else {
                System.out.println(opp.hp);
            }
            
            //random number for opponent move choice
            oppInput = (int)(Math.random() * 4);

            //assigns opponent a random move to use for a turn
            switch (oppInput) {
                case 1:
                    oppMoveSel = 0;
                    break;
                case 2:
                    oppMoveSel = 1;
                    break;
                case 3:
                    oppMoveSel = 2;
                    break;
                case 4:
                    oppMoveSel = 3;
                    break;
            }

            //opponent attacks  
            System.out.println("\nThe opposing " + opp.name + " used " + opp.moves[oppMoveSel].moveName + "!");
            if ((opp.moves[userMoveSel].moveKind).equals("Physical")) {
                damage = (4 * opp.moves[oppMoveSel].movePower * opp.atk / user.def) / 50 + 2;
            } else if ((opp.moves[userMoveSel].moveKind).equals("Special")) {
                damage = (4 * opp.moves[oppMoveSel].movePower * opp.spAtk / user.spDef) / 50 + 2;
            }
            System.out.println("(" + user.name + " took " + damage + " damage!)\n");
            System.out.print(user.name + "'s hp: " + user.hp + " -> ");
            user.hp = user.hp - damage;
            
            //ends method and marks user pokemon as fainted if hp reaches 0
            if (user.hp <= 0) {
                System.out.println("0");
                System.out.println(user.name + " fainted!");
                turnNumber++;
                user.name = user.name + " (fainted)";
                user.fainted = true;
                return turnNumber;
            } else {
                System.out.println(user.hp);
            }
            
            turnNumber++;
        }
        return turnNumber;
    }
}