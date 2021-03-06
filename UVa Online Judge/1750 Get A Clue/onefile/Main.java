import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final char[] CARD_RANGE_START = {'A', 'G', 'M', 'V'};

    class PlayerUnion {
        int player;
        char[] options;

        PlayerUnion(int player, char[] options) {
            this.player = player;
            this.options = options;
        }
    }


    public class CardAllocation {
        static final int UNALLOCATED = -1;
        final int[] PLAYER_HAND_SIZE = {3,5,5,4,4};
        static final int NUM_PLAYERS = 4;
        static final int TOTAL_CARDS = 21;


        int[] cardOwners;
        int[] cardNotOwner;
        int[] cardsAllocatedToPlayer;

        List<PlayerUnion> atLeastOneOfs;

        CardAllocation() {
            cardOwners = new int[TOTAL_CARDS];
            for (int i=0; i < TOTAL_CARDS; i++)
                cardOwners[i] = UNALLOCATED;

            cardNotOwner = new int[TOTAL_CARDS];
            cardsAllocatedToPlayer = new int[NUM_PLAYERS +1];
            atLeastOneOfs = new ArrayList<>();
        }


        void allocateCard(char card, int player) {
            cardOwners[card - 'A'] = player;
            cardsAllocatedToPlayer[player]++;
        }


        void definiteNotOwner(char c, int player) {
            cardNotOwner[c - 'A'] |= (1 << player);
        }

        boolean isDefiniteNotOwner(char c, int player) {
            return (cardNotOwner[c - 'A'] & (1 << player)) != 0;
        }


        void deallocateCard(char card, int player) {
            cardOwners[card - 'A'] = UNALLOCATED;
            cardsAllocatedToPlayer[player]--;
        }

        boolean tryAllocate(char card, int player) {
            if (isAllocated(card))
                return false;

            allocateCard(card, player);
            return true;
        }


        boolean isAllocated(char card) {
            return cardOwners[card - 'A'] != UNALLOCATED;
        }

        int getOwner(char card) {return cardOwners[card - 'A']; }

        void playerHasOneOf(PlayerUnion union) {
            atLeastOneOfs.add(union);
        }

        List<PlayerUnion> getPlayerHasOneOfs() {
            return atLeastOneOfs;
        }

        boolean playerHandFull(int player) {
            return cardsAllocatedToPlayer[player] >= PLAYER_HAND_SIZE[player];
        }


        void dump() {
            System.out.printf("Card owners: %s\n", Arrays.toString(cardOwners));
            System.out.printf("Card non-owners mask: %s\n", Arrays.toString(cardNotOwner));

            for (PlayerUnion option : atLeastOneOfs) {
                System.out.printf("P%d has one of: %s \n", option.player, Arrays.toString(option.options));

            }
            System.out.println();
        }


        CardAllocation copy() {
            CardAllocation ret = new CardAllocation();
            ret.cardOwners = cardOwners.clone();
            ret.cardNotOwner = cardNotOwner.clone();
            ret.cardsAllocatedToPlayer = cardsAllocatedToPlayer.clone();
            ret.atLeastOneOfs  = atLeastOneOfs;

            return ret;
        }

    }


    CardAllocation game, working;

    Main() {
        game = new CardAllocation();
    }



    void readPlayerHand(String line) {
        Scanner lineScanner = new Scanner(line);
        while (lineScanner.hasNext())
            game.allocateCard(lineScanner.next().charAt(0), 1);

    }


    void readSuggestion(String line, int player) {
        Scanner lineScanner = new Scanner(line);

        char[] suggestion = {lineScanner.next().charAt(0), lineScanner.next().charAt(0), lineScanner.next().charAt(0)};

        for (int responder = (player % 4) +1; lineScanner.hasNext(); responder = (responder  % 4 )+ 1) {
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
            readSuggestion(in.nextLine(), (i % 4) + 1);

    }




    boolean dealRemainingCards(char c) {
        if (c > 'U')
            return true;

        if (working.isAllocated(c))
            return dealRemainingCards((char) (c+1));

        for (int i = 4; i >= 2; i--) {
            if (!working.playerHandFull(i) && !working.isDefiniteNotOwner(c, i)) {
                working.allocateCard(c, i);

                if (dealRemainingCards((char) (c+1)))
                    return true;

                working.deallocateCard(c, i);
            }
        }

        return false;
    }



    boolean dealConstrainedCards(int i) {
        if (i >= working.getPlayerHasOneOfs().size())
            return dealRemainingCards('A');

        PlayerUnion option = working.getPlayerHasOneOfs().get(i);
        for (char card: option.options ) {
            int cardOwner = working.getOwner(card);

            if (!working.playerHandFull(option.player) && cardOwner == CardAllocation.UNALLOCATED && !working.isDefiniteNotOwner(card, option.player)) {
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


    void updateSolution(char[] solution, char[] curr) {
        for (int i=0; i < 3; i++) {
            if (solution[i] == '#')
                solution[i] = curr[i];

            else if (solution[i] != curr[i])
                solution[i] = '?';
        }
    }


    private void solve(int cardType, char[] solution, char[] curr) {
        if (cardType >= 3) {
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



    String solve() {
        char[] solution = {'#', '#', '#'};
        solve(0, solution, new char[]{0,0,0});

        return new String(solution);
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            Main clue = new Main();
            clue.parse(in);
            System.out.println(clue.solve());
        }
    }
}

