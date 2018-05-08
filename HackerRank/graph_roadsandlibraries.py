#!/bin/python

class Graph(object):
    def __init__(self, nodes, edges):
        self.G = [[] for i in range(nodes + 1)]
        self.nodes = nodes
        
        for e in edges:
            self.G[e[0]].append(e[1])
            self.G[e[1]].append(e[0])
            
        self.components = self.findComponents()

            
    def findComponents(self):
        components = []
        pending = set(range(self.nodes + 1))
        
        def findComponent(node, visited):
            pending.discard(node)
            visited.append(node)
            
            for n in self.G[node]:
                if n not in visited: 
                    findComponent(n, visited)

            return visited    
        
        while len(pending) > 0:
            current = pending.pop()
            components.append(findComponent(current, []))
            
        return components
        
    
    def totalCost(self, priceLibrary, priceRoad):
        if priceRoad <= priceLibrary:         
            total = 0
            for component in self.components:
                if 0 not in component:
                    total += (len(component) - 1) * priceRoad + priceLibrary
                    
            return total

        else:
            return priceLibrary * self.nodes
        

def roadsAndLibraries(n, c_lib, c_road, cities):
    G = Graph(n, cities)
    return G.totalCost(c_lib, c_road)

if __name__ == "__main__":
    q = int(raw_input().strip())
    for a0 in xrange(q):
        n, m, c_lib, c_road = raw_input().strip().split(' ')
        n, m, c_lib, c_road = [int(n), int(m), int(c_lib), int(c_road)]
        cities = []
        for cities_i in xrange(m):
            cities_temp = map(int,raw_input().strip().split(' '))
            cities.append(cities_temp)
        result = roadsAndLibraries(n, c_lib, c_road, cities)
        print result