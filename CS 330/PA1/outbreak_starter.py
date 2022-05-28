import sys
import random
import queue

############################

# DO NOT CHANGE THIS PART!!

############################

def readGraph(input_file):
    with open(input_file, 'r') as f:
        raw = [line.split(',') for line in f.read().splitlines()]

    N = int(raw[0][0])
    sin = raw[1]
    s = []
    for st in sin:
        s.append(int(st))
    adj_list = []
    for line in raw[2:]:
        if line == ['-']:
            adj_list.append([])
        else:
            adj_list.append([int(index) for index in line])
    return N, s, adj_list

def writeOutput(output_file, prob_infect, avg_day):
    with open(output_file, 'w') as f:
        for i in prob_infect:
            f.write(str(i) + '\n')
        f.write('\n')
        for i in avg_day:
            f.write(str(i) + '\n')

 

def Run(input_file, output_file, p):
    N, s, adj_list = readGraph(input_file)
    prob_infect, avg_day =   model_outbreak(N, s, adj_list, p)
    writeOutput(output_file, prob_infect, avg_day)



def  BFS(N, s, adj_list):
    level = ['x']*(N+1)
    #######################################

    # COPY YOUR BFS CODE FROM PART 1 HERE

    ########################################
    Q = queue.Queue()
    
    Q.put(s)
    level[s] = 0
    
    while (not Q.empty()):
        node = Q.get()
        
        for i in range(len(adj_list[node])):
            neighbour = adj_list[node][i]
            
            if (level[neighbour] == 'x'):
                Q.put(neighbour)
                level[neighbour] = level[node] + 1



    return level

#######################################

# WRITE YOUR SOLUTION IN THIS FUNCTION

########################################

def model_outbreak(N, s, adj_list, p):
    # Again, you are given N, s, and the adj_list
    # You can also call your BFS algorithm in this function,
    # or write other functions to use here.
    # Return two lists of size n, where each entry represents one vertex:
    prob_infect = [0]*N
    # the probability that each node gets infected after a run of the experiment
    avg_day = [0]*N
    # the average day of infection for each node
    # (you can write 'inf' for infinity if the node is never infected)
    # The code will write this information to a single text file.
    # If you do not name this file at the command prompt, it will be called 'outbreak_output.txt'.
    # The first N lines of the file will have the probability infected for each node.
    # Then there will be a single space.
    # Then the following N lines will have the avg_day_infected for each node.
    
    runtimes = 100
    
    for i in range(runtimes):
        newAdj = genRandomInstance(N, adj_list, s, p)
        level = BFS(N, N, newAdj)
        for i in range(N):
            if level[i] != 'x':
                prob_infect[i] += 1
                avg_day[i] += level[i]
    
    for i in range(N):
        if prob_infect[i] == 0:
            avg_day[i] = 0
            prob_infect[i] = 0
        else:
            avg_day[i] /= prob_infect[i]
            prob_infect[i] /= runtimes

    return prob_infect, avg_day



def genRandomInstance(N, adj_list, s, p):
    newAdj = [[] for i in range(N+1)]
      
    for i in range(N):
        newSubAdj = []
        for j in range(len(adj_list[i])):
            active = random.random()
            if active <= p:
                newSubAdj.append(adj_list[i][j])
        newAdj[i] = newSubAdj
    
    newAdj[N] = s
    
    return newAdj


############################

# DO NOT CHANGE THIS PART!!

############################


# read command line arguments and then run
def main(args=[]):
    filenames = []

    #graph file
    if len(args)>0:
        filenames.append(args[0])
        input_file = filenames[0]
    else:
        print()
        print('ERROR: Please enter file names on the command line:')
        print('>> python outbreak.py graph_file.txt output_file.txt')
        print()
        return

    # output file
    if len(args)>1:
        filenames.append(args[1])
    else:
        filenames.append('outbreak_output.txt')
    output_file = filenames[1]
    p = float(args[2])

    Run(input_file, output_file, p)


if __name__ == "__main__":
    main(sys.argv[1:])    
