import sys
import heapq

############################

# Secondary Functions Part 1

############################

def readGraph(input_file):
    with open(input_file, 'r') as f:
        raw = [[float(x) for x in s.split(',')] for s in f.read().splitlines()]
    N = int(raw[0][0])
    m = int(raw[1][0])
    s = int(raw[2][0])
    adj_list = [[] for foo in range(N)]
    for edge in raw[3:]:
        adj_list[int(edge[0])].append((int(edge[1]), edge[2]))
    return N, m, s, adj_list


def writeOutput(output_file, N, s, distances, parents, mst):
    with open(output_file, 'w') as f:
        for i in range(N):
            if i == s:
                f.write('0.0,-\n')
            else:
                f.write(str(distances[i])+','+str(parents[i])+'\n')
        
        f.write('\n')

        for j in range(N):
            neighbors = []
            for node in mst[j]:
                neighbors.append(str(node[0]))
            neighbors.sort()
            f.write(','.join(neighbors) +'\n')

def make_undirected_graph(N, adj_list):
    G = {}
    for u in range(N):
        G[u] ={}

    for u in range(N):
        for v in adj_list[u]:
            G[u][v[0]] = v[1]
            G[v[0]][u] = v[1]
    
    adj_list = ['x' for x in range(N)]
    for u in range(N):
        neighbors = []
        for v in G[u].keys():
            neighbors.append((v, G[u][v]))
        adj_list[u] = list(set(neighbors))
    return adj_list

def Run(input_file, output_file):
    N, m, s, adj_list = readGraph(input_file)
    distances, parents =   dijkstra(N, m, s, adj_list)
    undirected_adj_list = make_undirected_graph(N, adj_list)
    mst = kruskal(N, m, undirected_adj_list)
    writeOutput(output_file, N, s, distances, parents, mst)



############################

# Secondary Functions Part 2

############################

def getPathCosts(N, s, mst):
    costs = [float('inf') for v in range(0,N)]
    
    costs[s] = 0
    
    Q = []
    Q.append((s, costs[s]))
    
    while len(Q) > 0:
        v, currCost = Q.pop(0)
        
        if currCost > costs[v]:
            continue
        
        for neighbor, weight in mst[v]:
            cost = currCost + weight
            
            if cost < costs[neighbor]:
                costs[neighbor] = cost
                Q.append((neighbor, cost))
                
                
    for v in range(0,N):
        if costs[v] == float('inf'):
            costs[v] = -1
    
    return costs

def getMaxSP(s, u, parents, edgeCost):
    cost = 0
    
    curr = u
    while curr != s:
        parent = parents[curr]
        cost += edgeCost[(parent, curr)]
        curr = parent
    
    return cost

def getMSTCost(N, mst):
    addedEdges = {}
    mstCost = 0
    
    for v in range(0,N):
        for (neighbor, weight) in mst[v]:
            try:
                if addedEdges[(v, neighbor)]:
                    continue
                else:
                    mstCost += weight
                    addedEdges[(v, neighbor)] = True
                    addedEdges[(neighbor, v)] = True
            except KeyError:
                mstCost += weight
                addedEdges[(v, neighbor)] = True
                addedEdges[(neighbor, v)] = True
    
    return mstCost

def getSPCost(N, s, parents, undirected_adj_list):
    spCost = 0
    edgeCost = {}
    added = [False]*N
    
    for v in range(0,N):
        for (neighbor, weight) in undirected_adj_list[v]:
            edgeCost[(v, neighbor)] = weight
    
    for v in range(0,N):
        if added[v]:
            continue    
        
        curr = v
        localCost = 0
        while curr != s:
            if added[curr]:
                break
            added[curr] = True
            parent = parents[v]
            localCost += edgeCost[(v,parent)]
            curr = parent
            
        spCost += localCost
        
    return spCost

def RunTwo(input_file, output_file):
    N, m, s, adj_list = readGraph(input_file)
    distances, parents =   dijkstra(N, m, s, adj_list)
    undirected_adj_list = make_undirected_graph(N, adj_list)
    mst = kruskal(N, m, undirected_adj_list)
    
    getTWR(N, s, mst, parents, undirected_adj_list)
    getMDR(N, s, mst, parents, undirected_adj_list)
    



############################

# Main Functions Part 2

