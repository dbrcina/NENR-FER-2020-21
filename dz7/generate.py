import matplotlib.pyplot as plt
import numpy as np


def zad1(w=2):
    x_array = np.arange(-8, 10, 0.2)
    for s in [1, 0.25, 4]:
        y = 1 / (1 + np.absolute(x_array - w) / np.absolute(s))
        plt.plot(x_array, y, label=f'$s = {s}$')
    plt.xlabel('$x$')
    plt.ylabel('$y(x;w=2)$')
    plt.legend()
    plt.show()
    return


def zad2():
    dataset = np.loadtxt('zad7-dataset.txt')
    X = dataset[:, :2]
    y = dataset[:, 2:]
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.tab20b, marker='o', s=50)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.show()
    return


def zad45(params_file):
    dataset = np.loadtxt('zad7-dataset.txt')
    X = dataset[:, :2]
    y = dataset[:, 2:]
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.tab20b, marker='o', s=50)
    plt.xlabel('x')
    plt.ylabel('y')
    params = np.loadtxt(params_file)[:8 * 2 * 2]
    centroids = params[::2]
    plt.scatter(centroids[::2], centroids[1::2], color='black')
    plt.show()
    return


def zad6(params_file):
    dataset = np.loadtxt('zad7-dataset.txt')
    X = dataset[:, :2]
    y = dataset[:, 2:]
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.tab20b, marker='o', s=50)
    plt.xlabel('x')
    plt.ylabel('y')
    params = np.loadtxt(params_file)[:6 * 2 * 2]
    centroids = params[1::2]
    plt.scatter(centroids[::2], centroids[1::2], color='black')
    plt.show()
    return

# zad1()
# zad2()
# zad45('params.txt')
# zad45('params2.txt')
# zad6('params3.txt')
