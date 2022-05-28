import numpy as np
import warnings


def swapRows(A, i, j):
    """
    interchange two rows of A
    operates on A in place
    """
    tmp = A[i].copy()
    A[i] = A[j]
    A[j] = tmp


def relError(a, b):
    """
    compute the relative error of a and b
    """
    with warnings.catch_warnings():
        warnings.simplefilter("error")
        try:
            return np.abs(a - b) / np.max(np.abs(np.array([a, b])))
        except:
            return 0.0


def rowReduce(A, i, j, pivot):
    """
    reduce row j using row i with pivot pivot, in matrix A
    operates on A in place
    """
    factor = A[j][pivot] / A[i][pivot]
    for k in range(len(A[j])):
        # we allow an accumulation of error 100 times larger 
        # than a single computation
        # this is crude but works for computations without a large 
        # dynamic range
        if relError(A[j][k], factor * A[i][k]) < 100 * np.finfo('float').resolution:
            A[j][k] = 0.0
        else:
            A[j][k] = A[j][k] - factor * A[i][k]


# stage 1 (forward elimination)
def forwardElimination(B):
    """
    Return the row echelon form of B
    """
    A = B.copy()
    m, n = np.shape(A)
    for i in range(m - 1):
        # Let lefmostNonZeroCol be the position of the leftmost nonzero value 
        # in row i or any row below it 
        leftmostNonZeroRow = m
        leftmostNonZeroCol = n
        ## for each row below row i (including row i)
        for h in range(i, m):
            ## search, starting from the left, for the first nonzero
            for k in range(i, n):
                if (A[h][k] != 0.0) and (k < leftmostNonZeroCol):
                    leftmostNonZeroRow = h
                    leftmostNonZeroCol = k
                    break
        # if there is no such position, stop
        if leftmostNonZeroRow == m:
            break
        # If the leftmostNonZeroCol in row i is zero, swap this row 
        # with a row below it
        # to make that position nonzero. This creates a pivot in that position.
        if (leftmostNonZeroRow > i):
            swapRows(A, leftmostNonZeroRow, i)
        # Use row reduction operations to create zeros in all positions 
        # below the pivot.
        for h in range(i + 1, m):
            rowReduce(A, i, h, leftmostNonZeroCol)
    return A


####################

# If any operation creates a row that is all zeros except the last element,
# the system is inconsistent; stop.
def inconsistentSystem(A):
    """
    B is assumed to be in echelon form; return True if it represents
    an inconsistent system, and False otherwise
    """
    rows = len(A)
    for k in range(0, rows):
        collumns = len(A[k])
        count = 0
        for j in range(0, collumns - 1):
            count += A[k][j]
        if (count == 0 and A[k][collumns - 1] != 0):
            return True;
    
    return False;
    


def backsubstitution(B):
    """
    return the reduced row echelon form matrix of B
    """
    A = B.astype(float)
    rows = len(A)
    col = len(A[0])
    # Devision algorithm to get 
    for k in range(0, rows):
        non_zero = 0
        for j in range(0, col-1):
            if A[k][j] != 0:
                non_zero = A[k][j]
                break
        for j in range(0, col):
            if A[k][j] != 0:
                A[k][j] = A[k][j] / non_zero
                
    for k in range(0, rows):
        non_zero = 0
        for j in range(0, col-1):
            if A[k][j] != 0:
                non_zero = j
                break
        for j in range(0, k):
            value =  - A[j][non_zero]
            for l in range(0, col):
                if A[j][l] != 0:
                    A[j][l] += A[k][l] * value
        for j in range(k+1, rows):
            value =  - A[j][non_zero]
            for l in range(0, col):
                if A[j][l] != 0:
                    A[j][l] += A[k][l] * value
    
    
    return A
    

#####################


A = np.array([[1.0, 2.0, 3.0, -16.0], [5.0, 4.0, 6.0, -41.0], [10.0, 9.0, 8.0, -50.0]])
AEchelon = forwardElimination(A)
if (not inconsistentSystem(AEchelon)):
    AReducedEchelon = backsubstitution(AEchelon)


print(AReducedEchelon)