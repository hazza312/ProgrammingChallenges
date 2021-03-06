import unittest
from voting import get_winners

class VotingTestCase(unittest.TestCase):
    def test_1_voter_1_winner(self):
        self.assertEqual(3, [0], get_winners([(0,1,2)]))

    def test_2_voters_tie(self):
        self.assertEqual(3, [0], get_winners([(0,1,2), (0,1,2)]))

    def test_2_voters_tie(self):
        self.assertEqual(3, [0], get_winners([(0,1,2), (0,1,2)]))

if __name__ == '__main__':
    unittest.main()
