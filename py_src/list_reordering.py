#! /usr/bin/env
'''
http://stackoverflow.com/questions/29947896/how-to-find-a-list-of-steps-needed-to-reorder-a-list-to-get-another-list

Now what I'm trying to find is a list of steps that is needed to reorder the first list to match the second list
'''

first = ['A', 'B', 'C', 'D']
second = ['A', 'D', 'B', 'C']
steps = []
tmp = second[:]  # copy second list to temp list
for pos in range(len(first)):
    # find position where first and temp lists are different
    if first[pos] != tmp[pos]:
        # get element that should be placed in position 'pos'
        element = first[pos]
        # get position of that element in second list
        sec_pos = second.index(element)
        # add step: move element from sec_pos to pos
        step = '%d -> %d' % (sec_pos, pos)
        steps.append(step)
        # do permutation in temp list
        tmp.remove(element)  # remove element
        tmp.insert(pos, element)  # put it in proper position
        # print step and intermediate result
        print step, tmp
print steps
