import numpy as np

def innerProduct(a,b):
    value = 0
    for i in range(0, len(a)):
        value += a[i]*b[i]
        
    return value

def AxIP(A,x):
    b = [0 for j in range(0, len(A))]
    for i in range(0, len(A)):
        b[i] = innerProduct(A[i,:], x)
        
    return np.array(b)

def AxVS(A,x):
    b = [0 for j in range(0, len(A))]
    for i in range(0, len(A[0])):
        c = A[:,i]
        for k in range(0, len(A)):
            b[k] += c[k] * x[i]
        
    return np.array(b)