############################

def getMDR(N, s, mst, parents, undirected_adj_list):
    maxCost = 0
    node = s
    costOne = 0
    costTwo = 0
    
    edgeCost = {}
    for v in range(0,N):
        for (neighbor, weight) in undirected_adj_list[v]:
            edgeCost[(v, neighbor)] = weight

 
    pathCosts = getPathCosts(N, s, mst)
    
    for v in range(0, N):
        if (v != s):
            localCostOne = pathCosts[v]
            localCostTwo = getMaxSP(s, v, parents, edgeCost)
            localCost = localCostOne / localCostTwo
        
            if localCost > maxCost:
                maxCost = localCost
                costOne = localCostOne
                costTwo = localCostTwo
                node = v
    
    print("MDR: " + str(round(maxCost, 4)))
    print("Node for max MDR: " + str(node))
    print("======================")

def getTWR(N, s, mst, parents, undirected_adj_list):
    mstCost = getMSTCost(N, mst)
    
    spCost = getSPCost(N, s, parents, undirected_adj_list)

    print("cost(Tmst): " + str(round(mstCost, 4)))
    print("cost(Tsp): " + str(round(spCost,4)))
    print("TWR: " + str(round(spCost/mstCost,4)))
    print("======================")




############################

# Aux Functions for Algos

############################

def extractMin(Q, visited):
    dist, v = heapq.heappop(Q)

    while visited[v]:
        if (len(Q) <= 0):
            return -1, -1
        dist, v = heapq.heappop(Q)
    
    return dist, v


def find(parents, i):
    if parents[i] != i:
        parents[i] = find(parents, parents[i])
    return parents[i]
    
def union(parents, ranks, x, y):
    xroot = find(parents, x)
    yroot = find(parents, y)
    
    if ranks[xroot] < ranks[yroot]:
        parents[xroot] = yroot
    elif ranks[xroot] > ranks[yroot]:
        parents[yroot] = xroot
    else:
        parents[yroot] = xroot
        ranks[xroot] += 1
    


############################

# Algorithms

############################

def dijkstra(N, m, s, adj_list):    
    tempDist = []
    tempPar = []
    visited = []
    for v in range(0, N):
        tempDist.append((v, float('inf')))
        tempPar.append((v, None))
        visited.append(False)
    distances = dict(tempDist)
    parents = dict(tempPar)
    
    
    distances[s] = 0
    Q = []
    
    for v in range(0, N):
        heapq.heappush(Q, (distances[v], v))

    while len(Q) > 0:
        currDist, v = extractMin(Q, visited)
        if v == -1:
            break
        
        visited[v] = True
        
        if currDist > distances[v]:
            continue
            
        for neighbor, weight in adj_list[v]:
            distance = currDist + weight
            
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                parents[neighbor] = v
                heapq.heappush(Q, (distance, neighbor))

    return distances, parents

def kruskal(N, m, undirected_adj_list):
    mst_adj_list = []
    Q = []
    
    parents = []
    ranks = []
    
    added = {}
    for v in range(0, N):
        parents.append(v)
        ranks.append(0)
        mst_adj_list.append([])
        
        for neighbor, weight in undirected_adj_list[v]:
            try:
                if added[(v, neighbor)]:
                    continue
                else:
                    Q.append([v, neighbor, weight])
                    added[(v, neighbor)] = True
                    added[(neighbor, v)] = True
            except KeyError:
                Q.append([v, neighbor, weight])
                added[(v, neighbor)] = True
                added[(neighbor, v)] = True
    
    Q.sort(key=lambda item:item[2])
    
    for [u, v, w] in Q:
        x = find(parents, u)
        y = find(parents, v)
        
        if (x != y):
            mst_adj_list[u].append((v,w))
            mst_adj_list[v].append((u,w))
            union(parents, ranks, x, y)
    
    return mst_adj_list


############################

# Main Functions

############################

def main(args=[]):
    print("======================")
    RunTwo('pa2_graphs/g_randomEdges.txt', 'output_random.txt')
    RunTwo('pa2_graphs/g_donutEdges.txt', 'output_donut.txt')
    RunTwo('pa2_graphs/g_zigzagEdges.txt', 'output_zigzag.txt')

if __name__ == "__main__":
    main(sys.argv[1:])    