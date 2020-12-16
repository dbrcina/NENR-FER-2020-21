import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

def zadatak3():
	x = np.outer(np.arange(-4, 5, 1), np.ones(9))
	print(x)
	y = x.copy().T
	z = ((x-1)**2 + (y+2)**2 - 5*x*y + 3) * np.power(np.cos(x/5), 2)
	ax = plt.axes(projection='3d', xlabel='x', ylabel='y', title='Generirajuća funkcija')
	ax.set_zlabel('f')
	ax.plot_surface(x, y, z, cmap=plt.get_cmap('inferno'))
	plt.show()

def zadatak4a():
	df = pd.read_csv('data.csv')
	x = df['x'].values.reshape(9,9)
	y = df['y'].values.reshape(9,9)
	z = df['f'].values.reshape(9,9)
	ax = plt.axes(projection='3d', xlabel='x', ylabel='y', title='1 pravilo - online učenje')
	ax.set_zlabel('f')
	ax.plot_surface(x, y, z, cmap=plt.get_cmap('inferno'))
	plt.show()
	
def zadatak4b():
	df = pd.read_csv('data.csv')
	x = df['x'].values.reshape(9,9)
	y = df['y'].values.reshape(9,9)
	z = df['f'].values.reshape(9,9)
	z -= ((x-1)**2 + (y+2)**2 - 5*x*y + 3) * np.power(np.cos(x/5), 2)
	ax = plt.axes(projection='3d', xlabel='x', ylabel='y', title='7 pravila - grupno učenje')
	ax.set_zlabel('$\delta$')
	ax.plot_surface(x, y, z, cmap=plt.get_cmap('inferno'))
	plt.show()

plt.rcParams.update({'font.size': 16})
#zadatak3()
#zadatak4a()
zadatak4b()
