/*
 Problem Breakdown
 A bowling game has 10 frames, bonus 11th and 12th rolls are for if a strike or spare is thrown in frame 10 and a bonus needs to be calculated
 Each frame can have two rolls
 A frame is over once a strike, spare, or two rolls are done.
 A spare has one bonus roll
 A strike has two bonus rolls

Possible solutions - 
1: 2d array to hold scores. 
2: create frame class to hold scores
3: convert from String the total score.

Note: I started with idea 2, creating a class to store the data of each frame of the bowling game
but when I reached the point of calculating the score, I realized it wasn't that useful. It would be easier to 
calculate the score straight from the input string. So I did that instead, I didn't want to delete it, so I left it at the bottom.

Tasks which are out of scope
No need to check for valid rolls.
No need to check for correct number of rolls and frames.
No need to provide scores for intermediate frames.
 */
package bowling_score;

import java.util.Scanner;

/**
 *
 * @author Elias
 */
public class BowlingCalculator {

    static int calcScoresFromString(String scores) {
        int bonus = 0; //used for calculating the bonus of strikes and spares
        int score = 0; //track the score for the game
        int frameCount = 0; //tracks current frame
        int rollCount = 0; //track rolls, used for adding a frame every 2 throws that don't result in a strike
        //traverse the string and update score
        for (int index = 0; index < scores.length(); index++) {
            //frame 10 is when the game will end
            if (frameCount == 10) {
                return score;
            }
            char roll = scores.charAt(index);

            switch (roll) {
                //if a strike
                case 'X':
                    frameCount++;
                    //calculate bonus score for strick(the next two rolls), calcRoll returns a int
                    bonus = calcRoll(scores.charAt(index + 1), roll) + calcRoll(scores.charAt(index + 2), scores.charAt(index + 1));
                    //a strike gives you 10 points plus the bonus.
                    score += 10 + bonus;
                    break;
                //if a spare
                case '/':
                    rollCount++;
                    frameCount++;
                    //bonus for a spare is the next roll.
                    bonus = calcRoll(scores.charAt(index + 1), roll);
                    //a spare score is the bonus + the remaining pins (calcRoll(roll, scores.charAt(index - 1)) will calculate the remaining pins).
                    score += bonus + calcRoll(roll, scores.charAt(index - 1));
                    break;
                //if anything else (1 to 9 pins)
                default:
                    rollCount++;
                    if (roll != '-') {
                        score += Character.getNumericValue(roll);
                    }
                    if (rollCount % 2 == 0) {
                        frameCount++;
                    }
                    break;
            }
        }
        return score;
    }

    //This function will calculate the bonus for strikes and spares. It will also calculate the remaining pins for spares.
    static int calcRoll(char roll, char previousRoll) {
        switch (roll) {
            case 'X':
                return 10;
            case '/':
                return 10 - Character.getNumericValue(previousRoll);
            case '-':
                return 0;
            default:
                return Character.getNumericValue(roll);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //input bowling score
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter bolwing input: ");
        String input = sc.next();
        //output calculated score for the game
        System.out.println(calcScoresFromString(input));
    }

    /**
     * //Creating a class called frame to track the score of each frame class
     * Frame {
     *
     * //a frame in bowling consists of upto two rolls private char rollOne;
     * private char rollTwo; //constructor public Frame() { this.rollOne = ' ';
     * this.rollTwo = ' '; }
     *
     * //for adding rollOne scores public void rollOneScore(char score) {
     * this.rollOne = score; }
     *
     * public void rollTwoScore(char score) { this.rollTwo = score; }
     *
     * public char getRollOne() { return this.rollOne; }
     *
     * public char getRollTwo() { return this.rollTwo; }
     *
     * public void scores() { System.out.print(" Roll 1: " + this.rollOne);
     * System.out.println(" Roll 2: " + this.rollTwo); } }
     *
     * static void addScores(String scores, Frame F[]) { int frameCount = 0;
     * //tracks current frame int rollCount = 0; //track rolls, used for adding
     * a frame every 2 throws that don't result in a strike //traverse the
     * string and update frame values for (int index = 0; index <
     * scores.length(); index++) { char roll = scores.charAt(index);
     * //System.out.print(roll); //enter scores into frames switch (roll) { //if
     * a strike case 'X': F[frameCount].rollOneScore(roll); frameCount++; break;
     * //if a spare case '/': F[frameCount].rollTwoScore(roll); rollCount++;
     * frameCount++; break; //if anything else (0 to 9 pins) default:
     * rollCount++; if (rollCount % 2 == 1) { F[frameCount].rollOneScore(roll);
     * } else if (rollCount % 2 == 0) { F[frameCount].rollTwoScore(roll);
     * frameCount++; } break; } } }
     *
     */
}
