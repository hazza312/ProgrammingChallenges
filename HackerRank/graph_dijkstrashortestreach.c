#include <stdlib.h>
#include <stdio.h>
#include <limits.h>

struct Graph {
	int* AM;
	int* DM;
	int* PQ;
	int PQSize;
	int n;
};

struct Graph* newGraph(int n) {
	struct Graph *G = (struct Graph*) malloc(sizeof(struct Graph));

	G->AM = calloc((n+1) * (n+1), sizeof(int));
	G->PQ = malloc((n+1) * sizeof(int));
	G->DM = malloc((n+1) * sizeof(int));
	G->n = n;
	G->PQSize = 0;

	for (int i=0; i<=n; i++) {
		G->DM[i] = INT_MAX;
		G->PQ[i] = INT_MAX;
	}
	
	return G;
}

int getEdge(struct Graph *G, int u, int v) {
	return G->AM[v * G->n + u + 1];
}

void addEdge(struct Graph *G, int u, int v, int w) {
	// undirected graph
	if (w < G->AM[v * G->n + u + 1] ||G->AM[v * G->n + u + 1]==0 )	G->AM[v * G->n + u + 1] = w;
	if (w < G->AM[u * G->n + v + 1] || G->AM[u * G->n + v + 1]==0)	G->AM[u * G->n + v + 1] = w;
}

int* neighbours(struct Graph *G, int u) {
	int count = 0;
	for (int i=1; i<=G->n; i++) {
		if (u!= i && getEdge(G, u, i) > 0)		count++;
	}

	int* neighbours = calloc(count + 1, sizeof(int));
	// zeroth position indicates number of neighbours
	neighbours[0] = count;

	int pos = 1;
	for (int i=1; i<=G->n; i++) {
		if (u!= i && getEdge(G, u, i) > 0) {
			neighbours[pos] = i;
			pos++;
		}
	}

	return neighbours;	

}

void priorityAdd(struct Graph *G, int index) {
	G->PQ[index] = G->DM[index];
	G->PQSize++;
}

void priorityRemove(struct Graph *G, int index) {
	G->PQ[index] = INT_MAX;
	G->PQSize--;
}

int minDistPriority(struct Graph *G) {
	int max = INT_MAX;
	int pos = 0;

	for (int i=1; i<=G->n; i++) {
		if (G->PQ[i] < max) {
			max = G->PQ[i];
			pos = i;
		}
	}

	priorityRemove(G, pos);

	return pos;
}


void dijkstra(struct Graph *G, int s) {
	G->DM[s] = 0;
	priorityAdd(G, s);

	while (G->PQSize > 0) {
		int current = minDistPriority(G);
		int* currentNeighbours = neighbours(G, current);
		//printf("current %d, qsize %d\n", current, G->PQSize);

		for (int i=1; i<=currentNeighbours[0]; i++) {			
			int thisN = currentNeighbours[i];			
			//printf("  neighbour %d\n", thisN);
			int alt = G->DM[current] + getEdge(G, current, thisN);
			if (alt < G->DM[thisN]) {
				G->DM[thisN] = alt;
				priorityAdd(G, thisN);
			}
		}
		free(currentNeighbours);
	}

}

void destroy(struct Graph* G) {
	free(G->AM);
	free(G->DM);
	free(G->PQ);
	free(G);
}

int main() {
	int cases, n, m, a, b, w, s;

	scanf("%d", &cases);

	for (int c=0; c<cases; c++) {
		scanf("%d %d", &n, &m);

		struct Graph *G = newGraph(n+1);
	
		for (int m_i=0; m_i<m; m_i++) {
			scanf("%d %d %d", &a, &b, &w);
			addEdge(G, a, b, w);
		}

		scanf("%d", &s);
		dijkstra(G, s);

		for (int i=1; i<=n; i++) {
			if (i!=s) {
				int val = (G->DM[i] == INT_MAX)? -1 : G->DM[i]; 
				printf("%d ", val);
			}
			
		}
		printf("\n");
		
		destroy(G);
		
	}
	
	
}