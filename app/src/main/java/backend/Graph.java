package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;


class Graph {

    /* private data member*/
    private List<Vetrex> graph;

    /*constructors */
    public Graph() {
        graph = new ArrayList<Vetrex>();

    }
    public Graph(Graph g) {
        graph = new ArrayList<>(g.getGraph());
    }
    /* get graph*/
    public List<Vetrex> getGraph(){
        return graph;
    }

    public void add_vetrex(Vetrex v) {
        if (!this.contains_vetrex(v))
            graph.add(v);
    }
    /* check if the graph contain this vetrex*/
    public boolean contains_vetrex(Vetrex v) {
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).equals(v))
                return true;
        }
        return false;
    }

    /* return the vertex index at the graph, -1 if the vertex is not exists*/
    public int get_vetrex_index(Vetrex v){
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i).equals(v))
                return i;
        }
        return -1;
    }
    /* get the shift that the worker is regeister to, the worker, and the shift that the worker wants,
    and create 2 vetrex:
    shift_reg---> worker
    worker---> shift_wanted
     */
    public void add_edge(Vetrex shift_reg, Vetrex worker, Vetrex shift_wanted){
        add_vetrex(shift_reg);
        add_vetrex(worker);
        add_vetrex(shift_wanted);


       int worker_index=get_vetrex_index(worker);
       int shift_reg_index=get_vetrex_index(shift_reg);

       graph.get(shift_reg_index).addEdge(worker);
       graph.get(worker_index).addEdge(shift_wanted);
    }

    /* gets two vertex and remove the edge between them (if exists) */
    public void remove_edge(Vetrex v, Vetrex u){
        int v_index=get_vetrex_index(v);
        int u_index=get_vetrex_index(u);
        graph.get(v_index).remove_edge(u);
    }
    /*print the graph to the console*/
    public void printGraph(){
        for (Vetrex v: graph){
            System.out.print(v.getId()+" : ");
            for (Vetrex u: v.getNeg())
                System.out.println(u.getId()+" , ");
            System.out.println();
        }
    }
}
class Vetrex{

    /* private data members*/
    private String id;
    private boolean is_user;
    private LinkedList<Vetrex> neg;

    /*constructors */
    public Vetrex(boolean is_user, String id){
        neg= new LinkedList<>();
        this.is_user=is_user;
        this.id=id;
    }

    public Vetrex(Vetrex v){
        neg= new LinkedList<>(v.neg);
        this.is_user=v.is_user;
        this.id=id;
    }

    /* Getters & Setters */
    public String getId() {
        return id;
    }

    public boolean isIs_user() {
        return is_user;
    }

    public  LinkedList<Vetrex> getNeg(){
        return neg;
    }

    /*add vertex v to this neighborhoods list (create edge)*/
    public void addEdge(Vetrex v){
        if (!contains_edge(v))
            this.neg.add(v);
    }
    /*check if there edge from this vertex to v*/
    public boolean contains_edge(Vetrex v){
        for (int i=0; i<this.neg.size(); i++)
            if (neg.get(i).equals(v))
                return true;
        return false;
    }
    /*remove vertex v to this neighborhoods list (remove edge)*/
    public void remove_edge(Vetrex v){
        for (int i=0; i<this.neg.size(); i++)
            if (neg.get(i).equals(v))
                neg.remove(i);
    }
    /*check if this vetrex is equal to vetrex v*/
    public boolean equals(Vetrex v){
        return  this.id.equals(v.id);
    }
}
