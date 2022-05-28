# Hannah Catabia, catabia@bu.edu
# Solution code for PA2, CS330 Fall 2020
# Adapted from:
# Gavin Brown, grbrown@bu.edu
# CS330 Fall 2019, Programming Exercise Solution 



import sys
import copy

############################

# DO NOT CHANGE THIS PART!!

############################

def readInput(input_file):
    with open(input_file, 'r') as f:
        raw = [[float(x) for x in s.split(',')] for s in f.read().splitlines()]
        # number of intervals
        N = int(raw[0][0])
        # max number to schedule
        k =  int(raw[1][0])
        # intervals, with name of interval as an int
        intervals = raw[2:]

        for i in range(len(intervals)):
            intervals[i][0] = int(intervals[i][0])
        return N, k, intervals
    

def writeOutput(schedule, output_file):
    with open(output_file, 'w') as f:
        for i in schedule:
            f.write(str(i) + '\n')


def Run(input_file, output_file):
    N, k, intervals = readInput(input_file)
    schedule = find_solution(N, k, intervals)
    assert all(isinstance(n, int) for n in schedule), "All items in schedule array should be type INT, otherwise the autograder will fail."
    writeOutput(schedule, output_file)


############################

# ADD YOUR OWN METHODS HERE
# (IF YOU'D LIKE)

############################


############################

# FINISH THESE METHODS

############################

def find_solution(N, k, intervals):
    ############################

    # Calculating p(i)

    ############################
    sortedStartingTime = copy.copy(intervals)
    sortedStartingTime.sort(key=lambda x: x[1])
    sortedFinishTime = copy.copy(intervals)
    sortedFinishTime.sort(key=lambda x: x[2])
    
    for i in range(0,N):
        sortedStartingTime[i].append(-1)
        startTime = sortedStartingTime[i][1]
        for j in range(i-1,-1,-1):
            finishTime = sortedFinishTime[j][2]
            if (finishTime <= startTime):
                sortedStartingTime[i][4] = j
                break;

    #for i in range(0,N):
    #    print(sortedFinishTime[i])
        
    #print()
    
    ############################

    # Calculating the schedule

    ############################
    
    M = [([],-1) for x in range(0,N)]                           #Initialize the table
    M[0] = ([sortedFinishTime[0][0]], sortedFinishTime[0][3])   #Set base case
    
    for i in range(1,N):
        val = sortedFinishTime[i][3]
        lastIndex = sortedFinishTime[i][4]
        added = []
        
        if (lastIndex >= 0):
            val += M[lastIndex][1]
            added = M[lastIndex][0][:]
            
        added.append(sortedFinishTime[i][0])
        
        if(val >= M[i-1][1]):
            M[i] = (added, val)
        else:
            M[i] = M[i-1]


    schedule = M[N-1][0]
    
    return schedule




############################################

# CHANGE INPUT FILES FOR DEBUGGING HERE

############################################

def main(args=[]):
    # WHEN YOU SUBMIT TO THE AUTOGRADER, 
    # PLEASE MAKE SURE THE FOLLOWING FUNCTION LOOKS LIKE:
    Run('input', 'output')
    #Run('./student_examples/input_student_example_10_4', 'output')

if __name__ == "__main__":
    main(sys.argv[1:])    

