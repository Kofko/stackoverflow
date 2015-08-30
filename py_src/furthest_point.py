#! /usr/bin/env python

'''
http://stackoverflow.com/questions/31952383/finding-the-furthest-point-in-a-grid-when-compared-to-other-points

I have a rectangular grid of variable size but averaging 500x500 with a small number of x,y points in it (less than 5). I need to find an algorithm that returns an x,y pair that is the farthest away possible from any of the other points.
'''

from numpy import array
from numpy.linalg import norm
from numpy.random import random as rnd


def get_loss(pos, enem):
    loss = sum([100. / (norm(pos - _) + 0.1) ** 2 for _ in enem])
    loss += sum([1. / (norm(pos - _) + 0.1) ** 2 for _ in get_bounds(pos)])
    return loss


def ensure_bounds(pos):
    return array([min(max(_, 0.), 500.) for _ in pos])


def get_bounds(pos):
    return ((pos[0], 0.), (pos[0], 500.), (0., pos[1]), (500., pos[1]))


def one_dir_opt(pos, g, enem):
    min_pos = pos
    min_loss = get_loss(pos, enem)
    for pw in xrange(6):
        npos = ensure_bounds(pos + g * 2 ** pw)
        loss = get_loss(npos, enem)
        if loss < min_loss:
            min_loss = loss
            min_pos = npos
    return min_pos


def get_grad(pos, enem):
    g = array((0., 0.))
    for e in enem:
        vec = pos - array(e)
        vec *= 100. / (norm(vec) + 0.1) ** 4
        g += vec
    for e in get_bounds(pos):
        vec = pos - array(e)
        vec *= 1. / (norm(vec) + 0.1) ** 4
        g += vec
    g /= norm(g)
    return g


def get_pos(enem):
    # choose random start position
    pos = array((rnd() * 500., rnd() * 500.))
    # make several iterations
    for i in xrange(25):
        # get direction that minimize loss func
        g = get_grad(pos, enem)
        # do one dimentional optimization
        npos = one_dir_opt(pos, g, enem)
        # check stop condition
        if norm(npos - pos) < 0.5:
            break
        # update position
        pos = npos
    return pos


if __name__ == '__main__':
    enem = ((250, 250), (120, 120), (380, 120), (120, 380), (380, 380))
    pos = get_pos(enem)
    print 'Enemies: %s' % str(enem)
    print 'Position: %s' % str(pos)
    print 'Loss: %g' % get_loss(pos, enem)
