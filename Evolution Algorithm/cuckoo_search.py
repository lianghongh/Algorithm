# Cuckoo Algorithm简单实现

import numpy as np
from scipy.special import gamma

# 计算目标函数的最大值
def object_func(x):
    return x*np.sin(10*x*np.pi)+2

# 计算new_nest中适应度最大的个体，返回适应度和索引下标
def get_best_nest(nest, new_nest, fitness):
    for i in range(np.size(nest)):
        f = object_func(new_nest[i])
        if f > fitness[i]:
            fitness[i] = f
            nest[i] = new_nest[i]

    max = fitness[0]
    index = 0
    for i in range(1, np.size(fitness)):
        if fitness[i] > max:
            max = fitness[i]
            index = i

    return index, max

# 边界检查
def check_bound(s, LB, UB):
    if s < LB:
        return LB
    elif s > UB:
        return UB
    return s

# 局部随机游走：以pa概率抛弃掉一定的鸟巢，然后随即生成新的鸟巢
def empty_nest(nest, LB, UB, pa):
    new_nest = np.array(nest)
    k = np.random.rand(*nest.shape) > pa
    nest1 = np.array(nest)
    nest2 = np.array(nest)
    np.random.shuffle(nest1)
    np.random.shuffle(nest2)
    step = np.random.rand() * (nest1 - nest2)
    new_nest += step * k

    for i in range(np.size(new_nest)):
        new_nest[i] = check_bound(new_nest[i], LB, UB)

    return new_nest

# Levy飞行，全局随机游走
def cuckoo(nest, best, LB, UB, beta=1.5, alpha=0.01):
    n = np.size(nest)
    new_nest = np.array(nest)
    sigma = (gamma(1 + beta) * np.sin(np.pi * beta / 2) / (gamma((1 + beta) / 2) * beta * 2 ** ((beta - 1) / 2))) ** (1 / beta)
    for i in range(n):
        u = np.random.randn()
        v = np.random.randn()
        step = u * sigma / (np.abs(v) ** (1 / beta)) * alpha * (new_nest[i] - best)
        new_nest[i] += step * np.random.randn()
        new_nest[i] = check_bound(new_nest[i], LB, UB)
    return new_nest

# CS算法
def cs(npop, pa, LB, UB, max_iter):
    dim = 1
    nest = np.zeros([npop, dim])
    for i in range(npop):
        nest[i] = LB + (UB - LB) * np.random.rand()

    fitness = -10 ** 8 * np.ones([npop, dim])
    best_index, best = get_best_nest(nest, nest, fitness)
    for n in range(max_iter):
        new_nest = cuckoo(nest, best, LB, UB)
        index, b = get_best_nest(nest, new_nest, fitness)
        new_nest = empty_nest(nest, LB, UB, pa)
        index, b = get_best_nest(nest, new_nest, fitness)

        if b > best:
            best = b
            best_index = index
        print("gen %d---------->best_x is %f    best_fitness is %f" % (n, nest[best_index], best))


if __name__ == '__main__':
    cs(10, 0.25, -1, 2, 100)
