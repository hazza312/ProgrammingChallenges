

def get_position(m, n) -> str:
    pos = ""
    m1, n1 = 0, 1
    m2, n2 = 1, 0

    while True:
        curr_m, curr_n = (m1+m2, n1+n2)
        if (curr_m, curr_n) == (m, n):
            return pos

        elif m / n < curr_m / curr_n:
            pos += "L"
            m2, n2 = curr_m, curr_n

        else:
            pos += "R"
            m1, n1 = curr_m, curr_n


if __name__ == '__main__':
    while True:
        vals = tuple(map(int, input().split()))
        if vals == (1,1):
            break

        print(get_position(*vals))
