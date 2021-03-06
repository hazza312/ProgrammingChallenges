from typing import List, Tuple, Set, Union

# hk 4 / 20

def get_next_preference(contenders: Set[int], ballot: List[int]) -> Union[int, None]:
    """From a ballot, eliminate preferences until a contender is found and return this candidate"""
    while len(ballot) > 0:
        next_preference = ballot.pop(0)
        if next_preference in contenders:
            return next_preference

    return None


def candidates_with_highest_votes(contenders: Set[int], votes: List[List[int]]) -> Tuple[List[int], int]:
    """From the contenders, return those with the highest number votes, and the associated score"""
    winners, winner_total = [], -1
    for candidate in contenders:
        tally = len(votes[candidate])
        if tally > winner_total:
            winners, winner_total = [candidate], tally

        elif tally == winner_total:
            winners.append(candidate)

    return winners, winner_total


def candidates_with_lowest_votes(contenders: Set[int], votes: List[List[int]]) -> Tuple[List[int], int]:
    """From the contenders, return those with the lowest number of votes, and the associated score"""
    losers, loser_total = [], float("inf")
    for candidate in contenders:
        tally = len(votes[candidate])
        if tally < loser_total:
            losers, loser_total = [candidate], tally

        elif tally == loser_total:
            losers.append(candidate)

    return losers, loser_total


def redistribute_votes(voters_to_redistribute, ballots: List[List[int]], contenders: Set[int], candidate_votes: List[List[int]]) -> None:
    """Get next preferences from selected voters, and add to the candidate tallys"""
    for voter in voters_to_redistribute:
        next_preference = get_next_preference(contenders, ballots[voter])
        if next_preference is not None:
            candidate_votes[next_preference].append(voter)


def run_election(num_candidates: int, ballots: List[List[int]]) -> List[int]:
    candidate_votes = [[] for _ in range(num_candidates)]
    contenders = set(range(num_candidates))
    redistribute_votes(range(len(ballots)), ballots, contenders, candidate_votes)

    while True:
        winners, win_count = candidates_with_highest_votes(contenders, candidate_votes)
        losers, lose_count = candidates_with_lowest_votes(contenders, candidate_votes)

        if win_count > len(ballots) // 2 or win_count == lose_count:
            return winners

        contenders.difference_update(losers)
        for loser in losers:
            redistribute_votes(candidate_votes[loser], ballots, contenders, candidate_votes)


def read_candidates(n: int) -> List[str]:
    return [input() for _ in range(n)]


def read_ballots() -> List[List[int]]:
    ballots = []
    try:
        while True:
            ballot = [int(vote) -1 for vote in input().split()]
            if len(ballot) == 0:
                return ballots

            ballots.append(ballot)
    except (EOFError, ValueError):
        return ballots


if __name__ == '__main__':
    num_elections = int(input())
    input() # blank line

    for i in range(num_elections):
        num_candidates = int(input())
        candidate_names = read_candidates(num_candidates)
        ballots = read_ballots()
        for winner in run_election(num_candidates, ballots):
            print(candidate_names[winner])

        if i < num_elections -1:
            print()