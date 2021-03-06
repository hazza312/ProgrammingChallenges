import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
