from collections import Counter

STRAIGHT_FLUSH = 9
FOUR_KIND = 8
FULL_HOUSE = 7
FLUSH = 6
STRAIGHT = 5
THREE_KIND = 4
TWO_PAIRS = 3
PAIR = 2
HIGH_CARD = 1


class Card:
    def __init__(self, rank, suit):
        self.rank = rank
        self.suit = suit


def count_ranks(cards):
    counts = Counter([card.rank for card in cards]).most_common()
    return sorted(counts, key=lambda x: x[1] * 50 + x[0], reverse=True)


def deterimine_is_run(cards):
    for i in range(1, len(cards)):
        if cards[i].rank != cards[i-1].rank + 1:
            return False

    return True


def determine(cards):
    (most_common, most_common_count), (next_common, next_common_count) = count_ranks(cards)[:2]
    is_run = deterimine_is_run(cards)
    all_same_suit = all(card.suit == cards[0].suit for card in cards[1:])

    if is_run:
        return (STRAIGHT_FLUSH if all_same_suit else STRAIGHT, [cards[-1].rank])

    elif most_common_count == 4:
        return (FOUR_KIND, [most_common])

    elif most_common_count == 3 and next_common_count == 2:
        return (FULL_HOUSE, [most_common])

    elif all_same_suit:
        return (FLUSH, [card.rank for card in cards[::-1]])

    elif most_common_count == 3:
        return (THREE_KIND, [most_common])

    elif most_common_count == 2:
        if next_common_count == 2:
            pair_vals = [most_common, next_common]
            return (TWO_PAIRS,  pair_vals + [c.rank for c in cards[::-1] if c not in pair_vals])

        return (PAIR, [most_common] + [c.rank for c in cards[::-1] if c != most_common])

    return (HIGH_CARD, [card.rank for card in cards[::-1]])


def parse_card(s):
    rank = int(s[0]) if s[0].isdigit() else ('T', 'J', 'Q', 'K', 'A').index(s[0]) + 10
    suit = ('C', 'D', 'H', 'S').index(s[1])
    return Card(rank, suit)



def cmp(a, b):
    if a > b:
        return 1
    elif a == b:
        return 0

    return 2



def main():
    while True:
        try:
            cards = [parse_card(s) for s in input().split()]
        except EOFError:
            break

        p1_hand, p1_subscores = determine(sorted(cards[:5], key=lambda x: x.rank))
        p2_hand, p2_subscores = determine(sorted(cards[5:], key=lambda x: x.rank))

        winner = cmp(p1_hand, p2_hand)
        if winner == 0:
            for i in range(len(p1_subscores)):
                winner = cmp(p1_subscores[i], p2_subscores[i])
                if winner != 0:
                    break

        #print(p1_hand, p2_hand, p1_subscores, p2_subscores)
        print(("Tie.", "Black wins.", "White wins.")[winner])




if __name__ == '__main__':
    main()