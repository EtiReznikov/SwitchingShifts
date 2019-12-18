package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

class Graph<T> {


    private List<Vetrex<T>> graph;

    public Graph() {
        graph = new ArrayList<Vetrex<T>>();

    }

    public Graph(Graph g) {
        graph = new ArrayList<>();
        for (Vetrex<T> v: g.graph){
            graph.add(v);
            for(Vetrex<T> n: v.neg){
                v.addEdge(n);
            }
        }

    }
    public List<Vetrex<T>> getGraph(){
        return graph;
    }

    public void add_vetrex(Vetrex v) {
        if (!this.contains_vetrex(v))
            graph.add(v);
    }

    public boolean contains_vetrex(Vetrex v) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).equals(v))
                return true;
        }
        return false;
    }
    public void addEdge(Vetrex W, Vetrex R){
        graph.add(W);
        graph.add(R);
        R.addEdge(W);
        // if has no circle:
        for (Vetrex<T> v:  graph){
            if (v.equals(W)){
                R.addEdge(v);
                for (Vetrex<T> n: v.neg){
                    W.addEdge(n);
                }
            }
            else if (v.equals(R)){
                v.addEdge(W);
                for (Vetrex<T> n: v.neg){
                    R.addEdge(n);
                }
            }
            else{
                for (Vetrex<T> n: v.neg){
                    if (n.equals(W)){
                        R.addEdge(n);
                        v.addEdge(W);
                    }
                    else if (n.equals(R)){
                        v.addEdge(R);
                        n.addEdge(W);
                    }
                }
            }

        }
    }

}
class Vetrex<T>{

    String userid;


    String date;
    String shift_type;

    boolean flag;
    LinkedList<Vetrex<T>> neg;
    public Vetrex(String data_type, String email){
        this.date_type=data_type;
        this.email=email;
        neg= new LinkedList<>();
    }

    public void addEdge(Vetrex v){
        if (!contains_edge(v))
            this.neg.add(v);
    }

    public boolean contains_edge(Vetrex v){
        for (int i=0; i<this.neg.size(); i++)
            if (neg.get(i).equals(v))
                return true;
        return false;
    }
    public  LinkedList<Vetrex<T>> getNeg(){
        return neg;
    }
}
/**
 * Basic graph data structure
 */
//class Graph<T>{
//
//    private List<Edge<T>> allEdges;
//    private Map<Long,Vertex<T>> allVertex;
//
//
//    public Graph(){
//        allEdges = new ArrayList<Edge<T>>();
//        allVertex = new HashMap<Long,Vertex<T>>();
//    }
//
//
//
//    //This works only for directed graph because for undirected graph we can end up
//    //adding edges two times to allEdges
//    public void addVertex(Vertex<T> vertex){
//        if(allVertex.containsKey(vertex.getId())){
//            return;
//        }
//        allVertex.put(vertex.getId(), vertex);
//        for(Edge<T> edge : vertex.getEdges()){
//            allEdges.add(edge);
//        }
//    }
//
//    public Vertex<T> addSingleVertex(long id){
//        if(allVertex.containsKey(id)){
//            return allVertex.get(id);
//        }
//        Vertex<T> v = new Vertex<T>(id);
//        allVertex.put(id, v);
//        return v;
//    }
//
//    public Vertex<T> getVertex(long id){
//        return allVertex.get(id);
//    }
//
//    public void addEdge(long id1,long id2){
//        Vertex<T> vertex1 = null;
//        if(allVertex.containsKey(id1)){
//            vertex1 = allVertex.get(id1);
//        }else{
//            vertex1 = new Vertex<T>(id1);
//            allVertex.put(id1, vertex1);
//        }
//        Vertex<T> vertex2 = null;
//        if(allVertex.containsKey(id2)){
//            vertex2 = allVertex.get(id2);
//        }else{
//            vertex2 = new Vertex<T>(id2);
//            allVertex.put(id2, vertex2);
//        }
//
//        Edge<T> edge = new Edge<T>(vertex1,vertex2);
//        allEdges.add(edge);
//        vertex1.addAdjacentVertex(edge, vertex2);
//
//
//    }
//
//    public List<Edge<T>> getAllEdges(){
//        return allEdges;
//    }
//
//    public Collection<Vertex<T>> getAllVertex(){
//        return allVertex.values();
//    }
//    public void setDataForVertex(long id, T data){
//        if(allVertex.containsKey(id)){
//            Vertex<T> vertex = allVertex.get(id);
//            vertex.setData(data);
//        }
//    }
//
//    @Override
//    public String toString(){
//        StringBuffer buffer = new StringBuffer();
//        for(Edge<T> edge : getAllEdges()){
//            buffer.append(edge.getVertex1() + " " + edge.getVertex2()  );
//            buffer.append("\n");
//        }
//        return buffer.toString();
//    }
//}
//
//
//class Vertex<T> {
//    long id;
//    private T data;
//    private List<Edge<T>> edges = new ArrayList<>();
//    private List<Vertex<T>> adjacentVertex = new ArrayList<>();
//
//    Vertex(long id){
//        this.id = id;
//    }
//
//    public long getId(){
//        return id;
//    }
//
//    public void setData(T data){
//        this.data = data;
//    }
//
//    public T getData(){
//        return data;
//    }
//
//    public void addAdjacentVertex(Edge<T> e, Vertex<T> v){
//        edges.add(e);
//        adjacentVertex.add(v);
//    }
//
//    public String toString(){
//        return String.valueOf(id);
//    }
//
//    public List<Vertex<T>> getAdjacentVertexes(){
//        return adjacentVertex;
//    }
//
//    public List<Edge<T>> getEdges(){
//        return edges;
//    }
//
//    public int getDegree(){
//        return edges.size();
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + (int) (id ^ (id >>> 32));
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Vertex other = (Vertex) obj;
//        if (id != other.id)
//            return false;
//        return true;
//    }
//}
//
//class Edge<T>{
//    private Vertex<T> vertex1;
//    private Vertex<T> vertex2;
//
//    Edge(Vertex<T> vertex1, Vertex<T> vertex2){
//        this.vertex1 = vertex1;
//        this.vertex2 = vertex2;
//    }
//
//
//
//    Vertex<T> getVertex1(){
//        return vertex1;
//    }
//
//    Vertex<T> getVertex2(){
//        return vertex2;
//    }
//
//
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
//        result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        Edge other = (Edge) obj;
//        if (vertex1 == null) {
//            if (other.vertex1 != null)
//                return false;
//        } else if (!vertex1.equals(other.vertex1))
//            return false;
//        if (vertex2 == null) {
//            if (other.vertex2 != null)
//                return false;
//        } else if (!vertex2.equals(other.vertex2))
//            return false;
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Edge [, vertex1=" + vertex1
//                + ", vertex2=" + vertex2 +  "]";
//    }
//}
//
