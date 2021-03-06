/* hk 12/4/20 

https://onlinejudge.org/external/17/1750.pdf
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solver {

    static final int NUM_CARD_TYPES = 3; 
    static final int NUM_PLAYERS = 4;
    static final char[] CARD_RANGE_START = {'A', 'G', 'M', 'V'};

    CardAllocation game, working;


    Solver() {
        game = new CardAllocation();
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            Solver clue = new Solver();
            clue.parse(in);
            System.out.println(clue.solve());
        }
    }


/* Solving logic ****************************************************************/

    String solve() {
        char[] solution = {'#', '#', '#'};
        solve(0, solution, new char[]{0,0,0});

        return new String(solution);
    }


    private void solve(int cardType, char[] solution, char[] curr) {
        if (cardType >= NUM_CARD_TYPES) {
            working = game.copy();
            if (dealConstrainedCards(0))
                updateSolution(solution, curr);

            return;
        }

        for (curr[cardType] = CARD_RANGE_START[cardType]; curr[cardType] < CARD_RANGE_START[cardType+1]; curr[cardType]++) {
            if (game.tryAllocate(curr[cardType], 0)) {
                solve(cardType +1, solution, curr);
                game.deallocateCard(curr[cardType], 0);
            }

        }
    }


    // try dealing the cards we know each player has at least one of
    // i is the ith group of cards we know a player has at least one of
    private boolean dealConstrainedCards(int i) {
        if (i >= working.getPlayerHasOneOfs().size())
            return dealRemainingCards('A');

        PlayerUnion option = working.getPlayerHasOneOfs().get(i);
        for (char card: option.options ) {
            int cardOwner = working.getOwner(card);

            if (!working.playerHandFull(option.player) 
                && cardOwner == CardAllocation.UNALLOCATED 
                && !working.isDefiniteNotOwner(card, option.player)) {

                working.allocateCard(card, option.player);

                if (dealConstrainedCards(i+1))
                    return true;

                working.deallocateCard(card, option.player);

            } else if (cardOwner == option.player && dealConstrainedCards(i+1)) {
                return true;
            }
        }

        return false;
    }

    
    // deal the remaining cards
    boolean dealRemainingCards(char c) {
        if (c >= CARD_RANGE_START[NUM_CARD_TYPES])
            return true;

        if (working.isAllocated(c))
            return dealRemainingCards((char) (c+1));

        // player 1 is us, we know what our hand contains already
        for (int i = NUM_PLAYERS; i >= 2; i--) {
            if (!working.playerHandFull(i) && !working.isDefiniteNotOwner(c, i)) {
                working.allocateCard(c, i);

                if (dealRemainingCards((char) (c+1)))
                    return true;

                working.deallocateCard(c, i);
            }
        }

        return false;
    }


    void updateSolution(char[] solution, char[] curr) {
        for (int i=0; i < NUM_CARD_TYPES; i++) {
            if (solution[i] == '#')
                solution[i] = curr[i];

            else if (solution[i] != curr[i])
                solution[i] = '?';
        }
    }


/* Program IO *****************************************************************/
    void readPlayerHand(String line) {
        Scanner lineScanner = new Scanner(line);
        while (lineScanner.hasNext())
            game.allocateCard(lineScanner.next().charAt(0), 1);

    }


    void readSuggestion(String line, int player) {
        Scanner lineScanner = new Scanner(line);

        char[] suggestion = {
            lineScanner.next().charAt(0), 
            lineScanner.next().charAt(0), 
            lineScanner.next().charAt(0)};

        for (int responder = (player % NUM_PLAYERS) +1; 
                lineScanner.hasNext(); 
                responder = (responder  % NUM_PLAYERS )+ 1) {

            char response = lineScanner.next().charAt(0);

            switch(response) {
                case '-':
                    for (char card : suggestion)
                        game.definiteNotOwner(card, responder);

                    break;

                case '*':
                    game.playerHasOneOf(new PlayerUnion(responder, suggestion.clone()));
                    break;


                default:
                    if (!game.isAllocated(response))
                        game.allocateCard(response, responder);
            }
        }
    }


    void parse(Scanner in) {
        int numSuggestions = in.nextInt();
        in.nextLine();
        readPlayerHand(in.nextLine());

        for (int i = 0; i < numSuggestions; i++)
            readSuggestion(in.nextLine(), (i % NUM_PLAYERS) + 1);

    }

}

