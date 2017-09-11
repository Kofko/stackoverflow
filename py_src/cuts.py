#! /usr/bin/env python
'''https://stackoverflow.com/questions/45943773/dynamic-programming-table-finding-the-minimal-cost-to-break-a-string'''

# input
n = 20
cuts = [3, 8, 10]
# add fake cuts
cuts = [-1] + cuts + [n - 1]
cuts_num = len(cuts)
# init table
table = []
for i in xrange(cuts_num):
    table += [[0] * cuts_num]
# fill table
for diff in xrange(2, cuts_num):
    for start in xrange(0, cuts_num - diff):
        end = start + diff
        table[start][end] = 1e9
        for mid in xrange(start + 1, end):
            table[start][end] = min(table[start][end], table[
                                    start][mid] + table[mid][end])
        table[start][end] += cuts[end] - cuts[start]
# print result: 38
print table[0][cuts_num - 1]
