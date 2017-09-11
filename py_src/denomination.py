#! /usr/bin/env python
'''https://stackoverflow.com/questions/46137611/fast-algorithm-for-making-change-with-6-denominations-interview-practice'''

# input
A = [3, 4, 6, 5, 20, 18, 10, 30]
D = 50

# sort A and repeat 6 times
A = sorted(A * 6)
# create matrix M, where:
# 0 == uncomputed, 1 == True, -1 == False
arr1d = lambda x: [0] * x
arr2d = lambda x, y: [arr1d(y) for i in xrange(x)]
arr3d = lambda x, y, z: [arr2d(y, z) for i in xrange(x)]
M = arr3d(6 + 1, len(A), D + 1)


def fill_m(bills_num, start_pos, d):
    '''fill matrix M recursively'''
    global A, M
    if d == 0:  # can make change for 0 only with 0 bills
        return True if bills_num == 0 else False
    if d < 0 or bills_num <= 0 or start_pos >= len(A):
        return False
    if M[bills_num][start_pos][d] == 0:
        # need to compute cell value
        if fill_m(bills_num, start_pos + 1, d):
            M[bills_num][start_pos][d] = 1
        elif fill_m(bills_num - 1, start_pos + 1, d - A[start_pos]):
            M[bills_num][start_pos][d] = 1
        else:
            M[bills_num][start_pos][d] = -1
    return M[bills_num][start_pos][d] == 1


print 'Can make change for $', D, fill_m(6, 0, D)
