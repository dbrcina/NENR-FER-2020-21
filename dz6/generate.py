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

def zadatak5():
	x = np.arange(-4,5,1)
	a = np.loadtxt('a.txt')
	b = np.loadtxt('b.txt')
	c = np.loadtxt('c.txt')
	d = np.loadtxt('d.txt')
	for i in range(7):
		plt.ylim(0,1)
		plt.plot(x, 1/(1+np.exp(b[i]*(x-a[i]))))
		plt.xlabel('x')
		plt.ylabel(f'$A_{i+1}(x)$')
		plt.show()
		plt.ylim(0,1)
		plt.plot(x, 1/(1+np.exp(d[i]*(x-c[i]))))
		plt.xlabel('y')
		plt.ylabel(f'$B_{i+1}(y)$')
		plt.show()
		
def zadatak7():
	errors = np.loadtxt('errors.txt')
	plt.plot([i for i in range(1, len(errors)+1)], errors)
	plt.xlabel('Broj epoha')
	plt.ylabel('Gubitak')
	plt.title('Kretanje gubitka kroz epohe za grupno učenje')
	plt.show()
	
def zadatak8():
	for entry in [('errors_optimal.txt',1e-3,3e-4), ('errors_slow.txt',1e-5,3e-6), ('errors_div.txt',0.1,0.1)]:
		errors = np.loadtxt(entry[0])
		plt.plot([i for i in range(1,101)], errors[0:100], label=f'$\eta={entry[1],entry[2]}$')
	plt.xlabel('Broj epoha')
	plt.ylabel('Gubitak')
	plt.legend()
	plt.title('Kretanje gubitka kroz epohe za online učenje')
	plt.show()
	
plt.rcParams.update({'font.size': 12})
#zadatak3()
#zadatak4a()
#zadatak4b()
#zadatak5()
#zadatak7()
#zadatak8()
